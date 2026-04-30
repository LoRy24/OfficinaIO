package com.github.lory24.officinaio.commands;

import com.github.lory24.officinaio.ConsoleProvider;
import com.github.lory24.officinaio.core.Officina;

public interface Command {
    void execute(Officina officina, ConsoleProvider provider, String[] args);
}
