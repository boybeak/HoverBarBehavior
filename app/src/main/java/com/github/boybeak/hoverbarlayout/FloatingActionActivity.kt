package com.github.boybeak.hoverbarlayout

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.github.boybeak.design.FamItem
import com.github.boybeak.design.FamPopupWindow
import kotlinx.android.synthetic.main.activity_floating_action.*

class FloatingActionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_floating_action)

        val d = resources.getDrawable(R.mipmap.ic_launcher);
        val items: List<FamItem> = listOf(
            FamItem(0, "Javier Mendonca", d),
            FamItem(1, "Vasily Kabunov", d),
            FamItem(2, "Faheem Ahmad Khan", d),
            FamItem(4, "Ankit Gupta", d)
        )


        fab.setOnClickListener {
            val famPW = FamPopupWindow(this)
                .setDimView(content)
                .setAnchorView(fab)
                .setFamItems(items)
                .setOnItemSelectedListener {item ->
                    when(item.id) {

                    }
                    true
                }
            famPW.show()
        }

    }
}
