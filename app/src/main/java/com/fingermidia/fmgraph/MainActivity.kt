package com.fingermidia.fmgraph

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    var values = ArrayList<EntryPoint>()
    lateinit var chart: GraphNPS

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        chart = findViewById(R.id.chart2)
        chart.changeData(50)

    }

}