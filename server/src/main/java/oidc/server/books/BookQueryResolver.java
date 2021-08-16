package oidc.server.books;

import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@Component
public class BookQueryResolver implements GraphQLQueryResolver {

  private final BookService bookService;

  public BookQueryResolver(BookService bookService) {
    this.bookService = bookService;
  }

  @PreAuthorize("hasAuthority('CustomServer')")
  public Book bookById(String bookId) {
    return bookService.getBookById(bookId);
  }
}
