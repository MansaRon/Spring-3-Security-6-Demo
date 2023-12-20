package za.co.security.Spring3.Security6.Demo.services;

import za.co.security.Spring3.Security6.Demo.dtos.LoginRequestDTO;
import za.co.security.Spring3.Security6.Demo.dtos.LoginResponseDTO;
import za.co.security.Spring3.Security6.Demo.dtos.UserRequest;
import za.co.security.Spring3.Security6.Demo.dtos.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse saveUser(UserRequest userRequest);

    UserResponse getUser();

    List<UserResponse> getAllUser();

    LoginResponseDTO loginUser(LoginRequestDTO loginRequestDTO);
}
