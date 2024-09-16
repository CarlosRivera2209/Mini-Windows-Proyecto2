package Instagram;

import Inicio_de_Sesion.SessionManager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Comparator;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.util.List;

public class InstaInicioSesion extends JFrame {

    private SessionManager sessionManager;
    private Usuario usuarioActivo;
    private ArrayList<Usuario> usuarios; // Lista de usuarios cargados del archivo
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private File fotoPerfil;

    public InstaInicioSesion(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
        usuarios = new ArrayList<>();
        cargarUsuarios(); // Carga los usuarios del archivo binario

        setTitle("INSTA - Simulación de Red Social");
        setSize(1000, 800);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Estilo general de la ventana
        getContentPane().setBackground(new Color(30, 30, 30)); // Color de fondo moderno

        // Configurando el CardLayout para cambiar entre pantallas
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setOpaque(false); // Transparente para mantener el fondo principal

        // Añadir las pantallas al panel principal
        mainPanel.add(crearPantallaInicio(), "PantallaInicio");
        mainPanel.add(crearPantallaCrearCuenta(), "CrearCuenta");
        mainPanel.add(crearPantallaLogin(), "Login");

        add(mainPanel);
        cardLayout.show(mainPanel, "PantallaInicio"); // Mostrar la pantalla inicial
    }

