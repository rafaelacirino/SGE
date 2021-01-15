package com.basis.sge.service.repositorio;

import com.basis.sge.service.dominio.Pergunta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerguntaRepositorio extends JpaRepository<Pergunta,Integer> {
}
