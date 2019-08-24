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
package org.sourcei.calette.ui.viewholders

import android.annotation.SuppressLint
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.toColorInt
import androidx.recyclerview.widget.RecyclerView
import co.revely.gradient.RevelyGradient
import io.paperdb.Paper
import kotlinx.android.synthetic.main.inflator_gradient.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.sourcei.calette.R
import org.sourcei.calette.ui.pojo.PojoColor
import org.sourcei.calette.utils.functions.*
import org.sourcei.calette.utils.handlers.ColorHandler
import org.sourcei.calette.utils.reusables.POSITION

/**
 * @info -
 *
 * @author - Saksham
 * @note Last Branch Update - master
 *
 * @note Created on 2019-08-18 by Saksham
 * @note Updates :
 */
class HolderGradient(view: View, val allBookmarks: Boolean) : RecyclerView.ViewHolder(view) {
    private val gradient = view.gradient
    private val layout = view.gradientLayout!!
    private val bookmark = view.gradientBookmark!!
    private val bookmarkIcon = view.gradientBookmarkIcon!!
    private val outer = view.outerLayout!!
    private val context = view.context!!

    private val color = view.color
    private val colorB = view.colorBookmark
    private val colorBI = view.colorBookmarkIcon
    private val colorC = view.colorCopy
    private val colorCI = view.colorCopyIcon
    private val code = view.colorCode
    private val name = view.colorName

    /**
     * set gradient
     */
    fun setGradient(map: Map<Int, Any>) {

        val colors = map[0] as List<Int>
        val angle = map[1] as Int
        val height = map[2] as Int

        gradient.show()
        color.gone()

        val point = F.displayDimensions(context)
        val width = point.x / 2 - F.dpToPx(8, context)
        outer.layoutParams = ViewGroup.LayoutParams(width, height)

        // set bookmarked icon
        if (allBookmarks)
            bookmarkIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.vd_bookmarked))

        RevelyGradient
                .linear()
                .colors(colors.toIntArray())
                .angle(angle.toFloat())
                .onBackgroundOf(layout)


        bookmark.setOnClickListener {
            GlobalScope.launch {

                // we are doing this to keep the key unique
                Paper.book().write("$angle-${colors.map { it.toHexa() }}", map)

                (context as AppCompatActivity).runOnUiThread {
                    if (allBookmarks) {
                        // send event
                        val map = mutableMapOf<String, Any>()
                        map[POSITION] = adapterPosition
                        RxBusMap.accept(map)

                        context.toast("gradient removed")
                    } else
                        context.toast("gradient bookmarked")
                }
            }
        }
    }

    @SuppressLint("DefaultLocale")
    fun setColor(colorPojo: PojoColor) {
        gradient.gone()
        color.show()

        color.setBackgroundColor(colorPojo.color.toColorInt())
        code.text = colorPojo.color
        name.text = colorPojo.name

        val contrast = ColorHandler.getContrastColor(colorPojo.color.toColorInt())

        // color handling
        code.setTextColor(contrast)
        name.setTextColor(contrast)
        colorBI.colorFilter = PorterDuffColorFilter(contrast, PorterDuff.Mode.SRC_ATOP)
        colorCI.colorFilter = PorterDuffColorFilter(contrast, PorterDuff.Mode.SRC_ATOP)


        // on click listener
        colorB.setOnClickListener {
            GlobalScope.launch {

                Paper.book().delete(colorPojo.color)

                (context as AppCompatActivity).runOnUiThread {
                    if (allBookmarks) {
                        // send event
                        val map = mutableMapOf<String, Any>()
                        map[POSITION] = adapterPosition
                        RxBusMap.accept(map)

                        context.toast("color removed")
                    } else
                        context.toast("color bookmarked")
                }
            }
        }

        colorC.setOnClickListener {
            colorPojo.color.copy(context)
            context.toast("copied ${colorPojo.color.toUpperCase()} to clipboard")
        }
    }
}