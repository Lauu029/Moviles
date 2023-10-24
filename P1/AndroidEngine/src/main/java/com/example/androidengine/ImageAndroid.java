package com.example.androidengine;

import android.graphics.Bitmap;
import android.media.Image;

import com.example.engine.IImage;

//Aquí tendrán que ir todos los métodos que implementen para generar una imagen en android, entiendo
public class ImageAndroid implements IImage {
    private int width_= 0, height_ = 0;
    Image image;
    Bitmap bitmap_;
    public ImageAndroid(Image image_){
        image=image_;
    }

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
    public Bitmap getImage(){
        return bitmap_;
    }
    @Override
    public int getWidth() {
        return image.getWidth();
    }

    @Override
    public int getHeight() {
        return image.getHeight();
    }

}
