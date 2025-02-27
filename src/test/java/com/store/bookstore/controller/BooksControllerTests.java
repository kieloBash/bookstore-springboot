package com.store.bookstore.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.store.bookstore.dto.BookDTO;
import com.store.bookstore.service.BooksService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

@WebMvcTest(BooksController.class)
public class BooksControllerTests {

    @Mock
    private BooksService booksService;

    @InjectMocks
    private BooksController booksController;

    private MockMvc mockMvc;

    private BookDTO bookDTO;

    private final String BASE_URL = "/api/v1/books";

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(booksController).build();

        // Create a mock BookDTO for testing
        bookDTO = new BookDTO(1, "Book 1", "Description 1", "Author 1", "Category 1", 10.0);
    }

    @Test
    public void testGetAllBooks()throws Exception{

        //Arrange
        List<BookDTO> mockedBooks = Arrays.asList(bookDTO);
        when(booksService.getAllBooks(null,null)).thenReturn(mockedBooks);

        //Act
        mockMvc.perform(get(BASE_URL)
                        .param("search", "")
                        .param("category", ""))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Book 1"))
                .andExpect(jsonPath("$[0].description").value("Description 1"));

        //Assert

        //Verify
        verify(booksService,times(1)).getAllBooks(null,null);
    }

}
