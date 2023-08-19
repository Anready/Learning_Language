package org.tixan.settings;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Main {
    private static JTextField[] textFields; // Array to store text fields

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
        JFrame frame = new JFrame("Settings");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 3, 10, 10));

        Font font = new Font("Arial", Font.PLAIN, 16);

        String[] labels = {"Your language", "Learn language", "Password"};
        String[] text = readData();

        textFields = new JTextField[3]; // Initialize the array

        for (int i = 0; i < 3; i++) {
            JLabel label = new JLabel(labels[i]);
            label.setFont(font);

            JTextField textField = new JTextField();
            textField.setFont(font);
            textFields[i] = textField; // Save a reference to the text field
            textField.setText(text[i]);

            JButton button = new JButton("Save");
            button.setFont(font);

            final int buttonIndex = i;

            button.addActionListener(e -> performAction(buttonIndex));

            panel.add(label);
            panel.add(textField);
            panel.add(button);
        }

        frame.add(panel);
        frame.setVisible(true);
    }

    private static void performAction(int buttonIndex) {
        switch (buttonIndex) {
            case 0 -> {
                try {
                    write(textFields[0].getText(), 0);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                JOptionPane.showMessageDialog(null, "Saved");
            }
            case 1 -> {
                try {
                    write(textFields[1].getText(), 1);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                JOptionPane.showMessageDialog(null, "Saved");
            }
            case 2 -> {
                try {
                    write(textFields[2].getText(), 2);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                JOptionPane.showMessageDialog(null, "Saved");
            }
            default -> {
            }
        }
    }

    private static void write(String text, int field) throws IOException {
        String user = System.getProperty("user.name");
        String filePath = "C:\\Users\\" + user + "\\AppData\\Roaming\\Microsoft\\Windows\\settings"; // Specify your file path here

        String[] lines = readData();
        try {
            FileWriter fileWriter = new FileWriter(filePath, false); // 'true' means append mode

            String dataToAppend;
            if (field == 0)
                dataToAppend = text + "\n" + lines[1] + "\n" + lines[2];
            else if (field == 1)
                dataToAppend = lines[0] + "\n" + text + "\n" + lines[2];
            else
                dataToAppend = lines[0] + "\n" + lines[1] + "\n" + text;

            fileWriter.write(dataToAppend);
            fileWriter.close();
        } catch (IOException ignored) {
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