package com.example.spring_data_jdbc.service;

import com.example.spring_data_jdbc.model.Book;
import com.example.spring_data_jdbc.repository.CrudRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {
    private final CrudRepository<Book, Long> crudRepository;

    public Book createBook(Book book) {
        return crudRepository.save(book);
    }

    public Optional<Book> getBook(long id) {
        return crudRepository.findById(id);
    }

    public List<Book> getAll() {
        return crudRepository.findAll();
    }

    public void updateBook(Book book) {
        crudRepository.update(book);
    }

    public void deleteBook(long id) {
        crudRepository.deleteById(id);
    }
}
