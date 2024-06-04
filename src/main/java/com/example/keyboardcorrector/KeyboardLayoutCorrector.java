package com.example.keyboardcorrector;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.win32.StdCallLibrary;
import com.sun.jna.win32.W32APIOptions;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

public class KeyboardLayoutCorrector implements NativeKeyListener {

    private final StringBuilder typedText = new StringBuilder();

    public static void main(String[] args) {
        // Отключение логирования JNativeHook
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);
        logger.setUseParentHandlers(false);

        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());
            System.exit(1);
        }

        GlobalScreen.addNativeKeyListener(new KeyboardLayoutCorrector());
    }

    public static void switchToRussianLayout() {
        // Переключение на русскую раскладку
        WinDef.HKL hkl = ExtendedUser32.INSTANCE.LoadKeyboardLayout("00000419", ExtendedUser32.KLF_ACTIVATE);
        ExtendedUser32.INSTANCE.ActivateKeyboardLayout(hkl, ExtendedUser32.KLF_ACTIVATE);
    }

    public static void switchToEnglishLayout() {
        // Переключение на английскую раскладку
        WinDef.HKL hkl = ExtendedUser32.INSTANCE.LoadKeyboardLayout("00000409", ExtendedUser32.KLF_ACTIVATE);
        ExtendedUser32.INSTANCE.ActivateKeyboardLayout(hkl, ExtendedUser32.KLF_ACTIVATE);
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        // Обработка нажатий клавиш при необходимости
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {
        // Обработка отпускания клавиш при необходимости
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent e) {
        char keyChar = e.getKeyChar();

        if (keyChar == ' ') {
            String text = typedText.toString();
            if (needsCorrection(text)) {
                String correctedText = correctText(text);
                replaceText(correctedText);
                typedText.setLength(0); // Очистка буфера
            }
        } else {
            typedText.append(keyChar);
        }
    }

    private boolean needsCorrection(String text) {
        for (char c : text.toCharArray()) {
            if (KeyboardLayoutMaps.qwertyToCyrillic.containsKey(c) || KeyboardLayoutMaps.cyrillicToQwerty.containsKey(c)) {
                return true;
            }
        }
        return false;
    }

    private String correctText(String text) {
        StringBuilder correctedText = new StringBuilder();
        for (char c : text.toCharArray()) {
            if (KeyboardLayoutMaps.qwertyToCyrillic.containsKey(c)) {
                correctedText.append(KeyboardLayoutMaps.qwertyToCyrillic.get(c));
            } else if (KeyboardLayoutMaps.cyrillicToQwerty.containsKey(c)) {
                correctedText.append(KeyboardLayoutMaps.cyrillicToQwerty.get(c));
            } else {
                correctedText.append(c);
            }
        }
        return correctedText.toString();
    }

    private void replaceText(String correctedText) {
        try {
            Robot robot = new Robot();
            // Удаляем исходный текст
            for (int i = 0; i < typedText.length(); i++) {
                robot.keyPress(KeyEvent.VK_BACK_SPACE);
                robot.keyRelease(KeyEvent.VK_BACK_SPACE);
            }
            // Вставляем исправленный текст
            for (char c : correctedText.toCharArray()) {
                int keyCode = KeyEvent.getExtendedKeyCodeForChar(c);
                if (KeyEvent.CHAR_UNDEFINED == keyCode) {
                    throw new RuntimeException("Key code not found for character '" + c + "'"); // А должно работать
                }
                robot.keyPress(keyCode);
                robot.keyRelease(keyCode);
            }
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public interface ExtendedUser32 extends StdCallLibrary {
        ExtendedUser32 INSTANCE = Native.load("user32", ExtendedUser32.class, W32APIOptions.DEFAULT_OPTIONS);

        int KLF_ACTIVATE = 0x00000001;

        WinDef.HKL LoadKeyboardLayout(String pwszKLID, int Flags);

        WinDef.HKL ActivateKeyboardLayout(WinDef.HKL hkl, int Flags);
    }
}
