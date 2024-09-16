package Inicio_de_Sesion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminPanel extends JFrame {
    private JTextField newUserField;
    private JPasswordField newPasswordField;
    private JButton createUserButton;
    private JButton backButton;

    public AdminPanel() {
        // Configuración básica de la ventana
        setTitle("Panel de Administración");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal con fondo estilizado
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                // Aplicar gradiente al fondo
                g2d.setPaint(new GradientPaint(0, 0, new Color(60, 63, 65), getWidth(), getHeight(), new Color(30, 31, 32)));
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Etiqueta de título
        JLabel titleLabel = new JLabel("Administrar Usuarios");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        // Campo de entrada para el nuevo usuario
        newUserField = new JTextField();
        newUserField.setMaximumSize(new Dimension(300, 40));
        newUserField.setFont(new Font("Arial", Font.PLAIN, 16));
        newUserField.setAlignmentX(Component.CENTER_ALIGNMENT);
        newUserField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(70, 130, 180), 2),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        // Campo de entrada para la contraseña del nuevo usuario
        newPasswordField = new JPasswordField();
        newPasswordField.setMaximumSize(new Dimension(300, 40));
        newPasswordField.setFont(new Font("Arial", Font.PLAIN, 16));
        newPasswordField.setAlignmentX(Component.CENTER_ALIGNMENT);
        newPasswordField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(70, 130, 180), 2),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        // Botón para crear un nuevo usuario
        createUserButton = new JButton("Crear Usuario");
        styleButton(createUserButton, new Color(34, 167, 240), Color.WHITE);

        // Botón para regresar al inicio de sesión
        backButton = new JButton("Regresar");
        styleButton(backButton, new Color(192, 57, 43), Color.WHITE);

        // Añadir acción al botón de crear usuario
        createUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newUsername = newUserField.getText();
                String newPassword = new String(newPasswordField.getPassword());

                if (newUsername.equalsIgnoreCase("admin")) {
                    JOptionPane.showMessageDialog(null, "No se puede usar el nombre 'admin'.");
                } else {
                    // Lógica para crear usuario y su estructura de carpetas
                    new admin().createUser(newUsername, newPassword);
                    JOptionPane.showMessageDialog(null, "Usuario creado correctamente.");
                }
            }
        });

        // Añadir acción al botón de regresar
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginFrame().setVisible(true);
                dispose();
            }
        });

        // Añadir componentes al panel principal
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(new JLabel("Nuevo Usuario:") {{
            setForeground(Color.WHITE);
            setAlignmentX(Component.CENTER_ALIGNMENT);
        }});
        mainPanel.add(newUserField);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(new JLabel("Contraseña:") {{
            setForeground(Color.WHITE);
            setAlignmentX(Component.CENTER_ALIGNMENT);
        }});
        mainPanel.add(newPasswordField);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(createUserButton);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(backButton);

        add(mainPanel);
    }

    // Método para estilizar botones
    private void styleButton(JButton button, Color backgroundColor, Color textColor) {
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(backgroundColor);
        button.setForeground(textColor);
        button.setFocusPainted(false);
        button.setMaximumSize(new Dimension(150, 40));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
}
