package org.tixan.addWords;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                createAndShowGUI();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private static void createAndShowGUI() throws IOException {
        JFrame frame = new JFrame("Add Word");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(420, 240); // Increase height for better visibility
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        JPanel panel = new JPanel();
        panel.add(Box.createVerticalStrut(20)); // Increase spacing

        JTextField textField1 = new JTextField(15);
        textField1.setToolTipText("In " + readData()[0]);
        panel.add(textField1);
        panel.add(Box.createVerticalStrut(20)); // Increase spacing

        JTextField textField2 = new JTextField(15);
        textField2.setToolTipText("In " + readData()[1]);
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

            if (text1.isEmpty() || text2.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Fields empty!");
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

    private static String[] readData() throws IOException {
        String user = System.getProperty("user.name");
        String filePath = "C:\\Users\\" + user + "\\AppData\\Roaming\\Microsoft\\Windows\\settings"; // Specify your file path here

        File file = new File(filePath);
        String[] lines = {};
        if (file.exists()) {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(filePath),
                            StandardCharsets.UTF_8));

            String data;
            while ((data = reader.readLine()) != null) {
                lines = Arrays.copyOf(lines, lines.length + 1);
                lines[lines.length - 1] = data;
            }
            reader.close();
        } else {
            try {
                FileWriter fileWriter = new FileWriter(filePath, false); // 'true' means append mode

                String dataToAppend = "Russian\nEnglish\nAnready(T)";
                fileWriter.write(dataToAppend);

                fileWriter.close();
            } catch (IOException ignored) {
            }

            String[] data = {"Russian", "English", "Anready(T)"};

            for (int i = 0; i < 3; i++){
                lines = Arrays.copyOf(lines, lines.length + 1);
                lines[lines.length - 1] = data[i];
            }
        }

        return lines;
    }
}