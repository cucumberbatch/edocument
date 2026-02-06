package io.github.cucumberbatch.edocument.api.response;

import io.github.cucumberbatch.edocument.api.ApprovalStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApprovalResult {
    private final Long id;
    private final ApprovalStatus status;
}
