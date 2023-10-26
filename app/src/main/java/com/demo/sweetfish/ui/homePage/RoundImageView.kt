package com.demo.sweetfish.ui.homePage

import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.example.sweetfish.R

class RoundImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = -1,
) :
    AppCompatImageView(context, attrs, defStyleAttr) {
    private var mRect: RectF? = null
    private var mPath: Path? = null
    private var mRadius = 0f

    init {
        getAttributes(context, attrs)
        initView(context)
    }

    /**
     * 获取属性
     */
    private fun getAttributes(context: Context, attrs: AttributeSet?) {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.RoundImageView)
        mRadius = ta.getDimension(R.styleable.RoundImageView_radius, -1f)
        ta.recycle()
    }

    /**
     * 初始化
     */
    private fun initView(context: Context) {
        mRect = RectF()
        mPath = Path()
        setLayerType(LAYER_TYPE_SOFTWARE, null) // 禁用硬件加速
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (mRadius < 0) {
            clipCircle(w, h)
        } else {
            clipRoundRect(w, h)
        }
    }

    /**
     * 圆角
     */
    private fun clipRoundRect(width: Int, height: Int) {
        mRect?.left = 0f
        mRect?.top = 0f
        mRect?.right = width.toFloat()
        mRect?.bottom = height.toFloat()
        mPath?.addRoundRect(mRect!!, mRadius, mRadius, Path.Direction.CW)
    }

    /**
     * 圆形
     */
    private fun clipCircle(width: Int, height: Int) {
        val radius = Math.min(width, height) / 2
        mPath?.addCircle((width / 2).toFloat(), (height / 2).toFloat(), radius.toFloat(), Path.Direction.CW)
    }

    override fun onDraw(canvas: Canvas) {
        canvas.clipPath(mPath!!)
        super.onDraw(canvas)
    }
}