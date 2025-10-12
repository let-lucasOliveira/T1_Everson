package com.example.t1_everson.model;


public class Viagem {
    private String horarioSaida;
    private String horarioChegada;
    private String origem;
    private String destino;
    private double preco;

    public Viagem() {
    }


    public Viagem(String horarioSaida, String horarioChegada, String origem, String destino, double preco) {
        this.horarioSaida = horarioSaida;
        this.horarioChegada = horarioChegada;
        this.origem = origem;
        this.destino = destino;
        this.preco = preco;
    }

    public String getHorarioSaida() { return horarioSaida; }
    public String getHorarioChegada() { return horarioChegada; }
    public String getOrigem() { return origem; }
    public String getDestino() { return destino; }
    public double getPreco() { return preco; }

}