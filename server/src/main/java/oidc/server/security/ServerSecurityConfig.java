package oidc.server.security;

import static org.springframework.security.config.Customizer.withDefaults;

import java.util.Arrays;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@EnableWebSecurity
// The following annotation enables use of the "preAuthorize" annotation.
// This is used in REST and GraphQL endpoints to grant authority at the request level.
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ServerSecurityConfig extends WebSecurityConfigurerAdapter {
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .cors(withDefaults())
        .authorizeRequests()
        // Set a global policy for GraphQL and Actuator endpoints. These can be overridden at the
        // method level with the preAuthorize annotation.
        .antMatchers("/actuator/**").hasAuthority("Everyone")
        .antMatchers("/graphql").hasAuthority("Everyone")
        // All requests will be authenticated (JWT access token validated).
        .anyRequest().authenticated()
        .and()
        // Configures the OAuth 2 Resource Server filter, with a customized converter for extracting
        // user's authorities from JWT tokens.
        .oauth2ResourceServer().jwt().jwtAuthenticationConverter(jwtAuthenticationConverter());
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(List.of("http://localhost:3000"));
    configuration.setAllowedMethods(Arrays.asList("GET", "POST"));
    configuration.setAllowedHeaders(List.of("authorization", "content-type"));
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

  /**
   * This configuration disables the default Okta Spring Boot Starter library behavior, which
   * converts the 'groups' claim in the JWT to the user's set of Authorities. This is an unnecessary
   * feature if Okta's built-in Groups based authorization is needed, and can be removed if so.
   *
   * @return A converter that add authorities customized by the server application to a user's
   * authentication principle.
   */
  private JwtAuthenticationConverter jwtAuthenticationConverter() {
    ServerJwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter =
        new ServerJwtGrantedAuthoritiesConverter();
    JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
    jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
    return jwtAuthenticationConverter;
  }
}