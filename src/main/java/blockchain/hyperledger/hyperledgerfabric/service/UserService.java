package blockchain.hyperledger.hyperledgerfabric.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
@RequiredArgsConstructor
public class UserService {

    String caFilePath = "/opt/home/managedblockchain-tls-chain.pem";
    String channelID = "mychannel2";
    String chaincodeName = "mycc2";

    public String createUserBlock(String nickName, int mymPoint, String ownedToken[]) {

        return executeCommand(String.format("docker exec cli peer chaincode invoke " +
                        "--tls --cafile %s " +
                        "--channelID %s " +
                        "--name %s -c '{\"Args\":[\"MintToken\", \"%s\", \"%d\", \"%s\"]}'",
                caFilePath, channelID, chaincodeName, nickName, mymPoint, ownedToken));
    }

    public String getUser(String nickName) {

        return executeCommand(String.format("docker exec cli peer chaincode query " +
                "--tls --cafile %s " +
                "--channelID %s " +
                "--name %s -c '{\"Args\":[\"GetUser\", \"%s\"]}'", caFilePath, channelID, chaincodeName, nickName));
    }

    public String getAllUsers() {

        return executeCommand(String.format("docker exec cli peer chaincode query " +
                "--tls --cafile %s " +
                "--channelID %s " +
                "--name %s -c '{\"Args\":[\"GetAllUsers\"]}'", caFilePath, channelID, chaincodeName));
    }

    public String transferToken(String from, String to, String tokenId, int amount) {

        return executeCommand(String.format("docker exec cli peer chaincode query " +
                        "--tls --cafile %s " +
                        "--channelID %s " +
                        "--name %s -c '{\"Args\":[\"TransferToken\", \"%s\", \"%s\", \"%d\"]}'",
                caFilePath, channelID, chaincodeName, from, to, tokenId, amount));
    }

    public String updateMymPoint(String nickName, int delta) {

        return executeCommand(String.format("docker exec cli peer chaincode query " +
                        "--tls --cafile %s " +
                        "--channelID %s " +
                        "--name %s -c '{\"Args\":[\"UpdateMymPoint\", \"%s\", \"%d\"]}'",
                caFilePath, channelID, chaincodeName, nickName, delta));
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
