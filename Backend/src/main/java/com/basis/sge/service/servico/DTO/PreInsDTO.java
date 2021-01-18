package com.basis.sge.service.servico.DTO;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter

public class PreInsDTO implements Serializable {
    private Integer id;
    private Integer idUsuario;
    private Integer idEvento;
    private Integer idSituacaoPreInscricao;
}
