package com.basis.sge.service.web.rest;

import com.basis.sge.service.builder.PerguntaBuilder;
import com.basis.sge.service.dominio.Pergunta;
import com.basis.sge.service.repositorio.PerguntaRepositorio;
import com.basis.sge.service.servico.PerguntaServico;
import com.basis.sge.service.servico.mapper.PerguntaMapper;
import com.basis.sge.service.util.IntTestComum;
import com.basis.sge.service.util.TestUtil;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@Transactional
class PerguntaRecursoIT extends IntTestComum {

    @Autowired
    private PerguntaBuilder perguntaBuilder;

    @Autowired
    private PerguntaMapper perguntaMapper;

    @Autowired
    private PerguntaRepositorio perguntaRepositorio;

    @Autowired
    private PerguntaServico perguntaServico;

    @BeforeEach
    void inicializar() {
        perguntaRepositorio.deleteAll();
    }

    @Test
    void listarTest() throws Exception {
        perguntaBuilder.construir();
        getMockMvc().perform(get( "/api/perguntas"))
                .andExpect(status().isOk());
    }

    @Test
    void salvarTest() throws Exception {

        Pergunta pergunta = perguntaBuilder.construirEntidade();

        getMockMvc().perform(post( "/api/perguntas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(perguntaMapper.toDto(pergunta))))
                .andExpect(status().isCreated());
    }

    @Test
    void salvarTestTituloNull() throws Exception {

        Pergunta pergunta = perguntaBuilder.construirEntidade();
        pergunta.setTitulo(null);


        String result = getMockMvc().perform(post( "/api/perguntas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(perguntaMapper.toDto(pergunta))))
                .andExpect(status().isBadRequest()).andReturn().getResolvedException().getMessage();
                Assert.assertEquals("A pergunta não possui titulo",result);
    }

    @Test
    void salvarTestObrigatoriedadeNull() throws Exception {

        Pergunta pergunta = perguntaBuilder.construirEntidade();
        pergunta.setObrigatorio(null);


        String result = getMockMvc().perform(post( "/api/perguntas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(perguntaMapper.toDto(pergunta))))
                .andExpect(status().isBadRequest()).andReturn().getResolvedException().getMessage();
                Assert.assertEquals("A obrigatoriedade não existe",result);
    }

    @Test
    void salvarTituloDuplicadoTest() throws Exception{

        Pergunta pergunta = perguntaBuilder.construir();
        Pergunta perguntaDuplicada = perguntaBuilder.construirEntidade();
        pergunta.setObrigatorio(false);

        String result = getMockMvc().perform(post( "/api/perguntas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(perguntaMapper.toDto(perguntaDuplicada))))
                .andExpect(status().isBadRequest()).andReturn().getResolvedException().getMessage();
                Assert.assertEquals("A pergunta já existe",result);
    }

    @Test
    void salvarObrigatorioDuplicadoTest() throws Exception{

        Pergunta pergunta = perguntaBuilder.construir();
        Pergunta perguntaDuplicada = perguntaBuilder.construirEntidade();
        pergunta.setTitulo("Titulo Editado");
        getMockMvc().perform(post( "/api/perguntas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(perguntaMapper.toDto(perguntaDuplicada))))
                .andExpect(status().isBadRequest());
    }

    @Test
    void editarTestTitulo() throws Exception {

        Pergunta perguntaAntiga = perguntaBuilder.construir();
        Pergunta pergunta = perguntaBuilder.construirEntidade();
        pergunta.setId(perguntaAntiga.getId());
        pergunta.setTitulo("Alterando pergunta");

        getMockMvc().perform(put( "/api/perguntas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(perguntaMapper.toDto(pergunta))))
                .andExpect(status().isOk());
    }

    @Test
    void editarTestObrigatoriedade() throws Exception {

        Pergunta perguntaAntiga = perguntaBuilder.construir();
        Pergunta pergunta = perguntaBuilder.construirEntidade();
        pergunta.setId(perguntaAntiga.getId());
        pergunta.setObrigatorio(false);

        getMockMvc().perform(put( "/api/perguntas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(perguntaMapper.toDto(pergunta))))
                .andExpect(status().isOk());
    }

    @Test
    void editarTest() throws Exception {

        Pergunta perguntaAntiga = perguntaBuilder.construir();
        Pergunta pergunta = perguntaBuilder.construirEntidade();
        pergunta.setId(perguntaAntiga.getId());
        pergunta.setTitulo("Alterando pergunta");
        pergunta.setObrigatorio(false);

        getMockMvc().perform(put( "/api/perguntas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(perguntaMapper.toDto(pergunta))))
                .andExpect(status().isOk());
    }

    @Test
    void editarTestTituloNull() throws Exception {

        Pergunta perguntaAntiga = perguntaBuilder.construir();
        Pergunta pergunta = perguntaBuilder.construirEntidade();
        pergunta.setId(perguntaAntiga.getId());
        pergunta.setTitulo(null);

        String result = getMockMvc().perform(put( "/api/perguntas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(perguntaMapper.toDto(pergunta))))
                .andExpect(status().isBadRequest()).andReturn().getResolvedException().getMessage();
                Assert.assertEquals("A pergunta não possui titulo",result);
    }

    @Test
    void editarTestObrigatoriedadeNull() throws Exception {

        Pergunta perguntaAntiga = perguntaBuilder.construir();
        Pergunta pergunta = perguntaBuilder.construirEntidade();
        pergunta.setId(perguntaAntiga.getId());
        pergunta.setObrigatorio(null);

        String result = getMockMvc().perform(put( "/api/perguntas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(perguntaMapper.toDto(pergunta))))
                .andExpect(status().isBadRequest()).andReturn().getResolvedException().getMessage();
                Assert.assertEquals("A obrigatoriedade é nula",result);
    }

    @Test
    void editarTituloDuplicadoTest() throws Exception {
        Pergunta perguntaAntes = perguntaBuilder.construir();

        Pergunta perguntaNova = perguntaBuilder.construirEntidade();
        perguntaNova.setTitulo("Qual");
        perguntaServico.salvar(perguntaMapper.toDto(perguntaNova));

        perguntaAntes.setTitulo(perguntaNova.getTitulo());

        String result = getMockMvc().perform(put( "/api/perguntas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(perguntaMapper.toDto(perguntaAntes))))
                .andExpect(status().isBadRequest()).andReturn().getResolvedException().getMessage();
                Assert.assertEquals("Pergunta já existe",result);
    }

    @Test
    void deletarTest() throws Exception {
        Pergunta pergunta = perguntaBuilder.construir();

        getMockMvc().perform(delete("/api/perguntas/" + pergunta.getId()))
                .andExpect(status().isOk());



    }

    @Test
    void deletarTestIdErrado() throws Exception {

        String result = getMockMvc().perform(delete("/api/perguntas/1000" ))
                .andExpect(status().isBadRequest()).andReturn().getResolvedException().getMessage();
                Assert.assertEquals("Pergunta não existe",result);
    }
}