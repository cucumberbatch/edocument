package io.github.cucumberbatch.edocument.service;

import io.github.cucumberbatch.edocument.api.*;
import io.github.cucumberbatch.edocument.api.request.*;
import io.github.cucumberbatch.edocument.api.response.*;
import io.github.cucumberbatch.edocument.exception.DocumentNotFoundException;
import io.github.cucumberbatch.edocument.model.*;
import io.github.cucumberbatch.edocument.repository.ApprovalRegistryRepository;
import io.github.cucumberbatch.edocument.repository.DocumentHistoryRepository;
import io.github.cucumberbatch.edocument.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {
    private final ExecutorService executor = Executors.newWorkStealingPool();

    private final DocumentRepository documentRepository;
    private final DocumentHistoryRepository documentHistoryRepository;
    private final ApprovalRegistryRepository approvalRegistryRepository;


    @Override
    public CreateDocumentResponse createDocument(CreateDocumentRequest request) {
        Document document = new Document();
        document.setName(request.getName());
        document.setAuthor(request.getAuthor());
        document.setNumber(UUID.randomUUID());
        document.setStatus(DocumentStatus.DRAFT);
        document = documentRepository.save(document);

        return new CreateDocumentResponse(
                document.getId(),
                document.getNumber(),
                document.getName(),
                document.getAuthor(),
                document.getCreateDate()
        );
    }

    @Override
    public BatchSubmissionResponse batchSubmitDocuments(BatchSubmissionRequest request) {
        List<Future<SubmissionResult>> transactionResults = request.getDocumentIds().stream()
                .map(documentId -> new SubmissionRequest(documentId, request.getInitiator(), request.getComment()))
                .map(submissionRequest -> executor.submit(() -> submitSingleDocument(submissionRequest)))
                .toList();

        return new BatchSubmissionResponse(transactionResults.stream()
                .map(future -> {
                    SubmissionResult result;
                    try {
                        result = future.get();
                    } catch (InterruptedException | ExecutionException e) {
                        throw new RuntimeException(e);
                    }
                    return result;
                })
                .toList());
    }

    @Override
    public BatchApprovalResponse batchApproveDocuments(BatchApprovalRequest request) {
        //todo
        return new BatchApprovalResponse(Collections.emptyList());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public SubmissionResult submitSingleDocument(SubmissionRequest request) {
        SubmissionStatus status;
        try {
            Document document = documentRepository.findById(request.getDocumentId())
                    .orElseThrow(() -> new DocumentNotFoundException(request.getDocumentId()));

            document.setStatus(DocumentStatus.SUBMITTED);
            document.setUpdateDate(ZonedDateTime.now());
            documentRepository.save(document);

            DocumentHistory documentHistory = new DocumentHistory();
            documentHistory.setDocument(document);
            documentHistory.setKind(ActionKind.SUBMIT);
            documentHistory.setInitiator(request.getInitiator());
            documentHistory.setComment(request.getComment());
            documentHistoryRepository.save(documentHistory);

            status = SubmissionStatus.SUCCESS;

        } catch (DocumentNotFoundException e) {
            status = SubmissionStatus.NOT_FOUND;

        } catch (OptimisticLockingFailureException e) {
            status = SubmissionStatus.CONFLICT;

        }

        return new SubmissionResult(request.getDocumentId(), status);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ApprovalResult approveSingleDocument(ApprovalRequest request) {
        ApprovalStatus status;
        try {
            Document document = documentRepository.findById(request.getDocumentId())
                    .orElseThrow(() -> new DocumentNotFoundException(request.getDocumentId()));

            document.setStatus(DocumentStatus.APPROVED);
            document.setUpdateDate(ZonedDateTime.now());
            documentRepository.save(document);

            DocumentHistory documentHistory = new DocumentHistory();
            documentHistory.setDocument(document);
            documentHistory.setKind(ActionKind.APPROVE);
            documentHistory.setInitiator(request.getInitiator());
            documentHistory.setComment(request.getComment());
            documentHistoryRepository.save(documentHistory);

            Approvement approvement = new Approvement();
            approvement.setDocument(document);
            approvalRegistryRepository.save(approvement);

            status = ApprovalStatus.SUCCESS;

        } catch (DocumentNotFoundException e) {
            status = ApprovalStatus.NOT_FOUND;

        } catch (OptimisticLockingFailureException e) {
            status = ApprovalStatus.CONFLICT;

        }

        return new ApprovalResult(request.getDocumentId(), status);
    }
}
