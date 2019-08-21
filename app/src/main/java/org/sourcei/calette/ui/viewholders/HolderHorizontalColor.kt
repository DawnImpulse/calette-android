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
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.toColorInt
import androidx.recyclerview.widget.RecyclerView
import io.paperdb.Paper
import kotlinx.android.synthetic.main.inflator_horizontal_color.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.sourcei.calette.ui.pojo.PojoColor
import org.sourcei.calette.utils.functions.copy
import org.sourcei.calette.utils.functions.toast
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

    private var layout = view.horizontalColorLayout!!
    private var code = view.horizontalColorCode!!
    private var name = view.horizontalColorName!!
    private var bookmark = view.horizontalBookmark!!
    private var copy = view.horizontalCopy!!
    private var bookmarkIcon = view.horizontalBookmarkIcon!!
    private var copyIcon = view.horizontalCopyIcon!!
    private var context = view.context!!

    /**
     * set details
     */
    @SuppressLint("DefaultLocale")
    fun setDetails(color: PojoColor) {
        layout.setBackgroundColor(color.color.toColorInt())
        code.text = color.color
        name.text = color.name

        val contrast = ColorHandler.getContrastColor(color.color.toColorInt())

        // color handling
        code.setTextColor(contrast)
        name.setTextColor(contrast)
        bookmarkIcon.colorFilter = PorterDuffColorFilter(contrast, PorterDuff.Mode.SRC_ATOP)
        copyIcon.colorFilter = PorterDuffColorFilter(contrast, PorterDuff.Mode.SRC_ATOP)


        // on click listener
        bookmark.setOnClickListener {
            GlobalScope.launch {
                Paper.book().write(color.color, true)
                (context as AppCompatActivity).runOnUiThread {
                    context.toast("color bookmarked")
                }
            }
        }

        copy.setOnClickListener {
            color.color.copy(context)
            context.toast("copied ${color.color.toUpperCase()} to clipboard")
        }
    }
}