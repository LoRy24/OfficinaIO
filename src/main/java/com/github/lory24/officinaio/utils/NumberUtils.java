package com.github.lory24.officinaio.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class NumberUtils {

    public boolean isNumber(String key) {
        try {
            Integer.parseInt(key);
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }
}
