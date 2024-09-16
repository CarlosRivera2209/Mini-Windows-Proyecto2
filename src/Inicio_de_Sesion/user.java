package Inicio_de_Sesion;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;

public class user {
    private String username;
    private String password;
    private File userDirectory;
    private File userInfoFile;

    // Constructor para usuarios existentes
    public user(String username, String password) {
        this.username = username;
        this.password = password;
        this.userDirectory = new File("D:\\admin\\" + username);
        this.userInfoFile = new File(userDirectory, "user_info.txt");
        initializeUserDirectory();
    }

    // Constructor para nuevos usuarios
    public user(String username, String password, boolean isNewUser) {
        this(username, password); // Llama al constructor existente
        if (isNewUser) {
            createUserInfoFile();
        }
    }

    private void initializeUserDirectory() {
        if (!userDirectory.exists()) {
            userDirectory.mkdirs();
            new File(userDirectory, "Mis Documentos").mkdir();
            new File(userDirectory, "Música").mkdir();
            new File(userDirectory, "Mis Imágenes").mkdir();
        }
    }

    private void createUserInfoFile() {
        try (FileWriter writer = new FileWriter(userInfoFile)) {
            writer.write("Usuario: " + username + "\n");
            writer.write("Contraseña: " + password + "\n");
            writer.flush();
            System.out.println("Archivo de información de usuario creado correctamente.");
        } catch (IOException e) {
            System.out.println("Error al crear el archivo de usuario: " + e.getMessage());
        }
    }

    // Método para autenticar
    public boolean authenticate(String username, String password) {
        if (userInfoFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(userInfoFile))) {
                String line;
                String fileUsername = null;
                String filePassword = null;

                while ((line = reader.readLine()) != null) {
                    if (line.startsWith("Usuario: ")) {
                        fileUsername = line.substring(9).trim();
                    } else if (line.startsWith("Contraseña: ")) {
                        filePassword = line.substring(12).trim();
                    }
                }

                return username.equals(fileUsername) && password.equals(filePassword);
            } catch (IOException e) {
                System.out.println("Error al leer el archivo de usuario: " + e.getMessage());
            }
        }
        return false;
    }

    // Getters
    public String getUsername() {
        return username;
    }

    public File getUserDirectory() {
        return userDirectory;
    }
}
