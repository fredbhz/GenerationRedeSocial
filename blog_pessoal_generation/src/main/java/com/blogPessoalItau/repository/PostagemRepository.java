package com.blogPessoalItau.repository;

import com.blogPessoalItau.model.Postagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostagemRepository extends JpaRepository<Postagem, Long> {

    List<Postagem> findAllByTituloContainingIgnoreCase(@Param("titulo") String titulo);
}
