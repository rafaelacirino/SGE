package com.basis.sge.service.repositorio;

import com.basis.sge.service.dominio.SituacaoPreInscricao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SituacaoPreInscricaoRepositorio extends JpaRepository<SituacaoPreInscricao, Integer> {

}
