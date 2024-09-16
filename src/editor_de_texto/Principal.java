package editor_de_texto;

import Inicio_de_Sesion.SessionManager;
import Inicio_de_Sesion.Windows;
import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Principal extends javax.swing.JFrame {

    private SessionManager sessionManager;

    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton returnButton; // Declaración del botón Return
    private javax.swing.JComboBox<String> cb_font;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextPane tp_texto;
    private StyledDocument doc;
    private Style estilo;

    public Principal(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
        initComponents();
        doc = tp_texto.getStyledDocument();
        estilo = tp_texto.addStyle("miEstilo", null);

        DefaultComboBoxModel<String> modelo = (DefaultComboBoxModel<String>) cb_font.getModel();
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        String[] fontNames = ge.getAvailableFontFamilyNames();
        for (String fontName : fontNames) {
            modelo.addElement(fontName);
        }
        cb_font.setModel(modelo);
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tp_texto = new javax.swing.JTextPane();
        cb_font = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        returnButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jScrollPane1.setViewportView(tp_texto);

        cb_font.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb_fontActionPerformed(evt);
            }
        });

        jButton1.setText("Cambiar Color de Texto");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });

        returnButton.setText("Return");
        returnButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                returnButtonMouseClicked(evt);
            }
        });

        jButton2.setText("Negrita");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });

        jButton3.setText("Itálica");
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton3MouseClicked(evt);
            }
        });

        jButton4.setText("Subrayar");
        jButton4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton4MouseClicked(evt);
            }
        });

        jButton5.setText("Cambiar Color de Fondo");
        jButton5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton5MouseClicked(evt);
            }
        });

        jButton6.setText("Guardar");
        jButton6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton6MouseClicked(evt);
            }
        });

        jButton7.setText("Abrir");
        jButton7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton7MouseClicked(evt);
            }
        });

        jButton8.setText("Aumentar Tamaño");
        jButton8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton8MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                                        .addComponent(cb_font, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jButton1)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jButton2)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jButton3)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jButton4)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jButton5)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jButton6)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jButton7)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jButton8)
                                                .addComponent(returnButton))) // Asegúrate de agregar returnButton aquí
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(cb_font, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 263, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jButton1)
                                        .addComponent(jButton2)
                                        .addComponent(jButton3)
                                        .addComponent(jButton4)
                                        .addComponent(jButton5)
                                        .addComponent(jButton6)
                                        .addComponent(jButton7)
                                        .addComponent(jButton8)
                                        .addComponent(returnButton)) // Asegúrate de agregar returnButton aquí
                                .addContainerGap())
        );

        pack();
    }

    private void cb_fontActionPerformed(java.awt.event.ActionEvent evt) {
        String selectedFont = (String) cb_font.getSelectedItem();
        StyleConstants.setFontFamily(estilo, selectedFont);
        doc.setCharacterAttributes(0, doc.getLength(), estilo, false);
    }

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {
        try {
            // Verificar que el estilo no sea nulo antes de usarlo
            if (estilo == null) {
                estilo = tp_texto.addStyle("miEstilo", null); // Inicializa si es nulo
            }

            // Usar SimpleAttributeSet para aplicar cambios temporales
            SimpleAttributeSet attrs = new SimpleAttributeSet();
            Color selectedColor = JColorChooser.showDialog(this, "Seleccione Color", Color.red);

            // Aplicar color al atributo temporal
            if (selectedColor != null) { // Verificar que se haya seleccionado un color
                StyleConstants.setForeground(attrs, selectedColor);
                doc.setCharacterAttributes(
                        tp_texto.getSelectionStart(),
                        tp_texto.getSelectionEnd() - tp_texto.getSelectionStart(),
                        attrs,
                        true
                );
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {
        try {
            StyleConstants.setBold(estilo, true);
            doc.setCharacterAttributes(tp_texto.getSelectionStart(),
                    tp_texto.getSelectionEnd() - tp_texto.getSelectionStart(),
                    tp_texto.getStyle("miEstilo"),
                    true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jButton3MouseClicked(java.awt.event.MouseEvent evt) {
        try {
            StyleConstants.setItalic(estilo, true);
            doc.setCharacterAttributes(tp_texto.getSelectionStart(),
                    tp_texto.getSelectionEnd() - tp_texto.getSelectionStart(),
                    tp_texto.getStyle("miEstilo"),
                    true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jButton4MouseClicked(java.awt.event.MouseEvent evt) {
        try {
            StyleConstants.setUnderline(estilo, true);
            doc.setCharacterAttributes(tp_texto.getSelectionStart(),
                    tp_texto.getSelectionEnd() - tp_texto.getSelectionStart(),
                    tp_texto.getStyle("miEstilo"),
                    true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jButton5MouseClicked(java.awt.event.MouseEvent evt) {
        try {
            // Verificar que el estilo no sea nulo antes de usarlo
            if (estilo == null) {
                estilo = tp_texto.addStyle("miEstilo", null); // Inicializa si es nulo
            }

            // Crear un conjunto de atributos temporales para evitar manipular directamente el estilo
            SimpleAttributeSet attrs = new SimpleAttributeSet();
            Color selectedColor = JColorChooser.showDialog(this, "Seleccione Color de Fondo", Color.white);

            // Verificar que se haya seleccionado un color
            if (selectedColor != null) {
                // Aplicar el color de fondo usando SimpleAttributeSet
                StyleConstants.setBackground(attrs, selectedColor);
                doc.setCharacterAttributes(
                        tp_texto.getSelectionStart(),
                        tp_texto.getSelectionEnd() - tp_texto.getSelectionStart(),
                        attrs,
                        true
                );
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jButton6MouseClicked(java.awt.event.MouseEvent evt) {
        // Verificar que sessionManager no sea null
        if (sessionManager != null) {
            String documentsPath = sessionManager.getCurrentUserDocumentsFolderPath();

            if (documentsPath != null) {
                // Solicitar el nombre del archivo al usuario
                String fileName = JOptionPane.showInputDialog(this, "Ingrese el nombre del archivo:", "Guardar Archivo", JOptionPane.QUESTION_MESSAGE);

                // Verificar que se haya ingresado un nombre
                if (fileName != null && !fileName.trim().isEmpty()) {
                    File fileToSave = new File(documentsPath, fileName + ".txt"); // Agregar la extensión .txt

                    try (FileWriter writer = new FileWriter(fileToSave)) {
                        writer.write(tp_texto.getText());
                        writer.flush();
                        System.out.println("Archivo guardado correctamente en: " + fileToSave.getAbsolutePath());
                    } catch (IOException e) {
                        System.out.println("Error al guardar el archivo: " + e.getMessage());
                    }
                } else {
                    System.out.println("No se ingresó un nombre de archivo.");
                }
            } else {
                System.out.println("No se pudo guardar el archivo. La ruta de 'Mis Documentos' no está disponible.");
            }
        } else {
            System.out.println("SessionManager no está inicializado.");
        }
    }

    private void jButton7MouseClicked(java.awt.event.MouseEvent evt) {
        JFileChooser jfc = new JFileChooser();
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("El Inge Docs (.omf)", "omf");
        jfc.setFileFilter(filtro);
        int seleccion = jfc.showOpenDialog(this);
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            File fichero = jfc.getSelectedFile();
            // Obtener la extensión del archivo
            String fileName = fichero.getName();
            String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();

            // Verificar si es un archivo .omf (formato serializado) o un archivo de texto (.txt, etc.)
            if (fileExtension.equals("omf")) {
                // Manejar archivo .omf con ObjectInputStream
                try (FileInputStream entrada = new FileInputStream(fichero); ObjectInputStream objeto = new ObjectInputStream(entrada)) {
                    Documento temp = (Documento) objeto.readObject();
                    tp_texto.setText(temp.getPanel().getText());
                    tp_texto.setDocument(temp.getDoc());
                    System.out.println("Archivo .omf cargado correctamente.");
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                    System.out.println("Error al cargar el archivo .omf.");
                }
            } else if (fileExtension.equals("txt")) {
                // Manejar archivo .txt con BufferedReader o FileReader
                try (FileReader fr = new FileReader(fichero); BufferedReader br = new BufferedReader(fr)) {
                    tp_texto.setText(""); // Limpiar el JTextPane antes de cargar contenido
                    String linea;
                    while ((linea = br.readLine()) != null) {
                        tp_texto.setText(tp_texto.getText() + linea + "\n");
                    }
                    System.out.println("Archivo .txt cargado correctamente.");
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Error al cargar el archivo .txt.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Formato de archivo no soportado.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void jButton8MouseClicked(java.awt.event.MouseEvent evt) {
        String input = JOptionPane.showInputDialog(this, "Ingrese el tamaño de la fuente:");
        if (input != null && !input.isEmpty()) {
            try {
                int newSize = Integer.parseInt(input);
                StyleConstants.setFontSize(estilo, newSize);
                doc.setCharacterAttributes(tp_texto.getSelectionStart(),
                        tp_texto.getSelectionEnd() - tp_texto.getSelectionStart(),
                        tp_texto.getStyle("miEstilo"),
                        true);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Por favor, ingrese un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void returnButtonMouseClicked(java.awt.event.MouseEvent evt) {
        SessionManager sessionManager = new SessionManager();
        dispose();
    }

}
