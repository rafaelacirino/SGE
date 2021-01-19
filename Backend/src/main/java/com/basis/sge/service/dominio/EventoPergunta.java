package com.basis.sge.service.dominio;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "pergunta_evento")
@Getter
@Setter
public class EventoPergunta implements Serializable {

    @EmbeddedId
    private IdEventoPergunta id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idEvento")
    @JoinColumn(name = "id_evento")
    private Evento evento;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idPergunta")
    @JoinColumn(name = "id_pergunta")
    private Pergunta pergunta;
}
