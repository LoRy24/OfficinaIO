package com.github.lory24.officinaio.core.veicoli;

import com.github.lory24.officinaio.ConsoleProvider;

public class Autovettura extends Veicolo {

    public Autovettura(String targa, String proprietario) {
        super(targa, proprietario, TipoVeicolo.AUTOVETTURA);
    }

    @Override
    public void promptSpecificInfo(ConsoleProvider provider) {

    }
}
