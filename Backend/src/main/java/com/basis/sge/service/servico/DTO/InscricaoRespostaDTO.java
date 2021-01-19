package com.basis.sge.service.servico.DTO;

import com.basis.sge.service.dominio.IdInscricaoResposta;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class InscricaoRespostaDTO implements Serializable {
    IdInscricaoResposta id;
    private Integer idPergunta;
    private Integer idEvento;
    private Integer idPreInscricao;
    private String resposta;

}
