package com.github.lory24.officinaio;

import com.github.lory24.officinaio.commands.Command;
import com.github.lory24.officinaio.commands.CommandWrapper;
import com.github.lory24.officinaio.commands.impl.HelpCommand;
import com.github.lory24.officinaio.core.Officina;
import com.github.lory24.officinaio.utils.PrintingUtils;
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

    public void launchConsole() {
        // Autentica
        this.authenticate();

        // Se autenticato, continua
        if (!logged) return;

        // Prepara e avvia la console
        this.registerCommands();
        this.loop();
    }

    @SneakyThrows
    private void authenticate() {
        // Necessario per l'autenticazione
        String input = "";
        int attempts = 0;

        // Chiedi nickname
        while (input.isBlank()) {
            System.out.print("Inserisci il tuo username -> ");
            input = bufferedReader.readLine();
        }

        // Carica l'username
        this.username = input;

        // Chiedi il codice di sicurezza
        System.out.print("Ciao " + this.username + "! ");
        while (!input.equals(securityCode)) {
            System.out.print("Inserisci il codice di sicurezza -> ");
            input = bufferedReader.readLine();

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
    }

    private void registerCommand(Command executor, String... aliases) {
        this.commands.add(new CommandWrapper(Arrays.stream(aliases).toList(), executor));
    }

    private void executeCommand(String command, String[] args) {
        this.commands.stream().filter(commandWrapper -> commandWrapper.hasAlias(command)).findFirst().ifPresent(commandWrapper -> {
            commandWrapper.executor().execute(this.officina, this.bufferedReader, args);
        });
    }

    @SneakyThrows
    private void loop() {
        // Crea il necessario per l'esecuzione della console
        String input;

        // Fino a quando non viene eseguito il comando esci
        while (true) {
            // Chiedi l'input
            System.out.print("[Admin] " + username + " ~# ");
            input = bufferedReader.readLine();

            // Uscita
            if (input.equalsIgnoreCase("exit"))
                break;

            // Esegui il comando
            String[] tokens = input.split(" ");
            String[] args = new String[tokens.length - 1];
            System.arraycopy(tokens, 1, args, 0, tokens.length - 1);
            this.executeCommand(tokens[0], args);
        }
    }
}
