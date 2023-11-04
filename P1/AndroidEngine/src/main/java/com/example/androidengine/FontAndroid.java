package com.example.androidengine;

import android.graphics.Typeface;
import android.content.res.AssetManager;
import com.example.engine.IFont;

import java.io.InputStream;

public class FontAndroid implements IFont {

    private Typeface myFont_; //Apariencia del texto que se usa al pintar
    private int size_; //Tamanyo de la letra
    private boolean bold_; //Letra en negrita
    private boolean italic_; //Letra en cursiva

    //Constructor de la clase con parámetros: nombre de archivo, tamanyo letra, negrita,
    // cursiva y asset manager donde buscar el ttf
    public FontAndroid(String filename,int size,boolean isBold,boolean italic, AssetManager assetManager) {
        this.size_ = size;
        this.bold_ = isBold;
        this.italic_=italic;
        myFont_=null;
        myFont_=Typeface.createFromAsset(assetManager,filename);

    }
    //Devuelve el typefont que especifica el estilo del texto
    public Typeface getFont(){
        return myFont_;
    }
    //Asigna un valor al booleano de negrita
    @Override
    public void setBold(boolean bold) {
        this.bold_ = bold;
    }
    //Asigna un valor al booleano de cursiva
    @Override
    public void setItalic(boolean italic) {
        italic_=italic;
    }
    //Obtiene el booleano que indica si es negrita
    @Override
    public boolean isBold() {
        return this.bold_;
    }
    //Obtiene el booleano que indica si es cursiva
    @Override
    public boolean isItalic() {
        return italic_;
    }
    //Devuelve el tamanyo de la letra
    @Override
    public int getSize() {
        return this.size_;
    }
    //Etablece el tamanyo de la letra
    @Override
    public void setSize(int size) {
        this.size_ = size;
    }
}
