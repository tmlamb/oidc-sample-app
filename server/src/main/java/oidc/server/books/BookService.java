package oidc.server.books;

import org.springframework.stereotype.Component;

@Component
public class BookService {

  public Book getBookById(String bookId) {
    return Book.builder().id("book-1").name("Harry Potter and the Philosopher's Stone")
        .pageCount(223)
        .author(Author.builder().id("author-1").firstName("Joanne").lastName("Rowling").build())
        .build();
  }
}
