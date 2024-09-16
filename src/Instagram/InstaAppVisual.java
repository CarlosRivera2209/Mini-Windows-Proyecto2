package Instagram;

import Inicio_de_Sesion.SessionManager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;

public class InstaAppVisual extends JFrame {

    private SessionManager sessionManager;
    private Usuario usuarioActivo;
    private JPanel mainPanel;
    private ArrayList<Usuario> usuarios;

    public InstaAppVisual(Usuario usuarioActivo) {
        this.usuarioActivo = usuarioActivo;
        this.usuarios = new ArrayList<>(); // Inicializa la lista de usuarios

        setTitle("InstaApp - Feed");
        setSize(800, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Barra de Navegación Superior
        JPanel topBar = new JPanel();
        topBar.setBackground(Color.WHITE);
        topBar.setLayout(new FlowLayout(FlowLayout.LEFT));

        JButton homeButton = new JButton("Inicio");
        JButton profileButton = new JButton("Perfil");
        JButton hashtagButton = new JButton("Buscar Hashtag");
        JButton logoutButton = new JButton("Cerrar Sesión");

        topBar.add(homeButton);
        topBar.add(profileButton);
        topBar.add(hashtagButton);
        topBar.add(logoutButton);

        // Panel Lateral Izquierdo (Menú de Perfil)
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(Color.WHITE);
        leftPanel.setLayout(new GridLayout(5, 1, 10, 10));

        JButton uploadImageButton = new JButton("Subir Imagen");
        JButton settingsButton = new JButton("Configuración");

        leftPanel.add(uploadImageButton);
        leftPanel.add(settingsButton);

        // Panel Central (Feed de Imágenes)
        mainPanel = new JPanel();
        mainPanel.setBackground(Color.LIGHT_GRAY);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Añadir los paneles a la ventana
        add(topBar, BorderLayout.NORTH);
        add(leftPanel, BorderLayout.WEST);
        add(scrollPane, BorderLayout.CENTER);

        // Eventos de los botones
        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarFeed();
            }
        });

        profileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarPerfil();
            }
        });

        hashtagButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String hashtag = JOptionPane.showInputDialog(null, "Ingresa el hashtag a buscar:");
                if (hashtag != null && !hashtag.trim().isEmpty()) {
                    // Buscar comentarios con el hashtag
                    java.util.List<String> comentariosConHashtag = buscarHashtag(hashtag);
                    mostrarComentarios(comentariosConHashtag);
                }
            }
        });

        uploadImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                subirImagen();
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cerrarSesion();
            }
        });

        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirConfiguracion();
            }
        });

        mostrarFeed();
    }

    private void abrirConfiguracion() {
        // Crear un nuevo panel de configuración
        JPanel configPanel = new JPanel();
        configPanel.setLayout(new BoxLayout(configPanel, BoxLayout.Y_AXIS));
        configPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Botones para cada opción
        JButton buscarPersonasButton = new JButton("Buscar Personas");
        JButton entrarPerfilButton = new JButton("Entrar a un Perfil");
        JButton activarDesactivarButton = new JButton("Desactivar/Activar Cuenta");

        // Agregar ActionListeners
        buscarPersonasButton.addActionListener(e -> buscarPersonas());
        entrarPerfilButton.addActionListener(e -> entrarAPerfil());
        activarDesactivarButton.addActionListener(e -> activarDesactivarCuenta());

        // Añadir botones al panel de configuración
        configPanel.add(buscarPersonasButton);
        configPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Espaciado
        configPanel.add(entrarPerfilButton);
        configPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        configPanel.add(activarDesactivarButton);

        // Mostrar el panel en una ventana emergente
        JFrame configFrame = new JFrame("Configuración");
        configFrame.setSize(300, 200);
        configFrame.setLocationRelativeTo(this);
        configFrame.add(configPanel);
        configFrame.setVisible(true);
    }

    private void mostrarFeed() {
        mainPanel.removeAll();

        // Obtener imágenes del usuario activo
        java.util.List<File> imagenesFeed = obtenerImagenesUsuario(usuarioActivo.getUsername());

        // Obtener usuarios que sigo
        ArrayList<String> usuariosSeguidos = obtenerUsuariosSeguidos();

        // Obtener imágenes de los usuarios que sigo
        for (String usuario : usuariosSeguidos) {
            java.util.List<File> imagenesUsuario = obtenerImagenesUsuario(usuario);
            imagenesFeed.addAll(imagenesUsuario);
        }

        // Mostrar las imágenes en el feed
        for (File imagen : imagenesFeed) {
            JPanel postPanel = new JPanel();
            postPanel.setLayout(new BorderLayout());
            postPanel.setPreferredSize(new Dimension(500, 300));
            postPanel.setBackground(Color.WHITE);
            postPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            JLabel postImage = new JLabel(new ImageIcon(imagen.getPath()));
            postImage.setHorizontalAlignment(SwingConstants.CENTER);
            postImage.setPreferredSize(new Dimension(500, 250));

            JPanel postActions = new JPanel(new FlowLayout(FlowLayout.LEFT));
            postActions.setBackground(Color.WHITE);
            JButton likeButton = new JButton("❤");
            JButton commentButton = new JButton("💬");

            // Agregar el ActionListener al botón de comentario
            commentButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String comentario = JOptionPane.showInputDialog(null, "Escribe tu comentario:");
                    if (comentario != null && !comentario.trim().isEmpty()) {
                        guardarComentario(comentario); // Guarda el comentario en el archivo insta.ins
                        int verComentarios = JOptionPane.showConfirmDialog(null, "¿Quieres ver todos los comentarios?", "Comentarios", JOptionPane.YES_NO_OPTION);
                        if (verComentarios == JOptionPane.YES_OPTION) {
                            mostrarComentarios();
                        }
                    }
                }
            });

            postActions.add(likeButton);
            postActions.add(commentButton);

            postPanel.add(postImage, BorderLayout.CENTER);
            postPanel.add(postActions, BorderLayout.SOUTH);

            mainPanel.add(postPanel);
        }

        revalidate();
        repaint();
    }

    private void mostrarPerfil() {
        mainPanel.removeAll();

        // Mostrar username
        JLabel perfilLabel = new JLabel("Perfil de " + usuarioActivo.getUsername());
        perfilLabel.setFont(new Font("Arial", Font.BOLD, 20));
        perfilLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(perfilLabel);

        // Mostrar cantidad de followers y following
        JLabel followersLabel = new JLabel("Seguidores: " + contarFollowers());
        JLabel followingLabel = new JLabel("Siguiendo: " + contarFollowing());
        mainPanel.add(followersLabel);
        mainPanel.add(followingLabel);

        // Mostrar las imágenes del perfil
        java.util.List<File> imagenes = obtenerImagenesUsuario(usuarioActivo.getUsername());
        for (File imagen : imagenes) {
            JLabel imagenLabel = new JLabel(new ImageIcon(imagen.getPath()));
            mainPanel.add(imagenLabel);
        }

        revalidate();
        repaint();
    }

    private void mostrarComentarios(java.util.List<String> comentarios) {
        JPanel comentariosPanel = new JPanel();
        comentariosPanel.setLayout(new BoxLayout(comentariosPanel, BoxLayout.Y_AXIS));
        comentariosPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        for (String comentario : comentarios) {
            JLabel comentarioLabel = new JLabel("<html>" + comentario.replaceAll("(\r\n|\n)", "<br>") + "</html>");
            comentarioLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            comentariosPanel.add(comentarioLabel);
            comentariosPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Espaciado
        }

        JScrollPane scrollPane = new JScrollPane(comentariosPanel);
        scrollPane.setPreferredSize(new Dimension(600, 400));

        JOptionPane.showMessageDialog(this, scrollPane, "Comentarios con el Hashtag", JOptionPane.INFORMATION_MESSAGE);
    }

    private void mostrarComentarios() {
        ArrayList<String> comentarios = new ArrayList<>();

        // Cargar comentarios del usuario activo
        comentarios.addAll(cargarComentarios(usuarioActivo.getUsername()));

        // Cargar comentarios de los usuarios que sigue
        for (String usuarioSeguido : obtenerUsuariosSeguidos()) {
            comentarios.addAll(cargarComentarios(usuarioSeguido));
        }

        // Ordenar comentarios del más reciente al más antiguo
        comentarios.sort((c1, c2) -> {
            LocalDateTime fecha1 = LocalDateTime.parse(c1.split("\\(")[1].split("\\)")[0]);
            LocalDateTime fecha2 = LocalDateTime.parse(c2.split("\\(")[1].split("\\)")[0]);
            return fecha2.compareTo(fecha1); // Ordenar del más reciente al más antiguo
        });

        // Mostrar los comentarios en un panel
        JPanel comentariosPanel = new JPanel();
        comentariosPanel.setLayout(new BoxLayout(comentariosPanel, BoxLayout.Y_AXIS));
        comentariosPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        for (String comentario : comentarios) {
            JLabel comentarioLabel = new JLabel(comentario);
            comentarioLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            comentariosPanel.add(comentarioLabel);
            comentariosPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Espaciado
        }

        JScrollPane scrollPane = new JScrollPane(comentariosPanel);
        scrollPane.setPreferredSize(new Dimension(500, 400));

        JOptionPane.showMessageDialog(this, scrollPane, "Comentarios", JOptionPane.INFORMATION_MESSAGE);
    }

    private void subirImagen() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Imágenes", "jpg", "png"));
        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            guardarImagen(selectedFile);
            JOptionPane.showMessageDialog(this, "Imagen subida exitosamente.");
        }
    }

    private void cerrarSesion() {
        int confirm = JOptionPane.showConfirmDialog(this, "¿Estás seguro de que quieres cerrar sesión?", "Cerrar Sesión", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            InstaInicioSesion instaLogin = new InstaInicioSesion(sessionManager);
            instaLogin.setVisible(true);
            this.dispose(); // Cierra la ventana actual
        }
    }

    private int contarFollowers() {
        File followersFile = new File("D:/admin/usuarios/" + usuarioActivo.getUsername() + "/followers.ins");
        int count = 0;

        if (followersFile.exists() && followersFile.length() > 0) {  // Verificar que el archivo no esté vacío
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(followersFile))) {
                ArrayList<String> followers = (ArrayList<String>) ois.readObject();
                count = followers.size();
            } catch (EOFException eof) {
                System.out.println("El archivo de seguidores está vacío o corrupto.");
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        return count;
    }

    private int contarFollowing() {
        File followingFile = new File("D:/admin/usuarios/" + usuarioActivo.getUsername() + "/following.ins");
        int count = 0;

        if (followingFile.exists() && followingFile.length() > 0) {  // Verificar que el archivo no esté vacío
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(followingFile))) {
                ArrayList<String> following = (ArrayList<String>) ois.readObject();
                count = following.size();
            } catch (EOFException eof) {
                System.out.println("El archivo de siguiendo está vacío o corrupto.");
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        return count;
    }

    private java.util.List<File> obtenerImagenesUsuario(String username) {
        java.util.List<File> imagenes = new ArrayList<>();
        File userFolder = new File("D:/admin/usuarios/" + username + "/imagenes");  // Ruta a la carpeta de imágenes del usuario

        if (userFolder.exists() && userFolder.isDirectory()) {
            for (File file : userFolder.listFiles()) {
                if (file.isFile() && (file.getName().endsWith(".jpg") || file.getName().endsWith(".png"))) {
                    imagenes.add(file);
                }
            }
        }
        return imagenes;
    }

    private void guardarImagen(File imagen) {
        File userFolder = new File("D:/admin/usuarios/" + usuarioActivo.getUsername() + "/imagenes");
        if (!userFolder.exists()) {
            userFolder.mkdirs(); // Crear la carpeta de imágenes si no existe
        }
        File destino = new File(userFolder, imagen.getName());
        try {
            Files.copy(imagen.toPath(), destino.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private java.util.List<String> cargarInstasYSeguidos() {
        java.util.List<String> instas = new ArrayList<>();

        // Cargar los instas del usuario activo
        instas.addAll(cargarInstasDeUsuario(usuarioActivo.getUsername()));

        // Cargar los instas de los usuarios que sigue
        File followingFile = new File("D:/admin/usuarios/" + usuarioActivo.getUsername() + "/following.ins");
        if (followingFile.exists() && followingFile.length() > 0) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(followingFile))) {
                ArrayList<String> following = (ArrayList<String>) ois.readObject();
                for (String followedUser : following) {
                    // Verificar si el usuario seguido está activo
                    Usuario seguido = obtenerUsuarioPorUsername(followedUser);
                    if (seguido != null && seguido.isActivo()) {
                        instas.addAll(cargarInstasDeUsuario(followedUser));
                    }
                }
            } catch (EOFException eof) {
                System.out.println("El archivo de siguiendo está vacío o corrupto.");
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        return instas;
    }

    private java.util.List<String> cargarInstasDeUsuario(String username) {
        java.util.List<String> instas = new ArrayList<>();
        File instaFile = new File("D:/admin/usuarios/" + username + "/insta.ins");

        if (instaFile.exists() && instaFile.length() > 0) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(instaFile))) {
                instas = (ArrayList<String>) ois.readObject();
            } catch (EOFException eof) {
                System.out.println("El archivo insta.ins está vacío.");
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("El archivo insta.ins no existe o está vacío.");
        }
        return instas;
    }

    private java.util.List<String> buscarHashtag(String hashtag) {
        java.util.List<String> comentariosConHashtag = new ArrayList<>();

        // Leer los instas del usuario activo y de los que sigue
        java.util.List<String> instas = cargarInstasYSeguidos();

        for (String insta : instas) {
            if (insta.contains("#" + hashtag)) {
                comentariosConHashtag.add(insta);
            }
        }
        return comentariosConHashtag;
    }

    private void guardarComentario(String comentario) {
        File instaFile = new File("D:/admin/usuarios/" + usuarioActivo.getUsername() + "/insta.ins");

        // Obtener la fecha actual
        String fecha = java.time.LocalDateTime.now().toString();
        String comentarioCompleto = usuarioActivo.getUsername() + " (" + fecha + "): " + comentario;

        // Guardar el comentario en el archivo del usuario activo
        ArrayList<String> instas = new ArrayList<>();
        if (instaFile.exists() && instaFile.length() > 0) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(instaFile))) {
                instas = (ArrayList<String>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        instas.add(comentarioCompleto);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(instaFile))) {
            oos.writeObject(instas);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Propagar el comentario a los usuarios que sigues
        for (String usuarioSeguido : obtenerUsuariosSeguidos()) {
            File archivoSeguido = new File("D:/admin/usuarios/" + usuarioSeguido + "/insta.ins");
            ArrayList<String> comentariosSeguidos = new ArrayList<>();
            if (archivoSeguido.exists() && archivoSeguido.length() > 0) {
                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivoSeguido))) {
                    comentariosSeguidos = (ArrayList<String>) ois.readObject();
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            comentariosSeguidos.add(comentarioCompleto);
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivoSeguido))) {
                oos.writeObject(comentariosSeguidos);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void buscarPersonas() {
        String query = JOptionPane.showInputDialog(this, "Ingrese el username a buscar:");
        if (query == null || query.trim().isEmpty()) {
            return; // Cancelado o vacío
        }

        query = query.trim().toLowerCase();

        // Obtener la lista de todos los usuarios
        ArrayList<Usuario> todosUsuarios = cargarTodosUsuarios();

        // Filtrar usuarios que contengan el query en el username y estén activos
        ArrayList<Usuario> resultados = new ArrayList<>();
        for (Usuario usuario : todosUsuarios) {
            if (usuario.isActivo() && usuario.getUsername().toLowerCase().contains(query)) {
                resultados.add(usuario);
            }
        }

        if (resultados.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No se encontraron usuarios que coincidan con la búsqueda.");
            return;
        }

        // Crear una ventana para mostrar los resultados
        JPanel resultadosPanel = new JPanel();
        resultadosPanel.setLayout(new BoxLayout(resultadosPanel, BoxLayout.Y_AXIS));

        for (Usuario usuario : resultados) {
            boolean sigo = estoySiguiendo(usuario.getUsername());
            String estado = sigo ? "LO SIGO" : "NO LO SIGO";
            JLabel usuarioLabel = new JLabel(usuario.getUsername() + " - " + estado);
            resultadosPanel.add(usuarioLabel);
        }

        JScrollPane scrollPane = new JScrollPane(resultadosPanel);
        scrollPane.setPreferredSize(new Dimension(250, 200));

        JOptionPane.showMessageDialog(this, scrollPane, "Resultados de Búsqueda", JOptionPane.INFORMATION_MESSAGE);
    }

// Método para cargar todos los usuarios
    private ArrayList<Usuario> cargarTodosUsuarios() {
        ArrayList<Usuario> todosUsuarios = new ArrayList<>();
        File usuariosFile = new File("D:/admin/usuarios.bin");

        if (usuariosFile.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(usuariosFile))) {
                todosUsuarios = (ArrayList<Usuario>) ois.readObject();
            } catch (EOFException eof) {
                System.out.println("El archivo de usuarios está vacío o corrupto.");
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return todosUsuarios;
    }

// Método para verificar si estoy siguiendo a un usuario
    private boolean estoySiguiendo(String username) {
        File followingFile = new File("D:/admin/usuarios/" + usuarioActivo.getUsername() + "/following.ins");
        if (followingFile.exists() && followingFile.length() > 0) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(followingFile))) {
                ArrayList<String> following = (ArrayList<String>) ois.readObject();
                return following.contains(username);
            } catch (EOFException eof) {
                System.out.println("El archivo de siguiendo está vacío o corrupto.");
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private void entrarAPerfil() {
        String targetUsername = JOptionPane.showInputDialog(this, "Ingrese el username del perfil a entrar:");
        if (targetUsername == null || targetUsername.trim().isEmpty()) {
            return; // Cancelado o vacío
        }

        targetUsername = targetUsername.trim();

        // Cargar todos los usuarios
        ArrayList<Usuario> todosUsuarios = cargarTodosUsuarios();

        // Buscar el usuario objetivo
        Usuario targetUser = null;
        for (Usuario usuario : todosUsuarios) {
            if (usuario.getUsername().equals(targetUsername) && usuario.isActivo()) {
                targetUser = usuario;
                break;
            }
        }

        if (targetUser == null) {
            JOptionPane.showMessageDialog(this, "El usuario no existe o está desactivado.");
            return;
        }

        // Usar una copia final del usuario objetivo
        final Usuario finalTargetUser = targetUser;

        // Mostrar los detalles del perfil
        JPanel perfilPanel = new JPanel();
        perfilPanel.setLayout(new BoxLayout(perfilPanel, BoxLayout.Y_AXIS));
        perfilPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel nombreLabel = new JLabel("Nombre Completo: " + finalTargetUser.getNombre());
        JLabel generoLabel = new JLabel("Género: " + (finalTargetUser.getGenero() == 'M' ? "Masculino" : "Femenino"));
        JLabel edadLabel = new JLabel("Edad: " + finalTargetUser.getEdad());
        JLabel fechaIngresoLabel = new JLabel("Fecha de Ingreso: " + finalTargetUser.getFechaIngreso().toString());

        // Contar seguidores y siguiendo
        int followersCount = contarFollowers(finalTargetUser.getUsername());
        int followingCount = contarFollowing(finalTargetUser.getUsername());

        JLabel followersLabel = new JLabel("Seguidores: " + followersCount);
        JLabel followingLabel = new JLabel("Siguiendo: " + followingCount);

        // Verificar si sigo al usuario
        boolean sigo = estoySiguiendo(finalTargetUser.getUsername());
        JLabel sigoLabel = new JLabel(sigo ? "Actualmente lo sigues." : "Actualmente no lo sigues.");

        perfilPanel.add(nombreLabel);
        perfilPanel.add(generoLabel);
        perfilPanel.add(edadLabel);
        perfilPanel.add(fechaIngresoLabel);
        perfilPanel.add(followersLabel);
        perfilPanel.add(followingLabel);
        perfilPanel.add(sigoLabel);
        perfilPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Espaciado

        // Botones de acción
        JButton seguirButton = new JButton(sigo ? "DEJAR DE SIGUIER" : "SEGUIR");
        JButton verImagenesButton = new JButton("Ver Catálogo de Imágenes");

        // ActionListener para seguir/dejar de seguir
        seguirButton.addActionListener(e -> {
            if (sigo) {
                // Dejar de seguir
                int confirm = JOptionPane.showConfirmDialog(this, "¿Estás seguro de que deseas dejar de seguir a " + finalTargetUser.getUsername() + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    dejarDeSeguirUsuario(finalTargetUser.getUsername());
                    sigoLabel.setText("Actualmente no lo sigues.");
                    seguirButton.setText("SEGUIR");
                }
            } else {
                // Seguir
                seguirUsuario(finalTargetUser.getUsername());
                sigoLabel.setText("Actualmente lo sigues.");
                seguirButton.setText("DEJAR DE SIGUIER");
            }
        });

        // ActionListener para ver imágenes
        verImagenesButton.addActionListener(e -> verCatalogoImagenes(finalTargetUser.getUsername()));

        perfilPanel.add(seguirButton);
        perfilPanel.add(verImagenesButton);

        // Mostrar el panel en una ventana emergente
        JScrollPane scrollPane = new JScrollPane(perfilPanel);
        scrollPane.setPreferredSize(new Dimension(400, 300));
        JOptionPane.showMessageDialog(this, scrollPane, "Perfil de " + finalTargetUser.getUsername(), JOptionPane.INFORMATION_MESSAGE);
    }

// Método para contar seguidores de un usuario específico
    private int contarFollowers(String username) {
        File followersFile = new File("D:/admin/usuarios/" + username + "/followers.ins");
        int count = 0;

        if (followersFile.exists() && followersFile.length() > 0) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(followersFile))) {
                ArrayList<String> followers = (ArrayList<String>) ois.readObject();
                count = followers.size();
            } catch (EOFException eof) {
                System.out.println("El archivo de seguidores está vacío o corrupto.");
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return count;
    }

