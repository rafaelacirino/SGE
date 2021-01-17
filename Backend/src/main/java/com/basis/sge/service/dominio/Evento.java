package com.basis.sge.service.dominio;

import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;


@Entity
@Table(name="evento")
@Getter
@Setter
public class Evento implements Serializable {

    @Id
    @Column(name="id_evento")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "evento")
    @SequenceGenerator(name = "evento",sequenceName = "sq_evento",initialValue = 1,allocationSize = 1)
    private Integer id;

    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Column(name = "periodo_inicio", nullable = false)
    private Timestamp periodoInicio;

    @Column(name = "periodo_fim", nullable = false)
    private Timestamp periodoFim;

    @Column(name = "tipo_insc", nullable = false)
    private Boolean tipoInsc;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "qtd_vagas")
    private Integer qtdVagas;

    @Column(name = "valor")
    private Double valor;

    @Column(name = "local")
    private String local;

    @ManyToMany
    @JoinTable(name="pergunta_evento",
            joinColumns={@JoinColumn(name="id_evento")},
            inverseJoinColumns={@JoinColumn(name="id_pergunta")})
    private List<Pergunta> perguntas;

    @ManyToOne
    @JoinColumn(name = "id_tipo_evento", referencedColumnName = "id_tipo")
    private TipoEvento tipoEvento;

/*    @OneToMany(mappedBy = "evento")
    //@JoinColumn(name = "id_evento", referencedColumnName = "id_evento")
    private List<PreInscricao> preInscricoes;*/
}
