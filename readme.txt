参见:https://mp.weixin.qq.com/s/10QyDARvBI4iHnN9JrqOzg
自定义绘制知识的四个级别
    1.Canvas 的 drawXXX() 系列方法及 Paint 最常见的使用
        Canvas 类下的所有 draw- 打头的方法
        Paint 类的几个最常用的方法。具体是：
            Paint.setStyle(Style style) 设置绘制模式
            Paint.setColor(int color) 设置颜色
            Paint.setStrokeWidth(float width) 设置线条宽度
            Paint.setTextSize(float textSize) 设置文字大小
            Paint.setAntiAlias(boolean aa) 设置抗锯齿开关

        Path.setFillType(Path.FillType ft) 设置填充方式有四种:

         1.EVEN_ODD即 even-odd rule （奇偶原则）：
             从平面中的点向任意方向射出一条射线，这条射线和图形相交的次数（相交才算，相切不算哦）
             如果是奇数，则这个点被认为在图形内部，是要被涂色的区域；
             如果是偶数，则这个点被认为在图形外部，是不被涂色的区域。
         2.INVERSE_EVEN_ODD   反转EVEN_ODD效果

         3.WINDING即 non-zero winding rule （非零环绕数原则）：
             图形中的所有线条都是有绘制方向的
             从平面中的点向任意方向射出一条射线，这条射线和图形相交的次数（相交才算，相切不算哦）
             每遇到顺时针的交点,结果+1,逆时针的交点,结果-1,最终把所有的交点都算上;
             如果不是0，则这个点被认为在图形内部，是要被涂色的区域；
             如果是0，则这个点被认为在图形外部，是不被涂色的区域。

         4.INVERSE_WINDING    反转WINDING效果

    2.Paint 的完全攻略
        Canvas 的方法像素颜色的设置方式
            drawColor/RGB/ARGB()  直接作为参数传入
            drawBitmap()          与bitmap参数的像素颜色相同
            图形和文字 (drawCircle() / drawPath() / drawText() ...)
                    在paint参数中设置

    3.Canvas 对绘制的辅助——范围裁切和几何变换
    4.使用不同的绘制方法来控制绘制顺序

