package blockchain.hyperledger.hyperledgerfabric.service;

import com.amazonaws.services.managedblockchain.AmazonManagedBlockchain;
import com.amazonaws.services.managedblockchain.model.GetMemberRequest;
import com.amazonaws.services.managedblockchain.model.GetMemberResult;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ManagedBlockchainService {

    private final AmazonManagedBlockchain managedBlockchainClient;

    public GetMemberResult getMember(String memberId) {
        GetMemberRequest request = new GetMemberRequest().withMemberId(memberId);
        return managedBlockchainClient.getMember(request);
    }
}
