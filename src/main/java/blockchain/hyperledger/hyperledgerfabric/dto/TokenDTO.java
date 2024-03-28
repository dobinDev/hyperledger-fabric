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
public class TokenDTO {

    @NotBlank
    private String tokenId;

    @NotBlank
    private int categoryCode;

    @NotBlank
    private int pollingResultId;

    @NotBlank
    private String tokenType;

    @NotBlank
    private String sellStage;

    @NotBlank
    private LocalDateTime tokenCreatedTime;
}
