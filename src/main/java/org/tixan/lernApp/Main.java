package org.tixan.lernApp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Random;

public class Main extends GlobalKeyListener {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Learn");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                JTextField inputField = new JTextField(20);
                inputField.setToolTipText("Password");

                Object[] options = {"Yes", "No"};
                int option = JOptionPane.showOptionDialog(frame,
                        inputField,
                        "Confirm Exit",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[1]);

                if (option == JOptionPane.YES_OPTION) {
                    String inputText = inputField.getText();
                    if (inputText.equals("Anready939(T)"))
                        System.exit(0);
                }
            }
        });

        startListening();

        JPanel panel = new JPanel(new GridBagLayout());

        JButton button = getjButton(panel, frame);

        GridBagConstraints buttonConstraints = new GridBagConstraints();
        buttonConstraints.gridx = 0;
        buttonConstraints.gridy = 0;
        buttonConstraints.insets = new Insets(20, 20, 20, 20); // Double margins

        panel.add(button, buttonConstraints);

        frame.add(panel);
        frame.setResizable(false);
        frame.setUndecorated(true);
        frame.setVisible(true);
        frame.setAlwaysOnTop(true);
    }

    private static JButton getjButton(JPanel panel, JFrame frame) {
        JButton button = new JButton("Show Word");
        button.addActionListener(e -> {
            String[] word;
            try {
                word = word();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            JLabel label = new JLabel(word[0]);
            label.setFont(label.getFont().deriveFont(Font.BOLD, 36)); // Increase font size

            panel.removeAll();

            GridBagConstraints labelConstraints = new GridBagConstraints();
            labelConstraints.gridx = 0;
            labelConstraints.gridy = 0;
            labelConstraints.insets = new Insets(20, 20, 20, 20);
            labelConstraints.anchor = GridBagConstraints.CENTER;

            panel.add(label, labelConstraints);

            JTextField textField = new JTextField(40);
            textField.setFont(textField.getFont().deriveFont(Font.PLAIN, 24)); // Increase font size

            GridBagConstraints textFieldConstraints = new GridBagConstraints();
            textFieldConstraints.gridx = 0;
            textFieldConstraints.gridy = 1;
            textFieldConstraints.insets = new Insets(20, 20, 20, 20);
            textFieldConstraints.anchor = GridBagConstraints.CENTER;

            panel.add(textField, textFieldConstraints);

            JButton checkButton = new JButton("Check");
            checkButton.setFont(checkButton.getFont().deriveFont(Font.BOLD, 20));

            GridBagConstraints checkButtonConstraints = new GridBagConstraints();
            checkButtonConstraints.gridx = 0;
            checkButtonConstraints.gridy = 2;
            checkButtonConstraints.insets = new Insets(20, 20, 20, 20);
            checkButtonConstraints.anchor = GridBagConstraints.CENTER;
            checkButton.addActionListener(event -> {
                String inputText = textField.getText();
                if (inputText.equalsIgnoreCase(word[1])) {
                    frame.setVisible(false);

                    new Thread(() -> {
                        try {
                            Thread.sleep(15 * 60 * 1000); // set 15 minute as time hide
                        } catch (InterruptedException ignored) {
                        }

                        SwingUtilities.invokeLater(() -> {
                            textField.setText("");
                            frame.setVisible(true);
                            button.doClick();
                        });
                    }).start();
                } else {
                    JOptionPane.showMessageDialog(frame, "Incorrect!\nEntered text: " + inputText);
                }
            });

            panel.add(checkButton, checkButtonConstraints);

            frame.revalidate();
            frame.repaint();
        });
        return button;
    }

    static String[] word() throws IOException {
        String user = System.getProperty("user.name");

        String[] words = {};
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream("C:\\Users\\" + user + "\\AppData\\Roaming\\Microsoft\\Windows\\words"),
                        StandardCharsets.UTF_8));

        String data;
        while ((data = reader.readLine()) != null) {
            words = Arrays.copyOf(words, words.length + 1);
            words[words.length - 1] = data;
        }
        reader.close();

        Random r = new Random();
        return words[r.nextInt(words.length)].split("#");
    }
}