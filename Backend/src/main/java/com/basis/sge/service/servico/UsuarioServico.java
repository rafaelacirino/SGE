package com.basis.sge.service.servico;

import com.basis.sge.service.dominio.Usuario;
import com.basis.sge.service.repositorio.UsuarioRepositorio;
import com.basis.sge.service.servico.DTO.EmailDTO;
import com.basis.sge.service.servico.DTO.UsuarioDTO;
import com.basis.sge.service.servico.exception.RegraNegocioException;
import com.basis.sge.service.servico.mapper.UsuarioMapper;
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
    private final EmailServico emailServico;
    private static final LocalDate DIA_DE_HOJE = LocalDate.now();



    // GET LISTA
    public List<UsuarioDTO> listar(){
        return usuarioMapper.toDto(listarTodosUsuarios());
    }

    // GET ID
    public UsuarioDTO obterPorID(Integer id){
        return usuarioMapper.toDto(verificarUsuarioPorId(id));
    }

    //SALVAR

    public UsuarioDTO salvar(UsuarioDTO usuarioDTO){


        Usuario usuario = usuarioRepositorio.save(verificarPost(usuarioDTO));
        criarEmailCadastro(usuario.getEmail(),usuario.getChaveUnica());
        return usuarioMapper.toDto(usuario);
    }


    //EDITAR
    public UsuarioDTO editar( UsuarioDTO usuarioDTO){


        Usuario usuario = usuarioRepositorio.save(verificarPut(usuarioDTO));
        criarEmailUsuarioEditado(usuario.getEmail());
        return usuarioMapper.toDto(usuario);
    }


    //REMOVER

    public void remover(Integer id){
        if (id == null){
            throw new RegraNegocioException("ID nulo");
        }
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
        emailServico.sendMail(emailDTO);

    }
    // EMAILS //
    public void criarEmailUsuarioRemovido(String email){

        EmailDTO emailDTO = new EmailDTO();
        emailDTO.setAssunto("Remoção de cadastro no SGE");
        emailDTO.setCorpo("Você foi removido do cadastro do SGE!");
        emailDTO.setDestinatario(email);
        emailDTO.setCopias(new ArrayList<>());
        emailDTO.getCopias().add(emailDTO.getDestinatario());
        emailServico.sendMail(emailDTO);
    }

    public void criarEmailUsuarioEditado(String email){

        EmailDTO emailDTO = new EmailDTO();
        emailDTO.setAssunto("Alteração de cadastro no SGE");
        emailDTO.setCorpo("Seu cadastro foi alterado no SGE!");
        emailDTO.setDestinatario(email);
        emailDTO.setCopias(new ArrayList< >());
        emailDTO.getCopias().add(emailDTO.getDestinatario());
        emailServico.sendMail(emailDTO);
    }
    //////////////


    //METODOS DE VALIDAÇÃO

    // VERIFICAR POR ID
    public Usuario verificarUsuarioPorId(Integer id){
     usuarioRepositorio.findById(id).orElseThrow(() -> new RegraNegocioException("O usuário não foi cadastrado"));
            return usuarioRepositorio.findById(id).orElseThrow(() -> new RegraNegocioException("O usuário não foi cadastrado"));

    }
    //LISTAR OS USUARIOS
    public List<Usuario> listarTodosUsuarios(){

        return usuarioRepositorio.findAll();
    }
    //VERIFICAR PERSISTENCIA
    public Usuario verificarPost(UsuarioDTO usuarioDTO){

        // EXCEPTION SALVAR

        //USUARIO NULO
        if (usuarioDTO == null){
            throw new RegraNegocioException("O usuario é nulo");
        }
        // EXCEPTION EMAIL NULL
        if(usuarioDTO.getEmail() == null){
            throw new RegraNegocioException("O usuario não possui email");
        }
        // EXCEPTION CPF NULL
        if(usuarioDTO.getCpf() == null){
            throw new RegraNegocioException("O usuario não possui cpf");
        }
        // EXCEPTIONS NOME
        if (usuarioDTO.getNome() == null){
            throw new RegraNegocioException("O usuario não possui nome");
        }

        // EXCEPTIONS DATA NASCIMENTO
        if (usuarioDTO.getDataNascimento() == null){
            throw new RegraNegocioException("O usuario não possui data de nascimento");
        }

        //EXCEPTIONS TELEFONE
        if (usuarioDTO.getTelefone() == null){
            throw new RegraNegocioException("Telefone nulo");
        }

       //EXCEPTION EMAIL DUPLICADO
         if( !usuarioRepositorio.findByEmail(usuarioDTO.getEmail()).isEmpty() ){
            throw new RegraNegocioException("O email já foi cadastrado");
         }
         //EXCEPTIN CPF DUPLICADO
         if(!usuarioRepositorio.findByCpf(usuarioDTO.getCpf()).isEmpty()){
             throw new RegraNegocioException("Cpf já cadastrado");
         }


        //EXCEPTION CPF INVALIDO
        if (usuarioDTO.getCpf().length() != 11){
            throw new RegraNegocioException("CPF invalido");
        }

        //VERIFICAR TELEFONE

        if (usuarioDTO.getTelefone().length() > 14){
            throw new RegraNegocioException("Numero invalido");
        }
        /////////


        /////

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


        //SET


        // VERIFICAR CPF NULL
        if(usuarioDTO.getCpf() == null){
            throw new RegraNegocioException("CPF Nulo");
        }
        //VERIFICAR DATA NASCIMENTO NULL
        if(usuarioDTO.getDataNascimento() == null){
            throw new RegraNegocioException("Data de nascimento nula.");
        }

        //VERIFICAR EMAIL NULL
        if(usuarioDTO.getEmail() == null){
            throw new RegraNegocioException("Email nulo");
        }

        //VERIFICAR NOME
        if(usuarioDTO.getNome() == null){
            throw new RegraNegocioException("Nome nulo");
        }


        //EXCEPTION EMAIL DUPLICADO
        if(!listaEmail.isEmpty()){
            throw new RegraNegocioException("Email já cadastrado");
        }

        //EXCEPTION CPF
        if(!listaCpf.isEmpty()){
            throw new RegraNegocioException("CPF já cadastrado");
        }

        // EXCEPTION CPF INVALIDO
        if (usuarioDTO.getCpf().length() != 11){
            throw new RegraNegocioException("CPF invalido");
        }


        //EXCEPTION IDADE ERRADA

        if (usuarioDTO.getDataNascimento().isAfter(DIA_DE_HOJE)){
            throw new RegraNegocioException("Data de nascimento invalida.");
        }


        //VERIFICAR TELEFONE

        if (usuarioDTO.getTelefone().length() > 14){
            throw new RegraNegocioException("Numero invalido");
        }
        Usuario usuarioTemp = usuarioMapper.toEntity(usuarioDTO);
        usuarioTemp.setChaveUnica(usuario.getChaveUnica());
        return  usuarioTemp;

    }



    // VERIFICAR A REMOÇÃO
    public Usuario verificarDelete(Integer id){
         Usuario usuario = verificarUsuarioPorId(id);
         usuarioRepositorio.deleteById(id);
        return usuario;
    }


}
