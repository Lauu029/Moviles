package com.example.androidengine;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.PixelCopy;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;
import java.io.InputStream;

public class Graphics {
    private int width_ = 0, height_ = 0; //Parametros que gruardan el alto y el ancho del canvas
    private SurfaceView myView_; //Proporciona una superficie transparente donde dibujar
    private SurfaceHolder myHolder_; //Proporciona control sobre el formato y tamanyo de de una superficie
    private Paint myPaint_; //Contiene la información de estilo y color sobre cómo dibujar geometrías, texto y mapas de bits
    private Canvas myCanvas_; //Referencia al objeto canvas donde se realiza el renderizado en android
    private float scale_ = 1; //Parametro que guarda la proporcion de escalado de los elementos de la panatalla
    private float translateX_ = 0, translateY_ = 0; //Parametros que guardan el desplazamiento de los elementos de la pantalla al escalar
    private AssetManager myAssetManager_; //Referencia al asset manager para buscar las imagenes
    private String path_ = "Images/"; // Ruta predeterminada para buscar archivos de images

    //Contructor de la clase
    public Graphics(SurfaceView view, AssetManager asset) {
        //inicalizamos las variables
        this.myView_ = view;
        this.myHolder_ = this.myView_.getHolder();
        this.myPaint_ = new Paint();
        this.myCanvas_ = new Canvas();
        myAssetManager_ = asset;
    }

    //Método encargado de hacer los calculos necesarios para reescaalar la pantalla a un determinado tamanyo
    protected void resize(float sceneWidth, float sceneHeight) {
        //Obtiene el tamanyo actual
        height_ = myCanvas_.getHeight();
        width_ = myCanvas_.getWidth();
        //Calcula el factor de escalado para el tamanyo deseado
        float scaleW = (float) width_ / (float) sceneWidth;
        float scaleH = (float) height_ / (float) sceneHeight;
        //Guarda el menor de los factores
        if (scaleW < scaleH)
            scale_ = scaleW;
        else
            scale_ = scaleH;
        //Calcula el tamanyo que tendra al reescalar
        float resizeW, resizeH;
        resizeW = sceneWidth * scale_;
        resizeH = sceneHeight * scale_;
        //Calcula cuanto debera moverse en X e Y para que quede centrado
        translateX_ = (width_ - resizeW) / 2;
        translateY_ = (height_ - resizeH) / 2;
    }

    //Devuelve el valor del factor de escalado
    protected float getScale_() {
        return scale_;
    }

    //Devuelve el valor del factor de desplazamiento en X
    protected float getTranslateX_() {
        return translateX_;
    }

    //Devuelve el valor del factor de desplazamiento en Y
    protected float getTranslateY_() {
        return translateY_;
    }

