package za.co.security.Spring3.Security6.Demo.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.security.Spring3.Security6.Demo.dtos.LoginRequestDTO;
import za.co.security.Spring3.Security6.Demo.dtos.LoginResponseDTO;
import za.co.security.Spring3.Security6.Demo.dtos.UserRequest;
import za.co.security.Spring3.Security6.Demo.dtos.UserResponse;
import za.co.security.Spring3.Security6.Demo.services.UserService;

@RestController
@RequestMapping
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @GetMapping("/ping")
    public String test() {
        logger.debug("Entering test method");
        try {
            return "Welcome";
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/api/v1/register")
    public ResponseEntity saveUser(@RequestBody UserRequest userRequest) {
        logger.debug("Entering save user method");
        UserResponse userResponse = userService.saveUser(userRequest);
        return ResponseEntity.ok(userResponse);
    }

    @PostMapping("/api/v1/login")
    public LoginResponseDTO loginUser(@RequestBody LoginRequestDTO loginRequestDTO) {
        logger.debug("Entering user login method");
        LoginResponseDTO responseDTO = userService.loginUser(loginRequestDTO);
        return ResponseEntity.ok(responseDTO).getBody();
    }
}