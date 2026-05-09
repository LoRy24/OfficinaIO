package com.github.lory24.officinaio.core.veicoli;

import com.github.lory24.officinaio.ConsoleProvider;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public abstract class Veicolo {
    private final String targa;
    private final String proprietario;
    private final TipoVeicolo tipoVeicolo;

    public abstract void promptSpecificInfo(ConsoleProvider provider);

    @Override
    public String toString() {
        return "- Targa: " + this.targa + "\n" +
                "- Proprietario: " + this.proprietario + "\n" +
                "- Tipo Veicolo: " + this.tipoVeicolo.getTipo() + "\n";
    }
}
