package blockchain.hyperledger.hyperledgerfabric.service;

import blockchain.hyperledger.hyperledgerfabric.dto.TokenDTO;
import com.amazonaws.services.managedblockchain.AmazonManagedBlockchain;
import com.amazonaws.services.managedblockchain.model.GetMemberRequest;
import com.amazonaws.services.managedblockchain.model.GetMemberResult;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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

    /*public String getToken(String tokneId) {

        try {
            // 실행할 명령어를 배열로 정의합니다.
            String[] command = {};

            // ProcessBuilder를 사용하여 외부 명령어를 실행합니다.
            ProcessBuilder processBuilder = new ProcessBuilder(command);

            // 프로세스를 시작하고 실행 결과를 받습니다.
            Process process = processBuilder.start();

            // 실행 결과를 읽어오기 위한 BufferedReader를 생성합니다.
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            // 실행 결과를 읽어와 출력합니다.
            String line;

            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            // 프로세스가 종료될 때까지 기다립니다.
            process.waitFor();

            // 프로세스의 종료 코드를 확인합니다.
            int exitCode = process.exitValue();
            System.out.println("프로세스 종료 코드 : " + exitCode);

            // 리소스를 해제합니다.
            reader.close();
            process.destroy();
        } catch (IOException | InterruptedException exception) {
            exception.printStackTrace();
        }

        String caFilePath = "/opt/home/managedblockchain-tls-chain.pem";
        String channelID = "mychannel";
        String chaincodeName = "mycc";

        String tokenID = String.format("docker exec cli peer chaincode query \n" +
                        "--tls --cafile %s \n" +
                        "--channelID %s \n" +
                        "--name %s -c '{\"Args\":[\"GetToken\", \"%s\"]}'", caFilePath, channelID, chaincodeName, tokneId);

        return tokenID;
    }*/

    public String getToken(String tokenId) {
        String caFilePath = "/opt/home/managedblockchain-tls-chain.pem";
        String channelID = "mychannel";
        String chaincodeName = "mycc";

        return executeCommand(String.format("docker exec cli peer chaincode query " +
                "--tls --cafile %s " +
                "--channelID %s " +
                "--name %s -c '{\"Args\":[\"GetToken\", \"%s\"]}'", caFilePath, channelID, chaincodeName, tokenId));
    }

    public String getAllTokens() {
        String caFilePath = "/opt/home/managedblockchain-tls-chain.pem";
        String channelID = "mychannel";
        String chaincodeName = "mycc";

        return executeCommand(String.format("docker exec cli peer chaincode query " +
                "--tls --cafile %s " +
                "--channelID %s " +
                "--name %s -c '{\"Args\":[\"GetAllTokens\"]}'", caFilePath, channelID, chaincodeName));

    }

    private String executeCommand(String command) {
        StringBuilder output = new StringBuilder();

        try {
            // ProcessBuilder를 사용하여 외부 명령어를 실행합니다.
            Process process = new ProcessBuilder("sh", "-c", command).start();

            // 실행 결과를 읽어오기 위한 BufferedReader를 생성합니다.
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            // 실행 결과를 읽어와 StringBuilder에 추가합니다.
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            // 프로세스가 종료될 때까지 기다립니다.
            process.waitFor();

            // 리소스를 해제합니다.
            reader.close();
            process.destroy();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return output.toString();
    }
}
