package com.zlyandroid.apitoolkotlin.widget

import android.content.Context
import android.content.res.TypedArray
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.annotation.Nullable
import com.zlyandroid.apitoolkotlin.R

/**
 * @author zhangliyang
 * @date 2017/12/12
 * GitHub: https://github.com/ZLYang110
 * desc: 顶部圆弧背景
 */
class ArcView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private var mWidth = 0
    private var mHeight = 0

    private var mArcHeight //圆弧的高度
            = 0
    private var mBgColor //背景颜色
            = 0
    private var lgColor //变化的最终颜色
            = 0
    private lateinit var mPaint : Paint
    private var linearGradient: LinearGradient? = null
    private val rect: Rect = Rect(0, 0, 0, 0) //普通的矩形

    private val path: Path = Path() //用来绘制曲面
    init {
        val typedArray: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.ArcView)
        mArcHeight = typedArray.getDimensionPixelSize(R.styleable.ArcView_arcHeight, 0)
        mBgColor = typedArray.getColor(R.styleable.ArcView_bgColor, Color.parseColor("#303F9F"))
        lgColor = typedArray.getColor(R.styleable.ArcView_lgColor, mBgColor)
        mPaint = Paint()
        mPaint.setAntiAlias(true)
        typedArray.recycle()
    }



    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        //        Log.d("----","onSizeChanged");
        linearGradient = LinearGradient(
            0f, 0f, measuredWidth.toFloat(), 0f,
            mBgColor, lgColor, Shader.TileMode.CLAMP
        )
        mPaint.setShader(linearGradient)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //设置成填充
        mPaint.setStyle(Paint.Style.FILL)
        mPaint.setColor(mBgColor)

        //绘制矩形
        rect.set(0, 0, mWidth, mHeight - mArcHeight)
        canvas.drawRect(rect, mPaint)

        //绘制路径
        path.moveTo(0f, (mHeight - mArcHeight).toFloat())
        path.quadTo((mWidth shr 1).toFloat(), mHeight.toFloat(), mWidth.toFloat(),
            (mHeight - mArcHeight).toFloat()
        )
        canvas.drawPath(path, mPaint)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        if (widthMode == MeasureSpec.EXACTLY) {
            mWidth = widthSize
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            mHeight = heightSize
        }
        setMeasuredDimension(mWidth, mHeight)
    }
}