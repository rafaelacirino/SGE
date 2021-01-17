package com.basis.sge.service.servico;

import com.basis.sge.service.dominio.Usuario;
import com.basis.sge.service.repositorio.UsuarioRepositorio;
import com.basis.sge.service.servico.DTO.UsuarioDTO;

import com.basis.sge.service.servico.exception.RegraNegocioException;
import com.basis.sge.service.servico.mapper.UsuarioMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UsuarioServico {
    private final UsuarioRepositorio usuarioRepositorio;
    private final UsuarioMapper usuarioMapper;

    // GET LISTA
    public List<UsuarioDTO> listar(){
        List<Usuario> usuarios = usuarioRepositorio.findAll();

        return usuarioMapper.toDto(usuarios);
    }

    // GET ID
    public UsuarioDTO obterPorID(Integer id){
        Usuario usuario = usuarioRepositorio.findById(id)
                .orElseThrow(() -> new RegraNegocioException("O usuário não foi cadastrado"));

        return usuarioMapper.toDto(usuario);
    }

    public UsuarioDTO salvar(UsuarioDTO usuarioDTO){
        // EXCEPTION CAMPOS NULOS
        if (usuarioDTO == null){
            throw new RegraNegocioException("O usuario é nulo");
        }

        if(usuarioDTO.getEmail() == null){
            throw new RegraNegocioException("O usuario não possui email");
        }

        if(usuarioDTO.getCpf() == null){
            throw new RegraNegocioException("O usuario não possui cpf");
        }

        if (usuarioDTO.getNome() == null){
            throw new RegraNegocioException("O usuario não possui nome");
        }
        if (usuarioDTO.getDataNascimento() == null){
            throw new RegraNegocioException("O usuario não possui data de nascimento");
        }
        if (usuarioDTO.getChaveUnica() == null){
            throw new RegraNegocioException("O usuario não possui data de nascimento");
        }

        //EXCEPTION IDADE ERRADA (OBS: EVENTUALMENTE MUDAR PARA LOCALDATE)
        Date date = new Date(System.currentTimeMillis());
        if (usuarioDTO.getDataNascimento().after(date)){
            throw new RegraNegocioException("Data de nascimento invalida");
        }
        //EXCEPTION CPF INVALIDO
        if (usuarioDTO.getCpf().length() < 11 || usuarioDTO.getCpf().length() > 11){
            throw new RegraNegocioException("CPF invalido");
        }

        Usuario usuario = usuarioRepositorio.save(usuarioMapper.toEntity(usuarioDTO));

        return usuarioMapper.toDto(usuario);
    }

    public UsuarioDTO editar(Integer id, UsuarioDTO usuarioDTO){

       Usuario usuario = usuarioRepositorio.findById(id)
               .orElseThrow(()-> new RegraNegocioException("Usuario de id" + id + "não existe"));
      //SET
       usuario.setCpf(usuarioDTO.getCpf());
       usuario.setEmail(usuarioDTO.getEmail());
       usuario.setNome(usuarioDTO.getNome());
       usuario.setDataNascimento(usuarioDTO.getDataNascimento());
       usuario.setTelefone(usuarioDTO.getTelefone());

       return usuarioMapper.toDto(usuario);
    }

    public void remover(Integer id){
        usuarioRepositorio.findById(id)
                .orElseThrow(() -> new RegraNegocioException("O usuário não existe"));

        usuarioRepositorio.deleteById(id);
    }
}
