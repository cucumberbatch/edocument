package io.github.cucumberbatch.edocument.api.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class BatchSubmissionResponse {
    private List<SubmissionResult> results;
}
