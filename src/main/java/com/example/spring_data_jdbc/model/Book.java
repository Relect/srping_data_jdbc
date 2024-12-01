package com.example.spring_data_jdbc.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    @NonNull
    private long id;
    private String title;
    private String author;
    private int publicationYear;
}
