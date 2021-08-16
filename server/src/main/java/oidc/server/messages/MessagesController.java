package oidc.server.messages;

import java.util.stream.Collectors;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessagesController {

  @PreAuthorize("hasAuthority('CustomServer')")
  @GetMapping("/api/messages")
  public String messages(@AuthenticationPrincipal Jwt jwt) {

    return String.format(
        "Hello from the resource server, %s! You're token is %s, which decodes to: %s",
        jwt.getSubject(), jwt.getTokenValue(),
        jwt.getClaims().keySet().stream().map(key -> key + "=" + jwt.getClaims().get(key))
            .collect(Collectors.joining(", ", "{", "}")));

  }

}