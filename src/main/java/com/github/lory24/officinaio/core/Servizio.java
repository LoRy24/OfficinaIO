package com.github.lory24.officinaio.core;

import com.github.lory24.officinaio.core.veicoli.Veicolo;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public class Servizio {
    private final Veicolo veicolo;
    private final TipoServizio tipoServizio;
    private final List<Operazione> operazioni = new ArrayList<>();

    public Servizio(Veicolo veicolo, TipoServizio tipo, Operazione... operazioni) {
        this.veicolo = veicolo;
        this.tipoServizio = tipo;
        this.operazioni.addAll(Arrays.asList(operazioni));
    }

    public double calcolaCosto() {
        double totale = 0.0f;
        for (Operazione operazione: operazioni)
            totale += operazione.prezzo;

        return totale;
    }

    // Tipo per le operazioni
    public record Operazione(String descrizione, double prezzo) { }
}
