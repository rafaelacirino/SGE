package com.basis.sge.service.repositorio;

import com.basis.sge.service.dominio.RespostaPerguntaEvento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RespostaPerguntaEventoRepositorio extends JpaRepository<RespostaPerguntaEvento, Integer> {
}
