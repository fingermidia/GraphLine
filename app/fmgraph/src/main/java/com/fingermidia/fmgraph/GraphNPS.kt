package com.fingermidia.fmgraph

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class GraphNPS : androidx.appcompat.widget.AppCompatImageView, View.OnTouchListener {
    constructor(context: Context) : super(context) {}
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {}
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        createGraph()
    }

    override fun onTouch(p: View?, p1: MotionEvent?): Boolean {
        p1?.let {
            return true
        }

        return false
    }

    var value: Int = 0
    var colorGraphLine = Color.parseColor("#708baa")
    var colorGreen = Color.parseColor("#66c569")
    var backgroundGraphColor = Color.WHITE

    fun changeData(value: Int) {
        this.value = value
        createGraph()
    }

    private fun createGraph() {

        if (this.width > 0) {

            var paddingHorizontal = 70f
            var paddingVertical = 50f
            var paddingLeft = 100f


//            this.setImageBitmap(bitmap)

        }
    }

    private fun showText(x: Float, y: Float, clickX: Float, clickY: Float): Boolean {
        var range = 80
        var clickXMin = clickX - range
        var clickXMax = clickX + range
        var clickYMin = clickY - range
        var clickYMax = clickY + range

        var ret = x >= clickXMin && x <= clickXMax && y >= clickYMin && y <= clickYMax

        return ret
    }

    private fun drawText(canvas: Canvas, x: Float, y: Float, text: String, color: Int) {
        val paint = Paint()
        paint.color = color
        paint.textSize = 30f;

        val bounds = Rect()
        paint.getTextBounds(text, 0, text.length, bounds);

        canvas.drawText(text, x - (bounds.width() / 2), y - 25f, paint);
    }

    private fun drawCircle(canvas: Canvas, x: Float, y: Float, color: Int) {
        val paint = Paint()
        paint.style = Paint.Style.STROKE
        paint.color = color
        paint.strokeWidth = 5f
        canvas.drawCircle(x, y, 10f, paint)
        val paint2 = Paint()
        paint2.style = Paint.Style.FILL
        paint2.color = backgroundGraphColor
        canvas.drawCircle(x, y, 5f, paint2)
    }

    private fun calcPosition(h: Double, max: Double, v: Double): Double {
        var ret = h - (v * h / max)
        if (ret > h) {
            ret = h;
        }
        return ret;
    }


}