package com.micronautdataasyncsessionclosedproblem;

import io.micronaut.context.event.ApplicationEventPublisher;
import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.concurrent.Future;

@Singleton
@Transactional
public class BookService {

  private final BookRepository bookRepository;
  private final ApplicationEventPublisher<NewBookCreatedEvent>
      currentAppEventApplicationEventPublisher;

  public BookService(
      BookRepository bookRepository,
      ApplicationEventPublisher<NewBookCreatedEvent> currentAppEventApplicationEventPublisher) {
    this.bookRepository = bookRepository;
    this.currentAppEventApplicationEventPublisher = currentAppEventApplicationEventPublisher;
  }

  public Result create(Book newBook) {
    Book savedBook = bookRepository.save(newBook);
    Future<Void> future = this.currentAppEventApplicationEventPublisher.publishEventAsync(new NewBookCreatedEvent(savedBook.getId()));
    return new Result(savedBook, future);
  }

  public List<Book> getAll() {
    return bookRepository.findAll();
  }
}
