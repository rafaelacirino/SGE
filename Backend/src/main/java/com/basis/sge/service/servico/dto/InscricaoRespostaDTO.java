package com.basis.sge.service.servico.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import com.basis.sge.service.dominio.IdInscricaoResposta;


@Getter
@Setter
public class InscricaoRespostaDTO implements Serializable {

    IdInscricaoResposta id;
    private Integer idPergunta;
    private Integer idEvento;
    private Integer idInscricao;
    private String resposta;

}
