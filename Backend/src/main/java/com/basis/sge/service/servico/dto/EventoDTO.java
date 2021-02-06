package com.basis.sge.service.servico.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class EventoDTO implements Serializable {

    private Integer id;

    @NotNull(message = "Titulo não pode ser nulo")
    private String titulo;

    @NotNull(message = "Periodo de inicio do evento não pode ser nulo")
    private LocalDateTime periodoInicio;

    @NotNull(message = "Periodo de inicio do evento não pode ser nulo")
    private LocalDateTime periodoFim;
    private Boolean tipoInsc;
    private String descricao;
    private Integer qtdVagas;

    @NotNull(message = "Evento não pode ser nulo")
    private Integer idTipoEvento;
    private Double valor;
    private String local;
    private List<EventoPerguntaDTO> perguntas;

}
