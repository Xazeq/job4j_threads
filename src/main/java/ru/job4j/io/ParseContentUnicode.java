package ru.job4j.io;

import java.io.File;
import java.util.function.Predicate;

public class ParseContentUnicode implements Parse {
    private final File file;

    public ParseContentUnicode(File file) {
        this.file = file;
    }

    @Override
    public String getContent(Predicate<Character> filter) {
        return new ParseContent().parse(filter, file);
    }
}