    private JPanel crearPantallaInicio() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false); // Transparente para fondo moderno

        JLabel label = new JLabel("Bienvenido a INSTA", SwingConstants.CENTER);
        label.setFont(new Font("Segoe UI", Font.BOLD, 36));
        label.setForeground(Color.WHITE);

        JPanel botonesPanel = new JPanel();
        botonesPanel.setOpaque(false); // Transparente
        botonesPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

        JButton loginButton = crearBotonEstilizado("Log In");
        JButton crearCuentaButton = crearBotonEstilizado("Crear Cuenta");

        // Botón de Log In
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Login");
            }
        });

        // Botón de Crear Cuenta
        crearCuentaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "CrearCuenta");
            }
        });

        botonesPanel.add(loginButton);
        botonesPanel.add(crearCuentaButton);
        panel.add(label, BorderLayout.NORTH);
        panel.add(botonesPanel, BorderLayout.CENTER);

        return panel;
    }

    private JButton crearBotonEstilizado(String texto) {
        JButton button = new JButton(texto);
        button.setFont(new Font("Segoe UI", Font.BOLD, 18));
        button.setBackground(new Color(0, 120, 215)); // Azul moderno
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false); // Sin borde al hacer click
        button.setPreferredSize(new Dimension(180, 50)); // Tamaño del botón
        return button;
    }

    private JPanel crearPantallaLogin() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false); // Fondo transparente
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel usuarioLabel = crearLabelEstilizado("Username:");
        JTextField usuarioField = crearCampoTextoEstilizado(15);
        JLabel passwordLabel = crearLabelEstilizado("Password:");
        JPasswordField passwordField = crearCampoPasswordEstilizado(15);
        JButton loginButton = crearBotonEstilizado("Log In");

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(usuarioLabel, gbc);
        gbc.gridx = 1;
        panel.add(usuarioField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(passwordLabel, gbc);
        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(loginButton, gbc);

        // Acción para verificar el login
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usuarioField.getText();
                String password = new String(passwordField.getPassword());

                if (autenticarUsuario(username, password)) {
                    JOptionPane.showMessageDialog(null, "¡Login exitoso!");
                    usuarioActivo = obtenerUsuarioPorUsername(username);

                    // Lanzar la aplicación principal (InstaAppVisual)
                    InstaAppVisual appVisual = new InstaAppVisual(usuarioActivo);
                    appVisual.setVisible(true);

                    // Cerrar la ventana de inicio de sesión
                    dispose();

                } else {
                    int option = JOptionPane.showOptionDialog(null,
                            "Usuario o contraseña incorrectos. ¿Deseas intentar de nuevo o crear una cuenta?",
                            "Error de autenticación",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            new String[]{"Repetir Login", "Crear Cuenta"},
                            "Repetir Login");

                    if (option == JOptionPane.YES_OPTION) {
                        // Permite repetir el login
                    } else if (option == JOptionPane.NO_OPTION) {
                        // El usuario elige crear una cuenta
                        cardLayout.show(mainPanel, "CrearCuenta");
                    }
                }
            }
        });
        return panel;
    }

    private JPanel crearPantallaCrearCuenta() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.DARK_GRAY); // Fondo oscuro para un diseño moderno
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Espaciado más amplio

        // Definir fuentes y colores
        Font labelFont = new Font("Arial", Font.BOLD, 16);
        Font fieldFont = new Font("Arial", Font.PLAIN, 14);
        Color textColor = Color.WHITE;

        JLabel nombreLabel = new JLabel("Nombre:");
        nombreLabel.setFont(labelFont);
        nombreLabel.setForeground(textColor);
        JTextField nombreField = new JTextField(15);
        nombreField.setFont(fieldFont);

        JLabel generoLabel = new JLabel("Género:");
        generoLabel.setFont(labelFont);
        generoLabel.setForeground(textColor);
        JComboBox<String> generoComboBox = new JComboBox<>(new String[]{"M", "F"});
        generoComboBox.setFont(fieldFont);

        JLabel usuarioLabel = new JLabel("Username:");
        usuarioLabel.setFont(labelFont);
        usuarioLabel.setForeground(textColor);
        JTextField usuarioField = new JTextField(15);
        usuarioField.setFont(fieldFont);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(labelFont);
        passwordLabel.setForeground(textColor);
        JPasswordField passwordField = new JPasswordField(15);
        passwordField.setFont(fieldFont);

        JLabel edadLabel = new JLabel("Edad:");
        edadLabel.setFont(labelFont);
        edadLabel.setForeground(textColor);
        JTextField edadField = new JTextField(5);
        edadField.setFont(fieldFont);

        JButton cargarFotoButton = new JButton("Cargar Foto");
        cargarFotoButton.setFont(fieldFont);
        JLabel fotoLabel = new JLabel("No se ha seleccionado foto", SwingConstants.CENTER);
        fotoLabel.setForeground(Color.LIGHT_GRAY);
        fotoLabel.setFont(new Font("Arial", Font.ITALIC, 12));

        JButton crearCuentaButton = new JButton("Crear Cuenta");
        crearCuentaButton.setFont(new Font("Arial", Font.BOLD, 16));
        crearCuentaButton.setForeground(Color.WHITE);
        crearCuentaButton.setBackground(new Color(70, 130, 180)); // Azul claro

        cargarFotoButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos de imagen", "jpg", "png", "jpeg", "gif"));
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                fotoPerfil = fileChooser.getSelectedFile();
                ImageIcon imageIcon = new ImageIcon(new ImageIcon(fotoPerfil.getPath()).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
                fotoLabel.setIcon(imageIcon); // Mostrar la imagen en lugar de texto
                fotoLabel.setText(""); // Eliminar el texto una vez que se selecciona la imagen
            }
        });

        // Configuración de diseño de la pantalla
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(nombreLabel, gbc);
        gbc.gridx = 1;
        panel.add(nombreField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(generoLabel, gbc);
        gbc.gridx = 1;
        panel.add(generoComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(usuarioLabel, gbc);
        gbc.gridx = 1;
        panel.add(usuarioField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(passwordLabel, gbc);
        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(edadLabel, gbc);
        gbc.gridx = 1;
        panel.add(edadField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(fotoLabel, gbc);
        gbc.gridx = 1;
        panel.add(cargarFotoButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 6;
        panel.add(crearCuentaButton, gbc);

        // Acción para crear la cuenta
        crearCuentaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = nombreField.getText();
                String genero = generoComboBox.getSelectedItem().toString();
                String username = usuarioField.getText().trim();
                String password = new String(passwordField.getPassword()).trim();
                String edadTexto = edadField.getText().trim();

                if (nombre.isEmpty() || username.isEmpty() || password.isEmpty() || edadTexto.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.");
                    return;
                }

                if (password.length() < 6) {
                    JOptionPane.showMessageDialog(null, "La contraseña debe tener al menos 6 caracteres.");
                    return;
                }

                if (username.contains(" ")) {
                    JOptionPane.showMessageDialog(null, "El username no debe contener espacios.");
                    return;
                }

                int edad;
                try {
                    edad = Integer.parseInt(edadTexto);
                    if (edad < 13) {
                        JOptionPane.showMessageDialog(null, "Debes tener al menos 13 años para registrarte.");
                        return;
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Por favor, ingrese un número válido para la edad.");
                    return;
                }

                // Validar que el username sea único
                if (validarUsernameUnico(username)) {
                    Usuario nuevoUsuario = new Usuario(nombre, genero.charAt(0), username, password, edad, fotoPerfil != null ? fotoPerfil.getPath() : "");
                    usuarios.add(nuevoUsuario);
                    guardarUsuarios(); // Guarda el usuario en el archivo
                    crearCarpetaUsuario(username); // Crea la carpeta y archivos del usuario

                    JOptionPane.showMessageDialog(null, "¡Cuenta creada exitosamente!");

                    usuarioActivo = nuevoUsuario; // Establecer el nuevo usuario como activo

                    // Abrir InstaAppVisual
                    InstaAppVisual instaApp = new InstaAppVisual(nuevoUsuario); // Iniciar InstaAppVisual con el nuevo usuario
                    instaApp.setVisible(true);

                    // Cerrar la ventana de inicio de sesión
                    dispose();

                } else {
                    JOptionPane.showMessageDialog(null, "El username ya está en uso.");
                }
            }
        });

        return panel;
    }

    private JLabel crearLabelEstilizado(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        label.setForeground(Color.WHITE);
        return label;
    }

    private JTextField crearCampoTextoEstilizado(int columnas) {
        JTextField campo = new JTextField(columnas);
        campo.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        campo.setBackground(new Color(50, 50, 50));
        campo.setForeground(Color.WHITE);
        campo.setCaretColor(Color.WHITE);
        return campo;
    }

    private JPasswordField crearCampoPasswordEstilizado(int columnas) {
        JPasswordField campo = new JPasswordField(columnas);
        campo.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        campo.setBackground(new Color(50, 50, 50));
        campo.setForeground(Color.WHITE);
        campo.setCaretColor(Color.WHITE);
        return campo;
    }

    // Método para cargar usuarios desde el archivo binario
    private void cargarUsuarios() {
        File file = new File("usuarios.bin");
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                usuarios = (ArrayList<Usuario>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No se encontró el archivo, se creará uno nuevo.");
            guardarUsuarios(); // Si no existe el archivo, lo creamos al guardar usuarios por primera vez
        }
    }

// Método para guardar los usuarios en el archivo binario
    private void guardarUsuarios() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("usuarios.bin"))) {
            oos.writeObject(usuarios);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para autenticar usuarios
    private boolean autenticarUsuario(String username, String password) {
        for (Usuario u : usuarios) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                usuarioActivo = u; // Establecer el usuario activo
                return true; // Login exitoso
            }
        }
        return false; // Login fallido
    }

    // Método para validar que el username sea único
    private boolean validarUsernameUnico(String username) {
        for (Usuario u : usuarios) {
            if (u.getUsername().equals(username)) {
                return false;
            }
        }
        return true;
    }

    private Usuario obtenerUsuarioPorUsername(String username) {
        for (Usuario usuario : usuarios) {
            if (usuario.getUsername().equals(username)) {
                return usuario;
            }
        }
        return null;
    }

