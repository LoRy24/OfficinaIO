package com.github.lory24.officinaio.core;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum TipoServizio {
    RIPARAZIONE(100.0f, "Riparazione"),
    REVISIONE(50.0f, "Revisione"),
    ;

    private final double prezzoServizio;
    private final String tipo;
}
