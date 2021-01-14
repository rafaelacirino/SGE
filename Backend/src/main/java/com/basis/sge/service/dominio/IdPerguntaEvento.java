package com.basis.sge.service.dominio;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;


@Embeddable
public class IdPerguntaEvento implements Serializable {

    @Column(name = "id_pergunta")
    Integer idPergunta;
    @Column(name = "id_evento")
    Integer idEvento;


}
