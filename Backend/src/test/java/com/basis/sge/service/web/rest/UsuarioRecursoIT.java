package com.basis.sge.service.web.rest;

import com.basis.sge.service.builder.UsuarioBuilder;
import com.basis.sge.service.dominio.Usuario;
import com.basis.sge.service.repositorio.UsuarioRepositorio;
import com.basis.sge.service.servico.dto.UsuarioDTO;
import com.basis.sge.service.servico.UsuarioServico;
import com.basis.sge.service.servico.mapper.UsuarioMapper;
import com.basis.sge.service.util.IntTestComum;
import com.basis.sge.service.util.TestUtil;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Objects;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@Transactional
class UsuarioRecursoIT extends IntTestComum {

    @Autowired
    private UsuarioBuilder usuarioBuilder;

    @Autowired
    private UsuarioMapper usuarioMapper;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private UsuarioServico usuarioServico;

    @BeforeEach
    void incializar(){
        usuarioRepositorio.deleteAll();
    }


    //GET
    @Test
    void listarTest() throws Exception {
        usuarioBuilder.construir();
        getMockMvc().perform(get( "/api/usuarios"))
                .andExpect(status().isOk());

    }

    @Test
    void obterPorIdTest() throws Exception {
        Usuario usuario = usuarioBuilder.construir();
        getMockMvc().perform(get( "/api/usuarios/" + usuario.getId()))
                .andExpect(status().isOk());

    }
    @Test
    void obterPorIdInexistenteTest() throws Exception{
        String result = Objects.requireNonNull(getMockMvc().perform(get("/api/usuarios/20"))
                .andExpect(status().isBadRequest()).andReturn().getResolvedException()).getMessage();
        Assertions.assertEquals("O usuário não foi cadastrado",result);
    }


