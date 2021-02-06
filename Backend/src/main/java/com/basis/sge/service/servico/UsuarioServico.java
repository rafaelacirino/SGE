package com.basis.sge.service.servico;

import com.basis.sge.service.dominio.PreInscricao;
import com.basis.sge.service.dominio.Usuario;
import com.basis.sge.service.repositorio.PreInscricaoRepositorio;
import com.basis.sge.service.repositorio.UsuarioRepositorio;
import com.basis.sge.service.servico.dto.ChaveUsuarioDTO;
import com.basis.sge.service.servico.dto.EmailDTO;
import com.basis.sge.service.servico.dto.EventoDTO;
import com.basis.sge.service.servico.dto.PreInscricaoDTO;
import com.basis.sge.service.servico.dto.PreinscricaoUsuarioDTO;
import com.basis.sge.service.servico.dto.UsuarioDTO;
import com.basis.sge.service.servico.exception.RegraNegocioException;
import com.basis.sge.service.servico.mapper.PreInscricaoMapper;
import com.basis.sge.service.servico.mapper.UsuarioMapper;
import com.basis.sge.service.servico.producer.SgeProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class UsuarioServico {

    private final UsuarioRepositorio usuarioRepositorio;
    private final UsuarioMapper usuarioMapper;
    private static final LocalDate DIA_DE_HOJE = LocalDate.now();
    private final SgeProducer sgeProducer;
    private final PreInscricaoRepositorio preInscricaoRepositorio;
    private final PreInscricaoServico preInscricaoServico;
    private final PreInscricaoMapper preInscricaoMapper;

    public List<UsuarioDTO> listar(){
        return usuarioMapper.toDto(usuarioRepositorio.findAll());
    }

    public UsuarioDTO obterUsuarioPorChave(ChaveUsuarioDTO chaveUsuarioDTO){
        Usuario usuario = usuarioRepositorio.findByChaveUnica(chaveUsuarioDTO.getChave());
        if(usuario == null){
            throw  new RegraNegocioException("Usuario com chave não encontrado");
        }
        return usuarioMapper.toDto(usuario);
    }

    public UsuarioDTO obterPorID(Integer id){
        return usuarioMapper.toDto(verificarUsuarioPorId(id));
    }

    public UsuarioDTO salvar(UsuarioDTO usuarioDTO){
        usuarioDTO.setAdmin(false);
        Usuario usuario = verificarPost(usuarioDTO);
        usuario = usuarioRepositorio.save(usuario);
        criarEmailCadastro(usuario.getEmail(),usuario.getChaveUnica());
        return usuarioMapper.toDto(usuario);

    }

    public UsuarioDTO editar(UsuarioDTO usuarioDTO){
        Usuario usuario = usuarioRepositorio.save(verificarPut(usuarioDTO));
        criarEmailUsuarioEditado(usuario.getEmail());
        return usuarioMapper.toDto(usuario);
    }

    public void remover(Integer id){
        if (id == null){
            throw new RegraNegocioException("ID nulo");
        }

        if(!usuarioRepositorio.existsById(id)){
            throw new RegraNegocioException("O usuário não foi cadastrado");
        }

        preInscricaoRepositorio.deleteAllByUsuario(usuarioRepositorio.findById(id).orElseThrow(()
                -> new RegraNegocioException("O usuário não foi cadastrado")));

        Usuario usuario = verificarDelete(id);
        criarEmailUsuarioRemovido(usuario.getEmail());
    }
    public List<PreInscricao> obterPreinscricao(UsuarioDTO usuarioDTO){
        return preInscricaoRepositorio.findByUsuario(usuarioRepositorio.findById(usuarioDTO.getId()).orElseThrow(()->new RegraNegocioException("usuario não encontrado")));
    }

    public void criarEmailCadastro(String email,String chave){

        EmailDTO emailDTO = new EmailDTO();
        emailDTO.setAssunto("Cadastro SGE");
        emailDTO.setCorpo("Parabéns você se cadastrou no SGE com SUCESSO! Sua chave é " + chave);
        emailDTO.setDestinatario(email);
        emailDTO.setCopias(new ArrayList<>());
        emailDTO.getCopias().add(emailDTO.getDestinatario());
        this.sgeProducer.sendMail(emailDTO);
    }

    public void criarEmailUsuarioRemovido(String email){

        EmailDTO emailDTO = new EmailDTO();
        emailDTO.setAssunto("Remoção de cadastro no SGE");
        emailDTO.setCorpo("Você foi removido do cadastro do SGE!");
        emailDTO.setDestinatario(email);
        emailDTO.setCopias(new ArrayList<>());
        emailDTO.getCopias().add(emailDTO.getDestinatario());
        this.sgeProducer.sendMail(emailDTO);
    }

    public void criarEmailUsuarioEditado(String email){

        EmailDTO emailDTO = new EmailDTO();
        emailDTO.setAssunto("Alteração de cadastro no SGE");
        emailDTO.setCorpo("Seu cadastro foi alterado no SGE!");
        emailDTO.setDestinatario(email);
        emailDTO.setCopias(new ArrayList< >());
        emailDTO.getCopias().add(emailDTO.getDestinatario());
        this.sgeProducer.sendMail(emailDTO);
    }

    public Usuario verificarUsuarioPorId(Integer id){
     usuarioRepositorio.findById(id).orElseThrow(() -> new RegraNegocioException("O usuário não foi cadastrado"));
            return usuarioRepositorio.findById(id).orElseThrow(() -> new RegraNegocioException("O usuário não foi cadastrado"));

    }

    public Usuario verificarPost(UsuarioDTO usuarioDTO){

         if( !usuarioRepositorio.findByEmail(usuarioDTO.getEmail()).isEmpty() ){
            throw new RegraNegocioException("O email já foi cadastrado");
         }

         if(!usuarioRepositorio.findByCpf(usuarioDTO.getCpf()).isEmpty()){
             throw new RegraNegocioException("Cpf já cadastrado");
         }

        //EXCEPTION IDADE ERRADA (OBS: EVENTUALMENTE MUDAR PARA LOCALDATE)
        if (usuarioDTO.getDataNascimento().isAfter(DIA_DE_HOJE)){
            throw new RegraNegocioException("Data de nascimento invalida");
        }

        Usuario usuario = usuarioMapper.toEntity(usuarioDTO);
        usuario.setChaveUnica(UUID.randomUUID().toString());
        return usuario;
    }

    // VERIFICAR EDIÇÃO
    public Usuario verificarPut (UsuarioDTO usuarioDTO){

        // VERIFICAR ID NULL
        if( usuarioDTO.getId() == null){
            throw new RegraNegocioException("ID Nulo");
        }

        Usuario usuario = usuarioRepositorio.findById(usuarioDTO.getId())
                .orElseThrow(()-> new RegraNegocioException("Usuario não existe"));

        //CRIA LISTAS ONDE INSTANCIAS COM O MESMO CPF OU EMAIL DO DTO, REMOVENDO O USUARIO QUE VAI SER MODIFICADO//
        List<Usuario> listaCpf = usuarioRepositorio.findByCpf(usuarioDTO.getCpf());
        List<Usuario> listaEmail = usuarioRepositorio.findByEmail(usuarioDTO.getEmail());
        //REMOVE USUARIO
        listaCpf.remove(usuario);
        listaEmail.remove(usuario);



        //SET


        // VERIFICAR CPF NULL

        if(usuarioDTO.getCpf() == null){
            throw new RegraNegocioException("CPF Nulo");
        }

        if(usuarioDTO.getDataNascimento() == null){
            throw new RegraNegocioException("Data de nascimento nula.");
        }

        if(usuarioDTO.getEmail() == null){
            throw new RegraNegocioException("Email nulo");
        }

        if(usuarioDTO.getNome() == null){
            throw new RegraNegocioException("Nome nulo");
        }

        if(!listaEmail.isEmpty()){
            throw new RegraNegocioException("Email já cadastrado");
        }

        if(!listaCpf.isEmpty()){
            throw new RegraNegocioException("CPF já cadastrado");
        }


        // EXCEPTION CPF INVALIDO
        if (usuarioDTO.getCpf().length() != 14){
            throw new RegraNegocioException("CPF invalido");
        }


        //EXCEPTION IDADE ERRADA

        if (usuarioDTO.getDataNascimento().isAfter(DIA_DE_HOJE)){
            throw new RegraNegocioException("Data de nascimento invalida.");
        }

        if (usuarioDTO.getTelefone().length() > 14){
            throw new RegraNegocioException("Numero invalido");
        }
        Usuario usuarioTemp = usuarioMapper.toEntity(usuarioDTO);
        usuarioTemp.setChaveUnica(usuario.getChaveUnica());
        return  usuarioTemp;
    }

    public Usuario verificarDelete(Integer id){
         Usuario usuario = verificarUsuarioPorId(id);
         usuarioRepositorio.deleteById(id);
        return usuario;
    }
}
