package br.com.crudloja.dto;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {
    private String username;
    private String email;
    private String password;
    private List<String> roles;
    private Boolean mfaEnabled;
    private String mfaSecret;

}
