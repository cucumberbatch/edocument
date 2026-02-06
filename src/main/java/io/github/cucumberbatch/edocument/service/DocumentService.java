package io.github.cucumberbatch.edocument.service;

import io.github.cucumberbatch.edocument.api.request.*;
import io.github.cucumberbatch.edocument.api.response.*;

public interface DocumentService {

    CreateDocumentResponse createDocument(CreateDocumentRequest request);

    SubmissionResult submitSingleDocument(SubmissionRequest request);

    ApprovalResult approveSingleDocument(ApprovalRequest request);

    BatchSubmissionResponse batchSubmitDocuments(BatchSubmissionRequest request);

    BatchApprovalResponse batchApproveDocuments(BatchApprovalRequest request);

}
