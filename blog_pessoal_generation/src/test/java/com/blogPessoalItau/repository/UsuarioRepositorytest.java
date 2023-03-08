package com.blogPessoalItau.repository;

import com.blogPessoalItau.model.Usuario;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioRepositorytest {
    @Autowired
    UsuarioRepository usuarioRepository;

    @BeforeAll
    void start(){
        usuarioRepository.deleteAll();

        usuarioRepository.save(new Usuario(0L, "João da Silva", "joao@gmail.com", "12345678", "caminhoDaFoto.com"));
        usuarioRepository.save(new Usuario(0L, "Manuela da Silva", "manuela@gmail.com", "12345678","caminhoDaFotoManu.com"));
        usuarioRepository.save(new Usuario(0L, "Adriana da Silva", "adriana@gmail.com", "12345678", "caminhoDaFotoAdri.com"));
        usuarioRepository.save(new Usuario(0L, "Paulo Oliveira", "paulo@gmail.com", "12345678", "caminhoDaFotoPaulo.com"));
    }

    @Test
    @DisplayName("Retornar 1 usuário")
    public void deveRetornarUmUsuario(){
        Optional<Usuario> usuario = usuarioRepository.findByUsuario("joao@gmail.com");
        assertEquals("joao@gmail.com", usuario.get().getUsuario());

    }

    @Test
    @DisplayName("Retornar 3 usuários")
    public void deveRetornsarTresUsuarios(){
        List<Usuario> listaDeUsuarios = usuarioRepository.findAllByNomeContainingIgnoreCase("Silva");
        assertEquals(3, listaDeUsuarios.size());
        assertEquals("João da Silva", listaDeUsuarios.get(0).getNome());
        assertEquals("Manuela da Silva", listaDeUsuarios.get(1).getNome());
        assertEquals("Adriana da Silva", listaDeUsuarios.get(2).getNome());
    }
}
