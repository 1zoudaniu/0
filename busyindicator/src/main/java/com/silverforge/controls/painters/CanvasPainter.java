package com.silverforge.controls.painters;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;

import com.silverforge.controls.model.ClipShape;

import lombok.Getter;
import lombok.Setter;

public class CanvasPainter {

    @Getter
    private Paint bigPaint;

    @Getter
    private Paint singlePaint;

    @Getter
    private Paint singlePaintTransparent;

    @Getter
    private Paint textPaint;

    @Getter
    private Paint customTextPaint;

    private int alreadySetInnerPointColor = -1;


    public void initializePaints(int outerPointColor, int innerPointColor, float outerpointRadius, float innerpointRadius, float strokeWidthMultiplier, int indicatorAlpha) {

        float strokeWidth = innerpointRadius * strokeWidthMultiplier;

        float textSizeMultiplier = 0.7F;
//        if (strokeWidthMultiplier > 0 && strokeWidthMultiplier < 7)
//            textSizeMultiplier = 0.4F;
//        if (strokeWidthMultiplier > 6 && textSizeMultiplier < 11)
//            textSizeMultiplier = 0.35F;
//        if (strokeWidthMultiplier > 10 && textSizeMultiplier < 15)
//            textSizeMultiplier = 0.3F;

        bigPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bigPaint.setColor(outerPointColor);

        singlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        singlePaint.setColor(innerPointColor);

        singlePaintTransparent = new Paint();
        singlePaintTransparent.setAntiAlias(true);
        singlePaintTransparent.setStyle(Paint.Style.STROKE);
        singlePaintTransparent.setStrokeWidth(strokeWidth);
        if (alreadySetInnerPointColor != -1)
            singlePaintTransparent.setColor(alreadySetInnerPointColor);
        else
            singlePaintTransparent.setColor(innerPointColor);
        singlePaintTransparent.setAlpha(0);

        //分数的字体样式
       // float textSize = (float) (outerpointRadius * textSizeMultiplier);
        float textSize = (float) 0;
        textPaint = new Paint();
        textPaint.setColor(innerPointColor);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTypeface(Typeface.MONOSPACE);
        textPaint.setTextSize(textSize);

        float customTextSize = (float) (outerpointRadius * textSizeMultiplier * 0.6);
        customTextPaint = new Paint();
        customTextPaint.setColor(innerPointColor);
        customTextPaint.setTextAlign(Paint.Align.CENTER);
        customTextPaint.setTypeface(Typeface.MONOSPACE);
        customTextPaint.setTextSize(customTextSize);

    }

    public void setSingleColor(int innerPointColor) {
        if (singlePaint != null)
            singlePaint.setColor(innerPointColor);
        else
            alreadySetInnerPointColor = innerPointColor;
    }

    public Bitmap getRoundedBitmap(Bitmap bitmap, ClipShape clipShape) {
        int zeroIntValue = 0;
        Bitmap resultBitmap;
        int originalWidth = bitmap.getWidth();
        int originalHeight = bitmap.getHeight();

        int posX = originalWidth / 2;
        int posY = originalHeight / 2;
        float r;

        if (originalWidth > originalHeight) {
            r = originalHeight / 2;
        } else {
            r = originalWidth / 2;
        }

        resultBitmap = Bitmap.createBitmap(originalWidth, originalHeight, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(resultBitmap);
        final Paint paint = new Paint();
        final Rect rect = new Rect(zeroIntValue, zeroIntValue, originalWidth, originalHeight);

        paint.setAntiAlias(true);
        canvas.drawARGB(zeroIntValue, zeroIntValue, zeroIntValue, zeroIntValue);

        switch (clipShape) {
            case ROUNDED_RECTANGLE:
                RectF rectF = new RectF(rect);
                float cornerRadius = 32.0f;
                canvas.drawRoundRect(rectF, cornerRadius, cornerRadius, paint);
                break;
            case CIRCLE:
                canvas.drawCircle(posX, posY, r, paint);
                break;
        }
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return resultBitmap;
    }
}
