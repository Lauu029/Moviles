package com.example.desktopengine;

import com.example.engine.IFont;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class FontDesktop implements IFont {
    // Ruta predeterminada para buscar fuentes en la carpeta "Assets/"
    private String path = "Assets/";
    private Font myFont; // Almacena la fuente
    private int size; // Tamaño de la fuente
    private boolean bold; // Indica si la fuente es negrita
    private boolean italic; // Indica si la fuente es cursiva

    // Constructor de la clase
    public FontDesktop(String filename, int size, boolean isBold, boolean italic) {
        this.size = size;
        this.bold = isBold;
        this.italic = italic;

        InputStream is = null;
        myFont = null;

        try {
            // Intenta abrir un archivo de fuente en la carpeta especificada
            is = new FileInputStream(path + filename);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e); // Lanza una excepción en caso de error
        }

        try {
            // Crea una fuente a partir del archivo TrueType especificado
            myFont = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e); // Lanza excepciones en caso de error
        }

        // Determina el estilo de la fuente en función de las opciones de negrita y cursiva
        int style = Font.PLAIN;
        if (bold) {
            style |= Font.BOLD;
        }
        if (this.italic) {
            style |= Font.ITALIC;
        }

        // Deriva la fuente con el estilo y tamaño especificados
        myFont = myFont.deriveFont(style, this.size);
    }

    // Método para obtener la fuente
    Font getFont() {
        return myFont;
    }

    @Override
    public void setBold(boolean bold) {
        this.bold = bold;
    }

    @Override
    public void setItalic(boolean italic) {
        this.italic = italic;
    }

    @Override
    public boolean isBold() {
        return this.bold;
    }

    @Override
    public boolean isItalic() {
        return italic;
    }

    @Override
    public int getSize() {
        return this.size;
    }

    @Override
    public void setSize(int size) {
        this.size = size;
    }
}
