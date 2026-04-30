package com.github.lory24.officinaio;

import com.github.lory24.officinaio.commands.Command;
import com.github.lory24.officinaio.commands.CommandWrapper;
import com.github.lory24.officinaio.commands.impl.HelpCommand;
import com.github.lory24.officinaio.commands.impl.PrenotazioniCommand;
import com.github.lory24.officinaio.core.Officina;
import com.github.lory24.officinaio.utils.PrintingUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
public class ConsoleProvider {
    /* Riferimento all'officina */
    private final Officina officina;

    /* Operatore */
    private boolean logged = false;
    private String username;

    @SuppressWarnings("FieldCanBeLocal")
    private final String securityCode = "1234";

    /* Reader */
    private final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

    /* Costanti */
    @SuppressWarnings("FieldCanBeLocal")
    private final int maxAttempts = 3;

    /* Registri */
    private final List<CommandWrapper> commands = new ArrayList<>();
    private boolean shouldClose = false;

    public void launchConsole() {
        do {
            // Pulisci lo schermo
            this.clearScreen();

            // Autentica
            this.authenticate();

            // Se autenticato, continua
            if (!logged) return;

            // Prepara e avvia la console
            this.registerCommands();
            this.loop();
        }
        while (!this.shouldClose);
    }

    @SneakyThrows
    private void authenticate() {
        // Necessario per l'autenticazione
        String input = "";
        int attempts = 0;

        // Chiedi nickname
        while (input.isBlank()) {
            System.out.print("Inserisci il tuo username -> ");
            input = this.readLine();
        }

        // Carica l'username
        this.username = input;

        // Chiedi il codice di sicurezza
        System.out.print("Ciao " + this.username + "! ");
        while (!input.equals(securityCode)) {
            System.out.print("Inserisci il codice di sicurezza -> ");
            input = this.readLine();

            // Codice non valido
            if (!input.equals(securityCode) && !input.isBlank()) {
                // Ha esaurito i tentativi
                if (this.maxAttempts == attempts) {
                    System.out.println("Tentativi esauriti! Chiusura programma!");
                    System.exit(-1);
                    return;
                }

                // Messaggio del codice errato
                attempts++;
                System.out.println("Codice non valido! Hai ancora " + (this.maxAttempts - attempts + 1) + " tentativi.");
            }
        }

        // Autentica
        logged = true;
        PrintingUtils.printWelcomeMessage(this.officina);
    }

    private void registerCommands() {
        this.registerCommand(new HelpCommand(), "help", "?");
        this.registerCommand(new PrenotazioniCommand(), "prenotazioni");
    }

    @SuppressWarnings("SameParameterValue")
    private void registerCommand(Command executor, String... aliases) {
        this.commands.add(new CommandWrapper(Arrays.stream(aliases).toList(), executor));
    }

    private void executeCommand(@NonNull String command, String[] args) {
        // Controlla se la stringa è vuota
        if (command.trim().isBlank()) return;

        // Se non è vuota prova a eseguire
        this.commands.stream()
                .filter(commandWrapper -> commandWrapper.hasAlias(command.trim()))
                .findFirst()
                .ifPresentOrElse(
                        commandWrapper -> commandWrapper.executor().execute(this.officina, this, args),
                        () -> {
                            System.out.println("Comando sconosciuto! Digita 'help' o '?' per una lista dei comandi.");
                        }
                );
    }

    @SneakyThrows
    public String readLine() {
        return this.bufferedReader.readLine();
    }

    public void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public void printInputLine() {
        this.printInputLine("");
    }

    public void printInputLine(String section) {
        System.out.print("[Admin] " + username + " (" + section + ")~# ");
    }

    @SneakyThrows
    private void loop() {
        // Crea il necessario per l'esecuzione della console
        String input;

        // Fino a quando non viene eseguito il comando esci
        while (true) {
            // Chiedi l'input
            this.printInputLine();
            input = this.readLine();

            // Logout
            if (input.equalsIgnoreCase("logout")) {
                this.shouldClose = false;
                this.logged = false;
                this.username = "";
                break;
            }

            // Uscita
            if (input.equalsIgnoreCase("exit")) {
                this.shouldClose = true;
                break;
            }

            // Esegui il comando solo se la riga non è vuota
            if (!input.trim().isBlank()) {
                // Esegui il comando
                String[] tokens = input.split(" ");
                String[] args = new String[tokens.length - 1];
                System.arraycopy(tokens, 1, args, 0, tokens.length - 1);
                this.executeCommand(tokens[0], args);
            }
        }
    }
}
