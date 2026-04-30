package com.github.lory24.officinaio.core;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum TipoServizio {
    RIPARAZIONE(100.0f),
    REVISIONE(50.0f),
    ;

    private final double prezzoServizio;
}
