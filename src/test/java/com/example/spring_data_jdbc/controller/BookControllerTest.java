package com.example.spring_data_jdbc.controller;

import com.example.spring_data_jdbc.model.Book;
import com.example.spring_data_jdbc.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mvc;
    @Mock
    private BookService service;
    @InjectMocks
    private BookController bookController;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
    }
    @Test
    public void getBookByIdTest() throws Exception {
        Book book = new Book(1, "java", "java-author", 2020);
        Mockito.when(service.getBook(Mockito.any(Long.class))).thenReturn(Optional.of(book));

        mockMvc.perform(MockMvcRequestBuilders.get("/book/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("java"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value("java-author"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.publicationYear").value(2020));

        Mockito.verify(service, Mockito.times(1)).getBook(1);
    }

    @Test
    public void getBooksTest() throws Exception {
        Book book = new Book(1, "java", "java-author", 2020);
        Mockito.when(service.getAll()).thenReturn(List.of(book));

        mockMvc.perform(MockMvcRequestBuilders.get("/books"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("java"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].author").value("java-author"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].publicationYear").value(2020));

        Mockito.verify(service, Mockito.times(1)).getAll();
    }

    @Test
    public void updateBookTest() throws Exception {
        Book book = new Book();
        book.setTitle("java");
        book.setAuthor("java-author");
        book.setPublicationYear(2020);

        mockMvc.perform(MockMvcRequestBuilders.put("/book")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(book)))
            .andExpect(MockMvcResultMatchers.status().is4xxClientError());

    }
}
