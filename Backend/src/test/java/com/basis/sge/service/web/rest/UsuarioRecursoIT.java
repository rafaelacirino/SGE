package com.basis.sge.service.web.rest;
import com.basis.sge.service.builder.UsuarioBuilder;
import com.basis.sge.service.dominio.Pergunta;
import com.basis.sge.service.dominio.Usuario;
import com.basis.sge.service.repositorio.UsuarioRepositorio;
import com.basis.sge.service.servico.DTO.UsuarioDTO;
import com.basis.sge.service.servico.UsuarioServico;
import com.basis.sge.service.servico.mapper.UsuarioMapper;
import com.basis.sge.service.util.IntTestComum;
import com.basis.sge.service.util.TestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.time.LocalDate;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@Transactional
public class UsuarioRecursoIT extends IntTestComum {

    @Autowired
    private UsuarioBuilder usuarioBuilder;

    @Autowired
    private UsuarioMapper usuarioMapper;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private UsuarioServico usuarioServico;

    @BeforeEach
    public void incializar(){
        usuarioRepositorio.deleteAll();
    }


    //GET
    @Test
    public void listarTest() throws Exception {
        usuarioBuilder.construir();
        getMockMvc().perform(get( "/api/usuarios"))
                .andExpect(status().isOk());

    }

