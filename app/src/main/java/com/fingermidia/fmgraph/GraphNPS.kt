package com.fingermidia.fmgraph

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.graphics.RectF
import androidx.core.content.res.ResourcesCompat


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
    var colorGraphLine = Color.parseColor("#D9E1EB")
    var colorGreen = Color.parseColor("#66c569")
    var colorText = Color.parseColor("#708baa")
    var colorWhite = Color.WHITE

    fun changeData(value: Int) {
        this.value = value
        createGraph()
    }

    private fun createGraph() {

        if (this.width > 0) {

            val bitmap = Bitmap.createBitmap(
                this.width,
                this.height,
                Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(bitmap)
            canvas.drawColor(colorWhite)

            this.value = 100

            var p = (270f / 100) * value

            drawArc(canvas, 225f, colorGraphLine, this.width.toFloat(), 30f)
            drawArc(canvas, 12f, 33f, colorGraphLine, this.width.toFloat(), 30f)
//            drawArc(canvas, 270f, colorGraphLine, this.width.toFloat(), 30f)

            drawArc(canvas, p, colorGreen, this.width.toFloat(), 60f)

//            var vv = 0

//            Thread {
//                while (vv < p) {
////                    invalidate()
//                    Thread.sleep(50)
//                    drawArc(canvas, vv.toFloat(), colorGreen, this.width.toFloat(), 60f)
//                }
//                vv++
//            }.start()

//            drawCircle(canvas, 161f, 550f, 30f, colorGreen)
//            drawCircle(canvas, 161f, 550f, 15f, colorWhite)
            drawCircle(canvas, 140f, 550f, 15f, colorWhite) // posição -100
            drawCircle(canvas, 358f, 99f, 15f, colorWhite) // posição 0
            drawCircle(canvas, 608f, 265f, 15f, colorWhite) // posição 50
            drawCircle(canvas, 570f, 545f, 15f, colorWhite) // posição 100

            drawText(canvas, 355f, 70f, "0", colorText)
            drawText(canvas, 50f, 600f, "-100", colorText)
            drawText(canvas, 680f, 300f, "50", colorText)
            drawText(canvas, 690f, 450f, "75", colorText)
            drawText(canvas, 650f, 600f, "100", colorText)

            this.setImageBitmap(bitmap)

        }
    }

    private fun drawArc(
        canvas: Canvas,
        sweep: Float,
        color: Int,
        width: Float,
        strokeWidth: Float
    ) {
        drawArc(canvas, 135f, sweep, color, width, strokeWidth)
    }

    private fun drawArc(
        canvas: Canvas,
        start: Float,
        sweep: Float,
        color: Int,
        width: Float,
        strokeWidth: Float
    ) {
        val paint = Paint()
        paint.style = Paint.Style.STROKE
        paint.color = color
        paint.strokeWidth = strokeWidth

        var diff = ((width - 630f) / 2) + 30f

        val rectF = RectF(diff, diff + 20, 630f, 650f)

        canvas.drawArc(rectF, start, sweep, false, paint)
    }

    private fun drawText(canvas: Canvas, x: Float, y: Float, text: String, color: Int) {
        val customTypeface = ResourcesCompat.getFont(context, R.font.poppins_semibold)

        val paint = Paint()
        paint.color = color
        paint.textSize = 35f;

        val bounds = Rect()
        paint.getTextBounds(text, 0, text.length, bounds);
        paint.typeface = customTypeface

        canvas.drawText(text, x - (bounds.width() / 2), y - 25f, paint);
    }

    private fun drawCircle(canvas: Canvas, x: Float, y: Float, radius: Float, color: Int) {
        val paint = Paint()
        paint.style = Paint.Style.FILL
        paint.color = color
        canvas.drawCircle(x, y, radius, paint)
    }

}