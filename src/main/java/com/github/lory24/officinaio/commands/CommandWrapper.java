package com.github.lory24.officinaio.commands;

import java.util.List;

public record CommandWrapper(List<String> aliases, Command executor) {
    public boolean hasAlias(String target) {
        return this.aliases.stream().anyMatch(s -> s.equalsIgnoreCase(target));
    }
}
