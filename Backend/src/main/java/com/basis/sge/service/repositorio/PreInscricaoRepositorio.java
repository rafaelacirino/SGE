package com.basis.sge.service.repositorio;

import com.basis.sge.service.dominio.Evento;
import com.basis.sge.service.dominio.PreInscricao;
import com.basis.sge.service.dominio.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PreInscricaoRepositorio extends JpaRepository<PreInscricao, Integer> {

    void deleteAllByUsuario(Usuario usuario);
    void deleteAllByEvento (Evento evento);
    List<PreInscricao> findByEvento(Evento evento);
    List<PreInscricao> findByUsuario(Usuario usuario);

}
