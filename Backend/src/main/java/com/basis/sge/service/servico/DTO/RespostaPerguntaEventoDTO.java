package com.basis.sge.service.servico.DTO;

import com.basis.sge.service.dominio.Evento;
import com.basis.sge.service.dominio.IdPerguntaEventoInscricao;
import com.basis.sge.service.dominio.Pergunta;
import com.basis.sge.service.dominio.PreInscricao;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class RespostaPerguntaEventoDTO implements Serializable {
    IdPerguntaEventoInscricao id;
    private Pergunta pergunta;
    private Evento evento;
    private PreInscricao preInscricao;
    private String resposta;

}
