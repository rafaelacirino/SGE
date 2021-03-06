package com.basis.sge.service.dominio;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
public class IdInscricaoResposta implements Serializable {

    private Integer idPergunta;
    private Integer idEvento;
    private Integer idInscricao;
}
