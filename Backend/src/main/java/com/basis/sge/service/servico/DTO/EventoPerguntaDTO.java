package com.basis.sge.service.servico.DTO;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class EventoPerguntaDTO implements Serializable {
    private Integer evento;
    private Integer pergunta;
}
