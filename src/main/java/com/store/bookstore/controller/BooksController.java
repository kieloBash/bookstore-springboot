package com.store.bookstore.controller;

import com.store.bookstore.dto.BookDTO;
import com.store.bookstore.service.BooksService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
public class BooksController {

    private final BooksService booksService;

    public BooksController(BooksService booksService){
        this.booksService = booksService;
    }

    @GetMapping
    public ResponseEntity<List<BookDTO>> getAllBooks(
            @RequestParam(name = "search", required = false, defaultValue = "") String search,
            @RequestParam(name = "category", required = false, defaultValue = "") String category){
        return ResponseEntity.ok(booksService.getAllBooks(search,category));
    }

    @GetMapping("/categories")
    public ResponseEntity<List<String>> getAllBookCategories(){
        return ResponseEntity.ok(booksService.getAllBookCategories());
    }
}
