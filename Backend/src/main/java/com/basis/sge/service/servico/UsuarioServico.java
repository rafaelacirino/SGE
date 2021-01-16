package com.basis.sge.service.servico;

import com.basis.sge.service.dominio.Usuario;
import com.basis.sge.service.repositorio.UsuarioRepositorio;
import com.basis.sge.service.servico.DTO.UsuarioDTO;

import com.basis.sge.service.servico.exception.RegraNegocioException;
import com.basis.sge.service.servico.mapper.UsuarioMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UsuarioServico {
    private final UsuarioRepositorio usuarioRepositorio;
    private final UsuarioMapper usuarioMapper;

    public List<UsuarioDTO> listar(){
        List<Usuario> usuarios = usuarioRepositorio.findAll();

        return usuarioMapper.toDto(usuarios);
    }
    public UsuarioDTO obterPorID(Integer id){
        Usuario usuario = usuarioRepositorio.findById(id)
                .orElseThrow(() -> new RegraNegocioException("O usuário não foi cadastrado"));

        return usuarioMapper.toDto(usuario);
    }

    public UsuarioDTO salvar(UsuarioDTO usuarioDTO){

        Usuario usuarioEmail = usuarioRepositorio.findByEmail(usuarioDTO.getEmail());
        Usuario usuarioCpf = usuarioRepositorio.findByCpf(usuarioDTO.getCpf());

        if(usuarioEmail != null){
            throw new RegraNegocioException("Email já cadastrado");
        }
        if(usuarioCpf != null){
            throw new RegraNegocioException("CPF já cadastrado");
        }
        Usuario usuario = usuarioRepositorio.save(usuarioMapper.toEntity(usuarioDTO));
        return usuarioMapper.toDto(usuario);
    }

    public UsuarioDTO editar(UsuarioDTO usuarioDTO){
        Usuario usuario = usuarioRepositorio.findById(usuarioDTO.getId())
                    .orElseThrow(() -> new RegraNegocioException("O usuário não existe"));

        return usuarioMapper.toDto(usuario);
    }

    public void remover(Integer id){
        usuarioRepositorio.findById(id)
                .orElseThrow(() -> new RegraNegocioException("O usuário não existe"));

        usuarioRepositorio.deleteById(id);
    }
}
