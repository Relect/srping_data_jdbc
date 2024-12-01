package com.example.spring_data_jdbc.model;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    @Min(1900)
    private long id;
    private String title;
    private String author;
    private int publicationYear;
}
