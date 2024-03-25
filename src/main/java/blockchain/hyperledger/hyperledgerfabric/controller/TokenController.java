package blockchain.hyperledger.hyperledgerfabric.controller;

import blockchain.hyperledger.hyperledgerfabric.dto.TokenDTO;
import blockchain.hyperledger.hyperledgerfabric.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class TokenController {

    private final TokenService tokenService;

    // 컨텐츠 판매 티켓 NFT 토큰 발행
    @PostMapping("/mintToken")
    public String mintToken(@RequestBody TokenDTO tokenDTO) {
        String tokenId = tokenDTO.getTokenId();
        int categoryCode = tokenDTO.getCategoryCode();
        int pollingResultId = tokenDTO.getPollingResultId();
        String tokenType = tokenDTO.getTokenType();
        int totalTicket = tokenDTO.getTotalTicket();
        int amount = tokenDTO.getAmount();

        return tokenService.mintToken(tokenId, categoryCode, pollingResultId, tokenType, totalTicket, amount);
    }

    // 토큰 ID 조회
    @GetMapping("/token/{tokenId}")
    public String getToken(@PathVariable String tokenId) {
        return tokenService.getToken(tokenId);
    }

    // 토큰 전체 조회
    @GetMapping("/tokens")
    public String getAllTokens() {
        return tokenService.getAllTokens();
    }
}
