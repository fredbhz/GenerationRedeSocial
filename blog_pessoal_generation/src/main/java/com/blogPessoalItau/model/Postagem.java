package com.blogPessoalItau.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.UpdateTimestamp;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/*estamos estabelecendo que essa classe é uma entidade,
em que será usado para a modelagem dos dados da tabela, ou seja, os atributos dessa entidade.
Uma entidade é uma representação de um conjunto de informações sobre determinado conceito do sistema.
Toda entidade possui ATRIBUTOS, que são as informações que referenciam a entidade.*/
@Entity
@Table(name = "tb_postagem")
public class Postagem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @NotBlank(message = "o título não pode ser uma string vazia")
    @Size(min = 5, max = 100, message = "a quantidade minima de caracteres é 5 e o maximo é 100")
    private String titulo;

    @NotBlank(message = "o texto não pode ser uma string vazia")
    @Size(min= 10, max= 1000, message = "a quantidade minima de caracteres é 5 e o maximo é 1000")
    private String texto;

    @UpdateTimestamp
    private LocalDateTime data;

    @ManyToOne
    @JsonIgnoreProperties("postagem")
    private Tema tema;

    @ManyToOne
    @JsonIgnoreProperties("postagem")
    private Usuario usuario;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Tema getTema() {
        return tema;
    }

    public void setTema(Tema tema) {
        this.tema = tema;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }
}
