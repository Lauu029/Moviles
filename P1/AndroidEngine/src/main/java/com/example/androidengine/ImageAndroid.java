package com.example.androidengine;

import com.example.engine.IImage;

//Aquí tendrán que ir todos los métodos que implementen para generar una imagen en android, entiendo
public class ImageAndroid implements IImage {
    private int width_= 0, height_ = 0;


//    ImageAndroid(String source, int h, int w) {
//        height = h;
//        widht = w;
////        InputStream is = assetManager.open(source);
////        Bitmap bitmap = BitmapFactory.decodeStream(is);
////        Canvas canvas = holder.lockCanvas();
////        Paint paint = new Paint();
////        canvas.drawBitmap(bitmap, x, y, paint);
//
//    }
    public ImageAndroid(String source){

    }
    @Override
    public int getWidth() {
        return width_;
    }

    @Override
    public int getHeight() {
        return height_;
    }

}
