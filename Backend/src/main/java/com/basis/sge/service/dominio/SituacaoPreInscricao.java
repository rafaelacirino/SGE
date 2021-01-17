package com.basis.sge.service.dominio;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "situacao_preinscricao")
@Getter
@Setter
public class SituacaoPreInscricao {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "situacao_preinscricao")
    @SequenceGenerator(name = "situacao_preinscricao", sequenceName = "sq_pre", allocationSize = 1)

    @Column(name = "id_situacao")
    private Integer idSituacao;

    @Column(name = "descricao")
    private String descricao;

}
