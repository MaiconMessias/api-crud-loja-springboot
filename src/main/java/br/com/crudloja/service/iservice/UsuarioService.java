package br.com.crudloja.service.iservice;

import br.com.crudloja.dto.UsuarioDTO;
import br.com.crudloja.model.Usuario;

public interface UsuarioService {
    Usuario findByUsernameAndSenha(String username, String senha);
    Usuario update(UsuarioDTO usuarioDTO);
    Usuario findByToken(UsuarioDTO usuarioDTO);
}
