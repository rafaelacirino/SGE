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
import java.io.Serializable;

@Entity
@Table(name = "pergunta")
@Getter
@Setter
public class Pergunta implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pergunta")
    @SequenceGenerator(name = "pergunta",sequenceName = "sq_pergunta" ,initialValue = 1,allocationSize = 1)
    private Integer id;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "obrigatorio")
    private Integer obrigatorio;
}
