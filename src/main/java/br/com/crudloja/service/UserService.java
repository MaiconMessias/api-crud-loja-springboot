package br.com.crudloja.service;

import br.com.crudloja.dto.UserRequestDTO;
import br.com.crudloja.dto.UserResponseDTO;
import br.com.crudloja.model.Role;
import br.com.crudloja.model.User;
import br.com.crudloja.repositorio.UserRepository;
import br.com.crudloja.util.PasswordUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        User user = User.builder()
                .username(userRequestDTO.getUsername())
                .email(userRequestDTO.getEmail())
                .passwordHash(passwordEncoder.encode(userRequestDTO.getPassword()))
                .roles(convertNamesToRoles(userRequestDTO.getRoles()))
                .createdat(LocalDateTime.now())
                .updatedat(LocalDateTime.now())
                .accountLocked(false)
                .failedAttempts(0)
                .mfaEnabled(userRequestDTO.getMfaEnabled())
                .mfaSecret(userRequestDTO.getMfaEnabled() ? generateMfaSecret() : null)
                .build();
        User savedUser = userRepository.save(user);

        return toResponseDTO(savedUser);
    }

    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(users -> UserResponseDTO.builder()
                              .id(users.getId())
                              .username(users.getUsername())
                              .email(users.getEmail())
                              .build()
                ).collect(Collectors.toList());
    }

    public UserResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        return toResponseDTO(user);
    }

    public User updateUser(Long id, User updateUser) {
        return userRepository.findById(id).map(user -> {
            user.setUsername(updateUser.getUsername());
            user.setEmail(updateUser.getEmail());
            user.setPasswordHash(updateUser.getPasswordHash());
            user.setRoles(updateUser.getRoles());
            user.setUpdatedat(LocalDateTime.now());
            user.setMfaEnabled(updateUser.getMfaEnabled());
            user.setMfaSecret(updateUser.getMfaSecret());
            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    // Registrar login
    public void registerLogin(Long id){
        userRepository.findById(id).ifPresent(user -> {
            user.setLastLogin(LocalDateTime.now());
            user.setFailedAttempts(0);
            userRepository.save(user);
        });
    }

    // Incrementar tentativas de falhas
    public void registerFailedAttempt(Long id){
        userRepository.findById(id).ifPresent(user -> {
            user.setFailedAttempts(user.getFailedAttempts() + 1);
            if (user.getFailedAttempts() >= 5){
                user.setAccountLocked(true);
            }
            userRepository.save(user);
        });
    }

    // Conversão única: Set<Role> -> List<String>
    private List<String> convertRolesToNames(Set<Role> roles){
        return roles.stream()
                .map(Role::getName)
                 .collect(Collectors.toList());
    }

    // Conversão única: List<String> -> Set<Roles>
    private Set<Role> convertNamesToRoles(List<String> roles){
        return roles.stream()
                   .map(Role::new)
                   .collect(Collectors.toSet());
    }

    // Conversão de DTOs
    public UserResponseDTO toResponseDTO(User user){

        return new UserResponseDTO(
            user.getId(),
            user.getUsername(),
            user.getEmail(),
            convertRolesToNames(user.getRoles()),
            user.getCreatedat(),
            user.getUpdatedat(),
            user.getLastLogin(),
            user.getAccountLocked(),
            user.getFailedAttempts(),
            user.getMfaEnabled()
        );
    }

    public User toEntity(UserRequestDTO requestDto){
        return User.builder()
            .username(requestDto.getUsername())
            .email(requestDto.getEmail())
            .passwordHash(PasswordUtil.hashPassword(requestDto.getPassword()))
            .roles(requestDto.getRoles().stream()
                                        .map(Role::new)
                                        .collect(Collectors.toSet()))
            .mfaEnabled(requestDto.getMfaEnabled())
            .mfaSecret(requestDto.getMfaSecret())
            .build();
    }

    private String generateMfaSecret(){
        return UUID.randomUUID().toString();
    }
}
