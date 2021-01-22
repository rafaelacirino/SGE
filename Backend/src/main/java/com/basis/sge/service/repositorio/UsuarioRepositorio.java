package com.basis.sge.service.repositorio;
import com.basis.sge.service.dominio.Usuario;
import com.sun.el.stream.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.function.Supplier;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Integer> {

    List<Usuario> findByEmail(String email);
    List<Usuario> findByCpf(String cpf);
    Usuario findByChaveUnica(String chave);

}
