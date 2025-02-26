package com.store.bookstore.service;

import com.store.bookstore.dto.BookDTO;
import com.store.bookstore.model.Book;
import com.store.bookstore.repository.BooksRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BooksService {
    private final BooksRepository booksRepository;

    public BooksService(BooksRepository booksRepository){
        this.booksRepository = booksRepository;
    }

    public List<BookDTO> getAllBooks(String filterName){
        List<Book> booksList = this.booksRepository.findAll();

        if(filterName != null && !filterName.trim().isEmpty()){
            booksList = booksList.stream()
                    .filter(book-> book.getName().equalsIgnoreCase(filterName))
                    .toList();
        }

        return booksList.stream()
                .map(book -> new BookDTO(
                        book.getId(),
                        book.getName(),
                        book.getDescription(),
                        book.getAuthor(),
                        book.getCategory(),
                        book.getPrice()
                )).collect(Collectors.toList());
    }
}
