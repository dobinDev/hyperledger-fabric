package blockchain.hyperledger.hyperledgerfabric.service;

import blockchain.hyperledger.hyperledgerfabric.dto.TokenDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TokenService {

    private List<TokenDTO> tokenList = new ArrayList<>();

    // 토큰 생성 메서드
    public TokenDTO createToken(TokenDTO tokenDTO) {
        // 토큰을 생성하고 리스트에 추가
        tokenList.add(tokenDTO);
        return tokenDTO;
    }

    // 특정 tokenId에 해당하는 토큰을 찾아 반환하는 메서드
    public TokenDTO getTokenById(String tokenId) {
        for (TokenDTO token : tokenList) {
            if (token.getTokenId().equals(tokenId)) {
                return token;
            }
        }
        return null; // 특정 tokenId를 가진 토큰을 찾을 수 없는 경우 null 반환
    }
}