    @Test
    public void obterPorIdTest() throws Exception {
        Usuario usuario = usuarioBuilder.construir();
        getMockMvc().perform(get( "/api/usuarios/" + usuario.getId()))
                .andExpect(status().isOk());

    }
    @Test
    public void obterPorIdInexistenteTest() throws Exception{
        getMockMvc().perform(get( "/api/usuarios/20"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void salvarTest() throws Exception{
        Usuario usuario = usuarioBuilder.construirEntidade();
        usuario.setEmail("batotow@gmail.com");
        usuario.setCpf("12345678902");

        getMockMvc().perform(post( "/api/usuarios")
               .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(usuarioMapper.toDto(usuario))))
                .andExpect(status().isCreated());
    }
    @Test
    public void salvarObjetoNuloTest() throws Exception{
        Usuario usuario = null;

        getMockMvc().perform(post( "/api/usuarios")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(usuarioMapper.toDto(usuario))))
                .andExpect(status().isBadRequest());
    }
    @Test
    public void salvarCPFNuloTest() throws Exception {
        Usuario usuario = usuarioBuilder.construirEntidade();
        usuario.setCpf(null);
        getMockMvc().perform(post( "/api/usuarios")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(usuarioMapper.toDto(usuario))))
                .andExpect(status().isBadRequest());

    }
    @Test
    public void salvarEmailNuloTest() throws Exception{

        Usuario usuario = usuarioBuilder.construirEntidade();
        usuario.setEmail(null);
        getMockMvc().perform(post( "/api/usuarios")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(usuarioMapper.toDto(usuario))))
                .andExpect(status().isBadRequest());

    }
    @Test
    public void salvarNomeNuloTest() throws Exception{

        Usuario usuario = usuarioBuilder.construirEntidade();
        usuario.setNome(null);
        getMockMvc().perform(post( "/api/usuarios")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(usuarioMapper.toDto(usuario))))
                .andExpect(status().isBadRequest());

    }
    @Test
    public void salvarDtNascNuloTest() throws Exception{

        Usuario usuario = usuarioBuilder.construirEntidade();
        usuario.setDataNascimento(null);
        getMockMvc().perform(post( "/api/usuarios")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(usuarioMapper.toDto(usuario))))
                .andExpect(status().isBadRequest());

    }
    @Test
    public void salvarTelefoneNullTest() throws Exception{

        Usuario usuario = usuarioBuilder.construirEntidade();
        usuario.setTelefone(null);
        getMockMvc().perform(post( "/api/usuarios")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(usuarioMapper.toDto(usuario))))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void salvarTelefoneInvalidoTest() throws Exception{

        Usuario usuario = usuarioBuilder.construirEntidade();
        usuario.setTelefone("123456789012344");
        getMockMvc().perform(post( "/api/usuarios")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(usuarioMapper.toDto(usuario))))
                .andExpect(status().isBadRequest());
    }
    @Test
    public void salvarDtNascInvalidaTest() throws Exception{

        Usuario usuario = usuarioBuilder.construirEntidade();
        LocalDate date = LocalDate.of(2022,2,12);
        usuario.setDataNascimento(date);
        getMockMvc().perform(post( "/api/usuarios")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(usuarioMapper.toDto(usuario))))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void salvarCPFDuplicadoTest() throws Exception{

        Usuario usuario = usuarioBuilder.construir();
        Usuario usuarioCpfDuplicado = usuarioBuilder.construirEntidade();
        usuario.setEmail("batotow@gmail.com");
        getMockMvc().perform(post( "/api/usuarios")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(usuarioMapper.toDto(usuarioCpfDuplicado))))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void salvarEmailDuplicadoTest() throws Exception{

        Usuario usuario = usuarioBuilder.construir();
        Usuario usuarioEmailDuplicado = usuarioBuilder.construirEntidade();
        usuario.setCpf("11111111111");
        getMockMvc().perform(post( "/api/usuarios")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(usuarioMapper.toDto(usuarioEmailDuplicado))))
                .andExpect(status().isBadRequest());
    }




    //PUT

    @Test
    public void editarTodosCamposTest() throws Exception {
        Usuario usuarioAntigo = usuarioBuilder.construir();
        Usuario usuario = usuarioBuilder.construirEntidade();
        usuario.setId(usuarioAntigo.getId());
        usuario.setNome("CARLOS");
        usuario.setCpf("11111111111");
        usuario.setEmail("batotow@gmail.com");
        usuario.setTelefone("40028922");
        usuario.setDataNascimento(LocalDate.of(2000,03,12));

        getMockMvc().perform(put( "/api/usuarios")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(usuarioMapper.toDto(usuario))))
                .andExpect(status().isOk());
    }
    @Test
    public void editarCPFNuloTest() throws Exception {
        Usuario usuarioAntigo = usuarioBuilder.construir();
        Usuario usuario = usuarioBuilder.construirEntidade();
        usuario.setId(usuarioAntigo.getId());
        usuario.setNome("CARLOS");
        usuario.setCpf(null);
        usuario.setEmail("batotow@gmail.com");
        usuario.setTelefone("40028922");
        usuario.setDataNascimento(LocalDate.of(2000,03,12));

        getMockMvc().perform(put( "/api/usuarios")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(usuarioMapper.toDto(usuario))))
                .andExpect(status().isBadRequest());
    }
    @Test
    public void editarEmailNuloTest() throws Exception {
        Usuario usuarioAntigo = usuarioBuilder.construir();
        Usuario usuario = usuarioBuilder.construirEntidade();
        usuario.setId(usuarioAntigo.getId());
        usuario.setNome("CARLOS");
        usuario.setCpf("11111111111");
        usuario.setEmail(null);
        usuario.setTelefone("40028922");
        usuario.setDataNascimento(LocalDate.of(2000,03,12));

        getMockMvc().perform(put( "/api/usuarios")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(usuarioMapper.toDto(usuario))))
                .andExpect(status().isBadRequest());
    }
    @Test
    public void editarNomeNuloTest() throws Exception {
        Usuario usuarioAntigo = usuarioBuilder.construir();
        Usuario usuario = usuarioBuilder.construirEntidade();
        usuario.setId(usuarioAntigo.getId());
        usuario.setNome(null);
        usuario.setCpf("11111111111");
        usuario.setEmail("batotow@gmail.com");
        usuario.setTelefone("40028922");
        usuario.setDataNascimento(LocalDate.of(2000,03,12));

        getMockMvc().perform(put( "/api/usuarios")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(usuarioMapper.toDto(usuario))))
                .andExpect(status().isBadRequest());
    }
    @Test
    public void editarDtaNascNuloTest() throws Exception {
        Usuario usuarioAntigo = usuarioBuilder.construir();
        Usuario usuario = usuarioBuilder.construirEntidade();
        usuario.setId(usuarioAntigo.getId());
        usuario.setNome("Italo");
        usuario.setCpf("11111111111");
        usuario.setEmail("batotow@gmail.com");
        usuario.setTelefone("40028922");
        usuario.setDataNascimento(null);

        getMockMvc().perform(put( "/api/usuarios")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(usuarioMapper.toDto(usuario))))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void editarCPFDuplicadoTest() throws Exception {
        Usuario usuarioAntigo = usuarioBuilder.construir();
        Usuario usuarioAntigo2 = usuarioBuilder.construirEntidade();

        usuarioAntigo2.setCpf("11111111111");
        usuarioAntigo2.setEmail("batotow@gmail.com");

        UsuarioDTO usuario = usuarioServico.salvar(usuarioMapper.toDto(usuarioAntigo2));

        Usuario usuarioNovo = usuarioBuilder.construirEntidade();
        usuarioNovo.setId(usuario.getId());
        usuarioNovo.setCpf("12345678901");

        getMockMvc().perform(put( "/api/usuarios")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(usuarioMapper.toDto(usuarioNovo))))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void editarEmailDuplicadoTest() throws Exception {
        Usuario usuarioAntigo = usuarioBuilder.construir();
        Usuario usuarioAntigo2 = usuarioBuilder.construirEntidade();

        usuarioAntigo2.setCpf("11111111111");
        usuarioAntigo2.setEmail("batotow@gmail.com");
        UsuarioDTO usuario = usuarioServico.salvar(usuarioMapper.toDto(usuarioAntigo2));

        Usuario usuarioNovo = usuarioBuilder.construirEntidade();
        usuarioNovo.setId(usuario.getId());
        usuarioNovo.setEmail("italo.galdino@maisunifacisa.com.br");


        getMockMvc().perform(put( "/api/usuarios")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(usuarioMapper.toDto(usuarioNovo))))
                .andExpect(status().isBadRequest());
    }

    // TESTE REMOVER

    @Test
    public void removerTest() throws Exception {
        Usuario usuario = usuarioBuilder.construir();

        getMockMvc().perform(delete("/api/usuarios/" + usuario.getId()))
                .andExpect(status().isOk());
    }
    @Test
    public void removerUsuarioInexistenteTest() throws Exception {

        getMockMvc().perform(delete("/api/usuarios/200"))
                .andExpect(status().isBadRequest());
    }






}
