package com.github.lory24.officinaio.commands.impl;

import com.github.lory24.officinaio.ConsoleProvider;
import com.github.lory24.officinaio.commands.Command;
import com.github.lory24.officinaio.core.Officina;

public class HelpCommand implements Command {

    @Override
    public void execute(Officina officina, ConsoleProvider provider, String[] args) {
        System.out.println();
        System.out.println("Lista dei comandi utili di OfficinaIO, con i quali potrai gestire al meglio");
        System.out.println("questa officina");
        System.out.println();
        System.out.println("    Comando                        Parametri                         Descrizione");
        System.out.println("    - help | ?                     nessuno                           Mostra la lista dei comandi di OfficinaIO");
        System.out.println("    - prenotazioni                 nessuno                           Gestisci le prenotazioni del servizio (apre la");
        System.out.println("                                                                     sotto-categoria delle prenotazioni)");
        System.out.println();
    }
}
