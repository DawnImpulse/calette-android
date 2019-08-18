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

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.revely.gradient.RevelyGradient
import kotlinx.android.synthetic.main.inflator_gradient.view.*
import org.sourcei.calette.utils.functions.F

/**
 * @info -
 *
 * @author - Saksham
 * @note Last Branch Update - master
 *
 * @note Created on 2019-08-18 by Saksham
 * @note Updates :
 */
class HolderGradient(view: View) : RecyclerView.ViewHolder(view) {
    val layout = view.gradientLayout
    val bookmark = view.gradientBookmark
    val outer = view.outerLayout
    val context = view.context

    /**
     * set gradient
     */
    fun setGradient(colors: List<Int>, angle: Int, height: Int) {
        /*val point = F.displayDimensions(context)
        val width = point.x / 2 - F.dpToPx(8, context)

        outer.layoutParams = ViewGroup.LayoutParams(width, F.dpToPx((140..260).random(), context))
        F.randomGradient(layout)*/

        val point = F.displayDimensions(context)
        val width = point.x / 2 - F.dpToPx(8, context)
        outer.layoutParams = ViewGroup.LayoutParams(width, height)

        RevelyGradient
                .linear()
                .colors(colors.toIntArray())
                .angle(angle.toFloat())
                .onBackgroundOf(layout)


        bookmark.setOnClickListener {

        }
    }
}