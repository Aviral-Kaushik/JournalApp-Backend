package com.aviral.journalApp.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "journal")
@Data
@NoArgsConstructor
public class Journal {

    /// Data Annotation comes library lombok that generated Getter, Setter, All-args Constructor at Compile Time, so we don't
    /// have to create them. Hence, it reduces boilerplate code.

    @Id
    private String id;

    @NonNull
    private String title;

    private String content;

    private LocalDateTime date;
}
