package io.github.cucumberbatch.edocument.api.response;

import io.github.cucumberbatch.edocument.api.SubmissionStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SubmissionResult {
    private final Long id;
    private final SubmissionStatus status;
}
