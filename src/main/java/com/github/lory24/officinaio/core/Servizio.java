package com.github.lory24.officinaio.core;

import com.github.lory24.officinaio.core.veicoli.Veicolo;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

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
        double totale = tipoServizio.getPrezzoServizio();
        for (Operazione operazione: operazioni)
            totale += operazione.prezzo;

        return totale;
    }

    // Tipo per le operazioni
    public record Operazione(String descrizione, double prezzo) {
        @Override
        public @NotNull String toString() {
            return descrizione + " -> " + prezzo;
        }
    }

    @Override
    public String toString() {
        // Crea la lista delle operazioni
        StringBuilder operationsBuilder = new StringBuilder();
        for (Operazione operazione: operazioni)
            operationsBuilder.append("- ").append(operazione.toString()).append(" Euro").append("\n");

        return "Tipo: " +  this.tipoServizio.getTipo() + "\n" +
                "Prezzo base: " + this.tipoServizio.getPrezzoServizio() + " Euro\n" +
                "Veicolo:\n" + this.veicolo.toString() +
                "Operazioni:\n" + operationsBuilder +
                "TOTALE SERVIZIO: " + this.calcolaCosto() + " Euro\n";
    }
}
