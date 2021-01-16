package com.basis.sge.service.dominio;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;

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

    @ManyToOne
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    private Usuario usuario;

/*    @ManyToOne
    @JoinColumn(name = "id_evento", referencedColumnName = "id_evento")
    private Evento evento;*/

    @ManyToOne
    @JoinColumn(name = "id_tipo_situacao", referencedColumnName = "id_situacao")
    private SituacaoPreInscricao situacaoPreInscricao;
}
