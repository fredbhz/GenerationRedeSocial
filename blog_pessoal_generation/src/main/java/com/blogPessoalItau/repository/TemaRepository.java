package com.blogPessoalItau.repository;

import com.blogPessoalItau.model.Tema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TemaRepository extends JpaRepository<Tema, Long> {

    //encontre todos os items de descricao, independente se a letra é maiuscula ou minuscula
    List<Tema> findAllByDescricaoContainingIgnoreCase(@Param("descricao") String descricao);
}
