package com.github.lory24.officinaio.core;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Fattura {
    /* DATI CLIENTE */
    private final String nomeCliente;
    private final String cognomeCliente;
    private final String dataDiNascitaCliente;
    private final String luogoDiNascitaCliente;
    private final String residenzaCliente;
    private final String codiceFiscale;

    /* DATI OFFICINA */
    private final String ragioneSociale;
    private final String pIva;
    private final String indirizzoSede;

    /* DATI FATTURA */
    private final int numero;
    private final List<Voce> voci = new ArrayList<>();

    public Fattura(String nomeCliente, String cognomeCliente, String dataDiNascitaCliente, String luogoDiNascitaCliente, String residenzaCliente, String codiceFiscale, @NotNull Officina officina, int numero) {
        this.nomeCliente = nomeCliente;
        this.cognomeCliente = cognomeCliente;
        this.dataDiNascitaCliente = dataDiNascitaCliente;
        this.luogoDiNascitaCliente = luogoDiNascitaCliente;
        this.residenzaCliente = residenzaCliente;
        this.codiceFiscale = codiceFiscale;

        this.ragioneSociale = officina.getNome();
        this.pIva = officina.getPIva();
        this.indirizzoSede = officina.getIndirizzo();

        this.numero = numero;
    }

    public void addVoce(Voce voce) {
        this.voci.add(voce);
    }

    public double calcolaTotale() {
        double totale = 0;
        for (Voce v: this.voci) {
            totale += v.totaleNoIva();
        }
        return totale;
    }

    public double calcolaTotaleIva() {
        double totale = calcolaTotale();
        totale *= 1.22;
        return totale;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder()
                .append("-----------------------------------------------------------------------------------\n")
                .append("\n")
                .append("FATTURA #").append(this.numero).append("\n")
                .append("\n")
                .append("-----------------------------------------------------------------------------------\n")
                .append("\n")
                .append("CLIENTE:\n")
                .append("Nome e Cognome: ").append(this.nomeCliente).append(" ").append(this.cognomeCliente).append("\n")
                .append("Data di Nascita: ").append(this.dataDiNascitaCliente).append("\n")
                .append("Luogo di Nascita: ").append(this.luogoDiNascitaCliente).append("\n")
                .append("Residenza: ").append(this.residenzaCliente).append("\n")
                .append("C.F.: ").append(this.codiceFiscale).append("\n")
                .append("\n")
                .append("-----------------------------------------------------------------------------------\n")
                .append("\n")
                .append("ENTE:\n")
                .append("Ragione Sociale: ").append(this.ragioneSociale).append("\n")
                .append("Partita IVA: ").append(this.pIva).append("\n")
                .append("Indirizzo: ").append(this.indirizzoSede).append("\n")
                .append("\n")
                .append("-----------------------------------------------------------------------------------\n")
                .append("\n")
                .append("VOCI")
                .append("\n")
                .append("-----------------------------------------------------------------------------------\n")
                .append("\n");

        // Aggiungi le voci
        for (Voce v: this.voci) {
            // Crea la stringa per la voce
            StringBuilder voce = new StringBuilder()
                    .append("Descrizione: ").append(v.nome).append("\n")
                    .append("Operazioni:\n");

            for (String op: v.operazioni) {
                voce.append("- ").append(op).append("\n");
            }

            voce.append("Totale: ").append(v.totaleNoIva).append("\n");

            // Aggiungila alla fattura
            builder.append(voce);
        }

        // Aggiungi i totali
        builder.append("\n")
                .append("-----------------------------------------------------------------------------------\n")
                .append("\n")
                .append("TOTALE (no IVA): ").append(this.calcolaTotale()).append("\n")
                .append("TOTALE (+22% IVA): ").append(this.calcolaTotaleIva()).append("\n")
                .append("\n")
                .append("-----------------------------------------------------------------------------------\n")
        ;

        return builder.toString();
    }

    public record Voce(String nome, List<String> operazioni, double totaleNoIva) {

    }
}
