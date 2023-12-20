package za.co.security.Spring3.Security6.Demo.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import za.co.security.Spring3.Security6.Demo.dtos.LoginRequestDTO;
import za.co.security.Spring3.Security6.Demo.dtos.LoginResponseDTO;
import za.co.security.Spring3.Security6.Demo.dtos.UserRequest;
import za.co.security.Spring3.Security6.Demo.dtos.UserResponse;
import za.co.security.Spring3.Security6.Demo.models.UserInfo;
import za.co.security.Spring3.Security6.Demo.repository.UserRepository;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    ModelMapper modelMapper = new ModelMapper();

    @Override
    public UserResponse saveUser(UserRequest userRequest) {
        if (userRequest == null ||
                userRequest.getUsername().isEmpty() ||
                userRequest.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Username or password is missing in the request");
        }

        UserInfo savedUser = null;

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(userRequest.getPassword());

        UserInfo user = modelMapper.map(userRequest, UserInfo.class);
        user.setPassword(encodedPassword);
        if (userRequest.getId() != null) {
            UserInfo oldUser = userRepository.findFirstById(userRequest.getId());
            if (oldUser != null) {
                oldUser.setId(user.getId());
                oldUser.setUsername(user.getUsername());
                oldUser.setRoles(user.getRoles());
                oldUser.setPassword(user.getPassword());

                savedUser = userRepository.save(oldUser);
                // userRepository.refresh(savedUser);
            } else {
                throw new RuntimeException("Cant find record with ID..." + userRequest.getId());
            }
        } else {
            savedUser = userRepository.save(user);
        }
        // userRepository.refresh(savedUser);
        UserResponse userResponse = modelMapper.map(savedUser, UserResponse.class);
        return userResponse;
    }

    @Override
    public UserResponse getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        UserInfo userInfo = userRepository.findByUsername(username);
        UserResponse userResponse = modelMapper.map(userInfo, UserResponse.class);
        return userResponse;
    }

    @Override
    public List<UserResponse> getAllUser() {
        return null;
    }

    @Override
    public LoginResponseDTO loginUser(LoginRequestDTO loginRequestDTO) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDTO.getUsername(), loginRequestDTO.getPassword()));
        if (authentication.isAuthenticated()) {
            return LoginResponseDTO
                    .builder()
                    .accessToken(jwtService.GenerateToken(loginRequestDTO.getUsername()))
                    .build();
        } else {
            throw new UsernameNotFoundException("invalid user request..!!");
        }
    }
}
