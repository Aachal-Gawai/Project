package com.library.book.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookUpdateDetails {

    private String title;

    private String author;

    private String publicationBy;

    @NotEmpty(message = "The update date should not be null or empty")
    private String updateDate;
}
