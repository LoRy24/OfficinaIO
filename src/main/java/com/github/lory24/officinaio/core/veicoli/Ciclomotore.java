package com.github.lory24.officinaio.core.veicoli;

import com.github.lory24.officinaio.ConsoleProvider;

public class Ciclomotore extends Veicolo{

    public Ciclomotore(String targa, String proprietario) {
        super(targa, proprietario, TipoVeicolo.CICLOMOTORE);
    }

    @Override
    public void promptSpecificInfo(ConsoleProvider provider) {

    }
}
