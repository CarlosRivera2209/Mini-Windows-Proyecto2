package Cmd;

import Inicio_de_Sesion.SessionManager;
import Inicio_de_Sesion.Windows;
import java.io.File;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ConsolaGUI extends JFrame {

    private comandos cmd;
    private JTextArea outputTextArea;
    private JTextField inputTextField;
    private JLabel directoryLabel;
    private SessionManager sessionManager; // Asegúrate de tener una referencia al sessionManager

    private static final String HEADER
            = "Windows Feature Experience Pack 1000.22700.1027.0\n"
            + "Microsoft Services Agreement\n"
            + "MICROSOFT SOFTWARE LICENSE TERMS\n\n";

    private boolean headerShown = false;

    public ConsolaGUI(File userDirectory, SessionManager sessionManager) {
        this.sessionManager = sessionManager; // Inicializa sessionManager
        cmd = new comandos(userDirectory.getAbsolutePath());
        setTitle("Consola de Comandos");
        setSize(600, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        getContentPane().setBackground(Color.BLACK);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.BLACK);
        directoryLabel = new JLabel("Directorio actual: " + cmd.getCurrentPath());
        directoryLabel.setForeground(Color.WHITE);
        topPanel.add(directoryLabel, BorderLayout.NORTH);

        outputTextArea = new JTextArea();
        outputTextArea.setEditable(false);
        outputTextArea.setBackground(Color.BLACK);
        outputTextArea.setForeground(Color.WHITE);
        outputTextArea.setFont(new Font("Consolas", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(outputTextArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        inputTextField = new JTextField();
        inputTextField.setBackground(Color.WHITE);
        inputTextField.setForeground(Color.BLACK);
        inputTextField.setFont(new Font("Consolas", Font.PLAIN, 14));
        inputTextField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String command = inputTextField.getText().trim();
                ejecutarComando(command);
            }
        });

        getContentPane().add(topPanel, BorderLayout.NORTH);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(inputTextField, BorderLayout.SOUTH);

        inputTextField.requestFocusInWindow();
    }

    private void ejecutarComando(String command) {
        inputTextField.setEnabled(false);

        try {
            String result = "";

            if (!headerShown) {
                outputTextArea.append(HEADER);
                headerShown = true;
            }

            if (command.equals("...")) {
                result = cmd.Cd("..");
            } else {
                String[] parts = command.split("\\s+", 2);
                String cmdName = parts[0];
                String args = parts.length > 1 ? parts[1] : "";

                switch (cmdName.toLowerCase()) {
                    case "mkdir":
                        result = cmd.Mkdir(args);
                        break;
                    case "mfile":
                        result = cmd.Mfile(args);
                        break;
                    case "rm":
                        if (args.isEmpty()) {
                            result = "Debe ingresar la carpeta o archivo que desea eliminar.";
                        } else {
                            File destino = new File(cmd.getCurrentPath() + "/" + args);
                            result = cmd.Rm(destino);
                        }
                        break;
                    case "cd":
                        result = cmd.Cd(args);
                        break;
                    case "dir":
                        result = cmd.Dir();
                        break;
                    case "date":
                        result = cmd.date();
                        break;
                    case "time":
                        result = cmd.time();
                        break;
                    case "exit": // Agregar el comando para salir
                        dispose(); // Cierra la ventana de consola
                        return; // Termina la ejecución del comando
                    default:
                        result = "Error: Comando no reconocido.";
                }
            }

            outputTextArea.append("> " + command + "\n" + result + "\n");

            if (command.equals("...") || command.startsWith("cd")) {
                directoryLabel.setText("Directorio actual: " + cmd.getCurrentPath());
            }

        } catch (Exception e) {
            outputTextArea.append("Error ejecutando el comando: " + e.getMessage() + "\n");
        } finally {
            inputTextField.setEnabled(true);
            inputTextField.setText("");
            inputTextField.requestFocusInWindow();
        }
    }
}
