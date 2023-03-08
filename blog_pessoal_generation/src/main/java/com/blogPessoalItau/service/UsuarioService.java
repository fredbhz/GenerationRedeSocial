package com.blogPessoalItau.service;

import com.blogPessoalItau.model.UserLogin;
import com.blogPessoalItau.model.Usuario;
import com.blogPessoalItau.repository.UsuarioRepository;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Optional;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    //aplicamos uma regra de negocio referente a cadastrar o usuario, sendo assim, a senha já é salva sendo encriptada
    public Optional<Usuario> cadastrarUsuario(Usuario usuario) {

        if (usuarioRepository.findByUsuario(usuario.getUsuario()).isPresent())
            return Optional.empty();

        //pega o atributo senha e faça uma criptografia dela e defina uma nova senha encriptada
        usuario.setSenha(criptografarSenha(usuario.getSenha()));
        //salve a nova senha encriptada
        return Optional.of(usuarioRepository.save(usuario));

    }

    public Optional<Usuario> atualizarUsuario(Usuario usuario) {

        if(usuarioRepository.findById(usuario.getId()).isPresent()) {

            Optional<Usuario> buscaUsuario = usuarioRepository.findByUsuario(usuario.getUsuario());

            if ( (buscaUsuario.isPresent()) && (!Objects.equals(buscaUsuario.get().getId(), usuario.getId())))
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Usuário já existe!", null);

            usuario.setSenha(criptografarSenha(usuario.getSenha()));

            return Optional.of(usuarioRepository.save(usuario));

        }

        return Optional.empty();

    }
    public Optional<UserLogin> autenticarUsuario(Optional<UserLogin> usuarioLogin) {

        Optional<Usuario> usuario = usuarioRepository.findByUsuario(usuarioLogin.get().getUsuario());

        if (usuario.isPresent()) {

            if (compararSenhas(usuarioLogin.get().getSenha(), usuario.get().getSenha())) {

                usuarioLogin.get().setId(usuario.get().getId());
                usuarioLogin.get().setName(usuario.get().getNome());
                usuarioLogin.get().setFoto(usuario.get().getFoto());
                usuarioLogin.get().setToken(gerarBasicToken(usuarioLogin.get().getUsuario(), usuarioLogin.get().getSenha()));
                usuarioLogin.get().setSenha(usuario.get().getSenha());

                return usuarioLogin;

            }
        }

        return Optional.empty();

    }


    private String criptografarSenha(String senha) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        return encoder.encode(senha);

    }

    private boolean compararSenhas(String senhaDigitada, String senhaBanco) {
        //compara as duas senhas, a que está encriptada e registrada no banco de dados
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        return encoder.matches(senhaDigitada, senhaBanco);

    }

    private String gerarBasicToken(String usuario, String senha) {

        String token = usuario + ":" + senha;
        byte[] tokenBase64 = Base64.encodeBase64(token.getBytes(StandardCharsets.US_ASCII));
        return "Basic " + new String(tokenBase64);

    }
}
