package br.com.crudloja.service.impl;

import br.com.crudloja.dto.UsuarioDTO;
import br.com.crudloja.model.Usuario;
import br.com.crudloja.repositorio.UsuarioRepository;
import br.com.crudloja.service.iservice.UsuarioService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public Usuario findByUsernameAndSenha(String username, String senha) {
        return usuarioRepository.findByUsernameAndSenha(username, senha).orElse(null);
    }

    @Override
    public Usuario update(UsuarioDTO usuarioDTO) {
        return usuarioRepository.save(mapper.map(usuarioDTO, Usuario.class));
    }

    @Override
    public Usuario findByToken(UsuarioDTO usuarioDTO) {
        return usuarioRepository.findByTokenAndUsernameAndSenha(usuarioDTO.getToken(), usuarioDTO.getUsername(),
                                                                usuarioDTO.getSenha())
                                                                .orElse(null);
    }
}
