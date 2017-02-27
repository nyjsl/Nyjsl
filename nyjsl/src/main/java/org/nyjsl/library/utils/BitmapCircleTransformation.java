package org.nyjsl.library.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.v4.content.ContextCompat;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.TransformationUtils;

/**
 * Created by pc on 2016/12/23.
 */

public class BitmapCircleTransformation extends BitmapTransformation{


    private static final int DEFAULT_BORDER_WIDTH = 0;
    private static final int DEFAULT_BORDER_COLOR = Color.BLACK;

    public static final int CENTER_CROP = 0;
    public static final int FIT_CENTER = 1;
    /**
     * 图片填充模式  CENTER_CROP|FIT_CENTER
     */
    private int mode;
    /**
     * 边框的颜色
     */
    private int borderColor = DEFAULT_BORDER_COLOR;
    /**
     * 边框的宽度
     */
    private float boderWidth = DEFAULT_BORDER_WIDTH;
    /**
     * 是否有边框
     */
    private boolean shouldDrawBorder = false;
    /**
     * 圆形的宽度
     */
    private float circleSize = 0;

    private float outterSize = 0;

    /**
     * default CETNER_CROP and no border
     * @param context
     */
    public BitmapCircleTransformation(Context context){
        this(context,CENTER_CROP);
    }

    public BitmapCircleTransformation(Context context, int mode) {
        super(context);
        this.mode = mode;
    }

    public BitmapCircleTransformation(Context context, @ColorRes int colorResId, @DimenRes int borderWidth){
        this(context,CENTER_CROP);
        this.borderColor = ContextCompat.getColor(context,colorResId);
        this.boderWidth = context.getResources().getDimension(borderWidth);
    }

    public BitmapCircleTransformation(Context context, int mode, @ColorRes int colorResId, @DimenRes int borderWidth){
        this(context,mode);
        this.borderColor = ContextCompat.getColor(context,colorResId);
        this.boderWidth = context.getResources().getDimension(borderWidth);
    }



    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {

        calcCircleWidthAndIfShouldDrawBorder(outWidth, outHeight);

        Bitmap result;
        switch (mode) {
            case CENTER_CROP:
                result = centerCropResult(pool, toTransform, (int) circleSize);
                break;
            case FIT_CENTER:
                result = fitCenterResult(pool, toTransform, (int) circleSize);
                break;
            default:
                result = fitCenterResult(pool, toTransform, (int) circleSize);
                break;
        }
        final Bitmap whitCircle = whilteCircle((int) circleSize);
        final Bitmap resultNoBorder = xFerModeSrcIn(result, whitCircle);
        if(shouldDrawBorder){
            return drawBorderCircle(resultNoBorder);
        }
        return resultNoBorder;
    }

    /**
     * 图片使用fitCenter模式
     * @param pool
     * @param toTransform
     * @return
     */
    private Bitmap fitCenterResult(BitmapPool pool, Bitmap toTransform, int size) {
        return TransformationUtils.fitCenter(toTransform,pool,size,size);
    }


    /**
     * 计算圆形所在正方形的变成,当前ImageView宽高的较小值
     * @param outWidth
     * @param outHeight
     * @return
     */
    private void calcCircleWidthAndIfShouldDrawBorder(int outWidth, int outHeight) {
        circleSize = Math.min(outWidth, outHeight);
        outterSize = circleSize;
        shouldDrawBorder = boderWidth>0 && boderWidth*2 <circleSize;
        if(shouldDrawBorder){
            circleSize = (outterSize - boderWidth*2);
        }
    }

    /**使用centerCrop模式
     * @param pool
     * @param toTransform
     * @return
     */
    private Bitmap centerCropResult(BitmapPool pool, Bitmap toTransform, int size) {
        return TransformationUtils.centerCrop(pool.get(size,size,toTransform.getConfig()),toTransform,size,size);
    }

    /**
     * 两张bitmap取交集 ,详见PorterDuff.Mode.SRC_IN 使用方法
     * @param src
     * @param dest
     * @return
     */
    private Bitmap xFerModeSrcIn(Bitmap src, Bitmap dest){

        Canvas canvas = new Canvas(dest);
        Paint paint = new Paint();
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        paint.setAntiAlias(true);
        int srWidth = src.getWidth();
        int destWidth = dest.getWidth();
        float left = (destWidth - srWidth) / 2 ;
        left = left >0 ? left : 0;
        int srcHeight = src.getHeight();
        int destHeight = dest.getHeight();
        float top = (destHeight - srcHeight) / 2 ;
        top = top >0 ? top : 0;

        canvas.drawBitmap(src,left, top,paint);
        return dest;
    }

    /**
     * 生成白色背景的圆图
     * @return
     */
    private Bitmap whilteCircle(int size) {
        Bitmap result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        int[] colors = new int[]{Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE};
        Bitmap squared = Bitmap.createBitmap(colors,2,2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
        paint.setAntiAlias(true);
        float radius =  size / 2;
        float center = size/2;
        canvas.drawCircle(center, center, radius, paint);
        return result;
    }

    /**
     * 在小圈外画边框
     */
    private Bitmap drawBorderCircle(Bitmap bitmap) {
        //外层
        Bitmap outter = Bitmap.createBitmap((int) Math.ceil(outterSize),(int) Math.ceil(outterSize), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(outter);
        Paint innerPaint = new Paint();
        innerPaint.setAntiAlias(true);
        canvas.drawBitmap(bitmap,boderWidth+0.5f,boderWidth+1.5f,innerPaint); //0.5f ,1.5f 为修正值,创建外层圆的时候有四舍五入
//        //外面边框
        Paint borderPaint = new Paint();
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setAntiAlias(true);
        borderPaint.setStrokeWidth(boderWidth);
        borderPaint.setColor(borderColor);
        Rect rect = canvas.getClipBounds();
        RectF refcf = new RectF(rect.left+boderWidth/2,rect.top+boderWidth/2,rect.right-boderWidth/2,rect.bottom-boderWidth/2);
        canvas.drawRoundRect(refcf,(outterSize)/2,(outterSize)/2,borderPaint);//注意这里圆角的弧度为outtSize/2
        return outter;
    }


    @Override
    public String getId() {
        return getClass().getName();
    }
}
