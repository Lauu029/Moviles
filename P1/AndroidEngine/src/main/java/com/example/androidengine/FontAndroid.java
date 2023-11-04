package com.example.androidengine;

import android.graphics.Typeface;
import android.content.res.AssetManager;
import com.example.engine.IFont;

public class FontAndroid implements IFont {

    private Typeface myFont; //Apariencia del texto que se usa al pintar
    private int size; //Tamanyo de la letra
    private boolean bold; //Letra en negrita
    private boolean italic; //Letra en cursiva

    //Constructor de la clase con parámetros: nombre de archivo, tamanyo letra, negrita,
    // cursiva y asset manager donde buscar el ttf
    public FontAndroid(String filename,int size,boolean isBold,boolean italic, AssetManager assetManager) {
        this.size = size;
        this.bold = isBold;
        this.italic =italic;
        myFont =null;
        myFont =Typeface.createFromAsset(assetManager,filename);
    }
    //Devuelve el typefont que especifica el estilo del texto
    public Typeface getFont(){
        return myFont;
    }
    //Asigna un valor al booleano de negrita
    @Override
    public void setBold(boolean bold) {
        this.bold = bold;
    }
    //Asigna un valor al booleano de cursiva
    @Override
    public void setItalic(boolean italic) {
        this.italic =italic;
    }
    //Obtiene el booleano que indica si es negrita
    @Override
    public boolean isBold() {
        return this.bold;
    }
    //Obtiene el booleano que indica si es cursiva
    @Override
    public boolean isItalic() {
        return italic;
    }
    //Devuelve el tamanyo de la letra
    @Override
    public int getSize() {
        return this.size;
    }
    //Etablece el tamanyo de la letra
    @Override
    public void setSize(int size) {
        this.size = size;
    }
}