// Método para crear la carpeta del usuario en D:/admin
    private void crearCarpetaUsuario(String username) {
        // Cambiar la ruta de la carpeta base a D:/admin
        File userFolder = new File("D:/admin/usuarios/" + username);
        if (!userFolder.exists()) {
            userFolder.mkdirs();
            File imagenesFolder = new File(userFolder, "imagenes");
            imagenesFolder.mkdirs(); // Crear carpeta de imágenes
            try {
                new File(userFolder, "followers.ins").createNewFile();
                new File(userFolder, "following.ins").createNewFile();
                new File(userFolder, "insta.ins").createNewFile();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Error al crear los archivos del usuario.");
                ex.printStackTrace();
            }
        } else {
            int option = JOptionPane.showConfirmDialog(null, "La carpeta del usuario ya existe. ¿Deseas sobrescribirla?",
                    "Carpeta existente", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                // Sobrescribir carpeta
                try {
                    Files.walk(userFolder.toPath())
                            .sorted(Comparator.reverseOrder()) // Orden descendente para eliminar archivos primero y luego carpetas
                            .forEach(path -> {
                                try {
                                    Files.deleteIfExists(path); // Eliminar el archivo o carpeta
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }
                            });
                    // Volver a crear la carpeta del usuario después de eliminarla
                    crearCarpetaUsuario(username);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            } else {
                JOptionPane.showMessageDialog(null, "El usuario ya tiene una carpeta asignada.");
            }
        }
    }

    public void setUsuarioActivo(Usuario usuario) {
        this.usuarioActivo = usuario;
    }

}
