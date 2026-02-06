package io.github.cucumberbatch.edocument.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DocumentNotFoundException extends RuntimeException {
    private final Long documentId;
}
