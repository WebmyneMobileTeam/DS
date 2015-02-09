package com.wm.customviews;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.drawable.shapes.Shape;

public class ShapeCircle extends Shape{

    @Override
    public void draw(Canvas canvas, Paint paint) {
	// TODO Auto-generated method stub
	
	paint.setStrokeWidth(2);
	paint.setStyle(Style.FILL_AND_STROKE);
	paint.setColor(Color.parseColor("#ae0101"));
	
	canvas.drawCircle(10, 10, 5, paint);
	
	
    }
    
    

}
