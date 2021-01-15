package com.basis.sge.service.dominio;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "respostas_pergunta_evento")

public class RespostaPerguntaEvento implements Serializable {


    @EmbeddedId
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "resposta")
    @SequenceGenerator(name = "resposta", sequenceName = "sq_resposta", allocationSize = 1,initialValue = 1)
    IdPerguntaEventoInscricao id;

    @ManyToOne
    @MapsId("id_pergunta")
    @JoinColumn(name = "id_pergunta", referencedColumnName = "id_pergunta")
    private Pergunta pergunta;

    @ManyToOne
    @MapsId("id_evento")
    @JoinColumn(name = "id_evento", referencedColumnName = "id_evento")
    private Evento evento;

    @ManyToOne
    @MapsId("id_inscricao")
    @JoinColumn(name = "id_inscricao", referencedColumnName = "id_pre")
    private PreInscricao preInscricao;

    @Column(name = "resposta")
    private String resposta;

}
