package com.github.lory24.officinaio.core;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Getter
public class Prenotazione {
    private final int ID;
    private final String nome;
    private final String cognome;
    private final Date data;
    private final List<Servizio> servizi = new ArrayList<>();

    public Prenotazione(int id, String nome, String cognome, Date data, Servizio... servizi) {
        ID = id;
        this.nome = nome;
        this.cognome = cognome;
        this.data = data;
        this.servizi.addAll(Arrays.asList(servizi));
    }

    /* Calcoli */

    public double calcolaTotale() {
        double totale = 0.0f;

        for (Servizio servizio: servizi) {
            totale += servizio.calcolaCosto();
        }

        return totale;
    }

    public boolean scaduta() {
        return this.dateToMillis() < System.currentTimeMillis();
    }

    public long dateToMillis() {
        return this.data.getTime();
    }

    public void stampaPrenotazione() {
        System.out.println("--------------------------------------------------");
        System.out.println();
        System.out.println("ID: " + ID);
        System.out.println("Nome: " + nome);
        System.out.println("Cognome: " + cognome);
        System.out.println("Data: " + data);
        System.out.println();

        // Stampa i servizi
        int n = 1;
        for (Servizio servizio: servizi) {
            System.out.println("--------------------------------------------------");
            System.out.println();
            System.out.println("SERVIZIO N." + n);
            System.out.println();
            System.out.println(servizio);
            System.out.println();
        }

        // Stampa il totale
        System.out.println("--------------------------------------------------");
        System.out.println();
        System.out.println("TOTALE PRENOTAZIONE: " + this.calcolaTotale() + " Euro");
        System.out.println();
        System.out.println("--------------------------------------------------");
    }
}
