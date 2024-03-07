package blockchain.hyperledger.hyperledgerfabric.controller;

import blockchain.hyperledger.hyperledgerfabric.dto.TokenDTO;
import blockchain.hyperledger.hyperledgerfabric.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tokens")
@RequiredArgsConstructor
public class TokenController {

    private final TokenService tokenService;

    @GetMapping("{tokenId}")
    public ResponseEntity<TokenDTO> getToken(@PathVariable String tokenId) {
        // tokenId를 사용하여 토큰 정보를 가져오는 비즈니스 로직을 호출
        TokenDTO token = tokenService.getTokenById(tokenId);
        if (token != null) {
            return new ResponseEntity<>(token, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // HTTP POST 요청을 처리하는 메서드 예시
    @PostMapping
    public ResponseEntity<TokenDTO> createToken(@RequestBody TokenDTO tokenDTO) {
        // tokenDTO를 사용하여 토큰 생성하는 비즈니스 로직을 호출
        TokenDTO createdToken = tokenService.createToken(tokenDTO);
        return new ResponseEntity<>(createdToken, HttpStatus.CREATED);
    }
}
