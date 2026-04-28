package com.github.lory24.officinaio;

import com.github.lory24.officinaio.core.Officina;

/**
 * Classe {@link Program} del software OfficinaIO, la classe di partenza del programma, all'interno della
 * quale viene avviata l'officina con le configurazioni di partenza.
 *
 * @author Lorenzo Rocca
 */
public class Program {

    /**
     * Funzione di avvio del programma.
     *
     * @throws InterruptedException Per via della chiamata a {@code Thread.sleep(...)}
     */
    static void main() throws InterruptedException {
        // Scena di avvio
        System.out.println("Sto chiamando il meccanico...");
        Thread.sleep(500);
        System.out.println("Non risponde... Controllo se è sotto una macchina...");
        Thread.sleep(500);
        System.out.println("Ah no stava giocando alla play. Apertura officina...");
        Thread.sleep(1000);

        // Avvia l'officina
        new Officina("Via M. Carrara 3", "Lorenzo Rocca")
                .apriOfficina();
    }
}
