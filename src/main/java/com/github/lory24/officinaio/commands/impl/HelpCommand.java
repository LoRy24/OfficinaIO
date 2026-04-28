package com.github.lory24.officinaio.commands.impl;

import com.github.lory24.officinaio.commands.Command;
import com.github.lory24.officinaio.core.Officina;

import java.io.BufferedReader;

public class HelpCommand implements Command {

    @Override
    public void execute(Officina officina, BufferedReader input, String[] args) {
        System.out.println();
        System.out.println("Lista dei comandi utili di OfficinaIO, con i quali potrai gestire al meglio");
        System.out.println("questa officina");
        System.out.println();
        System.out.println("    Comando                        Descrizione");
        System.out.println("    - help | ?                     Mostra la lista dei comandi di OfficinaIO");
        System.out.println();
    }
}
