package com.korchid.msg;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

/**
 * Created by mac0314 on 2016-12-04.
 */

// http://stackoverflow.com/questions/16208365/how-to-create-a-circular-imageview-in-android/16208548#16208548
public class RoundedImageView extends ImageView {
    private static final String TAG = "RoundedImageView";
    private int resizeRadius = 0;

    public RoundedImageView(Context context, int resizeRadius) {
        super(context);

        this.resizeRadius = resizeRadius;
    }

    public RoundedImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RoundedImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d(TAG, "onDraw");

        Drawable drawable = getDrawable();

        if (drawable == null) {
            return;
        }

        if (getWidth() == 0 || getHeight() == 0) {
            return;
        }
        Bitmap b = ((BitmapDrawable) drawable).getBitmap();
        Bitmap bitmap = b.copy(Bitmap.Config.RGB_565, true);


        /*
        // Original code
        int w = getWidth();
        @SuppressWarnings("unused")
        int h = getHeight();

        Bitmap roundBitmap = getCroppedBitmap(bitmap, w);
        */

        // http://blog.daum.net/kwh4865/13153849
        // resize
        Bitmap resize = Bitmap.createScaledBitmap(bitmap, resizeRadius, resizeRadius, true);

        int w = resize.getWidth();
        @SuppressWarnings("unused")
        int h = resize.getHeight();

        Bitmap roundBitmap = getCroppedBitmap(resize, w);


        canvas.drawBitmap(roundBitmap, 0, 0, null);

    }

    public static Bitmap getCroppedBitmap(Bitmap bmp, int radius) {
        Log.d(TAG, "getCroppedBitmap");
        Bitmap sbmp;

        Log.d(TAG, "radius : " + radius);

        if (bmp.getWidth() != radius || bmp.getHeight() != radius) {
            float smallest = Math.min(bmp.getWidth(), bmp.getHeight());
            float factor = smallest / radius;
            sbmp = Bitmap.createScaledBitmap(bmp,
                    (int) (bmp.getWidth() / factor),
                    (int) (bmp.getHeight() / factor), false);
        } else {
            sbmp = bmp;
        }

        Bitmap output = Bitmap.createBitmap(radius, radius, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final String color = "#BAB399";
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, radius, radius);

        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(Color.parseColor(color));
        canvas.drawCircle(radius / 2 + 0.7f, radius / 2 + 0.7f,
                radius / 2 + 0.1f, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(sbmp, rect, rect, paint);

        return output;
    }

}
