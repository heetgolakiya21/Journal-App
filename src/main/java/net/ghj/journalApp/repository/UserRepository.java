package net.ghj.journalApp.repository;

import net.ghj.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ObjectId> {
//    Extra methods for DB related operations like Custom Query Methods, Custom Finder Methods etc.

    //    You do not need to write query or logic. because SpringDataMongoDB automatically create query for this.
    User findByUserName(String userName);

    void deleteByUserName(String userName);
}
