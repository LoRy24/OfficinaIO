package com.github.lory24.officinaio;

import com.github.lory24.officinaio.core.Officina;

import java.io.File;
import java.util.Calendar;

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
    @SuppressWarnings("ResultOfMethodCallIgnored")
    static void main() throws InterruptedException {
        // Scena di avvio
        System.out.println("Sto chiamando il meccanico...");
        Thread.sleep(500);
        System.out.println("Non risponde... Controllo se è sotto una macchina...");
        Thread.sleep(500);
        System.out.println("Ah no stava giocando alla play. Apertura officina...");
        Thread.sleep(1000);

        // Crea la cartella dei dati
        new File("./data/").mkdir();
        new File("./fatture/").mkdir();

        // Avvia l'officina
        new Officina("Via M. Carrara 3, Guanzate (CO), 22070", "Lorenzo Rocca", "IT1234567831", "OfficinaIO")
                .apriOfficina();
    }
}
