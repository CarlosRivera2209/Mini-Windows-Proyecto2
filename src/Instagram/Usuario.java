package Instagram;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Usuario implements Serializable {
    
    private String nombre;
    private char genero;
    private String username;
    private String password;
    private int edad;
    private String fotoPerfil;
    private boolean activo;
    private LocalDateTime fechaIngreso;

    public Usuario(String nombre, char genero, String username, String password, int edad, String fotoPerfil) {
        this.nombre = nombre;
        setGenero(genero); // Validar el género
        this.username = username;
        this.password = password;
        setEdad(edad); // Validar la edad
        this.fotoPerfil = fotoPerfil != null ? fotoPerfil : "";
        this.activo = true;
        this.fechaIngreso = LocalDateTime.now();
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public char getGenero() {
        return genero;
    }

    public void setGenero(char genero) {
        if (genero == 'M' || genero == 'F' || genero == 'O') {
            this.genero = genero;
        } else {
            throw new IllegalArgumentException("Género no válido. Use 'M', 'F' o 'O'.");
        }
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String oldPassword, String newPassword) {
        if (this.password.equals(oldPassword)) {
            this.password = newPassword;
        } else {
            throw new IllegalArgumentException("La contraseña actual no coincide.");
        }
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        if (edad >= 13) {
            this.edad = edad;
        } else {
            throw new IllegalArgumentException("La edad debe ser mayor o igual a 13 años.");
        }
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public LocalDateTime getFechaIngreso() {
        return fechaIngreso;
    }
}
