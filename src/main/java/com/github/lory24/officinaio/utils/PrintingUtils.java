package com.github.lory24.officinaio.utils;

import com.github.lory24.officinaio.core.Officina;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PrintingUtils {

    public void printHeader() {
        System.out.println(CoolMessages.HEADER);
        System.out.println("By LoRy24 - May... - I.T.I.S. Magistri Cumacini\n");
    }

    public void printWelcomeMessage(@NonNull Officina officina) {
        System.out.println();
        System.out.println("Benvenuto in OfficinaIO, il sistema gestionale dell'officina di " + officina.getTitolare() + ".");
        System.out.println("Per una lista dei comandi utili, digita 'aiuto' o '?'.");
        System.out.println();
    }

    public void printShutdownMessage() {
        System.out.println();
        System.out.println("Saluti dalla tua officina di fiducia!");
    }
}
