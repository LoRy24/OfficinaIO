package com.github.lory24.officinaio.core.veicoli;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum TipoVeicolo {
    // RICORDA! Quando aggiungi un veicolo mettilo anche in PRENOTAZIONI COMMAND!
    AUTOVETTURA(Autovettura.class, "Autovettura"),
    CICLOMOTORE(Ciclomotore.class, "Ciclomotore"),
    MOTOCICLO(Motociclo.class, "Motociclo"),
    AUTOCARRO(Autocarro.class, "Autocarro"),
    NESSUNO(Object.class, "Nessuno")
    ;

    private final Class<?> clazz;
    private final String tipo;
}
