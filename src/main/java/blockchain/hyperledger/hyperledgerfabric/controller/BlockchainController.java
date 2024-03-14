package blockchain.hyperledger.hyperledgerfabric.controller;

import blockchain.hyperledger.hyperledgerfabric.dto.TokenDTO;
import blockchain.hyperledger.hyperledgerfabric.service.ManagedBlockchainService;
import com.amazonaws.services.managedblockchain.model.GetMemberResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class BlockchainController {

    private final ManagedBlockchainService blockchainService;

    @GetMapping("/member/{memberId}")
    public GetMemberResult getMember(@PathVariable String memberId) {
        return blockchainService.getMember(memberId);
    }

    // 토큰 발행
    @PostMapping("/mintToken")
    public String mintToken(
            @PathVariable String tokenId,
            @PathVariable int categoryCode,
            @PathVariable int pollingResultId,
            @PathVariable String tokenType,
            @PathVariable int totalTicket,
            @PathVariable int amount,
            @PathVariable String owner
    ) {
        return blockchainService.mintToken(
                tokenId,
                categoryCode,
                pollingResultId,
                tokenType,
                totalTicket,
                amount,
                owner
        );
    }

    // 토큰 ID 조회
    @GetMapping("/token/{tokenId}")
    public String getToken(@PathVariable String tokenId) {
        return blockchainService.getToken(tokenId);
    }

    // 토큰 전체 조회
    @GetMapping("/tokens")
    public String getAllTokens() {
        return blockchainService.getAllTokens();
    }
}