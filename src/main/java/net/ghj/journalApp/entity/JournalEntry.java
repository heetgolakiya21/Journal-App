package net.ghj.journalApp.entity;

import jakarta.annotation.Nonnull;
import net.ghj.journalApp.enums.Sentiment;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "journal-entries")
@Getter // It creates getter and setter automatically.
@Setter // You can also write @Data.
public class JournalEntry {
//    POJO(Plain Old Java Object) or Collection of data or model class

    @Id
    private ObjectId id;

    @Nonnull
    private String title;

    private String content;

    private LocalDate date;

    private Sentiment sentiment;
}
