package com.example.engine;

public interface IFont {
    void setBold_(boolean bold_); //Establece negrita
    void setItalic_(boolean italic_); //Establece cursiva
    boolean isBold_(); //Devuelve si es negrita
    boolean isItalic_(); //Devuelve si es cursiva
    int getSize_(); //Devuelve el tamanyo
    void setSize_(int size_); //Establece el tamanyo
}
