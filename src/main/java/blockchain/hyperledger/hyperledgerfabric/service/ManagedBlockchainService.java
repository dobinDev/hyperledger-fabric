package blockchain.hyperledger.hyperledgerfabric.service;

import blockchain.hyperledger.hyperledgerfabric.dto.TokenDTO;
import com.amazonaws.services.managedblockchain.AmazonManagedBlockchain;
import com.amazonaws.services.managedblockchain.model.GetMemberRequest;
import com.amazonaws.services.managedblockchain.model.GetMemberResult;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ManagedBlockchainService {

    private final AmazonManagedBlockchain managedBlockchainClient;
    private final String networkId = "n-QS7KRQW5AJC2PBORGBREFPH3LU";

    public GetMemberResult getMember(String memberId) {
        GetMemberRequest request = new GetMemberRequest()
                .withNetworkId(networkId)
                .withMemberId(memberId);
        return managedBlockchainClient.getMember(request);
    }

    public String getToken(String tokneId) {

        String caFilePath = "/opt/home/managedblockchain-tls-chain.pem";
        String channelID = "mychannel";
        String chaincodeName = "mycc";

        String tokenID = String.format("docker exec cli peer chaincode query \n" +
                        "--tls --cafile %s \n" +
                        "--channelID %s \n" +
                        "--name %s -c '{\"Args\":[\"GetToken\", \"%s\"]}'", caFilePath, channelID, chaincodeName, tokneId);

        return tokenID;
    }

    public String getAllTokens() {

        String caFilePath = "/opt/home/managedblockchain-tls-chain.pem";
        String channelID = "mychannel";
        String chaincodeName = "mycc";

        String tokenID = String.format("docker exec cli peer chaincode query \n" +
                        "--tls --cafile %s \n" +
                        "--channelID %s \n" +
                        "--name %s -c '{\"Args\":[\"GetAllTokens\"]}'", caFilePath, channelID, chaincodeName);

        return tokenID;
    }
}
