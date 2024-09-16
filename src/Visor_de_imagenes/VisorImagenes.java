package Visor_de_imagenes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import javax.swing.filechooser.FileNameExtensionFilter;

public class VisorImagenes extends JFrame {
    private JLabel labelImagen;
    private JButton botonAdelantar, botonRegresar, botonSeleccionarCarpeta;
    private ArrayList<ImageIcon> listaImagenes;
    private int indiceActual = 0;

    public VisorImagenes() {
        super("Visor de Imágenes");

        // Configurar la interfaz
        setLayout(new BorderLayout());
        labelImagen = new JLabel();
        labelImagen.setHorizontalAlignment(JLabel.CENTER);

        botonAdelantar = new JButton("Adelantar");
        botonRegresar = new JButton("Regresar");
        botonSeleccionarCarpeta = new JButton("Seleccionar Carpeta");

        // Panel para los botones de Adelantar y Regresar
        JPanel panelBotones = new JPanel();
        panelBotones.add(botonRegresar);
        panelBotones.add(botonAdelantar);

        // Agregar los componentes a la ventana
        add(botonSeleccionarCarpeta, BorderLayout.NORTH);
        add(labelImagen, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        // Acciones para los botones
        botonAdelantar.addActionListener(e -> {
            if (listaImagenes != null && indiceActual < listaImagenes.size() - 1) {
                indiceActual++;
                actualizarImagen();
            }
        });

        botonRegresar.addActionListener(e -> {
            if (listaImagenes != null && indiceActual > 0) {
                indiceActual--;
                actualizarImagen();
            }
        });

        botonSeleccionarCarpeta.addActionListener(e -> seleccionarCarpeta());

        // Configuración de la ventana
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Método para seleccionar una carpeta con JFileChooser
    private void seleccionarCarpeta() {
        JFileChooser selectorCarpeta = new JFileChooser();
        selectorCarpeta.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        
        int resultado = selectorCarpeta.showOpenDialog(this);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            File carpetaSeleccionada = selectorCarpeta.getSelectedFile();
            cargarImagenes(carpetaSeleccionada);
        }
    }

    // Método para cargar imágenes de la carpeta seleccionada
    private void cargarImagenes(File carpeta) {
        listaImagenes = new ArrayList<>();
        File[] archivos = carpeta.listFiles((dir, nombre) -> nombre.endsWith(".jpg") || nombre.endsWith(".png"));

        if (archivos != null) {
            for (File archivo : archivos) {
                listaImagenes.add(new ImageIcon(archivo.getAbsolutePath()));
            }
        }

        if (!listaImagenes.isEmpty()) {
            indiceActual = 0;
            actualizarImagen();
        }
    }

    // Método para redimensionar la imagen
    private ImageIcon redimensionarImagen(ImageIcon icono) {
        Image imagen = icono.getImage();
        Image imagenRedimensionada = imagen.getScaledInstance(labelImagen.getWidth(), labelImagen.getHeight(), Image.SCALE_SMOOTH);
        return new ImageIcon(imagenRedimensionada);
    }

    // Método para actualizar la imagen mostrada
    private void actualizarImagen() {
        if (listaImagenes != null && !listaImagenes.isEmpty()) {
            ImageIcon imagen = listaImagenes.get(indiceActual);
            labelImagen.setIcon(redimensionarImagen(imagen));
        }
    }
}