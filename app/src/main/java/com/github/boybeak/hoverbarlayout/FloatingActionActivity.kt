package com.github.boybeak.hoverbarlayout

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.github.boybeak.design.FamPopupWindow
import kotlinx.android.synthetic.main.activity_floating_action.*

class FloatingActionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_floating_action)

        fab.setOnClickListener {
            val famPW = FamPopupWindow(this)
                .setDimView(content)
                .setAnchorView(fab)
                .inflate(R.menu.menu_demo)
                .setOnItemSelectedListener {item ->
                    when(item.itemId) {

                    }
                    true
                }
            famPW.show()

        }

    }
}
