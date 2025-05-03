package br.com.crudloja.controller;

import br.com.crudloja.dto.UsuarioDTO;
import br.com.crudloja.model.Usuario;
import br.com.crudloja.service.iservice.UsuarioService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@RestController
@RequestMapping("api")
@CrossOrigin(origins = "*")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    ModelMapper modelMapper;

    private final SecretKey CHAVE = Keys.hmacShaKeyFor(
            "7f-j&CKk=coNzZc0y7_4obMP?#TfcYq%fcD0mDpenW2nc!lfGoZ|d?f&RNbDHUX6"
                    .getBytes(StandardCharsets.UTF_8));

    @PostMapping("/usuario/validausuario")
    public ResponseEntity<UsuarioDTO> getUsuarioExiste(@RequestBody @NonNull Usuario usuario) {
        UsuarioDTO usuarioDTO = null;
        try{
            if(usuarioService.findByUsernameAndSenha(usuario.getUsername(), usuario.getSenha()) != null){
                String jwtToken = Jwts.builder()
                        .setSubject(usuario.getUsername())
                        .setIssuer("localhost:8080")
                        .setIssuedAt(new Date())
                        .setExpiration(
                                Date.from(
                                        LocalDateTime.now().plusMinutes(15L)
                                                .atZone(ZoneId.systemDefault())
                                                .toInstant()))
                        .signWith(CHAVE, SignatureAlgorithm.HS256)
                        .compact();


                usuarioDTO = modelMapper.map(usuario, UsuarioDTO.class);
                usuarioDTO.setToken("Bearer: " + jwtToken);
                usuarioService.update(usuarioDTO);
                return ResponseEntity.ok().body(usuarioDTO);
            } else
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        catch(Exception ex)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // metodo para validar sessão (não usado)
    @GetMapping("/usuario/validatoken")
    public ResponseEntity<Boolean> validaToken(@RequestBody @NonNull Usuario usuario) {
        // TODO retirar do token a palavra "Bearer"
        try {
            UsuarioDTO usuarioDto = modelMapper.map(usuario, UsuarioDTO.class);
            if(usuarioService.findByToken(usuarioDto) != null) {
              return ResponseEntity.ok().body(true);
            } else
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
