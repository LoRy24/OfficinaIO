package com.github.lory24.officinaio.commands;

import com.github.lory24.officinaio.core.Officina;

import java.io.BufferedReader;

public interface Command {
    void execute(Officina officina, BufferedReader input, String[] args);
}
