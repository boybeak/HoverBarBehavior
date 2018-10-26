package com.github.boybeak.hoverbarlayout

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.bumptech.glide.request.RequestOptions
import com.github.boybeak.design.FloatingActionMenu
import kotlinx.android.synthetic.main.activity_floating_action.*

class FloatingActionActivity : AppCompatActivity() {

    private val avatars = arrayOf(R.drawable.avatar1, R.drawable.avatar2, R.drawable.avatar3)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_floating_action)

        fab.setOnClickListener {
            val famPW = FloatingActionMenu(this)
                .setDimView(content)
                .setAnchorView(fab)
                .inflate(R.menu.menu_demo)
                .setOnCreatedListener { designMenu, famItemViews ->
                    /*famItemViews.forEachIndexed { index, famItemView ->
                        (famItemView.iconFab() as ImageView).scaleType = ImageView.ScaleType.CENTER_CROP
                        famItemView.setPadding(0, 0, 0, 0)
                        GlideApp.with(it)
                            .load(avatars[index])
                            .apply(RequestOptions.circleCropTransform())
                            .into(famItemView.iconFab())
                    }*/
                }
                .setOnItemSelectedListener {item ->
                    when(item.itemId) {

                    }
                    true
                }
            famPW.show()

        }

    }
}
