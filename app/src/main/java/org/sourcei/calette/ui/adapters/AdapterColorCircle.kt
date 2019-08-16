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
package org.sourcei.calette.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.sourcei.calette.R
import org.sourcei.calette.ui.viewholders.HolderCircleColor

/**
 * @info -
 *
 * @author - Saksham
 * @note Last Branch Update - master
 *
 * @note Created on 2019-08-16 by Saksham
 * @note Updates :
 */
class AdapterColorCircle(
        private val colors: List<Pair<String,Boolean>>
) : RecyclerView.Adapter<HolderCircleColor>() {

    override fun getItemCount(): Int {
        return colors.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderCircleColor {
        return HolderCircleColor(LayoutInflater.from(parent.context).inflate(R.layout.inflator_circle_color, parent, false))
    }

    override fun onBindViewHolder(holder: HolderCircleColor, position: Int) {
        holder.setColor(colors[position])
    }

}