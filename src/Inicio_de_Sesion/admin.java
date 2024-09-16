package Inicio_de_Sesion;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class admin extends user {

    public admin() {
        super("admin", "12345"); // Llama al constructor de user con credenciales de admin
    }

    // Método para crear un nuevo usuario y sus carpetas
    public void createUser(String username, String password) {
        // Ruta base dentro de la carpeta admin donde se crearán las carpetas y el archivo de usuario
        String baseDirectoryPath = "D:\\admin\\";
        String userDirectoryPath = baseDirectoryPath + username;

        // Crear las carpetas del usuario
        File userDirectory = new File(userDirectoryPath);
        File documentos = new File(userDirectoryPath + "\\Mis Documentos");
        File musica = new File(userDirectoryPath + "\\Música");
        File imagenes = new File(userDirectoryPath + "\\Mis Imágenes");

        // Verificar si la carpeta del usuario ya existe
        if (userDirectory.exists()) {
            System.out.println("El usuario ya existe. No se crearán nuevos directorios.");
            return;
        }

        try {
            // Crear la carpeta principal del usuario
            if (userDirectory.mkdir()) {
                // Intentar crear cada subcarpeta y validar si se crean correctamente
                if (!documentos.mkdir()) {
                    System.out.println("Error al crear la carpeta 'Mis Documentos'.");
                }
                if (!musica.mkdir()) {
                    System.out.println("Error al crear la carpeta 'Música'.");
                }
                if (!imagenes.mkdir()) {
                    System.out.println("Error al crear la carpeta 'Mis Imágenes'.");
                }

                // Crear el archivo .txt con la información del usuario
                File userInfoFile = new File(userDirectoryPath + "\\user_info.txt");
                if (userInfoFile.createNewFile()) {
                    // Escribir el nombre de usuario y la contraseña en el archivo
                    try (FileWriter writer = new FileWriter(userInfoFile)) {
                        writer.write("Usuario: " + username + "\n");
                        writer.write("Contraseña: " + password + "\n");
                        writer.flush();
                        System.out.println("Archivo de información de usuario creado correctamente.");
                    } catch (IOException e) {
                        System.out.println("Error al escribir en el archivo: " + e.getMessage());
                    }
                } else {
                    System.out.println("El archivo de información de usuario ya existe.");
                }
            } else {
                System.out.println("No se pudo crear el directorio del usuario.");
            }
        } catch (IOException e) {
            System.out.println("Error de E/S: " + e.getMessage());
        } catch (SecurityException e) {
            System.out.println("Error de permisos: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error al crear las carpetas o el archivo: " + e.getMessage());
        }
    }
}
