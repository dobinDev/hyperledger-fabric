package blockchain.hyperledger.hyperledgerfabric.chaincode;

import com.google.protobuf.ByteString;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hyperledger.fabric.shim.ChaincodeBase;
import org.hyperledger.fabric.shim.ChaincodeStub;

import java.io.UnsupportedEncodingException;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

public class ABstore2 extends ChaincodeBase {

    private static Log _logger = LogFactory.getLog(ABstore.class);

    // 체인코드 초기화
    public Response init(ChaincodeStub stub) {
        try {
            _logger.info("init java simple chaincode");
            List<String> args = stub.getParameters();
            if (args.size() != 4) {
                newErrorResponse("Incorrect number of arguments. Expecting 4");
            }

            //Initialize the chaincode
            String account1Key = args.get(0);
            int account1Value = Integer.parseInt(args.get(1));
            String account2Key = args.get(2);
            int account2Value = Integer.parseInt(args.get(3));

            _logger.info(String.format("account %s, value = %s; account %s, value %s",
                    account1Key, account1Value, account2Key, account2Value));
            stub.putStringState(account1Key, args.get(1));
            stub.putStringState(account2Key, args.get(3));

            return newErrorResponse();
        } catch (Throwable throwable) {
            return newErrorResponse(throwable);
        }
    }

    // 특정 기능을 호출
    public Response invoke(ChaincodeStub stub) {
        try {
            _logger.info("Invoke java simple chaincode");
            String func = stub.getFunction();
            List<String> params = stub.getParameters();

            if (func.equals("invoke")) {
                return invoke(stub, params);
            }
            if (func.equals("delete")) {
                return delete(stub, params);
            }
            if (func.equals("query")) {
                return query(stub, params);
            }
            if (func.equals("mintERC721")) {
                return mintERC721(stub, params);
            }
            return newErrorResponse("Invalid invoke function name. Expecting one of: [\"invoke\", \"delete\", \"query\", \"mintERC721\"]");
        } catch (Throwable e) {
            return newErrorResponse(e);
        }
    }

    // ERC-721 토큰을 발행하는 메서드
    private Response mintERC721(ChaincodeStub stub, List<String> args) {
        try {
            // ERC-721 발행 로직을 구현
            // 새로운 토큰 ID 및 수신자 주소를 매개변수로 받아와서 발행

            if (args.size() != 2) {
                return newErrorResponse("Incorrect number of arguments. Expecting 2 for mintERC721");
            }

            String toAddress = args.get(0);
            int tokenId = Integer.parseInt(args.get(1));

            // ERC-721 토큰 발행 로직 추가
            _logger.info(String.format("New ERC-721 token minted with ID %d and sent to %s", tokenId, toAddress));

            return newSuccessResponse("mintERC721 finished successfully", ByteString.copyFrom(String.format("New ERC-721 token minted with ID %d and sent to %s", tokenId, toAddress), String.valueOf(UTF_8)).toByteArray());
        } catch (Throwable e) {
            return newErrorResponse(e);
        }
    }

    private Response invoke(ChaincodeStub stub, List<String> args) throws UnsupportedEncodingException {
        if (args.size() != 3) {
            return newErrorResponse("Incorrecdt number of arguments. Expecting 3");
        }
        String accountFromKey = args.get(0);
        String accountToKey = args.get(1);

        String accountFormValueStr = stub.getStringState(accountFromKey);
        if (accountFormValueStr == null) {
            return newErrorResponse(String.format("Entity %s not found", accountFromKey));
        }
        int accountFromValue = Integer.parseInt(accountFormValueStr);

        String accountToValueStr = stub.getStringState(accountToKey);
        if (accountToValueStr == null) {
            return newErrorResponse(String.format("Entity %s not found", accountToKey));
        }
        int accountToValue = Integer.parseInt(accountToValueStr);

        int amount = Integer.parseInt(args.get(2));

        if (amount > accountFromValue) {
            return newErrorResponse(String.format("not enough money in account %s", accountFromKey));
        }

        accountFromValue -= amount;
        accountToValue += amount;

        _logger.info(String.format("new value of A : %s", accountFromValue));
        _logger.info(String.format("new value of B : %s", accountToValue));

        stub.putStringState(accountFromKey, Integer.toString(accountFromValue));
        stub.putStringState(accountToKey, Integer.toString(accountToValue));

        _logger.info("Transfer complete");

        return newSuccessResponse("invoke finished successfully", ByteString.copyFrom(accountFromKey + ": " +
                accountFromValue + " " + accountToKey + ": " + accountToValue, String.valueOf(UTF_8)).toByteArray());
    }

    // Deletes an entity from state
    private Response delete(ChaincodeStub stub, List<String> args) {
        if (args.size() != 1) {
            return newErrorResponse("Incorrect number of arguments. Expecting 1");
        }
        String key = args.get(0);
        // Delete the key from the state in ledger
        stub.delState(key);
        return newSuccessResponse();
    }

    // query callback representing the query of a chaincode
    private Response query(ChaincodeStub stub, List<String> args) throws UnsupportedEncodingException {
        if (args.size() != 1) {
            return newErrorResponse("Incorrect number of arguments. Expecting name of the person to query");
        }
        String key = args.get(0);
        // byte[] stateBytes
        String val = stub.getStringState(key);
        if (val == null) {
            return newErrorResponse(String.format("Eoor : state for %s is null", key));
        }
        _logger.info(String.format("Query Response:\nName: %s, Amount: %s\n", key, val));
        return newSuccessResponse(val, ByteString.copyFrom(val, String.valueOf(UTF_8)).toByteArray());
    }

    public static void main(String[] args) {
        new ABstore().start(args);
    }
}
