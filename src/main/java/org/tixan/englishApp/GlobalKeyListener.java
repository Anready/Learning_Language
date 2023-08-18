package org.tixan.englishApp;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

import java.awt.*;
import java.awt.event.KeyEvent;

public class GlobalKeyListener implements NativeKeyListener {

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        if (e.getKeyCode() == 3675) {
            try {
                Robot robot = new Robot();
                robot.keyPress(KeyEvent.VK_ESCAPE); // Имитируем нажатие клавиши Escape
                robot.keyRelease(KeyEvent.VK_ESCAPE); // Имитируем отпускание клавиши Escape
            } catch (AWTException ignored) {
            }
        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent e) {
    }

    public static void startListening() {
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ignored) {
        }
        GlobalScreen.addNativeKeyListener(new GlobalKeyListener());
    }
}