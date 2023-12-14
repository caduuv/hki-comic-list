package br.edu.ifce.caduuv.hki.repository;

import br.edu.ifce.caduuv.hki.model.HQ;
import br.edu.ifce.caduuv.hki.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HQRepository extends JpaRepository<HQ, Long> {
    List<HQ> findByUsuario(Usuario usuario);
}
