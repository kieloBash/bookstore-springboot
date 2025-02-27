package com.store.bookstore.repository;


import com.store.bookstore.model.Book;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class BooksRepositoryTests {

    @Autowired
    private BooksRepository booksRepository;

    @Test
    public void BooksRepository_FindAll_ReturnAllBooks(){

        //Arrange

        //Act
        List<Book> bookList = this.booksRepository.findAll();

        //Assert
        Assertions.assertThat(bookList).isNotEmpty();
        Assertions.assertThat(bookList).hasSizeGreaterThanOrEqualTo(10);
    }

    @Test
    public void BooksRepository_GetAllBooksCategories(){

        //Arrange

        //Act
        List<Book> bookList = this.booksRepository.findAll();
        Set<String> uniqueCategories = bookList.stream()
                .map(Book::getCategory)  // Extract categories
                .collect(Collectors.toSet());


        //Assert
        Assertions.assertThat(bookList).isNotEmpty();
        Assertions.assertThat(uniqueCategories).isNotEmpty();
        Assertions.assertThat(uniqueCategories).hasSizeGreaterThanOrEqualTo(1);
    }


}
