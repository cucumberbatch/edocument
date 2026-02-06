package io.github.cucumberbatch.edocument.api.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApprovalRequest {
    private Long documentId;
    private String initiator;
    private String comment;
}
