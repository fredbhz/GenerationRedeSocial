package com.blogPessoalItau.controller;


import com.blogPessoalItau.model.Tema;
import com.blogPessoalItau.repository.TemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/temas")
    @CrossOrigin(value = "*", allowedHeaders = "*")
public class TemaController {
    @Autowired
    private TemaRepository temaRepository;

    //retorna todos os temas do banco de dados
    @GetMapping
    public ResponseEntity<List<Tema>> getAll() { return ResponseEntity.ok(temaRepository.findAll());}

    //busca um tema especifico através do id
    @GetMapping("/{id}")
    public ResponseEntity<Tema> getById (@PathVariable Long id) {
        return temaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    //busca todos os temas que contenha a descricao
    @GetMapping("/descricao/{descricao}")
    public ResponseEntity<List<Tema>> getByDescricao (@PathVariable String descricao){
        return ResponseEntity.ok(temaRepository.findAllByDescricaoContainingIgnoreCase(descricao));
    }

    //precisamos do requestbody para que possamos passar informações através do JSON e assim salvar
    //quando queremos salvar dados no banco de dados, devemos retornar o status devido para a criação e salvar os dados
    @PostMapping
    public ResponseEntity<Tema> post(@RequestBody @Valid Tema tema){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(temaRepository.save(tema));
    }

    //aqui vamos pegar o id que vai ser passado na requestbody(JSON) e assim poder atualizar as informações
    @PutMapping
    public ResponseEntity<Tema> put(@RequestBody @Valid Tema tema){
        return temaRepository.findById(tema.getId())
                .map(response -> ResponseEntity.status(HttpStatus.CREATED)
                .body(temaRepository.save(tema)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    //a função é void pois não tem retorno, é apenas a remoção do item através do id
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        Optional<Tema> tema = temaRepository.findById(id);
                if(tema.isEmpty())
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        temaRepository.findById(id);
    }

}
