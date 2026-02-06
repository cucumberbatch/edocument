package io.github.cucumberbatch.edocument.repository;

import io.github.cucumberbatch.edocument.model.Approvement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApprovalRegistryRepository extends JpaRepository<Approvement, Long> {
}
