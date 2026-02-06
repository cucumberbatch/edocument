package io.github.cucumberbatch.edocument.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.ZonedDateTime;

@Getter
@Setter
@Entity
@Table(name = "document_history")
public class DocumentHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "document_id")
    private Document document;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "action_kind")
    private ActionKind kind;

    @CreationTimestamp
    @Column(name = "created_date")
    private ZonedDateTime createdDate;

    @Column(name = "initiator", length = 128)
    private String initiator;

    @Column(name = "comment", length = 256)
    private String comment;
}
