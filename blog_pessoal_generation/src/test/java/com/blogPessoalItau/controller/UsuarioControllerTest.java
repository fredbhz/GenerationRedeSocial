package com.blogPessoalItau.controller;

import com.blogPessoalItau.model.Usuario;
import com.blogPessoalItau.repository.UsuarioRepository;
import com.blogPessoalItau.repository.UsuarioRepositorytest;
import com.blogPessoalItau.service.UsuarioService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.swing.text.html.Option;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioControllerTest {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;


    @BeforeAll
    void star(){
        usuarioRepository.deleteAll();

        usuarioService.cadastrarUsuario(new Usuario(0L, "Root", "root@root.com", "rootroot", "" ));
    }

    @Test
    @DisplayName("Cadastrar um usuário")
    public void deveCriarUmUsuario(){
        HttpEntity<Usuario> corpoRequisicao = new HttpEntity<Usuario>(new Usuario(0L, "Marta Rocha", "martacamroc@gmail.com", "66655566688", "respireialiviado.com"));

        ResponseEntity<Usuario> corpoResposta= testRestTemplate
                .exchange("/usuarios/cadastrar", HttpMethod.POST, corpoRequisicao, Usuario.class);

        assertEquals(HttpStatus.CREATED, corpoResposta.getStatusCode());
        assertEquals(corpoRequisicao.getBody().getNome(), corpoResposta.getBody().getNome());
        assertEquals(corpoRequisicao.getBody().getUsuario(), corpoResposta.getBody().getUsuario());

    }

    @Test
    @DisplayName("Não deve permitir a duplicação de usuários")
    public void naoDeveDuplicarUsuario(){
        usuarioService.cadastrarUsuario(new Usuario(0L, "Marta Rocha", "martacamroc@gmail.com", "66655566688", "respireialiviado.com"));

        HttpEntity<Usuario> corpoRequisicao = new HttpEntity<Usuario>(new Usuario(0L, "Marta Rocha", "martacamroc@gmail.com", "66655566688", "respireialiviado.com"));

        ResponseEntity<Usuario> corpoResposta = testRestTemplate
                .exchange("/usuarios/cadastrar", HttpMethod.POST, corpoRequisicao, Usuario.class);

        assertEquals(HttpStatus.BAD_REQUEST, corpoResposta.getStatusCode());
    }

    @Test
    @DisplayName("Deve Atualizar um usuário")
    public void deveAtualizarUmUsuario(){
        Optional<Usuario> usuarioCadastrado = usuarioService.cadastrarUsuario(new Usuario(0L, "Joaquim Rocha", "joca@gmail.com","55566655577","respire.com"));

        Usuario usuarioUpdate = new Usuario(usuarioCadastrado.get().getId(),
                0L, "Joaquim Rocha", "joca@gmail.com","55566655577","respire.com");

        HttpEntity<Usuario> corpoRequisicao = new HttpEntity<Usuario>(usuarioUpdate);

        ResponseEntity<Usuario> corpoResposta = testRestTemplate
                .withBasicAuth("root@root.com","rootroot")
                .exchange("/usuarios/atualizar", HttpMethod.PUT, corpoRequisicao, Usuario.class);

        assertEquals(HttpStatus.OK, corpoResposta.getStatusCode());
        assertEquals(corpoRequisicao.getBody().getNome(), corpoResposta.getBody().getNome());
        assertEquals(corpoRequisicao.getBody().getUsuario(), corpoResposta.getBody().getUsuario());
    }

    @Test
    @DisplayName("Listar todos os usuários")
    public void deveListarTodosUsuarios(){
        usuarioService.cadastrarUsuario(new Usuario(0L, "Michael Jackson", "mj@gmail.com","11122233388","respireialiviado.com"));
        usuarioService.cadastrarUsuario(new Usuario(0L, "Andre Agassi", "tenis@gmail.com","44455566622","respireialiviado.com"));

        ResponseEntity<String> resposta = testRestTemplate
                .withBasicAuth("root@root.com","rootroot")
                .exchange("/usuarios/all", HttpMethod.GET, null, String.class);

        assertEquals(HttpStatus.OK, resposta.getStatusCode());
    }

}
