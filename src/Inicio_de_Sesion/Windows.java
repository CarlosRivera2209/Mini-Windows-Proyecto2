package Inicio_de_Sesion;

import javax.swing.JOptionPane;
import Cmd.ConsolaGUI;
import java.io.File;
import Inicio_de_Sesion.user;
import Inicio_de_Sesion.SessionManager;
import Instagram.InstaInicioSesion;
import Navegador.NavegadorArchivos;
import Reproductor.ReproductorMusical;
import editor_de_texto.Principal;
import Visor_de_imagenes.VisorImagenes; // Asegúrate de tener esta importación

public class Windows extends javax.swing.JFrame {

    private SessionManager sessionManager;
    private javax.swing.JButton CMD;
    private javax.swing.JButton EditorTexto;
    private javax.swing.JButton Instagram;
    private javax.swing.JButton ReproductorMusica;
    private javax.swing.JButton VisorImagenes;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton navegadorArchivos;
    private javax.swing.JButton windowsLogout;

    public Windows(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        
        

        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        navegadorArchivos = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        EditorTexto = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        VisorImagenes = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        ReproductorMusica = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        Instagram = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        CMD = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        windowsLogout = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/file.jpg"))); // Ícono del Navegador de Archivos
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 740, 40, 30));

        navegadorArchivos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                navegadorArchivosActionPerformed(evt);
            }
        });
        jPanel1.add(navegadorArchivos, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 740, 39, 34));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/texto.png"))); // Ícono del Editor de Texto
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 740, -1, -1));

        EditorTexto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EditorTextoActionPerformed(evt);
            }
        });
        jPanel1.add(EditorTexto, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 740, 39, 34));

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/visor.png"))); // Ícono del Visor de Imágenes
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 740, 40, 40));

        VisorImagenes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                VisorImagenesActionPerformed(evt);
            }
        });
        jPanel1.add(VisorImagenes, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 740, 40, 34));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/musica.png"))); // Ícono del Reproductor de Música
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 740, -1, -1));

        ReproductorMusica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ReproductorMusicaActionPerformed(evt);
            }
        });
        jPanel1.add(ReproductorMusica, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 740, 39, 34));

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Instagram_logo_2016.svg.jpg"))); // Ícono de Instagram
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 740, -1, -1));

        Instagram.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InstagramActionPerformed(evt);
            }
        });
        jPanel1.add(Instagram, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 740, 39, 34));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/lupa.png"))); // Ícono del CMD
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 740, 40, 40));

        CMD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CMDActionPerformed(evt);
            }
        });
        jPanel1.add(CMD, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 740, 40, 40));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/windows icon.png"))); // Ícono de Cerrar Sesión
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 740, -1, -1));

        windowsLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                windowsLogoutActionPerformed(evt);
            }
        });
        jPanel1.add(windowsLogout, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 740, 40, 40));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/windows-xp-bliss-4k-lu-1920x1080_1.jpg"))); // Fondo de pantalla
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 0, 1800, 730));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 801, Short.MAX_VALUE)
        );

        pack();
    }

     private void CMDActionPerformed(java.awt.event.ActionEvent evt) {                                    
        if (sessionManager != null) { // Verificar que sessionManager está inicializado
            user loggedInUser = sessionManager.getCurrentUser(); // Usar la instancia para obtener el usuario

            if (loggedInUser != null) {
                // Obtener el directorio del usuario
                File userDirectory = loggedInUser.getUserDirectory();

                // Abrir ConsolaGUI con el directorio del usuario
                ConsolaGUI consola = new ConsolaGUI(userDirectory, sessionManager);

                consola.setVisible(true);

            } else {
                JOptionPane.showMessageDialog(this, "No se ha iniciado sesión correctamente.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "No se ha inicializado el administrador de sesiones.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }                                   

   private void InstagramActionPerformed(java.awt.event.ActionEvent evt) {                                          
    InstaInicioSesion instaLogin = new InstaInicioSesion(sessionManager);
    instaLogin.setVisible(true);
}                                   

    private void ReproductorMusicaActionPerformed(java.awt.event.ActionEvent evt) {                                                  
// Verifica que el usuario haya iniciado sesión
        if (sessionManager != null) {
            user loggedInUser = sessionManager.getCurrentUser();
            if (loggedInUser != null) {
                // Crear y mostrar el reproductor de música
                ReproductorMusical reproductor = new ReproductorMusical(sessionManager);
                reproductor.setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "No se ha iniciado sesión correctamente.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "No se ha inicializado el administrador de sesiones.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }                                                 

    private void VisorImagenesActionPerformed(java.awt.event.ActionEvent evt) {                                              
        Visor_de_imagenes.VisorImagenes visor = new Visor_de_imagenes.VisorImagenes();
        visor.setVisible(true);
    }                                             

    private void EditorTextoActionPerformed(java.awt.event.ActionEvent evt) {                                            
        if (sessionManager != null) {
            user loggedInUser = sessionManager.getCurrentUser();
            if (loggedInUser != null) {
                // Crear y mostrar el editor de texto
                Principal principal = new Principal(sessionManager);
                principal.setVisible(true);

            } else {
                JOptionPane.showMessageDialog(this, "No se ha iniciado sesión correctamente.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "No se ha inicializado el administrador de sesiones.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }                                           

    private void navegadorArchivosActionPerformed(java.awt.event.ActionEvent evt) {                                                  
        if (sessionManager != null) {
            user loggedInUser = sessionManager.getCurrentUser();
            if (loggedInUser != null) {
                // Obtener el directorio del usuario
                File userDirectory = loggedInUser.getUserDirectory();

                // Crear y mostrar el navegador de archivos
                NavegadorArchivos navegador = new NavegadorArchivos(userDirectory); // Asegúrate de que NavegadorArchivos acepte un File
                navegador.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "No se ha iniciado sesión correctamente.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "No se ha inicializado el administrador de sesiones.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }                                                 

    private void windowsLogoutActionPerformed(java.awt.event.ActionEvent evt) {                                              
        int confirm = JOptionPane.showConfirmDialog(this, "¿Realmente desea cerrar sesión?", "Confirmar cierre de sesión", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            if (sessionManager != null) {
                sessionManager.logout(); // Cerrar sesión usando SessionManager
                LoginFrame loginFrame = new LoginFrame(); // Crear una nueva instancia de la ventana de inicio de sesión
                loginFrame.setVisible(true); // Mostrar la ventana de inicio de sesión
                dispose(); // Cerrar la ventana actual
            } else {
                JOptionPane.showMessageDialog(this, "No se ha inicializado el administrador de sesiones.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
     }                                             
}

