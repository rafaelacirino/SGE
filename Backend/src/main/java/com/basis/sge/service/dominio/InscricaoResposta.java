package com.basis.sge.service.dominio;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "respostas_pergunta_evento")
public class InscricaoResposta implements Serializable {

    @EmbeddedId
    private IdInscricaoResposta id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idPergunta")
    @JoinColumn(name = "id_pergunta")
    private Pergunta pergunta;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idEvento")
    @JoinColumn(name = "id_evento")
    private Evento evento;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idInscricao")
    @JoinColumn(name = "id_inscricao")
    private PreInscricao preInscricao;

    @Column(name = "resposta")
    private String resposta;
}
