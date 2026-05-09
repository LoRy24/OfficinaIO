package com.github.lory24.officinaio.core.veicoli;

import com.github.lory24.officinaio.ConsoleProvider;

public class Motociclo extends Veicolo {
    public Motociclo(String targa, String proprietario) {
        super(targa, proprietario, TipoVeicolo.MOTOCICLO);
    }

    @Override
    public void promptSpecificInfo(ConsoleProvider provider) {

    }
}
