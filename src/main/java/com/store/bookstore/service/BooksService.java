package com.store.bookstore.service;

import com.store.bookstore.dto.BookDTO;
import com.store.bookstore.model.Book;
import com.store.bookstore.repository.BooksRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BooksService {
    private final BooksRepository booksRepository;

    public BooksService(BooksRepository booksRepository){
        this.booksRepository = booksRepository;
    }

    public List<String> getAllBookCategories() {
        List<Book> booksList = this.booksRepository.findAll();

        // Use a Set to get unique categories
        Set<String> uniqueCategories = booksList.stream()
                .map(Book::getCategory)  // Extract categories
                .collect(Collectors.toSet()); // Collect into a Set

        // Convert Set back to a List if you prefer returning a List
        return new ArrayList<>(uniqueCategories);
    }

    public List<BookDTO> getAllBooks(String filterName, String filterCategory){
        List<Book> booksList = this.booksRepository.findAll();

        if(filterName != null && !filterName.trim().isEmpty()){
            booksList = booksList.stream()
                    .filter(book-> book.getName().equalsIgnoreCase(filterName))
                    .toList();
        }

        if(filterCategory != null && !filterCategory.trim().isEmpty()){
            booksList = booksList.stream()
                    .filter(book-> book.getCategory().equalsIgnoreCase(filterCategory))
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
