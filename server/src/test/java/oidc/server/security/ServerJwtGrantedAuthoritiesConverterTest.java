package oidc.server.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.util.Assert.notEmpty;

import java.util.Collection;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

class ServerJwtGrantedAuthoritiesConverterTest {

  @Test
  void convert_jwtWithOneScope_hasDefaultAndCustomAuthorities() {
    ServerJwtGrantedAuthoritiesConverter converter = new ServerJwtGrantedAuthoritiesConverter();
    Jwt jwt = Jwt.withTokenValue("token")
        .header("alg", "none")
        .claim("scope", "profile")
        .build();

    Collection<GrantedAuthority> authorities = converter.convert(jwt);

    notEmpty(authorities, "authorities must not be empty.");
    assertEquals(3, authorities.size());
    assertTrue(authorities.contains(new SimpleGrantedAuthority("Everyone")) &&
        authorities.contains(new SimpleGrantedAuthority("CustomServer")) &&
        authorities.contains(new SimpleGrantedAuthority("SCOPE_profile")));

  }

  @Test
  void convert_jwtWithNoScope_hasCustomAuthorities() {
    ServerJwtGrantedAuthoritiesConverter converter = new ServerJwtGrantedAuthoritiesConverter();
    Jwt jwt = Jwt.withTokenValue("token")
        .header("alg", "none")
        .claim("scope", null)
        .build();

    Collection<GrantedAuthority> authorities = converter.convert(jwt);

    notEmpty(authorities, "authorities must not be empty.");
    assertEquals(2, authorities.size());
    assertTrue(authorities.contains(new SimpleGrantedAuthority("Everyone")) &&
        authorities.contains(new SimpleGrantedAuthority("CustomServer")));

  }
}