package io.github.cucumberbatch.edocument.controller;

import io.github.cucumberbatch.edocument.api.request.BatchApprovalRequest;
import io.github.cucumberbatch.edocument.api.request.CreateDocumentRequest;
import io.github.cucumberbatch.edocument.api.request.BatchSubmissionRequest;
import io.github.cucumberbatch.edocument.api.response.BatchApprovalResponse;
import io.github.cucumberbatch.edocument.api.response.BatchSubmissionResponse;
import io.github.cucumberbatch.edocument.api.response.CreateDocumentResponse;
import io.github.cucumberbatch.edocument.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/document")
@RequiredArgsConstructor
public class DocumentController {
    private final DocumentService documentService;

    @PostMapping(path = "/")
    public ResponseEntity<CreateDocumentResponse> createDocument(@RequestBody CreateDocumentRequest request) {
        return new ResponseEntity<>(documentService.createDocument(request), HttpStatus.CREATED);
    }

    //todo: Get request with pagination/sort

    @PutMapping(path = "/batch-submit")
    public ResponseEntity<BatchSubmissionResponse> submitDocuments(@RequestBody BatchSubmissionRequest request) {
        return new ResponseEntity<>(documentService.batchSubmitDocuments(request), HttpStatus.OK);
    }

    //todo: approve request
    @PutMapping(path = "/batch-approve")
    public ResponseEntity<BatchApprovalResponse> approveDocuments(@RequestBody BatchApprovalRequest request) {
        return new ResponseEntity<>(documentService.batchApproveDocuments(request), HttpStatus.OK);
    }

}
