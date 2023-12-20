package za.co.security.Spring3.Security6.Demo.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponseDTO {
    String accessToken;
}
