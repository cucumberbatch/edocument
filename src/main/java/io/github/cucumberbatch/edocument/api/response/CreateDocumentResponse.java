package io.github.cucumberbatch.edocument.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class CreateDocumentResponse {
    private final Long id;
    private final UUID number;
    private final String name;
    private final String author;
    private final ZonedDateTime createdDate;
}
