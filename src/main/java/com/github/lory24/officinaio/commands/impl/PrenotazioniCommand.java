package com.github.lory24.officinaio.commands.impl;

import com.github.lory24.officinaio.ConsoleProvider;
import com.github.lory24.officinaio.commands.Command;
import com.github.lory24.officinaio.core.Officina;
import com.github.lory24.officinaio.core.Prenotazione;
import com.github.lory24.officinaio.core.Servizio;
import com.github.lory24.officinaio.utils.DateUtils;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PrenotazioniCommand implements Command {

    @Override
    public void execute(Officina officina, @NonNull ConsoleProvider provider, String[] args) {
        System.out.println("\nAccesso eseguito alla modalita' di gestione delle prenotazioni.");
        System.out.println("Per uscire, digita 'conferma', per la lista dei comandi 'aiuto' o '?'.\n");

        String input;
        while (true) {
            provider.printInputLine("prenotazioni");
            input = provider.readLine();

            // Dividi in comando e args
            String[] tokens = input.split(" ");
            String subCommand = tokens[0].toLowerCase();
            String[] subCommandArgs = new String[tokens.length - 1];
            System.arraycopy(tokens, 1, subCommandArgs, 0, tokens.length - 1);

            // Se l'ingresso è 'confirm' esci
            if (input.equalsIgnoreCase("conferma")) {
                break;
            }

            // Altrimenti vedi in base al caso
            switch (subCommand) {
                // Comando di aiuto
                case "aiuto":
                case "?": {
                    this.printHelpForm();
                    break;
                }

                // Comando per visualizzare le prenotazioni
                case "lista": {
                    printFilteredList(officina, subCommandArgs);
                    break;
                }

                // Comando per creare una prenotazione
                case "crea": {
                    creaPrenotazione(officina, provider);
                    break;
                }

                // Comando per eliminare
                case "elimina": {

                }

                // Comando per visualizzare in maniera approfondita
                case "mostra": {

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
        System.out.println();
        System.out.println("Lista dei sotto-comandi per gestire le prenotazioni:");
        System.out.println();
        System.out.println("    Comando                        Parametri                         Descrizione");
        System.out.println("    - aiuto | ?                    nessuno                           Mostra la lista dei sotto-comandi per le prenotazioni");
        System.out.println("    - lista                        nessuno                           Visualizza le prenotazioni con i filtri indicati");
        System.out.println("                                   -g                                Specifica il giorno per filtrare");
        System.out.println("                                   -m                                Specifica il mese per filtrare");
        System.out.println("                                   -a                                Specifica l'anno per filtrare");
        System.out.println("    - crea                         nessuno                           Entra nel form per la creazione di una nuova prenotazione");
        System.out.println("    - elimina                      <id>                              Elimina una prenotazione in base al suo ID");
        System.out.println("    - mostra                       <id>                              Visualizza in maniera dettagliata una singola prenotazione");
        System.out.println("    - conferma                     nessuno                           Conferma le modifiche ed esci dal sotto-sistema");
        System.out.println("                                                                     ");
        System.out.println();
    }

    private void printFilteredList(Officina officina, String[] args) {
        // Definisci i filtri
        boolean dayFilter = false, monthFilter = false, yearFilter = false;
        int dayFilterV = -1, monthFilterV = -1, yearFilterV = -1;

        // Applica i filtri
        for (int i = 0; i < args.length; i += 2) {
            try {
                switch (args[i]) {
                    case "-g": {
                        if (!dayFilter) {
                            dayFilterV = Integer.parseInt(args[i + 1]);
                            dayFilter = true;

                            if (dayFilterV < 0) {
                                System.out.println("Giorno non valido! Deve essere un valore positivo.");
                                return;
                            }
                        }
                        break;
                    }

                    case "-m": {
                        if (!monthFilter) {
                            monthFilterV = Integer.parseInt(args[i + 1]);
                            monthFilter = true;

                            if (monthFilterV < 0) {
                                System.out.println("Mese non valido! Deve essere un valore positivo.");
                                return;
                            }
                        }
                        break;
                    }

                    case "-a": {
                        if (!yearFilter) {
                            yearFilterV = Integer.parseInt(args[i + 1]);
                            yearFilter = true;

                            if (yearFilterV < 0) {
                                System.out.println("Anno non valido! Deve essere un valore positivo.");
                                return;
                            }
                        }
                        break;
                    }

                    default: {
                        System.out.println("Parametro '" + args[i] + "' non valido! Digita 'aiuto' o '?' per approfondire.");
                        return;
                    }
                }
            }
            catch (NumberFormatException ignored) {
                System.out.println("La data non può essere composta da numeri decimali!");
                return;
            }
        }

        // Controlla i filtri
        if (!DateUtils.isValidDate(dayFilterV, monthFilterV, yearFilterV)) {
            System.out.println("Data non valida! Controlla i filtri e riprova.");
            return;
        }

        // Carica le prenotazioni
        List<Prenotazione> lista = officina.filtraPrenotazioni(dayFilterV, monthFilterV, yearFilterV);
        this.printList(lista);
    }

    private void printList(List<Prenotazione> prenotazioni) {
        if (prenotazioni.isEmpty()) {
            System.out.println("\nNon ci sono prenotazioni da mostrare.");
            System.out.println("Se pensi si tratti di un errore, controlla i filtri e riprova.\n");
            return;
        }

        // Header con ID
        System.out.printf("\n%-5s %-15s %-15s %-12s %-10s%n", "ID", "Nome", "Cognome", "Data", "Totale");
        System.out.println("-----------------------------------------------------------------------");

        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");

        int id = 1;
        for (Prenotazione p : prenotazioni) {
            String dataStr = sdf.format(p.getData());
            double totale = p.calcolaTotale();

            System.out.printf(
                    "%-5d %-15s %-15s %-12s %-10.2f%n",
                    id++,
                    p.getNome(),
                    p.getCognome(),
                    dataStr,
                    totale
            );
        }

        System.out.println("-----------------------------------------------------------------------\n");
    }

    private void creaPrenotazione(Officina officina, ConsoleProvider provider) {
        // Welcome
        System.out.println("\nCREAZIONE NUOVA PRENOTAZIONE\n");

        // region Dati base
        int newID = officina.getCurrentCount();
        String nome = "", cognome = "", data = "";
        Date parsedDate;

        // Chiedi i dati base
        while (nome.isEmpty()) {
            System.out.print("Inserisci il nome del cliente: ");
            nome = provider.readLine();
        }

        while (cognome.isEmpty()) {
            System.out.print("Inserisci il cognome del cliente: ");
            cognome = provider.readLine();
        }

        // Data e verifica
        while (data.isEmpty() || DateUtils.parseDate(data) == null) {
            System.out.print("Inserisci la data della prenotazione (dd/MM/yyyy): ");
            data = provider.readLine();

            if (DateUtils.parseDate(data) == null) {
                System.out.println("Data non valida! Riprova.");
            }
        }
        parsedDate = DateUtils.parseDate(data);

        // endregion Dati base

        // region Servizi

        // Crea la lista dei servizi
        List<Servizio> servizi = new ArrayList<>();

        // Chiedi di inserire servizi fino a quando l'utente non digita n
        System.out.println("\nINSERIMENTO DEI SERVIZI\n");
        String continueInput;
        do {
            // TODO Inserimento servizio

            // Chiedi se si vuole continuare
            do {
                System.out.print("Vuoi inserire un altro servizio? (s, n): ");
                continueInput = provider.readLine();
                if (!continueInput.equalsIgnoreCase("n") && !continueInput.equalsIgnoreCase("s") && !continueInput.isBlank()) {
                    System.out.println("Selezione non valida! Riprova.");
                }
            }
            while (!continueInput.equalsIgnoreCase("n") && !continueInput.equalsIgnoreCase("s"));
        }
        while (continueInput.equalsIgnoreCase("s"));

        // endregion Servizi

        // TODO Manda resoconto

        // region Conferma

        // Chiedi conferma
        String confirmInput = "";
        while (!confirmInput.equalsIgnoreCase("n") && !confirmInput.equalsIgnoreCase("s")) {
            do {
                System.out.print("\nVuoi confermare? (s, n): ");
                confirmInput = provider.readLine();
                if (!confirmInput.equalsIgnoreCase("n") && !continueInput.equalsIgnoreCase("s") && !continueInput.isBlank()) {
                    System.out.println("Selezione non valida! Riprova.");
                }
            }
            while (!confirmInput.equalsIgnoreCase("n") && !confirmInput.equalsIgnoreCase("s"));
        }

        // Se conferma la creazione, crea
        if (confirmInput.equals("s")) {
            // TODO Salva in memoria la prenotazione

            // Conferma
            System.out.println("\nCreazione CONFERMATA!\n");
        }
        else {
            System.out.println("\nCreazione ANNULLATA!\n");
        }

        // endregion Conferma
    }
}
