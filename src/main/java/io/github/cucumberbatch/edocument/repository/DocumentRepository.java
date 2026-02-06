package io.github.cucumberbatch.edocument.repository;

import io.github.cucumberbatch.edocument.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
}