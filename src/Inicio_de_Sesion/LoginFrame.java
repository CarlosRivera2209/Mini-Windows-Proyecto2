package Inicio_de_Sesion;

import Cmd.ConsolaGUI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.net.URL;
import java.io.File;

public class LoginFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JLabel avatarLabel;
    private SessionManager sessionManager;

    public LoginFrame() {
        // Inicializar el gestor de sesiones
        sessionManager = new SessionManager();

        // Configuración básica de la ventana
        setTitle("Inicio de Sesión");
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
                g2d.setPaint(new GradientPaint(0, 0, new Color(45, 45, 60), getWidth(), getHeight(), new Color(25, 25, 40)));
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Cargar la imagen del avatar
        ImageIcon avatarIcon = loadImage("pfp.png");
        avatarLabel = new JLabel(avatarIcon);
        avatarLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Ajustar la imagen al tamaño de la ventana
        adjustAvatarSize();

        // Listener para ajustar el tamaño de la imagen cuando se redimensiona la ventana
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                adjustAvatarSize();
            }
        });


        usernameField = new JTextField(20);
        usernameField.setMaximumSize(new Dimension(300, 40));
        usernameField.setFont(new Font("Arial", Font.PLAIN, 18));
        usernameField.setAlignmentX(Component.CENTER_ALIGNMENT);
        usernameField.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Campo de texto para la contraseña
        passwordField = new JPasswordField(20);
        passwordField.setMaximumSize(new Dimension(300, 40));
        passwordField.setFont(new Font("Arial", Font.PLAIN, 18));
        passwordField.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordField.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Botón de ingresar
        loginButton = new JButton("Ingresar");
        loginButton.setFont(new Font("Arial", Font.BOLD, 18));
        loginButton.setBackground(new Color(70, 130, 180));
        loginButton.setForeground(Color.WHITE);
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.setFocusPainted(false);
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                // Verificar credenciales utilizando SessionManager
                if (sessionManager.login(username, password)) {
                    if (username.equals("admin")) {
                        new AdminPanel().setVisible(true); // Mostrar panel de administrador
                    } else {
                        // Obtener el nombre del usuario actual desde SessionManager
                        user loggedInUser = sessionManager.getCurrentUser();
                        if (loggedInUser != null) {
                            String currentUser = loggedInUser.getUsername();
                            File userDirectory = new File("D:\\admin\\" + currentUser);
                            Windows windows = new Windows(sessionManager);
                            windows.setVisible(true);

                        } else {
                            JOptionPane.showMessageDialog(null, "Error al obtener la información del usuario", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    dispose(); // Cerrar la ventana de inicio de sesión
                } else {
                    JOptionPane.showMessageDialog(null, "Credenciales incorrectas", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Añadir componentes al panel
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        mainPanel.add(avatarLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(usernameField);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(passwordField);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(loginButton);

        add(mainPanel);
    }

    // Método para ajustar el tamaño del avatar según la ventana
    private void adjustAvatarSize() {
        int avatarSize = Math.min(getWidth() / 3, getHeight() / 4); // Ajuste proporcional
        ImageIcon icon = (ImageIcon) avatarLabel.getIcon();
        if (icon != null) {
            Image image = icon.getImage().getScaledInstance(avatarSize, avatarSize, Image.SCALE_SMOOTH);
            avatarLabel.setIcon(new ImageIcon(image));
        }
    }

    // Método para cargar la imagen de avatar
    private ImageIcon loadImage(String path) {
        URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("No se pudo encontrar la imagen: " + path);
            return new ImageIcon(); // Devuelve un icono vacío si no se encuentra la imagen
        }
    }
}
