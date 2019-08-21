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

import android.graphics.drawable.GradientDrawable
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.graphics.toColorInt
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.inflator_circle_color.view.*
import org.sourcei.calette.R
import org.sourcei.calette.utils.functions.RxBus
import org.sourcei.calette.utils.functions.gone
import org.sourcei.calette.utils.functions.show

/**
 * @info -
 *
 * @author - Saksham
 * @note Last Branch Update - master
 *
 * @note Created on 2019-08-16 by Saksham
 * @note Updates :
 */
class HolderCircleColor(view: View) : RecyclerView.ViewHolder(view) {
    val circleColor = view.circle
    val selected = view.circleSelectedLayout
    val selectedColor = view.circleSelectedColor
    val layout = view.circleColorLayout
    val context = view.context

    /**
     * set color or selected
     */
    fun setColor(color: Pair<String, Boolean>) {

        if (color.second) {
            selected.show()
            circleColor.gone()

            (selectedColor.background.current as GradientDrawable).setColor(ContextCompat.getColor(context, R.color.white))

        } else {
            selected.gone()
            circleColor.show()

            (circleColor.background.current as GradientDrawable).setColor(color.first.toColorInt())
        }

        // click handling
        layout.setOnClickListener {
            RxBus.accept(color.first)
        }

    }
}