package com.github.lory24.officinaio.core;

import com.github.lory24.officinaio.core.veicoli.Veicolo;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Getter
public class Prenotazione {
    private final String nome;
    private final String cognome;
    private final Date data;
    private final List<Servizio> servizi = new ArrayList<>();

    public Prenotazione(String nome, String cognome, Date data, Servizio... servizi) {
        this.nome = nome;
        this.cognome = cognome;
        this.data = data;
        this.servizi.addAll(Arrays.asList(servizi));
    }

    /* Aggiunta servizi */

    public void aggiungiServizio(Servizio servizio) {
        this.servizi.add(servizio);
    }

    public void aggiungiServizio(Veicolo veicolo, TipoServizio tipo, Servizio.Operazione... operazioni) {
        this.aggiungiServizio(new Servizio(veicolo, tipo, operazioni));
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
        return this.data.getTime() < System.currentTimeMillis();
    }
}
