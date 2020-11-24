package br.com.fuzari.agenda.models;

import java.io.Serializable;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class Aluno implements Serializable {
    private Long id;
    private String nome;
    private String telefone;
    private String email;
    private Number peso;

    public Aluno() {

    }

    public Aluno(String nome, String telefone, String email, Number peso) {
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.peso = peso;
    }

}
