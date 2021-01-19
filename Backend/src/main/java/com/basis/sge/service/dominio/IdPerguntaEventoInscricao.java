package com.basis.sge.service.dominio;

import lombok.Getter;
import lombok.Setter;
<<<<<<< HEAD
=======

>>>>>>> sprint-1
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
public class IdPerguntaEventoInscricao implements Serializable {

    private Integer idPergunta;
    private Integer idEvento;
    private Integer idInscricao;
}