    @Test
    void salvarTest() throws Exception{
        Usuario usuario = usuarioBuilder.construirEntidade();
        usuario.setEmail("batotow@gmail.com");
        usuario.setCpf("12345678902");

        getMockMvc().perform(post( "/api/usuarios")
               .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(usuarioMapper.toDto(usuario))))
                .andExpect(status().isCreated());
    }
    @Test
    void salvarObjetoNuloTest() throws Exception{

        String result = Objects.requireNonNull(getMockMvc().perform(post("/api/usuarios")
               .contentType(TestUtil.APPLICATION_JSON_UTF8)
               .content(TestUtil.convertObjectToJsonBytes(usuarioMapper.toDto((Usuario) null))))
               .andExpect(status().isBadRequest()).andReturn().getResolvedException()).getMessage();
       Assertions.assertEquals("Required request body is missing: public org.springframework.http.ResponseEntity<com.basis.sge.service.servico.dto.UsuarioDTO> com.basis.sge.service.recurso.UsuarioRecurso.salvar(com.basis.sge.service.servico.dto.UsuarioDTO)",result);
    }
    @Test
    void salvarCPFNuloTest() throws Exception {
        Usuario usuario = usuarioBuilder.construirEntidade();
        usuario.setCpf(null);
        String result = Objects.requireNonNull(getMockMvc().perform(post("/api/usuarios")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(usuarioMapper.toDto(usuario))))
                .andExpect(status().isBadRequest()).andReturn().getResolvedException()).getMessage();
        Assertions.assertEquals("O usuario não possui cpf",result);

    }
    @Test
    void salvarEmailNuloTest() throws Exception{

        Usuario usuario = usuarioBuilder.construirEntidade();
        usuario.setEmail(null);
        String result = Objects.requireNonNull(getMockMvc().perform(post("/api/usuarios")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(usuarioMapper.toDto(usuario))))
                .andExpect(status().isBadRequest()).andReturn().getResolvedException()).getMessage();

        Assertions.assertEquals("O usuario não possui email",result);

    }
    @Test
    void salvarNomeNuloTest() throws Exception{

        Usuario usuario = usuarioBuilder.construirEntidade();
        usuario.setNome(null);
       String result = Objects.requireNonNull(getMockMvc().perform(post("/api/usuarios")
               .contentType(TestUtil.APPLICATION_JSON_UTF8)
               .content(TestUtil.convertObjectToJsonBytes(usuarioMapper.toDto(usuario))))
               .andExpect(status().isBadRequest()).andReturn().getResolvedException()).getMessage();
       Assertions.assertEquals("O usuario não possui nome",result);
    }

    @Test
    void salvarDtNascNuloTest() throws Exception{

        Usuario usuario = usuarioBuilder.construirEntidade();
        usuario.setDataNascimento(null);
        String result = Objects.requireNonNull(getMockMvc().perform(post("/api/usuarios")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(usuarioMapper.toDto(usuario))))
                .andExpect(status().isBadRequest()).andReturn().getResolvedException()).getMessage();
        Assertions.assertEquals("O usuario não possui data de nascimento",result);

    }
    @Test
    void salvarTelefoneNullTest() throws Exception{

        Usuario usuario = usuarioBuilder.construirEntidade();
        usuario.setTelefone(null);
        String result = Objects.requireNonNull(getMockMvc().perform(post("/api/usuarios")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(usuarioMapper.toDto(usuario))))
                .andExpect(status().isBadRequest()).andReturn().getResolvedException()).getMessage();
        Assertions.assertEquals("Telefone nulo",result);
    }

    @Test
    void salvarTelefoneInvalidoTest() throws Exception{

        Usuario usuario = usuarioBuilder.construirEntidade();
        usuario.setTelefone("123456789012344");
        String result = Objects.requireNonNull(getMockMvc().perform(post("/api/usuarios")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(usuarioMapper.toDto(usuario))))
                .andExpect(status().isBadRequest()).andReturn().getResolvedException()).getMessage();
        Assertions.assertEquals("Numero invalido",result);
    }
    @Test
    void salvarDtNascInvalidaTest() throws Exception{

        Usuario usuario = usuarioBuilder.construirEntidade();
        LocalDate date = LocalDate.of(2022,2,12);
        usuario.setDataNascimento(date);
        String result = Objects.requireNonNull(getMockMvc().perform(post("/api/usuarios")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(usuarioMapper.toDto(usuario))))
                .andExpect(status().isBadRequest()).andReturn().getResolvedException()).getMessage();

        Assertions.assertEquals("Data de nascimento invalida",result);
    }

    @Test
    void salvarCPFDuplicadoTest() throws Exception{
        usuarioBuilder.construir();
        Usuario usuarioCpfDuplicado = usuarioBuilder.construirEntidade();
        usuarioCpfDuplicado.setEmail("batotow@gmail.com");
        String result = Objects.requireNonNull(getMockMvc().perform(post("/api/usuarios")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(usuarioMapper.toDto(usuarioCpfDuplicado))))
                .andExpect(status().isBadRequest()).andReturn().getResolvedException()).getMessage();
        Assertions.assertEquals("Cpf já cadastrado",result);

    }

    @Test
    void salvarEmailDuplicadoTest() throws Exception{

        Usuario usuario = usuarioBuilder.construir();
        Usuario usuarioEmailDuplicado = usuarioBuilder.construirEntidade();
        usuario.setCpf("11111111111");
        String result = Objects.requireNonNull(getMockMvc().perform(post("/api/usuarios")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(usuarioMapper.toDto(usuarioEmailDuplicado))))
                .andExpect(status().isBadRequest()).andReturn().getResolvedException()).getMessage();
        Assertions.assertEquals("O email já foi cadastrado",result);
    }




    //PUT

    @Test
    void editarTodosCamposTest() throws Exception {
        Usuario usuarioAntigo = usuarioBuilder.construir();
        Usuario usuario = usuarioBuilder.construirEntidade();
        usuario.setId(usuarioAntigo.getId());
        usuario.setNome("CARLOS");
        usuario.setCpf("11111111111");
        usuario.setEmail("batotow@gmail.com");
        usuario.setTelefone("40028922");
        usuario.setDataNascimento(LocalDate.of(2000, 3,12));

        getMockMvc().perform(put( "/api/usuarios")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(usuarioMapper.toDto(usuario))))
                .andExpect(status().isOk());
    }
    @Test
    void editarCPFNuloTest() throws Exception {
        Usuario usuarioAntigo = usuarioBuilder.construir();
        Usuario usuario = usuarioBuilder.construirEntidade();
        usuario.setId(usuarioAntigo.getId());
        usuario.setNome("CARLOS");
        usuario.setCpf(null);
        usuario.setEmail("batotow@gmail.com");
        usuario.setTelefone("40028922");
        usuario.setDataNascimento(LocalDate.of(2000, 3,12));

        String result = Objects.requireNonNull(getMockMvc().perform(put("/api/usuarios")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(usuarioMapper.toDto(usuario))))
                .andExpect(status().isBadRequest()).andReturn().getResolvedException()).getMessage();
        Assertions.assertEquals("CPF Nulo",result);
    }
    @Test
    void editarIdInexistenteTest() throws Exception {
        Usuario usuarioAntigo = usuarioBuilder.construir();
        Usuario usuario = usuarioBuilder.construirEntidade();
        usuario.setNome("CARLOS");
        usuario.setCpf("12332112345");
        usuario.setEmail("batotow@gmail.com");
        usuario.setTelefone("40028922");
        usuario.setDataNascimento(LocalDate.of(2000,03,12));

        String result =getMockMvc().perform(put( "/api/usuarios")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(usuarioMapper.toDto(usuario))))
                .andExpect(status().isBadRequest()).andReturn().getResolvedException().getMessage();
        Assert.assertEquals("ID Nulo",result);
    }

    @Test
    void editarEmailNuloTest() throws Exception {
        Usuario usuarioAntigo = usuarioBuilder.construir();
        Usuario usuario = usuarioBuilder.construirEntidade();
        usuario.setId(usuarioAntigo.getId());
        usuario.setNome("CARLOS");
        usuario.setCpf("11111111111");
        usuario.setEmail(null);
        usuario.setTelefone("40028922");
        usuario.setDataNascimento(LocalDate.of(2000, 3,12));

        String result = Objects.requireNonNull(getMockMvc().perform(put("/api/usuarios")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(usuarioMapper.toDto(usuario))))
                .andExpect(status().isBadRequest()).andReturn().getResolvedException()).getMessage();
        Assertions.assertEquals("Email nulo",result);
    }
    @Test
    void editarNomeNuloTest() throws Exception {
        Usuario usuarioAntigo = usuarioBuilder.construir();
        Usuario usuario = usuarioBuilder.construirEntidade();
        usuario.setId(usuarioAntigo.getId());
        usuario.setNome(null);
        usuario.setCpf("11111111111");
        usuario.setEmail("batotow@gmail.com");
        usuario.setTelefone("40028922");
        usuario.setDataNascimento(LocalDate.of(2000, 3,12));

        String result = Objects.requireNonNull(getMockMvc().perform(put("/api/usuarios")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(usuarioMapper.toDto(usuario))))
                .andExpect(status().isBadRequest()).andReturn().getResolvedException()).getMessage();
        Assertions.assertEquals("Nome nulo",result);
    }
    @Test
    void editarDtaNascNuloTest() throws Exception {
        Usuario usuarioAntigo = usuarioBuilder.construir();
        Usuario usuario = usuarioBuilder.construirEntidade();
        usuario.setId(usuarioAntigo.getId());
        usuario.setNome("Italo");
        usuario.setCpf("11111111111");
        usuario.setEmail("batotow@gmail.com");
        usuario.setTelefone("40028922");
        usuario.setDataNascimento(null);

        String result = Objects.requireNonNull(getMockMvc().perform(put("/api/usuarios")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(usuarioMapper.toDto(usuario))))
                .andExpect(status().isBadRequest()).andReturn().getResolvedException()).getMessage();
        Assertions.assertEquals("Data de nascimento nula.",result);
    }

    @Test
    void editarCPFDuplicadoTest() throws Exception {
        usuarioBuilder.construir();
        Usuario usuarioAntigo2 = usuarioBuilder.construirEntidade();

        usuarioAntigo2.setCpf("11111111111");
        usuarioAntigo2.setEmail("batotow@gmail.com");

        UsuarioDTO usuario =usuarioServico.salvar(usuarioMapper.toDto(usuarioAntigo2));

        Usuario usuarioNovo = usuarioBuilder.construirEntidade();
        usuarioNovo.setId(usuario.getId());
        usuarioNovo.setCpf("12345678901");
        usuarioNovo.setEmail("AAAAAA@AAAAAA.com");

        String result = Objects.requireNonNull(getMockMvc().perform(put("/api/usuarios")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(usuarioMapper.toDto(usuarioNovo))))
                .andExpect(status().isBadRequest()).andReturn().getResolvedException()).getMessage();
        Assertions.assertEquals("CPF já cadastrado",result);
    }

    @Test
    void editarEmailDuplicadoTest() throws Exception {
        usuarioBuilder.construir();
        Usuario usuarioAntigo2 = usuarioBuilder.construirEntidade();

        usuarioAntigo2.setCpf("11111111111");
        usuarioAntigo2.setEmail("batotow@gmail.com");
        UsuarioDTO usuario = usuarioServico.salvar(usuarioMapper.toDto(usuarioAntigo2));

        Usuario usuarioNovo = usuarioBuilder.construirEntidade();
        usuarioNovo.setId(usuario.getId());
        usuarioNovo.setEmail("italo.galdino@maisunifacisa.com.br");


        String result = Objects.requireNonNull(getMockMvc().perform(put("/api/usuarios")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(usuarioMapper.toDto(usuarioNovo))))
                .andExpect(status().isBadRequest()).andReturn().getResolvedException()).getMessage();
        Assertions.assertEquals("Email já cadastrado",result);
    }

    @Test
    void editarTelefoneInvalidoTest() throws Exception {
        Usuario usuarioAntigo = usuarioBuilder.construir();
        Usuario usuario = usuarioBuilder.construirEntidade();
        usuario.setId(usuarioAntigo.getId());
        usuario.setNome("CARLOS");
        usuario.setCpf("11111111111");
        usuario.setEmail("batotow@gmail.com");
        usuario.setTelefone("000000000000000000");
        usuario.setDataNascimento(LocalDate.of(2000, 3,12));

        String result =  Objects.requireNonNull(getMockMvc().perform(put("/api/usuarios")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(usuarioMapper.toDto(usuario))))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest()).andReturn().getResolvedException()).getMessage();


        Assertions.assertEquals("Numero invalido",result);



    }

    @Test
    void editarCPFInvalidoTest() throws Exception {
        Usuario usuarioAntigo = usuarioBuilder.construir();
        Usuario usuario = usuarioBuilder.construirEntidade();
        usuario.setId(usuarioAntigo.getId());
        usuario.setNome("CARLOS");
        usuario.setCpf("1111111111122222");
        usuario.setEmail("batotow@gmail.com");
        usuario.setTelefone("40028922");
        usuario.setDataNascimento(LocalDate.of(2000, 3,12));

        String result = Objects.requireNonNull(getMockMvc().perform(put("/api/usuarios")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(usuarioMapper.toDto(usuario))))
                .andExpect(status().isBadRequest()).andReturn().getResolvedException()).getMessage();
        Assertions.assertEquals("CPF invalido",result);
    }

    @Test
    void editarDtaNascInvalidaTest() throws Exception {
        Usuario usuarioAntigo = usuarioBuilder.construir();
        Usuario usuario = usuarioBuilder.construirEntidade();
        usuario.setId(usuarioAntigo.getId());
        usuario.setNome("Italo");
        usuario.setCpf("11111111111");
        usuario.setEmail("batotow@gmail.com");
        usuario.setTelefone("40028922");
        usuario.setDataNascimento(LocalDate.of(2023,12,12));

        String result = Objects.requireNonNull(getMockMvc().perform(put("/api/usuarios")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(usuarioMapper.toDto(usuario))))
                .andExpect(status().isBadRequest()).andReturn().getResolvedException()).getMessage();
        Assertions.assertEquals("Data de nascimento invalida.",result);
    }

    // TESTE REMOVER

    @Test
    void removerTest() throws Exception {
        Usuario usuario = usuarioBuilder.construir();

        getMockMvc().perform(delete("/api/usuarios/" + usuario.getId()))
                .andExpect(status().isOk());
    }
    @Test
    void removerUsuarioInexistenteTest() throws Exception {

        String result = Objects.requireNonNull(getMockMvc().perform(delete("/api/usuarios/200"))
                .andExpect(status().isBadRequest()).andReturn().getResolvedException()).getMessage();
        Assertions.assertEquals("O usuário não foi cadastrado",result);
    }
}