// Método para contar siguiendo de un usuario específico
    private int contarFollowing(String username) {
        File followingFile = new File("D:/admin/usuarios/" + username + "/following.ins");
        int count = 0;

        if (followingFile.exists() && followingFile.length() > 0) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(followingFile))) {
                ArrayList<String> following = (ArrayList<String>) ois.readObject();
                count = following.size();
            } catch (EOFException eof) {
                System.out.println("El archivo de siguiendo está vacío o corrupto.");
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return count;
    }

// Método para seguir a un usuario
    private void seguirUsuario(String username) {
        // Actualizar el archivo following.ins del usuario activo
        File followingFile = new File("D:/admin/usuarios/" + usuarioActivo.getUsername() + "/following.ins");
        ArrayList<String> following = new ArrayList<>();

        // Cargar los usuarios que sigue actualmente
        if (followingFile.exists() && followingFile.length() > 0) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(followingFile))) {
                following = (ArrayList<String>) ois.readObject();
            } catch (EOFException eof) {
                System.out.println("El archivo de siguiendo está vacío o corrupto.");
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        // Agregar el nuevo usuario a seguir si no está ya en la lista
        if (!following.contains(username)) {
            following.add(username);
        }

        // Guardar la lista actualizada
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(followingFile))) {
            oos.writeObject(following);
        } catch (IOException e) {
            e.printStackTrace();
        }

        File followersFile = new File("D:/admin/usuarios/" + username + "/followers.ins");
        ArrayList<String> followers = new ArrayList<>();

        if (followersFile.exists() && followersFile.length() > 0) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(followersFile))) {
                followers = (ArrayList<String>) ois.readObject();
            } catch (EOFException eof) {
                System.out.println("El archivo de seguidores está vacío o corrupto.");
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        if (!followers.contains(usuarioActivo.getUsername())) {
            followers.add(usuarioActivo.getUsername());
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(followersFile))) {
            oos.writeObject(followers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void dejarDeSeguirUsuario(String username) {
        // Actualizar el archivo following.ins del usuario activo
        File followingFile = new File("D:/admin/usuarios/" + usuarioActivo.getUsername() + "/following.ins");
        ArrayList<String> following = new ArrayList<>();

        // Cargar los usuarios que sigue actualmente
        if (followingFile.exists() && followingFile.length() > 0) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(followingFile))) {
                following = (ArrayList<String>) ois.readObject();
            } catch (EOFException eof) {
                System.out.println("El archivo de siguiendo está vacío o corrupto.");
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        // Eliminar el usuario de la lista
        following.remove(username);

        // Guardar la lista actualizada
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(followingFile))) {
            oos.writeObject(following);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Actualizar el archivo followers.ins del usuario dejado de seguir
        File followersFile = new File("D:/admin/usuarios/" + username + "/followers.ins");
        ArrayList<String> followers = new ArrayList<>();

        if (followersFile.exists() && followersFile.length() > 0) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(followersFile))) {
                followers = (ArrayList<String>) ois.readObject();
            } catch (EOFException eof) {
                System.out.println("El archivo de seguidores está vacío o corrupto.");
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        // Eliminar el usuario de la lista de seguidores
        followers.remove(usuarioActivo.getUsername());

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(followersFile))) {
            oos.writeObject(followers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

// Método para ver el catálogo de imágenes de un usuario
    private void verCatalogoImagenes(String username) {
        java.util.List<File> imagenes = obtenerImagenesUsuario(username);

        if (imagenes.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El usuario no tiene imágenes cargadas.");
            return;
        }

        JPanel imagenesPanel = new JPanel();
        imagenesPanel.setLayout(new BoxLayout(imagenesPanel, BoxLayout.Y_AXIS));
        imagenesPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        for (File imagen : imagenes) {
            JLabel imagenLabel = new JLabel(new ImageIcon(new ImageIcon(imagen.getPath()).getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH)));
            imagenLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            imagenesPanel.add(imagenLabel);
            imagenesPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Espaciado
        }

        JScrollPane scrollPane = new JScrollPane(imagenesPanel);
        scrollPane.setPreferredSize(new Dimension(500, 400));

        JOptionPane.showMessageDialog(this, scrollPane, "Catálogo de Imágenes de " + username, JOptionPane.INFORMATION_MESSAGE);
    }

    private void activarDesactivarCuenta() {
        if (usuarioActivo.isActivo()) {
            int confirm = JOptionPane.showConfirmDialog(this, "¿Estás seguro de que deseas desactivar tu cuenta?", "Confirmar Desactivación", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                usuarioActivo.setActivo(false);
                guardarUsuarioActivo();
                JOptionPane.showMessageDialog(this, "Tu cuenta ha sido desactivada.");
                // Cerrar sesión y regresar al inicio
                cerrarSesion();
            }
        } else {
            usuarioActivo.setActivo(true);
            guardarUsuarioActivo();
            JOptionPane.showMessageDialog(this, "Tu cuenta ha sido activada.");
        }
    }

// Método para guardar el estado actualizado del usuario activo
    private void guardarUsuarioActivo() {
        // Cargar todos los usuarios
        ArrayList<Usuario> todosUsuarios = cargarTodosUsuarios();

        // Buscar y actualizar el usuario activo
        for (Usuario usuario : todosUsuarios) {
            if (usuario.getUsername().equals(usuarioActivo.getUsername())) {
                usuario.setActivo(usuarioActivo.isActivo());
                break;
            }
        }

        // Guardar todos los usuarios nuevamente
        File usuariosFile = new File("D:/admin/usuarios.bin");  // Ruta actualizada
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(usuariosFile))) {
            oos.writeObject(todosUsuarios);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Usuario obtenerUsuarioPorUsername(String username) {
        for (Usuario usuario : usuarios) {
            if (usuario.getUsername().equals(username)) {
                return usuario;
            }
        }
        return null;
    }

    private ArrayList<String> cargarComentarios(String username) {
        ArrayList<String> comentarios = new ArrayList<>();
        File instaFile = new File("D:/admin/usuarios/" + username + "/insta.ins");  // Ruta actualizada
        if (instaFile.exists() && instaFile.length() > 0) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(instaFile))) {
                comentarios = (ArrayList<String>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return comentarios;
    }

    private ArrayList<String> obtenerUsuariosSeguidos() {
        File followingFile = new File("D:/admin/usuarios/" + usuarioActivo.getUsername() + "/following.ins");  // Ruta actualizada
        ArrayList<String> usuariosSeguidos = new ArrayList<>();
        if (followingFile.exists() && followingFile.length() > 0) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(followingFile))) {
                usuariosSeguidos = (ArrayList<String>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return usuariosSeguidos;
    }

}
