自定义View绘制 （到绘制阶段都是像素px）
    GraphActivity
                1.CircularProgressBarView  -- 文字进度条
                2.PieChartView -- 饼图
                3.AvatarView -- 各种形状的头像
                4.DashBoardView -- 刻度仪
                5.UnderlineView -- 下划线
=================================================================================
	基本要素：onDraw(Canvas)、Canvas、Paint、坐标系、尺寸单位

	dp2px:
		TypedValue.applyDimension(TypeValue.COMPLEX_UNIT_DIP,150,dp值，Resources.getSystem().getDisplayMetrics());


	onSizeChanged(): 在layout之后实际尺寸改变了会调用一次，尺寸不变是不会调用的 
					 这样每次尺寸变了，path会重置；相比measure不会过多被调用,性能节省
		
		@Override
		protected void onSizeChanged(int w,int h,int oldw,int oldh){
			super.onSizeChanged(w,h,oldw,oldh);

			path.reset();
			path.addxx()
		}	

    
	//绘制弧形 
	canvas.drawArc(矩形空间Rect,起始角度，扫过的角度);

	Path.Direction : 绘制方向，与图像的交叉有关，
         从一个绘制的图形中取一个点作射线 （对相交的区域有不同的计算方式，使得有空心和实心的区分）

         //填充类型策略：EVEN_ODD（偶数)及其反向、WINDING(环绕走廊盘旋)及其反向
         //跟顺时针还是逆时针有关，使得填充结果不同
         paint.setFillType(Path.FillType.xx)


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

    //X轴向Y轴（向下）靠近就是正向
	Path.Direction.CW：  顺时针方向绘制 （clockwise）
	Path.Direction.CCW： 逆时针方向绘制 （counterclockwise）
		
	//测量path	
	PathMeasure pm = new PathMeasure(path,是否封口)；
	pm.getLength(); //测量path的长度，若绘制的圆则为周长 	


	//文字排印
	public static class FontMetrics {
        /**
         * 能给予文字的顶部最高的位置.
         */
        public float   top;
        /**
         * 推荐基于基线的顶部
         */
        public float   ascent;
        /**
         * 推荐基于基线的底部
         */
        public float   descent;
        /**
         * 能给予文字的底部最低的位置.
         */
        public float   bottom;
        /**
         * 基线（baseline）
         */
        public float   leading;
    }

=================================================================================    
    FrameAnimActivity -- 帧动画
    PropertyAnimActivity -- 属性动画
    TweenAnimActivity -- 补间动画
    
=================================================================================        
    BitmapTestActivity -- 屏幕分辨率相关