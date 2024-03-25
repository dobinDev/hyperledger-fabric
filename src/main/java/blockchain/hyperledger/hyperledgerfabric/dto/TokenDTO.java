package blockchain.hyperledger.hyperledgerfabric.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TokenDTO {

    private String tokenId;
    private int categoryCode;
    private int pollingResultId;
    private String tokenType;
    private int totalTicket;
    private int amount;
    private LocalDateTime tokenCreateTime;

    public TokenDTO() {
    }

    public TokenDTO(String tokenId, int categoryCode, int pollingResultId, String tokenType,
                    int totalTicket, int amount, LocalDateTime tokenCreateTime) {
        this.tokenId = tokenId;
        this.categoryCode = categoryCode;
        this.pollingResultId = pollingResultId;
        this.tokenType = tokenType;
        this.totalTicket = totalTicket;
        this.amount = amount;
        this.tokenCreateTime = tokenCreateTime;
    }
}
