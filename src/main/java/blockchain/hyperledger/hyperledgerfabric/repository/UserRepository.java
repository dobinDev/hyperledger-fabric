package blockchain.hyperledger.hyperledgerfabric.repository;

import blockchain.hyperledger.hyperledgerfabric.entity.User;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
@Data
@Document
public interface UserRepository extends MongoRepository<User, String> {


    private String nickName;

}
