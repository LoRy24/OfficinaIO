package com.github.lory24.officinaio.core;

import com.github.lory24.officinaio.ConsoleProvider;
import com.github.lory24.officinaio.utils.PrintingUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Getter
public class Officina {
    /* Dettagli Officina */
    private final String indirizzo;
    private final String titolare;

    /* Provider della console */
    private ConsoleProvider consoleProvider;

    /* Registri Officina */
    private final List<Prenotazione> prenotazioni = new ArrayList<>();

    public void apriOfficina() {
        // Intestazione
        PrintingUtils.printHeader();

        // Logiche
        this.avviaGestorePrenotazioni();

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
}
