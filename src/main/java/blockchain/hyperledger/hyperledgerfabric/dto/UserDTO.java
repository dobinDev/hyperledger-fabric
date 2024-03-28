package blockchain.hyperledger.hyperledgerfabric.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    @NotBlank
    private String nickName;

    @NotBlank
    private int mymPoint;

    @NotBlank
    private String[] ownedToken;

    @NotBlank
    private LocalDateTime blockCreatedTime;
}
