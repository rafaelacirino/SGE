package com.basis.sge.service.dominio;

import lombok.Getter;
import lombok.Setter;


import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table (name = "pergunta_evento")
@Getter
@Setter


public class PerguntaEvento implements Serializable {

    @EmbeddedId
    IdPerguntaEvento id;

    @Column(name = "id_pergunta")
    @ManyToOne
    @JoinColumn(name = "id_pergunta", referencedColumnName = "id_pergunta")
    private Pergunta pergunta;

    @Column(name = "id_evento")
    @ManyToOne
    @JoinColumn(name = "id_evento", referencedColumnName = "id_evento")
    private Evento evento;
}
