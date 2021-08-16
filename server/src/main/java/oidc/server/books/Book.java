package oidc.server.books;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Book {
  private String id;
  private String name;
  private int pageCount;
  private Author author;
}
