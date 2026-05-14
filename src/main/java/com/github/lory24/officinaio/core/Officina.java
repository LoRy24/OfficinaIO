package com.github.lory24.officinaio.core;

import com.github.lory24.officinaio.ConsoleProvider;
import com.github.lory24.officinaio.ProviderFatture;
import com.github.lory24.officinaio.utils.PrintingUtils;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Getter
public class Officina {
    /* Dettagli Officina */
    private final String indirizzo;
    private final String titolare;
    private final String pIva;
    private final String nome;

    /* Provider della console */
    private ConsoleProvider consoleProvider;

    /* Provider per le fatture */
    private ProviderFatture providerFatture;

    /* Registri Officina */
    private final List<Prenotazione> prenotazioni = new ArrayList<>();
    private int currentCount = 0;

    public void apriOfficina() {
        // Intestazione
        PrintingUtils.printHeader();

        // Logiche
        this.avviaGestorePrenotazioni();

        // Carica il provider per le fatture
        this.providerFatture = new ProviderFatture();
        this.providerFatture.load();

        // Ciclo della console
        this.consoleProvider = new ConsoleProvider(this);
        consoleProvider.launchConsole();

        // Messaggio di saluti
        PrintingUtils.printShutdownMessage();
    }

    /* Prenotazioni */

    private void avviaGestorePrenotazioni() {
        Thread thread = new Thread(() -> {
            try {
                // Ogni 15 secondi controlla
                Thread.sleep(15 * 1000);
                this.prenotazioni.removeIf(Prenotazione::scaduta);
            } catch (InterruptedException ignored) {
            }
        });

        thread.setName("updater-prenotazioni");
    }

    public List<Prenotazione> filtraPrenotazioni(int giorno, int mese, int anno) {
        return prenotazioni.stream()
                .filter(p -> matchData(p, giorno, mese, anno))
                .collect(Collectors.toList());
    }

    private boolean matchData(@NonNull Prenotazione p, int giorno, int mese, int anno) {
        LocalDate data = Instant.ofEpochMilli(p.getData().getTime()).atZone(ZoneId.systemDefault()).toLocalDate();

        if (giorno != -1 && data.getDayOfMonth() != giorno) {
            return false;
        }

        if (mese != -1 && data.getMonthValue() != mese) {
            return false;
        }

        return anno == -1 || data.getYear() == anno;
    }

    public void addPrenotazione(Prenotazione prenotazione) {
        this.prenotazioni.add(prenotazione);
        this.currentCount++;
    }

    public void rimuoviPrenotazione(int id) {
        this.prenotazioni.removeIf(prenotazione -> prenotazione.getID() == id);
    }
}
