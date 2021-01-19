package com.basis.sge.service.servico.DTO;

import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class EventoDTO implements Serializable {
    private Integer id;
    private String titulo;
    private LocalDateTime periodoInicio;
    private LocalDateTime periodoFim;
    private Boolean tipoInsc;
    private String descricao;
    private Integer qtdVagas;
    private Integer idTipoEvento;
    private Double valor;
    private String local;
    private List<EventoPerguntaDTO> perguntas;

}
