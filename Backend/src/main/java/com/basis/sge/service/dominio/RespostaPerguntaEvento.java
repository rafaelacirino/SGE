package com.basis.sge.service.dominio;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "respostas_pergunta_evento")

public class RespostaPerguntaEvento implements Serializable {

    @EmbeddedId
    IdPerguntaEventoInscricao id;

    @ManyToOne
    @MapsId("idPergunta")
    @JoinColumn(name = "id_pergunta", referencedColumnName = "id_pergunta")
    private Pergunta pergunta;

    @ManyToOne
    @MapsId("idEvento")
    @JoinColumn(name = "id_evento", referencedColumnName = "id_evento")
    private Evento evento;

    @ManyToOne
    @MapsId("idInscricao")
    @JoinColumn(name = "id_inscricao", referencedColumnName = "id_pre")
    private PreInscricao preInscricao;

    @Column(name = "resposta")
    private Integer resposta;

}
