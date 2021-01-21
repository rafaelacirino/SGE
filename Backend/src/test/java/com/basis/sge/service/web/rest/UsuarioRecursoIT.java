package com.basis.sge.service.web.rest;

import com.basis.sge.service.builder.UsuarioBuilder;
import com.basis.sge.service.dominio.Usuario;
import com.basis.sge.service.repositorio.UsuarioRepositorio;
import com.basis.sge.service.servico.mapper.UsuarioMapper;
import com.basis.sge.service.util.IntTestComum;
import com.basis.sge.service.util.TestUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.io.IOException;
import java.text.ParseException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@Transactional
public class UsuarioRecursoIT extends IntTestComum {

    @Autowired
    private UsuarioBuilder usuarioBuilder;

    @Autowired
    private UsuarioMapper usuarioMapper;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @BeforeEach
    public void incializar(){
        usuarioRepositorio.deleteAll();
    }

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

        getMockMvc().perform(post( "/api/perguntas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(usuarioMapper.toDto(usuario))))
                .andExpect(status().isCreated());
    }
    @Test
    public void salvarObjetoNuloTest() throws Exception{
        Usuario usuario = null;

        getMockMvc().perform(post( "/api/perguntas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(usuarioMapper.toDto(usuario))))
                .andExpect(status().isBadRequest());
    }

    public void salvarCPFNuloTest() throws Exception {
        Usuario usuario = usuarioBuilder.construirEntidade();
        usuario.setCpf(null);
        getMockMvc().perform(post( "/api/perguntas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(usuarioMapper.toDto(usuario))))
                .andExpect(status().isBadRequest());

    }



}
