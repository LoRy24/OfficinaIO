package com.github.lory24.officinaio.core;

import com.github.lory24.officinaio.ConsoleProvider;
import com.github.lory24.officinaio.utils.PrintingUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Officina {
    /* Dettagli Officina */
    private final String indirizzo;
    private final String titolare;

    /* Provider della console */
    private ConsoleProvider consoleProvider;

    /* Registri Officina */

    public void apriOfficina() {
        // Intestazione
        PrintingUtils.printHeader();

        // Ciclo della console
        this.consoleProvider = new ConsoleProvider(this);
        consoleProvider.launchConsole();

        // Messaggio di saluti
        PrintingUtils.printShutdownMessage();
    }
}
