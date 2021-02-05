package com.basis.sge.service.servico.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class CancelarInscricaoDTO implements Serializable {

    private Integer id;
    private String chave;
}
