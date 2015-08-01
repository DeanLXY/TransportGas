/*
 * Copyright (C) 2008 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.zxing.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import com.android.util.DensityUtils;
import com.example.transportgas.R;
import com.google.zxing.ResultPoint;
import com.google.zxing.camera.CameraManager;

import java.util.Collection;
import java.util.HashSet;

/**
 * This view is overlaid on top of the camera preview. It adds the viewfinder
 * rectangle and partial transparency outside it, as well as the laser scanner
 * animation and result points.
 *
 * @author dswitkin@google.com (Daniel Switkin)
 */
public final class ViewfinderView extends View {

    private static final int[] SCANNER_ALPHA = {0, 64, 128, 192, 255, 192,
            128, 64};
    private static final long ANIMATION_DELAY = 10L;
    private static final int OPAQUE = 0xFF;

    private final Paint paint;
    private final Paint textPaint;
    private Bitmap resultBitmap;
    private int textHeight;
    private final int maskColor;
    private final int resultColor;
    private final int frameColor;
    private final int laserColor;
    private final int resultPointColor;
    private int scannerAlpha;
    private float scannerOffset;
    private Drawable laserLine;
    private Collection<ResultPoint> possibleResultPoints;
    private Collection<ResultPoint> lastPossibleResultPoints;

    // This constructor is used when the class is built from an XML resource.
    public ViewfinderView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // Initialize these once for performance rather than calling them every
        // time in onDraw().
        paint = new Paint();
        textPaint = new Paint();
        textPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(DensityUtils.sp2px(getContext(),18.0f));
        textPaint.setUnderlineText(false);
        Paint.FontMetrics fm = textPaint.getFontMetrics();
        textHeight = (int) Math.ceil(fm.descent - fm.ascent);
        Resources resources = getResources();
        textPaint.setColor(resources.getColor(R.color.white));
        maskColor = resources.getColor(R.color.viewfinder_mask);
        resultColor = resources.getColor(R.color.result_view);
        frameColor = resources.getColor(R.color.appTitleBackground);
        laserColor = resources.getColor(R.color.transparent);
        resultPointColor = resources.getColor(R.color.transparent);
        laserLine = resources.getDrawable(R.drawable.barcode_laser_line);
        scannerAlpha = 0;
        possibleResultPoints = new HashSet<ResultPoint>(5);
    }

    @Override
    public void onDraw(Canvas canvas) {
        Rect frame = CameraManager.get().getFramingRect();
        if (frame == null) {
            return;
        }
        int width = canvas.getWidth();
        int height = canvas.getHeight();

        // Draw the exterior (i.e. outside the framing rect) darkened
        paint.setColor(maskColor);
        canvas.drawRect(0, 0, width, frame.top, paint);
        canvas.drawRect(0, frame.top, frame.left, frame.bottom + 1, paint);
        canvas.drawRect(frame.right + 1, frame.top, width, frame.bottom + 1,
                paint);
        canvas.drawRect(0, frame.bottom + 1, width, height, paint);

        if (resultBitmap != null) {
            // Draw the opaque result bitmap over the scanning rectangle
            paint.setAlpha(OPAQUE);
            canvas.drawBitmap(resultBitmap, frame.left, frame.top, paint);
        } else {
            int linewidht = 10;
            paint.setColor(frameColor);

            canvas.drawRect(frame.left - 15, frame.top - 15,
                    (linewidht + frame.left) - 15, (50 + frame.top) - 15, paint);
            canvas.drawRect(frame.left - 15, frame.top - 15,
                    (50 + frame.left) - 15, (linewidht + frame.top) - 15, paint);
            canvas.drawRect(15 + ((0 - linewidht) + frame.right),
                    frame.top - 15, 15 + (1 + frame.right),
                    (50 + frame.top) - 15, paint);
            canvas.drawRect(15 + (-50 + frame.right), frame.top - 15, 15
                    + frame.right, -15 + (linewidht + frame.top), paint);
            canvas.drawRect(-15 + frame.left, 15 + (-49 + frame.bottom),
                    -15 + (linewidht + frame.left), 15 + (1 + frame.bottom),
                    paint);
            canvas.drawRect(-15 + frame.left, 15
                            + ((0 - linewidht) + frame.bottom), -15 + (50 + frame.left),
                    15 + (1 + frame.bottom), paint);
            canvas.drawRect(15 + ((0 - linewidht) + frame.right), 15
                    + (-49 + frame.bottom), 15 + (1 + frame.right), 15
                    + (1 + frame.bottom), paint);
            canvas.drawRect(15 + (-50 + frame.right), 15
                    + ((0 - linewidht) + frame.bottom), 15 + frame.right, 15
                    + (linewidht - (linewidht - 1) + frame.bottom), paint);


            paint.setColor(frameColor);
            paint.setAlpha(SCANNER_ALPHA[scannerAlpha]);
            scannerAlpha = (scannerAlpha + 1) % SCANNER_ALPHA.length;
            if (scannerOffset <=frame.height()){
                scannerOffset +=2;
            }else {
                scannerOffset = 0;
            }

           float vOffset= frame.top + scannerOffset;
            laserLine.setBounds(frame.left + 2,frame.top,frame.right - 1, (int) vOffset+2);
            laserLine.draw(canvas);
            Collection<ResultPoint> currentPossible = possibleResultPoints;
            Collection<ResultPoint> currentLast = lastPossibleResultPoints;
            if (currentPossible.isEmpty()) {
                lastPossibleResultPoints = null;
            } else {
                possibleResultPoints = new HashSet<ResultPoint>(5);
                lastPossibleResultPoints = currentPossible;
                paint.setAlpha(OPAQUE);
                paint.setColor(resultPointColor);
                for (ResultPoint point : currentPossible) {
                    canvas.drawCircle(frame.left + point.getX(), frame.top
                            + point.getY(), 6.0f, paint);
                }
            }
            if (currentLast != null) {
                paint.setAlpha(OPAQUE / 2);
                paint.setColor(resultPointColor);
                for (ResultPoint point : currentLast) {
                    canvas.drawCircle(frame.left + point.getX(), frame.top
                            + point.getY(), 3.0f, paint);
                }
            }
            drawText(canvas, frame);
            // Request another update at the animation interval, but only
            // repaint the laser line,
            // not the entire viewfinder mask.
            postInvalidateDelayed(ANIMATION_DELAY, frame.left, frame.top,
                    frame.right, frame.bottom);
        }
    }

    private void drawText(Canvas canvas, Rect frame) {
        String textTips = "将二维码/条形码放置框内，即开始扫描";
        float textWidth = textPaint.measureText(textTips, 0, textTips.length());
        float textStart = frame.width() / 2 +frame.left- textWidth / 2;
        canvas.drawText(textTips, textStart, frame.bottom + textHeight * 2, textPaint);
    }

    public void drawViewfinder() {
        resultBitmap = null;
        invalidate();
    }

    public void addPossibleResultPoint(ResultPoint point) {
        possibleResultPoints.add(point);
    }

}
