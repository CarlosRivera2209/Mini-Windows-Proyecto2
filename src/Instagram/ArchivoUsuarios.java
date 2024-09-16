package Instagram;

import java.io.*;
import java.util.ArrayList;

public class ArchivoUsuarios {

    private static final String FILE_NAME = "users.ins";

    // Método para leer todos los usuarios del archivo
    public static synchronized ArrayList<Usuario> leerUsuarios() {
        ArrayList<Usuario> usuarios = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            usuarios = (ArrayList<Usuario>) ois.readObject();
        } catch (FileNotFoundException e) {
            // Si el archivo no existe, crear uno vacío
            guardarUsuarios(usuarios);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return usuarios;
    }

    // Método para guardar todos los usuarios en el archivo
    public static synchronized void guardarUsuarios(ArrayList<Usuario> usuarios) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(usuarios);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para agregar un usuario
    public static synchronized void agregarUsuario(Usuario nuevoUsuario) {
        ArrayList<Usuario> usuarios = leerUsuarios();
        for (Usuario u : usuarios) {
            if (u.getUsername().equalsIgnoreCase(nuevoUsuario.getUsername())) {
                System.out.println("El username '" + nuevoUsuario.getUsername() + "' ya existe. Por favor, elige otro.");
                return;
            }
        }
        usuarios.add(nuevoUsuario);
        guardarUsuarios(usuarios);
        System.out.println("Usuario agregado exitosamente.");
    }

    // Método para buscar un usuario por username
    public static Usuario buscarUsuario(String username) {
        ArrayList<Usuario> usuarios = leerUsuarios();
        for (Usuario u : usuarios) {
            if (u.getUsername().equalsIgnoreCase(username)) {
                return u;
            }
        }
        System.out.println("Usuario con el username '" + username + "' no encontrado.");
        return null; // Si no se encuentra
    }
}
