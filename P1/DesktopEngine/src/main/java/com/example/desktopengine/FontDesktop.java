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
    private String path_ = "Assets/";
    private Font myFont_; // Almacena la fuente
    private int size_; // Tamaño de la fuente
    private boolean bold_; // Indica si la fuente es negrita
    private boolean italic_; // Indica si la fuente es cursiva

    // Constructor de la clase
    public FontDesktop(String filename, int size, boolean isBold, boolean italic) {
        this.size_ = size;
        this.bold_ = isBold;
        this.italic_ = italic;

        InputStream is = null;
        myFont_ = null;

        try {
            // Intenta abrir un archivo de fuente en la carpeta especificada
            is = new FileInputStream(path_ + filename);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e); // Lanza una excepción en caso de error
        }

        try {
            // Crea una fuente a partir del archivo TrueType especificado
            myFont_ = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e); // Lanza excepciones en caso de error
        }

        // Determina el estilo de la fuente en función de las opciones de negrita y cursiva
        int style = Font.PLAIN;
        if (bold_) {
            style |= Font.BOLD;
        }
        if (italic_) {
            style |= Font.ITALIC;
        }

        // Deriva la fuente con el estilo y tamaño especificados
        myFont_ = myFont_.deriveFont(style, size_);
    }

    // Método para obtener la fuente
    Font getFont() {
        return myFont_;
    }

    @Override
    public void setBold(boolean bold) {
        this.bold_ = bold;
    }

    @Override
    public void setItalic(boolean italic) {
        italic_ = italic;
    }

    @Override
    public boolean isBold() {
        return this.bold_;
    }

    @Override
    public boolean isItalic() {
        return italic_;
    }

    @Override
    public int getSize() {
        return this.size_;
    }

    @Override
    public void setSize(int size) {
        this.size_ = size;
    }
}
