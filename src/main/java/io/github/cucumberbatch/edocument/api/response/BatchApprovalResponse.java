package io.github.cucumberbatch.edocument.api.response;

import lombok.Data;

import java.util.List;

@Data
public class BatchApprovalResponse {
    private final List<ApprovalResult> results;
}
