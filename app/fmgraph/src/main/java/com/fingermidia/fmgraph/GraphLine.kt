package com.fingermidia.fmgraph

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.widget.ImageView
import com.fingermidia.fmgraph.R


class GraphLine : androidx.appcompat.widget.AppCompatImageView {
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

    var v = ArrayList<EntryPoint>()
    var clickX = 0.0
    var clickY = 0.0
    var colorGraphLine = Color.GRAY
    var colorLine = Color.BLUE
    var backgroundGraphColor = Color.WHITE

    fun changeData(
        v: ArrayList<EntryPoint>,
        clickX: Double,
        clickY: Double
    ) {
        this.v = v
        this.clickX = clickX
        this.clickY = clickY
        createGraph()
    }

    private fun createGraph() {

        if (this.width > 0) {

            var paddingHorizontal = 70f
            var paddingVertical = 50f
            var paddingLeft = 100f

            var max = v[0].value.toInt()
            v.forEach {
                if (it.value > max) {
                    max = it.value.toInt()
                }
            }

            var points = ArrayList<Point>()
            var width = this.width - (paddingHorizontal * 2) - paddingLeft
            var height = this.height - (paddingVertical * 2)
            var pointDistance = (width / (v.size - 1))

            val bitmap = Bitmap.createBitmap(
                this.width,
                this.height,
                Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(bitmap)
            canvas.drawColor(backgroundGraphColor)

            var interval = height / 3
            var vy = 1f

            var valueY = max
            var valueInterval = max / 3

            for (i in 1..4) {
                createLine(
                    canvas,
                    paddingHorizontal + paddingLeft,
                    vy + paddingVertical,
                    width + paddingHorizontal + paddingLeft,
                    vy + paddingVertical,
                    colorLine,
                    1f
                )

                drawLegend(
                    canvas,
                    paddingHorizontal,
                    vy + paddingVertical + 10,
                    valueY.toString(),
                    colorGraphLine
                )

                vy += interval
                valueY -= valueInterval
            }

            var vx = paddingHorizontal + paddingLeft
            var initY = calcPosition(height.toDouble(), max.toDouble(), 0.0).toFloat()
            var endY = calcPosition(height.toDouble(), max.toDouble(), max.toDouble()).toFloat()

            createLine(
                canvas,
                vx,
                initY + paddingVertical,
                vx,
                endY + paddingVertical,
                colorLine,
                1f
            )


            var posX = paddingHorizontal + paddingLeft

            for (i in 0 until v.size) {
                var y = calcPosition(height.toDouble(), max.toDouble(), v[i].value)
                points.add(Point(posX.toDouble(), y + paddingVertical, v[i].value, v[i].text))
                posX += pointDistance
            }

            for (i in 1 until points.size) {
                var p1 = points[i - 1]
                var p2 = points[i]

                createLine(
                    canvas,
                    p1.x.toFloat(),
                    p1.y.toFloat(),
                    p2.x.toFloat(),
                    p2.y.toFloat(),
                    colorGraphLine,
                    2.5f
                )

                drawCircle(
                    canvas,
                    p1.x.toFloat(),
                    p1.y.toFloat(),
                    colorGraphLine
                )

                if (showText(
                        p1.x.toFloat(),
                        p1.y.toFloat(),
                        clickX.toFloat(),
                        clickY.toFloat()
                    )
                ) {
                    drawText(
                        canvas,
                        p1.x.toFloat(),
                        p1.y.toFloat(),
                        String.format("R$ %.2f", p1.value),
                        colorGraphLine
                    )
                }

                drawLegend(
                    canvas,
                    p1.x.toFloat(),
                    initY + paddingVertical + 45,
                    p1.text,
                    colorGraphLine
                )

            }

            drawCircle(
                canvas,
                points[points.size - 1].x.toFloat(),
                points[points.size - 1].y.toFloat(),
                colorGraphLine
            )

            if (showText(
                    points[points.size - 1].x.toFloat(),
                    points[points.size - 1].y.toFloat(),
                    clickX.toFloat(),
                    clickY.toFloat()
                )
            ) {
                drawText(
                    canvas,
                    points[points.size - 1].x.toFloat(),
                    points[points.size - 1].y.toFloat(),
                    String.format("R$ %.2f", points[points.size - 1].value),
                    colorGraphLine
                )
            }

            drawLegend(
                canvas,
                points[points.size - 1].x.toFloat(),
                initY + paddingVertical + 45,
                points[points.size - 1].text,
                colorGraphLine
            )

            this.setImageBitmap(bitmap)

        }
    }

    fun showText(x: Float, y: Float, clickX: Float, clickY: Float): Boolean {
        var range = 80
        var clickXMin = clickX - range
        var clickXMax = clickX + range
        var clickYMin = clickY - range
        var clickYMax = clickY + range

        var ret = x >= clickXMin && x <= clickXMax && y >= clickYMin && y <= clickYMax

        return ret
    }

    fun drawText(canvas: Canvas, x: Float, y: Float, text: String, color: Int) {
        val paint = Paint()
        paint.color = color
        paint.textSize = 30f;

        val bounds = Rect()
        paint.getTextBounds(text, 0, text.length, bounds);

        canvas.drawText(text, x - (bounds.width() / 2), y - 25f, paint);
    }

    fun drawLegend(canvas: Canvas, x: Float, y: Float, text: String, color: Int) {
        val paint = Paint()
        paint.color = color
        paint.textSize = 30f;
        canvas.drawText(text, x - 15, y, paint);
    }

    fun drawCircle(canvas: Canvas, x: Float, y: Float, color: Int) {
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

    fun calcPosition(h: Double, max: Double, v: Double): Double {
        var ret = h - (v * h / max)
        if (ret > h) {
            ret = h;
        }
        return ret;
    }

    fun createLine(
        canvas: Canvas,
        x1: Float,
        y1: Float,
        x2: Float,
        y2: Float,
        color: Int,
        line: Float
    ) {
        val paint = Paint()
        paint.color = color
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = line
        paint.isAntiAlias = true
        canvas.drawLine(
            x1,
            y1,
            x2,
            y2,
            paint
        )

    }
}