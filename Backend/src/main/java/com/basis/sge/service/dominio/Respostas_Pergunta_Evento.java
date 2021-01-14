package com.basis.sge.service.dominio;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "respostas_pergunta_evento")
@Getter
@Setter
public class Respostas_Pergunta_Evento {
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "id_pergunta")
    //@OneToOne()
    //@JoinColumn(name = "id_pergunta", referencedColumnName = "id_pergunta")
    private Integer idPergunta;

    @Column(name = "id_usuario")
    //@OneToOne()
    //@JoinColumn(name = "id_usuario", referencedColumnName = "usuario")
    private Integer idUsuario;

    @Column(name = "id_evento")
    //@OneToOne()
    //@JoinColumn(name = "id_evento", referencedColumnName = "id_evento")
    private Integer idEvento;
}
