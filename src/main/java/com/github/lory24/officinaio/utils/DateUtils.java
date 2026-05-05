package com.github.lory24.officinaio.utils;

import lombok.experimental.UtilityClass;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@UtilityClass
public class DateUtils {

    public boolean isValidDate(int giorno, int mese, int anno) {
        if (giorno == -1 && mese == -1 && anno == -1) {
            return true;
        }

        if (mese != -1 && (mese < 1 || mese > 12)) {
            return false;
        }

        if (anno != -1 && anno <= 0) {
            return false;
        }

        if (giorno != -1 && (giorno < 1 || giorno > 31)) {
            return false;
        }

        if (giorno != -1 && mese != -1) {
            int maxGiorni = giorniNelMese(mese, anno);
            if (giorno > maxGiorni) {
                return false;
            }
        }

        return true;
    }

    private int giorniNelMese(int mese, int anno) {
        switch (mese) {
            case 4: case 6: case 9: case 11:
                return 30;

            case 2:
                if (anno == -1 || isBisestile(anno)) {
                    return 29;
                } else {
                    return 28;
                }

            default:
                return 31;
        }
    }

    private boolean isBisestile(int anno) {
        return (anno % 4 == 0 && anno % 100 != 0) || (anno % 400 == 0);
    }

    public static Date parseDate(String input) {
        if (input == null || input.isBlank()) {
            return null;
        }

        // Controllo formato base
        if (!input.matches("\\d{2}/\\d{2}/\\d{4}")) {
            return null;
        }

        SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);

        try {
            return sdf.parse(input);
        } catch (ParseException e) {
            return null;
        }
    }
}