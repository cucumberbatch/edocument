package io.github.cucumberbatch.edocument.api.request;

import lombok.Data;

@Data
public class CreateDocumentRequest {
    private String name;
    private String author;
}
