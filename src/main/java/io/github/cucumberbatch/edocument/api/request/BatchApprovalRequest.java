package io.github.cucumberbatch.edocument.api.request;

import lombok.Data;

import java.util.List;

@Data
public class BatchApprovalRequest {
    private List<Long> ids;
}
