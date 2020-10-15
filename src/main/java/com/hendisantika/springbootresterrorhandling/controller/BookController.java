package com.hendisantika.springbootresterrorhandling.controller;

import com.hendisantika.springbootresterrorhandling.entity.Book;
import com.hendisantika.springbootresterrorhandling.error.BookNotFoundException;
import com.hendisantika.springbootresterrorhandling.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Project : springboot-rest-error-handling
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 15/10/20
 * Time: 17.41
 */
@RestController
@Validated
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    // Find
    @GetMapping("/books")
    List<Book> findAll() {
        return bookRepository.findAll();
    }

    // Save
    @PostMapping("/books")
    Book newBook(@Valid @RequestBody Book newBook) {
        return bookRepository.save(newBook);
    }

    // Find
    @GetMapping("/books/{id}")
    Book findOne(@PathVariable @Min(1) Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    // Save or update
    @PutMapping("/books/{id}")
    Book saveOrUpdate(@RequestBody Book newBook, @PathVariable Long id) {
        return bookRepository.findById(id)
                .map(x -> {
                    x.setName(newBook.getName());
                    x.setAuthor(newBook.getAuthor());
                    x.setPrice(newBook.getPrice());
                    return bookRepository.save(x);
                })
                .orElseGet(() -> {
                    newBook.setId(id);
                    return bookRepository.save(newBook);
                });
    }
}
