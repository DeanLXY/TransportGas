package com.android.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.TypedValue;

class Bar {

	// Member Variables ////////////////////////////////////////////////////////

	private final Paint mPaint;
	private final Paint mTextPaint;

	// Left-coordinate of the horizontal bar.
	private final float mLeftX;
	private final float mRightX;
	private final float mY;

	private int mNumSegments;
	private float mTickDistance;
	private final float mTickHeight;
	private final float mTickStartY;
	private final float mTickEndY;

	// Constructor /////////////////////////////////////////////////////////////

	Bar(Context ctx, float x, float y, float length, int tickCount, float tickHeightDP, float BarWeight, int BarColor) {

		mLeftX = x;
		mRightX = x + length;
		mY = y;

		mNumSegments = tickCount - 1;
		mTickDistance = length / mNumSegments;
		mTickHeight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, tickHeightDP, ctx.getResources()
				.getDisplayMetrics());
		mTickStartY = mY - mTickHeight / 2f;
		mTickEndY = mY + mTickHeight / 2f;

		// Initialize the paint.
		mPaint = new Paint();
		mPaint.setColor(BarColor);
		mPaint.setStrokeWidth(BarWeight);
		mPaint.setAntiAlias(true);
		mTextPaint = new Paint();
		mTextPaint.setTextSize(36);
		mTextPaint.setColor(Color.RED);
		mTextPaint.setAntiAlias(true);
	}

	// Package-Private Methods /////////////////////////////////////////////////

	/**
	 * Draws the bar on the given Canvas.
	 * 
	 * @param canvas
	 *            Canvas to draw on; should be the Canvas passed into {#link
	 *            View#onDraw()}
	 */
	void draw(Canvas canvas) {

		canvas.drawLine(mLeftX, mY, mRightX, mY, mPaint);

		drawTicks(canvas);
	}

	/**
	 * Get the x-coordinate of the left edge of the bar.
	 * 
	 * @return x-coordinate of the left edge of the bar
	 */
	float getLeftX() {
		return mLeftX;
	}

	/**
	 * Get the x-coordinate of the right edge of the bar.
	 * 
	 * @return x-coordinate of the right edge of the bar
	 */
	float getRightX() {
		return mRightX;
	}

	/**
	 * Gets the x-coordinate of the nearest tick to the given x-coordinate.
	 * 
	 * @param x
	 *            the x-coordinate to find the nearest tick for
	 * @return the x-coordinate of the nearest tick
	 */
	float getNearestTickCoordinate(Thumb thumb) {

		final int nearestTickIndex = getNearestTickIndex(thumb);

		final float nearestTickCoordinate = mLeftX + (nearestTickIndex * mTickDistance);

		return nearestTickCoordinate;
	}

	float getNearestTickCoordinate(int thumbX) {

		final int nearestTickIndex = thumbX;

		final float nearestTickCoordinate = mLeftX + (nearestTickIndex * mTickDistance);

		return nearestTickCoordinate;
	}

	/**
	 * Gets the zero-based index of the nearest tick to the given thumb.
	 * 
	 * @param thumb
	 *            the Thumb to find the nearest tick for
	 * @return the zero-based index of the nearest tick
	 */
	int getNearestTickIndex(Thumb thumb) {

		final int nearestTickIndex = (int) ((thumb.getX() - mLeftX + mTickDistance / 2f) / mTickDistance);

		return nearestTickIndex;
	}

	/**
	 * Set the number of ticks that will appear in the RangeBar.
	 * 
	 * @param tickCount
	 *            the number of ticks
	 */
	void setTickCount(int tickCount) {

		final float barLength = mRightX - mLeftX;

		mNumSegments = tickCount - 1;
		mTickDistance = barLength / mNumSegments;
	}

	// Private Methods /////////////////////////////////////////////////////////

	/**
	 * Draws the tick marks on the bar.
	 * 
	 * @param canvas
	 *            Canvas to draw on; should be the Canvas passed into {#link
	 *            View#onDraw()}
	 */
	private void drawTicks(Canvas canvas) {

		int temp = 0;
		// Loop through and draw each tick (except final tick).
		for (int i = 0; i < mNumSegments; i++) {
			final float x = i * mTickDistance + mLeftX;
			// canvas.drawLine(x, mTickStartY, x, mTickEndY, mPaint);
			String text = "" + i;
			temp = i;
			float textWidth = mTextPaint.measureText(text, 0, text.length());
			canvas.drawText(text, x - textWidth / 2, mTickStartY, mTextPaint);
		}
		// Draw final tick. We draw the final tick outside the loop to avoid any
		// rounding discrepancies.
		// canvas.drawLine(mRightX, mTickStartY, mRightX, mTickEndY, mPaint);
		String text = "" + (temp + 1);
		float textWidth = mTextPaint.measureText(text, 0, text.length());
		canvas.drawText("" + text, mRightX - textWidth / 2, mTickStartY, mTextPaint);
	}
}