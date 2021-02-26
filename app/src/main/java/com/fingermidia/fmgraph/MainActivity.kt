package com.fingermidia.fmgraph

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), View.OnTouchListener {
    var values = ArrayList<EntryPoint>()
    lateinit var chart: GraphLine

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        values.add(EntryPoint(0.0, "13"))
        values.add(EntryPoint(23.0, "14"))
        values.add(EntryPoint(50.0, "15"))
        values.add(EntryPoint(125.0, "16"))
        values.add(EntryPoint(12.0, "17"))
        values.add(EntryPoint(34.0, "18"))
        values.add(EntryPoint(190.0, "19"))
        values.add(EntryPoint(90.0, "20"))

        chart = findViewById(R.id.chart)

        chart.changeData(values, 0.0, 0.0)
        chart.setOnTouchListener(this)

    }

    override fun onTouch(p: View?, p1: MotionEvent?): Boolean {
//        p1?.let {
//            GraphLine.createGraph(p as ImageView, values, it.x.toDouble(), it.y.toDouble(), this)
//            return true
//        }

        return false
    }
}