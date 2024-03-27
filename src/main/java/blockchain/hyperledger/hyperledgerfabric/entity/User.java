package blockchain.hyperledger.hyperledgerfabric.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document("user")
public class User {

    @Id
    private String nickName;

    @Field
    private int mymPoint;

    @Field
    private String[] ownedToken;

    @Field
    @JsonFormat(pattern = "yyyy.MM.dd/HH:mm/E")
    private LocalDateTime blockCreatedTime;
}
