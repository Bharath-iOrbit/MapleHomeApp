package com.assignment.maplehomeapp.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;

import com.assignment.maplehomeapp.R;

public class Circle extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle);
    }
  
    public class CircleView extends View
    {

        public CircleView(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            Paint p = new Paint();
            p.setColor(Color.RED);
            DashPathEffect dashPath = new DashPathEffect(new float[]{5,5}, (float)1.0);

            p.setPathEffect(dashPath);
            p.setStyle(Paint.Style.STROKE);


            for (int i = 0; i < 7; i ++) {
                canvas.drawCircle(100, 100, 50+(i*10), p);
            }


            invalidate();
        }
    }

}
