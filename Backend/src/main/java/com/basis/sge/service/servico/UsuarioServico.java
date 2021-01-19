package com.basis.sge.service.servico;

import com.basis.sge.service.dominio.Usuario;
import com.basis.sge.service.repositorio.UsuarioRepositorio;
import com.basis.sge.service.servico.DTO.UsuarioDTO;

import com.basis.sge.service.servico.exception.RegraNegocioException;
import com.basis.sge.service.servico.mapper.UsuarioMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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

    //SALVAR

    public UsuarioDTO salvar(UsuarioDTO usuarioDTO){
        // EXCEPTION SALVAR


        if (usuarioDTO == null){
            throw new RegraNegocioException("O usuario é nulo");
        }
        // EXCEPTION EMAIL
        if(usuarioDTO.getEmail() == null){
            throw new RegraNegocioException("O usuario não possui email");
        }
        else if (usuarioRepositorio.findByEmail(usuarioDTO.getEmail()) != null){
            throw new RegraNegocioException("email já cadastrado");
        }
        /////////

        // EXCEPTIONS CPF
        if(usuarioDTO.getCpf() == null){
            throw new RegraNegocioException("O usuario não possui cpf");
        }
        else if(usuarioRepositorio.findByCpf(usuarioDTO.getCpf()) != null){
            throw new RegraNegocioException("Cpf já cadastrado");
        }
        //EXCEPTION CPF INVALIDO
        else if (usuarioDTO.getCpf().length() < 11 || usuarioDTO.getCpf().length() > 11){
            throw new RegraNegocioException("CPF invalido");
        }
        /////////

        // EXCEPTIONS NOME
         if (usuarioDTO.getNome() == null){
            throw new RegraNegocioException("O usuario não possui nome");
        }
        /////

        // EXCEPTIONS DATA NASCIMENTO
         if (usuarioDTO.getDataNascimento() == null){
            throw new RegraNegocioException("O usuario não possui data de nascimento");
        }
        //EXCEPTION IDADE ERRADA (OBS: EVENTUALMENTE MUDAR PARA LOCALDATE)
        Date date = new Date(System.currentTimeMillis());
        if (usuarioDTO.getDataNascimento().after(date)){
            throw new RegraNegocioException("Data de nascimento invalida");
        }

        //EXCEPTIONS TELEFONE
        if (usuarioDTO.getTelefone() == null){
            throw new RegraNegocioException("Telefone nulo");
        }
        if (usuarioDTO.getTelefone().length() > 14){
            throw new RegraNegocioException("Numero invalido");
        }

        Usuario usuario = usuarioMapper.toEntity(usuarioDTO);
        usuario.setChaveUnica(UUID.randomUUID().toString());

        return usuarioMapper.toDto(usuarioRepositorio.save(usuario));
    }


    //EDITAR
    public UsuarioDTO editar(Integer id, UsuarioDTO usuarioDTO){


        Usuario usuario = usuarioRepositorio.findById(id)
                .orElseThrow(()-> new RegraNegocioException("Usuario de id" + id + "não existe"));


      //SET

        // VERIFICAR CPF()
        if (usuarioDTO.getCpf().length() > 11){
            throw new RegraNegocioException("CPF invalido");
        }

        //EXCEPTION IDADE ERRADA (OBS: EVENTUALMENTE MUDAR PARA LOCALDATE)
        Date date = new Date(System.currentTimeMillis());
        if (usuarioDTO.getDataNascimento().after(date)){
            throw new RegraNegocioException("Data de nascimento invalida");
        }

        //VERIFICAR TELEFONE

        if (usuarioDTO.getTelefone().length() > 14){
            throw new RegraNegocioException("Numero invalido");
        }

        //EXCEPTION EMAIL
        try {
            usuario.setEmail(usuario.getEmail());
        }
        catch (RegraNegocioException e){
            throw new RegraNegocioException("Email já cadastrado");
        }

        //EXCEPTION CPF
        try {
            usuario.setCpf(usuarioDTO.getCpf());
        }
        catch (DataIntegrityViolationException e){
            throw new RegraNegocioException("Cpf já cadastrado");
        }
        usuario.setNome(usuarioDTO.getNome());
        usuario.setDataNascimento(usuarioDTO.getDataNascimento());
        usuario.setTelefone(usuarioDTO.getTelefone());

       return usuarioMapper.toDto(usuario);
    }


    //REMOVER

    public void remover(Integer id){
        usuarioRepositorio.findById(id)
                .orElseThrow(() -> new RegraNegocioException("O usuário não existe"));

        usuarioRepositorio.deleteById(id);
    }
}
