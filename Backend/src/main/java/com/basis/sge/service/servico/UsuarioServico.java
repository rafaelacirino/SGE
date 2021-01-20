package com.basis.sge.service.servico;

import com.basis.sge.service.dominio.Usuario;
import com.basis.sge.service.repositorio.UsuarioRepositorio;
import com.basis.sge.service.servico.DTO.EmailDTO;
import com.basis.sge.service.servico.DTO.UsuarioDTO;
import com.basis.sge.service.servico.exception.RegraNegocioException;
import com.basis.sge.service.servico.mapper.UsuarioMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class UsuarioServico {
    private final UsuarioRepositorio usuarioRepositorio;
    private final UsuarioMapper usuarioMapper;
    private final EmailServico emailServico;



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
        else if( !usuarioRepositorio.findByEmail(usuarioDTO.getEmail()).isEmpty() ){
            throw new RegraNegocioException("O email já foi cadastrado");
        }
        /////////

        // EXCEPTIONS CPF
        if(usuarioDTO.getCpf() == null){
            throw new RegraNegocioException("O usuario não possui cpf");
        }
        else if(!usuarioRepositorio.findByCpf(usuarioDTO.getCpf()).isEmpty()){
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
        usuarioRepositorio.save(usuario);
        criarEmailCadastro(usuario.getEmail(),usuario.getChaveUnica());
        return usuarioMapper.toDto(usuario);
    }


    //EDITAR
    public UsuarioDTO editar( UsuarioDTO usuarioDTO){


        Usuario usuario = usuarioRepositorio.findById(usuarioDTO.getId())
                .orElseThrow(()-> new RegraNegocioException("Usuario de id" + usuarioDTO.getId() + "não existe"));
        List<Usuario> listaCpf = usuarioRepositorio.findByCpf(usuarioDTO.getCpf());
        List<Usuario> listaEmail = usuarioRepositorio.findByEmail(usuarioDTO.getEmail());
        listaCpf.remove(usuario);
        listaEmail.remove(usuario);


        //SET

        // VERIFICAR CPF()
        if (usuarioDTO.getCpf().length() > 11 || usuarioDTO.getCpf().length() < 11){
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
        if(!listaEmail.isEmpty()){
            throw new RegraNegocioException("Email já cadastrado");
        }

        //EXCEPTION CPF
        if(!listaCpf.isEmpty()){
            throw new RegraNegocioException("CPF já cadastrado");
        }
        Usuario usuarioTemp = usuarioMapper.toEntity(usuarioDTO);
        usuarioTemp.setChaveUnica(usuario.getChaveUnica());
        usuarioRepositorio.save(usuarioTemp);
        criarEmailUsuarioEditado(usuario.getEmail());

        return usuarioMapper.toDto(usuario);
    }


    //REMOVER

    public void remover(Integer id){
        Usuario usuario = usuarioRepositorio.findById(id)
                .orElseThrow(() -> new RegraNegocioException("O usuário não existe"));

        usuarioRepositorio.deleteById(id);
        criarEmailUsuarioRemovido(usuario.getEmail());

    }
    public void criarEmailCadastro(String email,String chave){

        EmailDTO emailDTO = new EmailDTO();
        emailDTO.setAssunto("Cadastro SGE");
        emailDTO.setCorpo("Parabéns você se cadastrou no SGE com SUCESSO! Sua chave e " + chave +".");
        emailDTO.setDestinatario(email);
        emailDTO.setCopias(new ArrayList<String>());
        emailDTO.getCopias().add(emailDTO.getDestinatario());
        emailServico.sendMail(emailDTO);

    }

    public void criarEmailUsuarioRemovido(String email){

        EmailDTO emailDTO = new EmailDTO();
        emailDTO.setAssunto("Remoção de cadastro no SGE");
        emailDTO.setCorpo("Você foi removido do cadastro do SGE!");
        emailDTO.setDestinatario(email);
        emailDTO.setCopias(new ArrayList<String>());
        emailDTO.getCopias().add(emailDTO.getDestinatario());
        emailServico.sendMail(emailDTO);
    }

    public void criarEmailUsuarioEditado(String email){

        EmailDTO emailDTO = new EmailDTO();
        emailDTO.setAssunto("Alteração de cadastro no SGE");
        emailDTO.setCorpo("Seu cadastro foi alterado no SGE!");
        emailDTO.setDestinatario(email);
        emailDTO.setCopias(new ArrayList<String>());
        emailDTO.getCopias().add(emailDTO.getDestinatario());
        emailServico.sendMail(emailDTO);
    }


}
