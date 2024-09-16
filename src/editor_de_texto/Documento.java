package editor_de_texto;

import java.io.Serializable;
import javax.swing.JTextPane;
import javax.swing.text.StyledDocument;
import javax.swing.text.Style;

public class Documento implements Serializable {
    private static final long serialVersionUID = 1L; // Identificador de versión de serialización
    private transient JTextPane panel; // No se serializa directamente
    private transient StyledDocument doc; // No se serializa directamente
    private transient Style estilo; // No se serializa directamente
    private String texto; // Se guarda el texto para restaurarlo al cargar

    // Constructor de la clase
    public Documento(JTextPane panel, StyledDocument doc, Style estilo) {
        this.panel = panel;
        this.doc = doc;
        this.estilo = estilo;
        this.texto = panel.getText(); // Guardar el texto del JTextPane
    }

    // Métodos Getters y Setters
    public JTextPane getPanel() {
        return panel;
    }

    public void setPanel(JTextPane panel) {
        this.panel = panel;
        this.doc = panel.getStyledDocument();
    }

    public StyledDocument getDoc() {
        return doc;
    }

    public void setDoc(StyledDocument doc) {
        this.doc = doc;
    }

    public Style getEstilo() {
        return estilo;
    }

    public void setEstilo(Style estilo) {
        this.estilo = estilo;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    @Override
    public String toString() {
        return "Documento{" + "texto=" + texto + '}';
    }
}
