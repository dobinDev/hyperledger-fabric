package blockchain.hyperledger.hyperledgerfabric.controller;

import blockchain.hyperledger.hyperledgerfabric.service.ManagedBlockchainService;
import com.amazonaws.services.managedblockchain.model.GetMemberResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BlockchainController {

    private final ManagedBlockchainService blockchainService;

    @GetMapping("/member")
    public GetMemberResult getMember(@PathVariable String memberId) {
        return blockchainService.getMember(memberId);
    }
}
