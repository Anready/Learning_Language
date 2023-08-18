package org.tixan.addWords;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Add Word");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(420, 240); // Increase height for better visibility
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        JPanel panel = new JPanel();
        panel.add(Box.createVerticalStrut(20)); // Increase spacing

        JTextField textField1 = new JTextField(15);
        textField1.setToolTipText("In Russian");
        panel.add(textField1);
        panel.add(Box.createVerticalStrut(20)); // Increase spacing

        JTextField textField2 = new JTextField(15);
        textField2.setToolTipText("In English");
        panel.add(textField2);
        panel.add(Box.createVerticalStrut(20)); // Increase spacing

        JButton button = getjButton(textField1, textField2, frame);
        panel.add(button);

        frame.add(panel);
        frame.setVisible(true);

        Font largerFont = new Font(Font.SANS_SERIF, Font.PLAIN, 16); // Increase font size
        textField1.setFont(largerFont);
        textField2.setFont(largerFont);
        button.setFont(largerFont);

        int largerWidth = 300; // Increase component width
        int largerHeight = 50; // Increase component height
        textField1.setPreferredSize(new Dimension(largerWidth, largerHeight));
        textField2.setPreferredSize(new Dimension(largerWidth, largerHeight));
        button.setPreferredSize(new Dimension(largerWidth, largerHeight));
    }


    private static JButton getjButton(JTextField textField1, JTextField textField2, JFrame frame) {
        JButton button = new JButton("Save");
        button.addActionListener(e -> {
            String text1 = textField1.getText();
            String text2 = textField2.getText();

            if (text1.contains("#") || text2.contains("#")) {
                JOptionPane.showMessageDialog(frame, "Fields should not contain '#' character");
                return;
            }

            int result = JOptionPane.showConfirmDialog(
                    frame,
                    "Are you sure?",
                    "Choose an Action",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );

            if (result == JOptionPane.YES_OPTION) {
                writeToFile(text1, text2); // Write information to file
                textField1.setText("");
                textField2.setText("");
            }
        });
        return button;
    }

    private static void writeToFile(String text1, String text2) {
        String user = System.getProperty("user.name");
        File myFile = new File("C:\\Users\\" + user + "\\AppData\\Roaming\\Microsoft\\Windows\\words");
        try {
            FileWriter writer = new FileWriter(myFile, StandardCharsets.UTF_8, true);
            writer.write(text1 + "#" + text2.toLowerCase() + "\n");
            writer.close();
        } catch (Exception ignored) {
        }
    }
}