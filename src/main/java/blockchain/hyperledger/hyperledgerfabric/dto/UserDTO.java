package blockchain.hyperledger.hyperledgerfabric.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDTO {

    private String nickName;
    private int mymPoint;
    private String[] ownedToken;
    private LocalDateTime blockCreatedTime;

    public UserDTO() {
    }

    public UserDTO(String nickName, int mymPoint, String[] ownedToken, LocalDateTime blockCreatedTime) {
        this.nickName = nickName;
        this.mymPoint = mymPoint;
        this.ownedToken = ownedToken;
        this.blockCreatedTime = blockCreatedTime;
    }
}
