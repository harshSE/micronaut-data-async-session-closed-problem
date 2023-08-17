package com.micronautdataasyncsessionclosedproblem;


import io.micronaut.runtime.event.annotation.EventListener;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

@Singleton
public class BookEventListener {

  private static final Logger LOG = LoggerFactory.getLogger(BookEventListener.class);

  private final BookHistoryRepository bookHistoryRepository;

  public BookEventListener(BookHistoryRepository bookHistoryRepository) {
    this.bookHistoryRepository = bookHistoryRepository;
  }

  @EventListener
  public void exceptionOnEvent(NewBookCreatedEvent newBookCreatedEvent) {

    try {
      LOG.info("Saving into history: " + newBookCreatedEvent.bookId());
      Thread.sleep(TimeUnit.SECONDS.toMillis(10));
      bookHistoryRepository.save(new BookHistory(newBookCreatedEvent.bookId()));
      LOG.info("Saved into history: " + newBookCreatedEvent.bookId());
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
    }
  }

}
