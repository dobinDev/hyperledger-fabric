package blockchain.hyperledger.hyperledgerfabric.controller;

import blockchain.hyperledger.hyperledgerfabric.dto.UserDTO;
import blockchain.hyperledger.hyperledgerfabric.service.TokenService;
import blockchain.hyperledger.hyperledgerfabric.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 유저 데이터 생성
    @PostMapping("/createUserBlock")
    public String createUserBlock(@RequestBody UserDTO userDTO) {
        String nickName = userDTO.getNickName();
        int mymPoint = userDTO.getMymPoint();
        String[] ownedToken = userDTO.getOwnedToken();

        return userService.createUserBlock(nickName, mymPoint, ownedToken);
    }

    @PostMapping("/transferToken")
    public String transferToken(@RequestBody UserDTO userDTO) {

        TokenService tokenService1 = new TokenService();


        return userService.transferToken()
    }

    @PostMapping("updateMymPoint")
    public String updateMymPoint() {

    }

    // 유저 닉네임 조회
    @GetMapping("/user/{nickName}")
    public String getUser(@PathVariable String nickName) {
        return userService.getUser(nickName);
    }

    // 유저 전체 조회
    @GetMapping("/users")
    public String getAllUsers() {
        return userService.getAllUsers();
    }
}
