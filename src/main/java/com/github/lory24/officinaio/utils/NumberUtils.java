package com.github.lory24.officinaio.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class NumberUtils {

    public boolean isNotANumber(String key) {
        try {
            Double.parseDouble(key);
            return false;
        } catch (Exception ignored) {
            return true;
        }
    }
}
