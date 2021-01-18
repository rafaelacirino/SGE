package com.basis.sge.service.dominio;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.criteria.CriteriaBuilder;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "pre_inscricao")
@Getter
@Setter
public class PreInscricao implements Serializable {

    @Id
    @Column(name = "id_preinscricao")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pre_inscricao")
    @SequenceGenerator(name = "pre_inscricao", sequenceName = "sq_pre", allocationSize = 1)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_evento")
    private Evento evento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_situacao")
    private SituacaoPreInscricao situacaoPreInscricao;

    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "pre_inscricao")
    private List<RespostaPerguntaEvento> respostaPerguntaEventos;
}