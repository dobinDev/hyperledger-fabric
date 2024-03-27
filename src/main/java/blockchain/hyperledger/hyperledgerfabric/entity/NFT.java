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
@Document("nft")
public class NFT {

    @Id
    private String tokenId;

    @Field
    private int categoryCode;

    @Field
    private int pollingResultId;

    @Field
    private String tokenType;

    @Field
    private int totalTicket;

    @Field
    private int amount;

    @Field
    @JsonFormat(pattern = "yyyy.MM.dd/HH:mm/E")
    private LocalDateTime tokenCreatedTime;
}
