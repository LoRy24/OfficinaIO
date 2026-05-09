package com.github.lory24.officinaio.core.veicoli;

import com.github.lory24.officinaio.ConsoleProvider;

public class Autocarro extends Veicolo {
    public Autocarro(String targa, String proprietario) {
        super(targa, proprietario, TipoVeicolo.AUTOCARRO);
    }

    @Override
    public void promptSpecificInfo(ConsoleProvider provider) {

    }
}
