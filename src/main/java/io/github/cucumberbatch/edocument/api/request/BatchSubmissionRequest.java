package io.github.cucumberbatch.edocument.api.request;

import lombok.Data;

import java.util.List;

@Data
public class BatchSubmissionRequest {
    private List<Long> documentIds;
    private String initiator;
    private String comment;
}
