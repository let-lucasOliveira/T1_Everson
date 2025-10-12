package com.example.t1_everson.model;

import java.io.Serializable;

public class Passageiro implements Serializable {
    private String nome;
    private String cpf;
    private int numeroPoltrona;

    public Passageiro() {
    }


    public Passageiro(String nome, String cpf, int numeroPoltrona) {
        this.nome = nome;
        this.cpf = cpf;
        this.numeroPoltrona = numeroPoltrona;
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public int getNumeroPoltrona() { return numeroPoltrona; }
    public void setNumeroPoltrona(int numeroPoltrona) { this.numeroPoltrona = numeroPoltrona; }
}