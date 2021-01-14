package com.basis.sge.service.dominio;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.MapsId;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

@Entity
@Table (name = "pergunta_evento")
@Getter
@Setter


public class PerguntaEvento implements Serializable {

    @EmbeddedId
    IdPerguntaEvento id;

    @ManyToMany
    @MapsId("idPergunta")
    @JoinColumn(name = "id_pergunta", referencedColumnName = "id_pergunta")
    private List<Pergunta> pergunta;

    @ManyToMany
    @MapsId("idEvento")
    @JoinColumn(name = "id_evento", referencedColumnName = "id_evento")
    private List<Evento>     evento;
}
