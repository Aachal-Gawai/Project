package com.library.book.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookDetails {

    private Long id;


    private String userId;

    @NotNull
    private String title;

    private String author;

    private String publicationBy;

    @NotEmpty(message = "The create date should not be null or empty")
    private String createDate;

    @NotEmpty(message = "The update date should not be null or empty")
    private String updateDate;
}
