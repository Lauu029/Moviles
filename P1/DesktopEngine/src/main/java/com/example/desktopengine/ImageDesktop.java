package com.example.desktopengine;

import com.example.engine.IImage;
import java.awt.Image;  // Importa la clase Image de java.awt


public class ImageDesktop implements IImage {

    private Image image;  // Atributo que almacena una instancia de la clase Image

    // Constructor de la clase, recibe una instancia de Image como argumento
    ImageDesktop(Image image){
        this.image = image;  // Asigna la instancia de Image al atributo image_
    }

    // Metodo que devuelve la instancia de Image almacenada en el atributo image_
    Image getImage(){
        return image;
    }

    // Implementacion del metodo getWidth definido en la interfaz IImage
    @Override
    public int getWidth() {
        return image.getWidth(null);  // Obtiene el ancho de la imagen
    }

    // Implementacion del método getHeight definido en la interfaz IImage
    @Override
    public int getHeight() {
        return image.getHeight(null);  // Obtiene la altura de la imagen
    }
}
