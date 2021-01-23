package com.basis.sge.service.web.rest;

import com.basis.sge.service.builder.EventoBuilder;
import com.basis.sge.service.dominio.Evento;
import com.basis.sge.service.repositorio.EventoRepositorio;
import com.basis.sge.service.servico.DTO.EventoDTO;
import com.basis.sge.service.servico.DTO.EventoPerguntaDTO;
import com.basis.sge.service.servico.mapper.EventoMapper;
import com.basis.sge.service.util.IntTestComum;
import com.basis.sge.service.util.TestUtil;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@Transactional
public class EventoRecursoIT extends IntTestComum {

    @Autowired
    private EventoBuilder eventoBuilder;

    @Autowired
    private EventoMapper eventoMapper;

    @Autowired
    private EventoRepositorio eventoRepositorio;

    @BeforeEach
    public void inicializar() {
        eventoRepositorio.deleteAll();
    }

    @Test
    public void listarTest() throws Exception {
        eventoBuilder.construir();
        getMockMvc().perform(get( "/api/eventos"))
                .andExpect(status().isOk());
    }

    @Test
    public void salvarTest() throws Exception {

        Evento evento = eventoBuilder.construirEntidade();
        EventoDTO eventoDTO = eventoMapper.toDto(evento);
        EventoPerguntaDTO eventoPergunta = new EventoPerguntaDTO();
        List<EventoPerguntaDTO> eventoPerguntaDTOS = new ArrayList<>();
        eventoPerguntaDTOS.add(eventoPergunta);
        eventoDTO.setPerguntas(eventoPerguntaDTOS);

        getMockMvc().perform(post( "/api/eventos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(eventoDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    public void salvarTesteTituloNull() throws Exception {

        Evento evento = eventoBuilder.construirEntidade();
        evento.setTitulo(null);

        String result = getMockMvc().perform(post( "/api/eventos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(eventoMapper.toDto(evento))))
                .andExpect(status().isBadRequest()).andReturn().getResolvedException().getMessage();
                Assert.assertEquals("Titulo do evento não pode ser vazio",result);
    }

    @Test
    public void salvarTestePeriodiInicioNull() throws Exception {

        Evento evento = eventoBuilder.construirEntidade();
        evento.setPeriodoInicio(null);

        String result = getMockMvc().perform(post( "/api/eventos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(eventoMapper.toDto(evento))))
                .andExpect(status().isBadRequest()).andReturn().getResolvedException().getMessage();
                Assert.assertEquals("Periodo inicio não pode ser vazio",result);
    }

    @Test
    public void salvarTestePeriodiFimNull() throws Exception {

        Evento evento = eventoBuilder.construirEntidade();
        evento.setPeriodoFim(null);

        String result = getMockMvc().perform(post( "/api/eventos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(eventoMapper.toDto(evento))))
                .andExpect(status().isBadRequest()).andReturn().getResolvedException().getMessage();
                Assert.assertEquals("Periodo fim não pode ser vazio",result);
    }

    @Test
    public void salvarTesteTipoInscNull() throws Exception {

        Evento evento = eventoBuilder.construirEntidade();
        evento.setTipoInsc(null);

        String result = getMockMvc().perform(post( "/api/eventos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(eventoMapper.toDto(evento))))
                .andExpect(status().isBadRequest()).andReturn().getResolvedException().getMessage();
                Assert.assertEquals("Tipo Inscricao não pode ser vazio",result);
    }

    @Test
    public void editarTestTituloCerto() throws Exception {

        Evento evento = eventoBuilder.construir();
        evento.setTitulo("Alterando o título do Evento");

        getMockMvc().perform(put( "/api/eventos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(eventoMapper.toDto(evento))))
                .andExpect(status().isOk());
    }

    @Test
    public void editarTestPeriodoInicioCerto() throws Exception {

        Evento evento = eventoBuilder.construir();
        evento.setPeriodoInicio(LocalDateTime.now());

        getMockMvc().perform(put( "/api/eventos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(eventoMapper.toDto(evento))))
                .andExpect(status().isOk());
    }

    @Test
    public void editarTestPeriodoFimCerto() throws Exception {

        Evento evento = eventoBuilder.construir();
        evento.setPeriodoFim(LocalDateTime.now());

        getMockMvc().perform(put( "/api/eventos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(eventoMapper.toDto(evento))))
                .andExpect(status().isOk());
    }

    @Test
    public void editarTestTipoInscCerto() throws Exception {

        Evento evento = eventoBuilder.construir();
        evento.setTipoInsc(false);

        getMockMvc().perform(put( "/api/eventos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(eventoMapper.toDto(evento))))
                .andExpect(status().isOk());
    }

    @Test
    public void editarDescricaoCerto() throws Exception {

        Evento evento = eventoBuilder.construir();
        evento.setDescricao("Alterando a descrição do Evento");

        getMockMvc().perform(put( "/api/eventos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(eventoMapper.toDto(evento))))
                .andExpect(status().isOk());
    }

    @Test
    public void editarQtdVagasCerto() throws Exception {

        Evento evento = eventoBuilder.construir();
        evento.setQtdVagas(500);

        getMockMvc().perform(put( "/api/eventos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(eventoMapper.toDto(evento))))
                .andExpect(status().isOk());
    }

    @Test
    public void editarValorCerto() throws Exception {

        Evento evento = eventoBuilder.construir();
        evento.setValor(137.90);

        getMockMvc().perform(put( "/api/eventos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(eventoMapper.toDto(evento))))
                .andExpect(status().isOk());
    }

    @Test
    public void editarLocalCerto() throws Exception {

        Evento evento = eventoBuilder.construir();
        evento.setLocal("Campina Grande - PB");

        getMockMvc().perform(put( "/api/eventos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(eventoMapper.toDto(evento))))
                .andExpect(status().isOk());
    }

    @Test
    public void editarTituloNull() throws Exception {

        Evento evento = eventoBuilder.construirEntidade();
        evento.setTitulo(null);

        String result = getMockMvc().perform(post( "/api/eventos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(eventoMapper.toDto(evento))))
                .andExpect(status().isBadRequest()).andReturn().getResolvedException().getMessage();
                Assert.assertEquals("Titulo do evento não pode ser vazio",result);;
    }

    @Test
    public void editarPeriodoInicioNull() throws Exception {

        Evento evento = eventoBuilder.construirEntidade();
        evento.setPeriodoInicio(null);

        String result = getMockMvc().perform(post( "/api/eventos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(eventoMapper.toDto(evento))))
                .andExpect(status().isBadRequest()).andReturn().getResolvedException().getMessage();
                Assert.assertEquals("Periodo inicio não pode ser vazio",result);
    }

    @Test
    public void editarPeriodoFimNull() throws Exception {

        Evento evento = eventoBuilder.construirEntidade();
        evento.setPeriodoFim(null);

        String result = getMockMvc().perform(post( "/api/eventos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(eventoMapper.toDto(evento))))
                .andExpect(status().isBadRequest()).andReturn().getResolvedException().getMessage();
                Assert.assertEquals("Periodo fim não pode ser vazio",result);
    }

    @Test
    public void editarTipoInscNull() throws Exception {

        Evento evento = eventoBuilder.construirEntidade();
        evento.setTipoInsc(null);

        String result = getMockMvc().perform(post( "/api/eventos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(eventoMapper.toDto(evento))))
                .andExpect(status().isBadRequest()).andReturn().getResolvedException().getMessage();
                Assert.assertEquals("Tipo Inscricao não pode ser vazio",result);
    }


    @Test
    public void editarTipoEventoNull() throws Exception {

        Evento evento = eventoBuilder.construirEntidade();
        evento.setTipoEvento(null);

        String result = getMockMvc().perform(post( "/api/eventos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(eventoMapper.toDto(evento))))
                .andExpect(status().isBadRequest()).andReturn().getResolvedException().getMessage();
                Assert.assertEquals("O tipo do evento não pode ser vazio",result);
    }


    @Test
    public void deletarTest() throws Exception {

        Evento evento = eventoBuilder.construir();

        getMockMvc().perform(delete("/api/eventos/" + evento.getId()))
                .andExpect(status().isOk());
    }

    @Test
    public void deletarTestIdErrado() throws Exception{

        Evento evento = eventoBuilder.construir();
        evento.setId(500);

        String result = getMockMvc().perform(delete("/api/eventos/" + evento.getId()))
                .andExpect(status().isBadRequest()).andReturn().getResolvedException().getMessage();
                Assert.assertEquals("Evento com esse id não existe",result);
    }
}
