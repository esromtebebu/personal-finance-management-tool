package project.group8.oeconomus.repository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.data.mongodb.repository.DeleteQuery;

import project.group8.oeconomus.model.User;

public interface UserRepository extends MongoRepository<User, String> {
    @DeleteQuery("{ 'userID': ?0 }")
    void deleteExpense(String userID, String transactionID);
}
