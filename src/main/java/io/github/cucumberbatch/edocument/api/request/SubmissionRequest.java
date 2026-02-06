package io.github.cucumberbatch.edocument.api.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SubmissionRequest {
    private Long documentId;
    private String initiator;
    private String comment;
}
