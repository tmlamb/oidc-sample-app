package oidc.server.security;

import java.util.Collection;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

/**
 * Allows the server to add custom authorities to the user's authorization context.
 * Useful if the default behavior that converts the JWT's claim/groups needs to be extended.
 */
public class ServerJwtGrantedAuthoritiesConverter implements
    Converter<Jwt, Collection<GrantedAuthority>> {

  // This is Spring Securities default JWT based converter, which takes the claims from the token
  // and converts them to a SimpleGrantedAuthority.
  static final JwtGrantedAuthoritiesConverter DEFAULT_CONVERTER =
      new JwtGrantedAuthoritiesConverter();

  @Override
  public Collection<GrantedAuthority> convert(Jwt source) {
    // Start with the default authorities taken from the JWT claims.
    Collection<GrantedAuthority> result = DEFAULT_CONVERTER.convert(source);

    // Add custom authorities. This is a simple example, but could be enhanced to pull these from
    // another datasource.
    result.add(new SimpleGrantedAuthority("Everyone"));
    result.add(new SimpleGrantedAuthority("CustomServer"));

    return result;
  }
}
