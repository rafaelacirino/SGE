package com.basis.sge.service.repositorio;

<<<<<<< HEAD
public interface UsuarioRepositorio {
=======
import com.basis.sge.service.dominio.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface UsuarioRepositorio extends JpaRepository<Usuario, Integer> {
    Usuario findByEmail(String email);
    Usuario findByCpf(String cpf);
>>>>>>> featureRafaela
}
