package com.basis.sge.service.servico;

import com.basis.sge.service.dominio.Usuario;
import com.basis.sge.service.repositorio.PreInscricaoRepositorio;
import com.basis.sge.service.repositorio.UsuarioRepositorio;
import com.basis.sge.service.servico.DTO.EmailDTO;
import com.basis.sge.service.servico.DTO.UsuarioDTO;
import com.basis.sge.service.servico.exception.RegraNegocioException;
import com.basis.sge.service.servico.mapper.UsuarioMapper;
import com.basis.sge.service.servico.producer.SgeProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
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
<<<<<<< HEAD
    private final EmailServico emailServico;
    private static final LocalDate DIA_DE_HOJE = LocalDate.now();

=======
    private final SgeProducer sgeProducer;
    private final PreInscricaoRepositorio preInscricaoRepositorio;
>>>>>>> 17879469ff1d4f0475ee24fada285684336326ff


    public List<UsuarioDTO> listar(){
        return usuarioMapper.toDto(listarTodosUsuarios());
    }

    public UsuarioDTO obterPorID(Integer id){
        return usuarioMapper.toDto(verificarUsuarioPorId(id));
    }

    public UsuarioDTO salvar(UsuarioDTO usuarioDTO){
        Usuario usuario = usuarioRepositorio.save(verificarPost(usuarioDTO));
        criarEmailCadastro(usuario.getEmail(),usuario.getChaveUnica());
        return usuarioMapper.toDto(usuario);
    }

    public UsuarioDTO editar( UsuarioDTO usuarioDTO){
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

    public void criarEmailCadastro(String email,String chave){

        EmailDTO emailDTO = new EmailDTO();
        emailDTO.setAssunto("Cadastro SGE");
        emailDTO.setCorpo("Parabéns você se cadastrou no SGE com SUCESSO! Sua chave e " + chave +".");
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

    public List<Usuario> listarTodosUsuarios(){

        return usuarioRepositorio.findAll();
    }

    public Usuario verificarPost(UsuarioDTO usuarioDTO){

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

        if (usuarioDTO.getTelefone() == null){
            throw new RegraNegocioException("Telefone nulo");
        }

         if( !usuarioRepositorio.findByEmail(usuarioDTO.getEmail()).isEmpty() ){
            throw new RegraNegocioException("O email já foi cadastrado");
         }

         if(!usuarioRepositorio.findByCpf(usuarioDTO.getCpf()).isEmpty()){
             throw new RegraNegocioException("Cpf já cadastrado");
         }

<<<<<<< HEAD

        //EXCEPTION CPF INVALIDO
        if (usuarioDTO.getCpf().length() != 11){
=======
        if (usuarioDTO.getCpf().length() < 11 || usuarioDTO.getCpf().length() > 11){
>>>>>>> 17879469ff1d4f0475ee24fada285684336326ff
            throw new RegraNegocioException("CPF invalido");
        }

        if (usuarioDTO.getTelefone().length() > 14){
            throw new RegraNegocioException("Numero invalido");
        }

<<<<<<< HEAD
        /////

        //EXCEPTION IDADE ERRADA (OBS: EVENTUALMENTE MUDAR PARA LOCALDATE)

        if (usuarioDTO.getDataNascimento().isAfter(DIA_DE_HOJE)){
=======
        LocalDate date = LocalDate.now();
        if (usuarioDTO.getDataNascimento().isAfter(date)){
>>>>>>> 17879469ff1d4f0475ee24fada285684336326ff
            throw new RegraNegocioException("Data de nascimento invalida");
        }

        Usuario usuario = usuarioMapper.toEntity(usuarioDTO);
        usuario.setChaveUnica(UUID.randomUUID().toString());
        return usuario;

    }

<<<<<<< HEAD


    // VERIFICAR EDIÇÃO
=======
>>>>>>> 17879469ff1d4f0475ee24fada285684336326ff
    public Usuario verificarPut (UsuarioDTO usuarioDTO){

        // VERIFICAR ID NULL
        if(usuarioDTO.getId() == null){
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

<<<<<<< HEAD

        //SET


        // VERIFICAR CPF NULL
=======
        if(usuarioDTO.getId() == null){
            throw new RegraNegocioException("ID Nulo");
        }

>>>>>>> 17879469ff1d4f0475ee24fada285684336326ff
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

<<<<<<< HEAD
        // EXCEPTION CPF INVALIDO
        if (usuarioDTO.getCpf().length() != 11){
            throw new RegraNegocioException("CPF invalido");
        }


        //EXCEPTION IDADE ERRADA

        if (usuarioDTO.getDataNascimento().isAfter(DIA_DE_HOJE)){
=======
        if (usuarioDTO.getCpf().length() > 11 || usuarioDTO.getCpf().length() < 11){
            throw new RegraNegocioException("CPF invalido");
        }

        if (usuarioDTO.getDataNascimento().isAfter(date)){
>>>>>>> 17879469ff1d4f0475ee24fada285684336326ff
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
