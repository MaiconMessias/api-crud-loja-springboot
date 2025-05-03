package br.com.crudloja.repositorio;

import br.com.crudloja.model.Usuario;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UsuarioRepository extends CrudRepository<Usuario, String> {
    Optional<Usuario> findByUsernameAndSenha(String username, String senha);
    Optional<Usuario> findByTokenAndUsernameAndSenha(String token, String username, String senha);
}
