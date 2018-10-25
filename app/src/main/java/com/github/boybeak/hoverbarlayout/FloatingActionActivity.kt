package com.github.boybeak.hoverbarlayout

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.ListPopupWindow
import android.support.v7.widget.PopupMenu
import android.view.Gravity
import android.view.View
import kotlinx.android.synthetic.main.activity_floating_action.*

class FloatingActionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_floating_action)

        fab.setOnClickListener {
            /*if (fam.visibility == View.VISIBLE) {
                fam.visibility = View.GONE
            } else {
                fam.visibility = View.VISIBLE
            }*/
            val d = resources.getDrawable(R.mipmap.ic_launcher);
            val items: List<FamItem> = listOf(
                FamItem(0, "Javier Mendonca", d),
                FamItem(1, "Vasily Kabunov", d),
                FamItem(2, "Faheem Ahmad Khan", d),
                FamItem(4, "Ankit Gupta", d)
            )
            FamPopupWindow(this)
                .setDimView(content)
                .setOnItemSelectedListener {item ->
                    when(item.id) {

                    }
                    true
                }
                .show(fab, items)
        }

    }
}
