package Inicio_de_Sesion;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class SessionManager {

    private user currentUser; // Usuario regular actualmente autenticado
    private admin adminUser; // Usuario administrador

    // Método para iniciar sesión
    public boolean login(String username, String password) {
        if (username.equals("admin")) {
            // Validación del usuario administrador con credenciales fijas
            if (password.equals("12345")) {
                adminUser = new admin(); // Crear instancia de admin
                currentUser = null; // No asignamos admin a currentUser
                return true; // Login exitoso para admin
            } else {
                return false; // Contraseña incorrecta para admin
            }
        } else {
            // Autenticación de un usuario regular
            currentUser = findUser(username, password);
            adminUser = null; // Limpiar adminUser si no es administrador
            return currentUser != null; // Retorna true si el usuario fue encontrado
        }
    }

    // Método para buscar y autenticar un usuario regular desde su archivo de información
    private user findUser(String username, String password) {
        // Ruta base donde se encuentra la carpeta del usuario
        String userDirectoryPath = "D:\\admin\\" + username;

        // Archivo con la información del usuario
        File userInfoFile = new File(userDirectoryPath + "\\user_info.txt");

        // Verificar si el archivo de información existe
        if (userInfoFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(userInfoFile))) {
                String line;
                String fileUsername = null;
                String filePassword = null;

                // Leer el archivo línea por línea
                while ((line = reader.readLine()) != null) {
                    if (line.startsWith("Usuario: ")) {
                        fileUsername = line.substring(9).trim(); // Extraer nombre de usuario
                    } else if (line.startsWith("Contraseña: ")) {
                        filePassword = line.substring(12).trim(); // Extraer contraseña
                    }
                }

                // Comparar credenciales con las proporcionadas
                if (username.equals(fileUsername) && password.equals(filePassword)) {
                    return new user(username, password); // Retornar un objeto User si las credenciales coinciden
                }
            } catch (IOException e) {
                System.out.println("Error al leer el archivo de usuario: " + e.getMessage());
            }
        }

        // Retornar null si no se encuentra el usuario o no coinciden las credenciales
        return null;
    }

    // Método para cerrar la sesión del usuario actual
    public void logout() {
        currentUser = null;
        adminUser = null;
        System.out.println("Sesión cerrada.");
    }

    // Obtener el usuario regular actual
    public user getCurrentUser() {
        return currentUser;
    }

    // Obtener el usuario administrador actual
    public admin getAdminUser() {
        return adminUser;
    }

    // Método para obtener la ruta de la carpeta de música del usuario actual
    public String getCurrentUserMusicFolderPath() {
        if (currentUser != null) {
            // Ruta base para la carpeta de música del usuario
            String userMusicFolderPath = "D:\\admin\\" + currentUser.getUsername() + "\\Música";
            File musicFolder = new File(userMusicFolderPath);

            // Verificar si la carpeta de música existe
            if (musicFolder.exists() && musicFolder.isDirectory()) {
                return userMusicFolderPath;
            } else {
                System.out.println("La carpeta de música no existe para el usuario actual.");
                return null;
            }
        } else {
            System.out.println("No hay ningún usuario autenticado.");
            return null;
        }
    }

    // Agregar este método en SessionManager
    public String getCurrentUserDocumentsFolderPath() {
        if (currentUser != null) {
            // Ruta base para la carpeta de Mis Documentos del usuario
            String userDocumentsFolderPath = "D:\\admin\\" + currentUser.getUsername() + "\\Mis Documentos";
            File documentsFolder = new File(userDocumentsFolderPath);

            // Verificar si la carpeta de Mis Documentos existe
            if (documentsFolder.exists() && documentsFolder.isDirectory()) {
                return userDocumentsFolderPath;
            } else {
                System.out.println("La carpeta de Mis Documentos no existe para el usuario actual.");
                return null;
            }
        } else {
            System.out.println("No hay ningún usuario autenticado.");
            return null;
        }
    }

}
