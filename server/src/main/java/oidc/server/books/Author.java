package oidc.server.books;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Author {
  private String id;
  private String firstName;
  private String lastName;
}
