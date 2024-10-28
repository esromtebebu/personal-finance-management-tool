package project.group8.oeconomus.repository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import project.group8.oeconomus.model.Transaction;

@RepositoryRestResource(collectionResourceRel = "transactions", path = "transactions")
public interface TransactionRepository extends MongoRepository<Transaction, String> {
    
}
