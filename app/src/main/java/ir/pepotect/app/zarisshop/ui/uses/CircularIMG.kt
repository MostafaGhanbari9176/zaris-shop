/*
Owner => MostafaGhanbari9176 :PowerFul:
 */

package ir.pepotect.app.zarisshop.ui.uses

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import ir.pepotect.app.zarisshop.R


class CircularIMG : AppCompatImageView {

    private var paint: Paint? = null
    private var maskPaint: Paint? = null

    private var topLeft = 0f
    private var topRight = 0f
    private var bottomLeft = 0f
    private var bottomRight = 0f
    private var rectCorner = 0f
    private var circular = false

    constructor(context: Context) : super(context) {
        init(context, null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(context, attrs, defStyle)
    }

    private fun init(context: Context, attrs: AttributeSet?, defStyle: Int) {
        setUpAttr(attrs)
        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        maskPaint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG)
        maskPaint!!.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)

        setWillNotDraw(false)
    }

    private fun setUpAttr(attrs: AttributeSet?) {
        val attrList = context.theme.obtainStyledAttributes(attrs, R.styleable.CircularIMG, 0, 0)
        topLeft = attrList.getDimension(R.styleable.CircularIMG_topLeft, topLeft)
        topRight = attrList.getDimension(R.styleable.CircularIMG_topRight, topRight)
        bottomLeft = attrList.getDimension(R.styleable.CircularIMG_bottomLeft, bottomLeft)
        bottomRight = attrList.getDimension(R.styleable.CircularIMG_bottomRight, bottomRight)
        rectCorner = attrList.getDimension(R.styleable.CircularIMG_rectCorner, rectCorner)
        circular = attrList.getBoolean(R.styleable.CircularIMG_circular, false)
        invalidate()
    }

    override fun draw(canvas: Canvas) {
        val offscreenBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val offscreenCanvas = Canvas(offscreenBitmap)

        super.draw(offscreenCanvas)

        val maskBitmap = createMask(width, height)

        offscreenCanvas.drawBitmap(maskBitmap, 0f, 0f, maskPaint)
        canvas.drawBitmap(offscreenBitmap, 0f, 0f, paint)
    }

    private fun createMask(width: Int, height: Int): Bitmap {
        val mask = Bitmap.createBitmap(width, height, Bitmap.Config.ALPHA_8)
        val canvas = Canvas(mask)

        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.color = Color.WHITE

        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint)

        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)

        if (circular)
            canvas.drawCircle(width / 2f, height / 2f, width / 2f, paint)
        else if (rectCorner > 0)
            canvas.drawRoundRect(RectF(0f, 0f, width.toFloat(), height.toFloat()), rectCorner, rectCorner, paint)
        else
            canvas.drawPath(customPath(), paint)

        return mask
    }

    private fun customPath(): Path {
        val p = Path()
        p.moveTo(0f, height / 2f)
        if (topLeft == 0f) {
            p.lineTo(0f, 0f)
            p.lineTo(width / 2f, 0f)
        } else {
            p.lineTo(0f, topLeft)
            p.cubicTo(0f, topLeft / 2, topLeft / 2, 0f, topLeft, 0f)
            p.lineTo(width / 2f, 0f)
        }
        if (topRight == 0f) {
            p.lineTo(width.toFloat(), 0f)
            p.lineTo(width.toFloat(), height / 2f)
        } else {
            p.lineTo(width.toFloat() - topRight, 0f)
            p.cubicTo(width.toFloat() - topRight / 2, 0f, width.toFloat(), topRight / 2, width.toFloat(), topRight)
            p.lineTo(width.toFloat(), height / 2f)
        }
        p.lineTo(width.toFloat(), height.toFloat())
        p.lineTo(0f, height.toFloat())
        p.close()
        return p
    }

}