package com.basis.sge.service.servico.DTO;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class SituacaoPreInscricaoDTO implements Serializable {
    private Integer id;
    private String descricao;

}