    //Crea una imagen de android a base de un mapa de bits y la ruta de  la imagen
    public Image newImage(String name) {
        Bitmap bitmap = null;
        InputStream is = null;
        try {
            is = this.myAssetManager_.open(path_+name); //Abre el archivo
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (is != null)
            bitmap = BitmapFactory.decodeStream(is);

        return new Image(bitmap);
    }

    //Crea un objeto fuente de android
    public Font newFont(String filename, int size, boolean isBold, boolean italic) {
        return new Font(filename, size, isBold, italic, myAssetManager_);
    }
    //Pinta el canvas de un determinado color "limpiando" lo que hay en pantalla
    public void clear(int color) {
        myCanvas_.drawColor(color);
    }
    //Actualiza el grosor de la pintura a un determinado valor
    public void setStrokeWidth(int width) {
        myPaint_.setStrokeWidth(width);
    }
    //Dibuja una imagen en una determinada posicion a partir de un rectangulo origen y otro destino
    public void drawImage(Image iimage, int posX, int posY, int height, int widht) {
        Image imageAndroid = (Image) iimage;
        Rect src = new Rect();
        src.left = 0;
        src.top = 0;
        src.right = imageAndroid.getWidth();
        src.bottom = imageAndroid.getHeight();
        Rect dst = new Rect();
        dst.left = posX;
        dst.top = posY;
        dst.right = posX + widht;
        dst.bottom = posY + height;
        this.myCanvas_.drawBitmap(imageAndroid.getImage(), src, dst, this.myPaint_);
    }
    //Establece el color con el que se va a pintar
    public void setColor(int color_) {
        this.myPaint_.setColor(color_);
    }
    //Metodo para pintar un rectangulo con relleno
    public void fillRectangle(int cX, int cY, int width, int height) {
        myPaint_.setStyle(Paint.Style.FILL);
        myCanvas_.drawRect(cX, cY, width + cX, height + cY, this.myPaint_);
    }
    //Metodo para pintar un rectangulo con bordes redondeados y relleno
    public void fillRoundRectangle(int cX, int cY, int width, int height, int arc) {
        myPaint_.setStyle(Paint.Style.FILL);
        RectF rect = new RectF(cX, cY, cX + width, cY + height);
        myCanvas_.drawRoundRect(rect, arc, arc, myPaint_);
    }
    //Metodo para pintar un rectangulo sin relleno
    public void drawRectangle(int cX, int cY, int width, int height) {
        myPaint_.setStyle(Paint.Style.STROKE);
        myCanvas_.drawRect(cX, cY, width, height, this.myPaint_);
    }
    //Metodo para pintar un rectangulo con bordes redondeados sin relleno
    public void drawRoundRectangle(int cX, int cY, int width, int height, int arc) {
        myPaint_.setStyle(Paint.Style.STROKE);

        RectF rect = new RectF(cX, cY, cX + width, cY + height);
        myCanvas_.drawRoundRect(rect, arc, arc, myPaint_);
    }
    //Metodo para pintar una linea recta
    public void drawLine(int initX, int initY, int endX, int endY) {
        myCanvas_.drawLine(initX, initY, endX, endY, this.myPaint_);
    }
    //Establece el tipo de fuente con el que se va a escribir
    public void setFont(Font font) {

        myPaint_.setTypeface(font.getFont()); //Establece el tipo de letra a usar
        myPaint_.setTextSize(font.getSize_());
    }
    //Metodo para dibujar un circulo con relleno
    public void drawCircle(int cx, int cy, int radius) {
        myPaint_.setStyle(Paint.Style.FILL);
        myCanvas_.drawCircle(cx, cy, radius, myPaint_);
    }
    //Metodo para dibujar un texto centrado
    public void drawText(String text, int x, int y) {
        myPaint_.setStyle(Paint.Style.FILL);

        // Medir el ancho del texto
        float textWidth = myPaint_.measureText(text);
        float textHeight = myPaint_.getTextSize(); // Altura de la fuente
        // Calcular las coordenadas para centrar el texto
        float centerX = x - (textWidth / 2);
        float centerY = y + (textHeight / 2);

        myCanvas_.drawText(text, centerX, centerY, myPaint_);
    }
    //Metodo que devuelve el ancho del canvas
    protected int getWidth_() {
        return width_;
    }
    //Metodo que devuelve el alto del canvas
    protected int getHeight_() {
        return height_;
    }
    //Metodo para desplazar el canvas
    protected void translate(float x, float y) {
        this.myCanvas_.translate(x, y);
    }
    //Metodo para reescalar el canvas
    public void scale(float x, float y) {
        this.myCanvas_.scale(x, y);
    }
    protected void save() {}

    protected void restore() {}
    //Metodo que permite comenzar a editar los pixeles del canvas
    protected void prepareFrame() {
        while (!this.myHolder_.getSurface().isValid()) ;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { //Dependiendo de la version usamos lockCanvas o LockHardwareCanvas
            this.myCanvas_ = this.myHolder_.lockHardwareCanvas();
        } else this.myCanvas_ = this.myHolder_.lockCanvas();
        this.translate(translateX_, translateY_); //Situa el canvas en la posicion correcta si ha sido reescalado
        this.scale(scale_, scale_);
    }
    //Metodo que indica que se ha terminado de editar píxeles en la superficie.
    // Después de esta llamada, los píxeles actuales de la superficie se mostrarán en la pantalla.
    protected void endFrame() {
        this.myHolder_.unlockCanvasAndPost(myCanvas_);
    }

    public void generateScreenshot(int x, int y, int w, int h, ImageProcessingCallback callback){
        Bitmap bitmap = Bitmap.createBitmap(this.myView_.getWidth(), this.myView_.getHeight(),
                Bitmap.Config.ARGB_8888);

        if(x < 0) x=0;
        if(y < 0) y=0;
        if(x>myView_.getWidth()) x=myView_.getWidth();
        if(y>myView_.getHeight()) y=myView_.getHeight();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            HandlerThread handlerThread = new HandlerThread("PixelCopier");
            handlerThread.start();
            int finalX = x;
            int finalY = y;
            PixelCopy.request(this.myView_, bitmap, new PixelCopy.OnPixelCopyFinishedListener() {
                @Override
                public void onPixelCopyFinished(int copyResult) {
                    if (copyResult == PixelCopy.SUCCESS) {

                        int realX=Math.abs(finalX -(int)translateX_);
                        int realY=Math.abs(finalY -(int)translateY_);

                        int realW=(int)(w*scale_);
                        int realH=(int)(h*scale_);
                        Bitmap finalBitmap=Bitmap.createBitmap(bitmap,realX,realY,realW,realH);
                        callback.processImage(finalBitmap);
                    }
                    handlerThread.quitSafely();
                }
            }, new Handler(handlerThread.getLooper()));
        }
    }
}
