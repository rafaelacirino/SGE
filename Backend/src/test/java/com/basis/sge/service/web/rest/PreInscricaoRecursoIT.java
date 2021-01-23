package com.basis.sge.service.web.rest;

import com.basis.sge.service.builder.PreInscricaoBuilder;
import com.basis.sge.service.builder.SituacaoPreInscricaoBuilder;
import com.basis.sge.service.builder.UsuarioBuilder;
import com.basis.sge.service.dominio.PreInscricao;
import com.basis.sge.service.dominio.SituacaoPreInscricao;
import com.basis.sge.service.dominio.Usuario;
import com.basis.sge.service.repositorio.PreInscricaoRepositorio;
import com.basis.sge.service.repositorio.UsuarioRepositorio;
import com.basis.sge.service.servico.DTO.CancelarInscricaoDTO;
import com.basis.sge.service.servico.DTO.InscricaoRespostaDTO;
import com.basis.sge.service.servico.DTO.PreInscricaoDTO;
import com.basis.sge.service.servico.UsuarioServico;
import com.basis.sge.service.servico.mapper.PreInscricaoMapper;
import com.basis.sge.service.util.IntTestComum;
import com.basis.sge.service.util.TestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@Transactional
public class PreInscricaoRecursoIT extends IntTestComum {

    @Autowired
    private PreInscricaoBuilder preInscricaoBuilder;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private PreInscricaoMapper preInscricaoMapper;

    @Autowired
    private PreInscricaoRepositorio preInscricaoRepositorio;

    @Autowired
    private SituacaoPreInscricaoBuilder situacaoPreInscricaoBuilder;

    @BeforeEach
    public void inicializar() {
        preInscricaoRepositorio.deleteAll();
    }

    @Test
    public void listarTest() throws Exception {
        preInscricaoBuilder.construir();
        getMockMvc().perform(get( "/api/preinscricao"))
                .andExpect(status().isOk());
    }

    @Test
    public void salvarTest() throws Exception {

        PreInscricao preInscricao = preInscricaoBuilder.construirEntidade();
        PreInscricaoDTO preInscricaoDTO = preInscricaoMapper.toDto(preInscricao);
        List<InscricaoRespostaDTO> inscricaoRespostaDTOList = new ArrayList<>();
        inscricaoRespostaDTOList.add(new InscricaoRespostaDTO());
        preInscricaoDTO.setInscricaoRespostas(inscricaoRespostaDTOList);

        getMockMvc().perform(post( "/api/preinscricao")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(preInscricaoDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    public void editarTest() throws Exception {
        PreInscricao preInscricao = preInscricaoBuilder.construir();
        SituacaoPreInscricao situacaoPreInscricao = situacaoPreInscricaoBuilder.construir();
        preInscricao.setSituacaoPreInscricao(situacaoPreInscricao);

        getMockMvc().perform(put( "/api/preinscricao")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(preInscricaoMapper.toDto(preInscricao))))
                .andExpect(status().isOk());
    }

    @Test
    public void cancelarTest() throws Exception {
        PreInscricao preInscricao = preInscricaoBuilder.construir();

        Usuario usuario = usuarioRepositorio.findById(preInscricao.getUsuario().getId()).get();
        CancelarInscricaoDTO cancelarInscricaoDTO = new CancelarInscricaoDTO();
        cancelarInscricaoDTO.setChave(usuario.getChaveUnica());
        cancelarInscricaoDTO.setId(preInscricao.getId());

        getMockMvc().perform(put( "/api/preinscricao/cancelar")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cancelarInscricaoDTO)))
                .andExpect(status().isOk());
    }

    @Test
    public void cancelarComChaveErradaTest() throws Exception {
        PreInscricao preInscricao = preInscricaoBuilder.construir();

        CancelarInscricaoDTO cancelarInscricaoDTO = new CancelarInscricaoDTO();
        cancelarInscricaoDTO.setChave("hjghshadnjb,msa");
        cancelarInscricaoDTO.setId(preInscricao.getId());

        getMockMvc().perform(put( "/api/preinscricao/cancelar")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cancelarInscricaoDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deletarTest() throws Exception {
        PreInscricao preInscricao = preInscricaoBuilder.construir();

        getMockMvc().perform(delete("/api/preinscricao/" + preInscricao.getId()))
                .andExpect(status().isOk());
    }
}