package blockchain.hyperledger.hyperledgerfabric.dto;

import lombok.Data;

@Data
public class TokenDTO {

    private String tokenId;
    private int categoryCode;
    private int pollingResultId;
    private String tokenType;
    private int totalTicket;
    private int amount;
    private String owner;

    public TokenDTO() {
    }

    public TokenDTO(String tokenId, int categoryCode, int pollingResultId, String tokenType,
                    int totalTicket, int amount, String owner) {
        this.tokenId = tokenId;
        this.categoryCode = categoryCode;
        this.pollingResultId = pollingResultId;
        this.tokenType = tokenType;
        this.totalTicket = totalTicket;
        this.amount = amount;
        this.owner = owner;
    }
}
