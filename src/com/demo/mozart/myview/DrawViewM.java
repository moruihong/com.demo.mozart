
package com.demo.mozart.myview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

public class DrawViewM extends View {

    public DrawViewM(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public DrawViewM(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DrawViewM(Context context) {
        super(context);
        init();
    }

    private void init() {}

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /*
         * 方法 说明 drawRect 绘制矩形 drawCircle 绘制圆形 drawOval 绘制椭圆 drawPath 绘制任意多边形 drawLine 绘制直线 drawPoin 绘制点
         */
        // 创建画笔
        Paint p = new Paint();
        p.setColor(Color.RED);// 设置红色

        // canvas.drawText("画三角形：", 10, 200, p);
        // // 绘制这个三角形,你可以绘制任意多边形
        // Path path = new Path();
        // path.moveTo(80, 200);// 此点为多边形的起点
        // path.lineTo(120, 250);
        // path.lineTo(80, 250);
        // path.close(); // 使这些点构成封闭的多边形
        // canvas.drawPath(path, p);

        // 你可以绘制很多任意多边形，比如下面画六连形
        // p.reset();// 重置
        // p.setColor(Color.LTGRAY);
        // p.setStyle(Paint.Style.STROKE);// 设置空心
        /* 设置渐变色 这个正方形的颜色是改变的 */
        Shader mShader = new LinearGradient(0, 0, 100, 100, new int[] {
                Color.RED, Color.GREEN, Color.BLUE,
                Color.YELLOW, Color.LTGRAY
        }, null, Shader.TileMode.REPEAT); // 一个材质,打造出一个线性梯度沿著一条线。
        // p.setShader(mShader);
        p.setStyle(Paint.Style.FILL);// 设置填满
        Path path1 = new Path();
        path1.moveTo(0, 0);
        path1.lineTo(500, 500);
        path1.lineTo(500, 1000);
        path1.lineTo(0, 1500);
        path1.close();// 封闭
        canvas.drawPath(path1, p);
        /*
         * Path类封装复合(多轮廓几何图形的路径 由直线段*、二次曲线,和三次方曲线，也可画以油画。drawPath(路径、油漆),要么已填充的或抚摸 (基于油漆的风格),或者可以用于剪断或画画的文本在路径。
         */
    }

    // @Override
    // protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    // int width = MeasureSpec.getSize(widthMeasureSpec);
    // int height = MeasureSpec.getSize(heightMeasureSpec);
    //
    // // if (width > height) {
    // // super.onMeasure(heightMeasureSpec, widthMeasureSpec);
    // // } else {
    // // super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    // // }
    //
    // super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    // }
}
