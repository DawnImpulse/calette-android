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

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.view.View
import androidx.core.graphics.toColorInt
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.inflator_horizontal_color.view.*
import org.sourcei.calette.ui.pojo.PojoColor
import org.sourcei.calette.utils.handlers.ColorHandler

/**
 * @info -
 *
 * @author - Saksham
 * @note Last Branch Update - master
 *
 * @note Created on 2019-08-16 by Saksham
 * @note Updates :
 */
class HolderHorizontalColor(view: View) : RecyclerView.ViewHolder(view) {

    var layout = view.horizontalColorLayout
    var code = view.horizontalColorCode
    var name = view.horizontalColorName
    var bookmark = view.horizontalBookmark
    var bookmarkIcon = view.horizontalBookmarkIcon
    var copyIcon = view.horizontalCopyIcon

    /**
     * set details
     */
    fun setDetails(color: PojoColor) {
        layout.setBackgroundColor(color.color.toColorInt())
        code.text = color.color
        name.text = color.name

        val contrast = ColorHandler.getContrastColor(color.color.toColorInt())

        code.setTextColor(contrast)
        name.setTextColor(contrast)
        bookmarkIcon.drawable.colorFilter = PorterDuffColorFilter(contrast,PorterDuff.Mode.SRC_ATOP)
        copyIcon.drawable.colorFilter = PorterDuffColorFilter(contrast,PorterDuff.Mode.SRC_ATOP)

        bookmark.setOnClickListener {

        }
    }
}