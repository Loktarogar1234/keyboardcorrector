package com.example.keyboardcorrector;

import java.util.HashMap;
import java.util.Map;

public class KeyboardLayoutMaps {
    public static final Map<Character, Character> qwertyToCyrillic = new HashMap<>();
    public static final Map<Character, Character> cyrillicToQwerty = new HashMap<>();

    static {
        qwertyToCyrillic.put('q', 'й');
        qwertyToCyrillic.put('w', 'ц');
        qwertyToCyrillic.put('e', 'у');
        qwertyToCyrillic.put('r', 'к');
        qwertyToCyrillic.put('t', 'е');
        qwertyToCyrillic.put('y', 'н');
        qwertyToCyrillic.put('u', 'г');
        qwertyToCyrillic.put('i', 'ш');
        qwertyToCyrillic.put('o', 'щ');
        qwertyToCyrillic.put('p', 'з');
        qwertyToCyrillic.put('[', 'х');
        qwertyToCyrillic.put(']', 'ъ');
        qwertyToCyrillic.put('a', 'ф');
        qwertyToCyrillic.put('s', 'ы');
        qwertyToCyrillic.put('d', 'в');
        qwertyToCyrillic.put('f', 'а');
        qwertyToCyrillic.put('g', 'п');
        qwertyToCyrillic.put('h', 'р');
        qwertyToCyrillic.put('j', 'о');
        qwertyToCyrillic.put('k', 'л');
        qwertyToCyrillic.put('l', 'д');
        qwertyToCyrillic.put(';', 'ж');
        qwertyToCyrillic.put('\'', 'э'); // Arrrrr
        qwertyToCyrillic.put('z', 'я');
        qwertyToCyrillic.put('x', 'ч');
        qwertyToCyrillic.put('c', 'с');
        qwertyToCyrillic.put('v', 'м');
        qwertyToCyrillic.put('b', 'и');
        qwertyToCyrillic.put('n', 'т');
        qwertyToCyrillic.put('m', 'ь');
        qwertyToCyrillic.put(',', 'б');
        qwertyToCyrillic.put('.', 'ю');


        for (Map.Entry<Character, Character> entry : qwertyToCyrillic.entrySet()) {
            cyrillicToQwerty.put(entry.getValue(), entry.getKey());
        }
    }
}
