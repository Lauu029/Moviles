package com.example.desktopengine;

import com.example.engine.IImage;
import java.awt.Image;  // Importa la clase Image de java.awt
import sun.font.GlyphRenderData;  // Importa la clase GlyphRenderData de sun.font
import sun.jvm.hotspot.utilities.BitMap;  // Importa la clase BitMap de sun.jvm.hotspot.utilities

public class ImageDesktop implements IImage {

    Image image_;  // Atributo que almacena una instancia de la clase Image

    // Constructor de la clase, recibe una instancia de Image como argumento
    ImageDesktop(Image image){
        image_ = image;  // Asigna la instancia de Image al atributo image_
    }

    // Metodo que devuelve la instancia de Image almacenada en el atributo image_
    Image getImage(){
        return image_;
    }

    // Implementacion del metodo getWidth definido en la interfaz IImage
    @Override
    public int getWidth() {
        return image_.getWidth(null);  // Obtiene el ancho de la imagen
    }

    // Implementacion del método getHeight definido en la interfaz IImage
    @Override
    public int getHeight() {
        return image_.getHeight(null);  // Obtiene la altura de la imagen
    }
}
