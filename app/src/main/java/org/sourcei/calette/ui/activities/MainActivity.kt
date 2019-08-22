/**
 * ISC License
 *
 * Copyright 2018-2019, Saksham (DawnImpulse)
 *
 * Permission to use, copy, modify, and/or distribute this software for any purpose with or without fee is hereby granted,
 * provided that the above copyright notice and this permission notice appear in all copies.
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES WITH REGARD TO THIS SOFTWARE INCLUDING ALL
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY SPECIAL, DIRECT,
 * INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS,
 * WHETHER IN AN ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR IN CONNECTION WITH THE USE
 * OR PERFORMANCE OF THIS SOFTWARE.
 **/
package org.sourcei.calette.ui.activities

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.LayerDrawable
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.toColorInt
import androidx.recyclerview.widget.LinearLayoutManager
import co.revely.gradient.RevelyGradient
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*
import org.sourcei.calette.R
import org.sourcei.calette.ui.adapters.AdapterColorCircle
import org.sourcei.calette.ui.adapters.AdapterColorHorizontal
import org.sourcei.calette.ui.pojo.PojoColor
import org.sourcei.calette.utils.functions.F
import org.sourcei.calette.utils.functions.RxBus
import org.sourcei.calette.utils.functions.openActivity
import org.sourcei.calette.utils.handlers.ColorHandler
import org.sourcei.calette.utils.reusables.Arrays

/**
 * @info -
 *
 * @author - Saksham
 * @note Last Branch Update - master
 *
 * @note Created on 2019-08-15 by Saksham
 * @note Updates :
 */
class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var circleColorAdapter: AdapterColorCircle
    private lateinit var horizontalColorAdapter: AdapterColorHorizontal
    private lateinit var circleColors: MutableList<Pair<String, Boolean>>
    private lateinit var palette: List<PojoColor>
    private val disposables by lazy { CompositeDisposable() }
    private var currentCirclePos = 0

    /**
     * on create
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // gradient on fab
        fabGradient()

        // fetching colors for circle recycler
        circleColors = Arrays.materialColorsList.filter { it.second[5].name == "500" }.map { Pair(it.second[5].color, false) }.toMutableList()

        // marking first position as active
        val red = circleColors[0]
        circleColors.removeAt(0)
        circleColors.add(0, Pair(red.first, true))

        // handling for circle colors
        circleColorAdapter = AdapterColorCircle(circleColors)
        colorCircleRecycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        colorCircleRecycler.adapter = circleColorAdapter

        // handling for color palette
        palette = Arrays.materialColorsList[0].second
        horizontalColorAdapter = AdapterColorHorizontal(palette)
        mainRecycler.layoutManager = LinearLayoutManager(this)
        mainRecycler.adapter = horizontalColorAdapter

        // changing appbar text & color
        mainTitle.setTextColor(ColorHandler.getContrastColor(circleColors[0].first.toColorInt()))
        mainTitle.text = Arrays.materialColorsList[0].first.toUpperCase()
        (mainAppBar.background.current as LayerDrawable).colorFilter = PorterDuffColorFilter(circleColors[0].first.toColorInt(), PorterDuff.Mode.SRC_OVER)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            window.statusBarColor = Arrays.materialColorsList[0].second[7].color.toColorInt()

        // on click listeners
        gradient.setOnClickListener(this)
        bookmarks.setOnClickListener(this)

        // event listener
        disposables.add(RxBus.subscribe { event(it) })
    }

    /**
     * removing subscribers
     */
    override fun onDestroy() {
        super.onDestroy()

        disposables.clear()
    }

    // handling click listener
    override fun onClick(view: View) {
        when (view.id) {
            gradient.id -> openActivity(GradientListActivity::class.java)
            bookmarks.id -> openActivity(BookmarksActivity::class.java)
        }
    }

    // event bus
    fun event(color: String) {
        if (color.isNotEmpty()) {

            val pos = circleColors.withIndex().filter { it.value.first == color }.map { it.index }[0]

            if (pos != currentCirclePos) {
                val colored = circleColors[currentCirclePos]

                // highlighting new color
                circleColors.removeAt(pos)
                circleColors.add(pos, Pair(color, true))
                circleColorAdapter.notifyItemChanged(pos)

                // removing highlight of previous color
                circleColors.removeAt(currentCirclePos)
                circleColors.add(currentCirclePos, Pair(colored.first, false))
                circleColorAdapter.notifyItemChanged(currentCirclePos)

                // changing the palette
                palette = Arrays.materialColorsList[pos].second
                horizontalColorAdapter = AdapterColorHorizontal(palette)
                mainRecycler.adapter = horizontalColorAdapter

                // changing appbar color & name
                mainTitle.setTextColor(ColorHandler.getContrastColor(color.toColorInt()))
                mainTitle.text = Arrays.materialColorsList[pos].first.toUpperCase()
                (mainAppBar.background.current as LayerDrawable).colorFilter = PorterDuffColorFilter(color.toColorInt(), PorterDuff.Mode.SRC_OVER)

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                    window.statusBarColor = Arrays.materialColorsList[pos].second[7].color.toColorInt()


                currentCirclePos = pos
            }
        }
    }

    // random gradient for fab
    private fun fabGradient() {
        RevelyGradient
                .linear()
                .colors(intArrayOf(F.randomColor().toColorInt(), F.randomColor().toColorInt()))
                .onBackgroundOf(fabLayout)
    }
}