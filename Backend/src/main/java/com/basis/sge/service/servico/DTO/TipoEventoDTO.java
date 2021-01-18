package com.basis.sge.service.servico.DTO;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

@Getter
@Setter
public class TipoEventoDTO implements Serializable {
    private Integer id;
    private String descricao;
}
