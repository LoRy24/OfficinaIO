package com.github.lory24.officinaio.commands.impl;

import com.github.lory24.officinaio.ConsoleProvider;
import com.github.lory24.officinaio.commands.Command;
import com.github.lory24.officinaio.core.Officina;
import lombok.NonNull;

import java.util.Locale;

public class PrenotazioniCommand implements Command {

    @Override
    public void execute(Officina officina, @NonNull ConsoleProvider provider, String[] args) {
        System.out.println("\nAccesso eseguito alla modalita' di gestione delle prenotazioni.");
        System.out.println("Per uscire, digita 'confirm'.\n");

        String input;
        while (true) {
            provider.printInputLine("prenotazioni");
            input = provider.readLine();

            // Se l'ingresso è 'confirm' esci
            if (input.equalsIgnoreCase("confirm")) {
                break;
            }

            // Altrimenti vedi in base al caso
            switch (input.toLowerCase(Locale.ROOT).split(" ")[0]) {
                // Comando di aiuto
                case "help":
                case "?": {
                    this.printHelpForm();
                    break;
                }

                // Comando per visualizzare le prenotazioni
                case "view": {
                    break;
                }

                // Comando per creare una prenotazione
                case "create": {

                }

                // Comando per eliminare
                case "delete": {

                }

                // Comando vuoto
                case "":
                    break;

                // Default
                default: {
                    System.out.println("Sotto-comando non trovato! Digita 'help' o '?' per una lista dei sotto-comandi per la categoria attiva.");
                    break;
                }
            }
        }
    }

    private void printHelpForm() {

    }
}
