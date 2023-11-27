package com.example.androidengine;

import android.graphics.Typeface;
import android.content.res.AssetManager;
import com.example.engine.IFont;

public class FontAndroid implements IFont {

    private Typeface myFont_; //Apariencia del texto que se usa al pintar
    private int size_; //Tamanyo de la letra
    private boolean bold_; //Letra en negrita
    private boolean italic_; //Letra en cursiva
    private String path_ = "Fonts/"; // Ruta predeterminada para buscar archivos de fuente

    //Constructor de la clase con parámetros: nombre de archivo, tamanyo letra, negrita,
    // cursiva y asset manager donde buscar el ttf
    public FontAndroid(String filename,int size,boolean isBold,boolean italic, AssetManager assetManager) {
        this.size_ = size;
        this.bold_ = isBold;
        this.italic_ =italic;
        myFont_ =null;
        myFont_ =Typeface.createFromAsset(assetManager,path_+filename);

        int style = Typeface.NORMAL;
        if (this.bold_&& this.italic_) {
            style = Typeface.BOLD_ITALIC;
        }
        else if(this.bold_){
            style = Typeface.BOLD;
        }
        else if (this.italic_) {
            style = Typeface.ITALIC;
        }
        // Deriva la fuente con el estilo y tamaño especificados
        myFont_ = Typeface.create(myFont_,style);
    }
    //Devuelve el typefont que especifica el estilo del texto
    public Typeface getFont(){
        return myFont_;
    }
    //Asigna un valor al booleano de negrita
    @Override
    public void setBold_(boolean bold_) {
        this.bold_ = bold_;
    }
    //Asigna un valor al booleano de cursiva
    @Override
    public void setItalic_(boolean italic_) {
        this.italic_ = italic_;
    }
    //Obtiene el booleano que indica si es negrita
    @Override
    public boolean isBold_() {
        return this.bold_;
    }
    //Obtiene el booleano que indica si es cursiva
    @Override
    public boolean isItalic_() {
        return italic_;
    }
    //Devuelve el tamanyo de la letra
    @Override
    public int getSize_() {
        return this.size_;
    }
    //Etablece el tamanyo de la letra
    @Override
    public void setSize_(int size_) {
        this.size_ = size_;
    }
}
