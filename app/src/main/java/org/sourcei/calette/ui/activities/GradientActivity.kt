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

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import co.revely.gradient.RevelyGradient
import io.paperdb.Paper
import kotlinx.android.synthetic.main.activity_gradient.*
import kotlinx.android.synthetic.main.inflator_color_square.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.sourcei.calette.R
import org.sourcei.calette.utils.functions.gone
import org.sourcei.calette.utils.functions.toHexa
import org.sourcei.calette.utils.functions.toast
import org.sourcei.calette.utils.reusables.BOOKMARKED
import org.sourcei.calette.utils.reusables.Config

/**
 * @info -
 *
 * @author - Saksham
 * @note Last Branch Update - master
 *
 * @note Created on 2019-08-28 by Saksham
 * @note Updates :
 */
class GradientActivity : AppCompatActivity() {

    // on create
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gradient)

        setDetails()

        // remove bookmark icon if already bookmarked
        intent.extras?.let {
            if (it.containsKey(BOOKMARKED))
                gradientB.gone()
        }
    }

    // set color & angle
    @SuppressLint("SetTextI18n")
    private fun setDetails() {

        val gradient = Config.gradient
        val colors = gradient[0] as List<Int>
        val angle = gradient[1] as Int

        // setting various colors
        colors.forEach {
            val color = layoutInflater.inflate(R.layout.inflator_color_square, null)
            color.colorSquareColor.setBackgroundColor(it)
            color.colorSquareName.text = it.toHexa()

            color.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
            gradientSingleColors.addView(color)
        }

        //gradient angle
        gradientAngle.text = "Angle $angle°"

        //gradient color
        RevelyGradient
                .linear()
                .colors(colors.toIntArray())
                .angle(angle.toFloat())
                .onBackgroundOf(gradientColor)

        // bookmark
        gradientB.setOnClickListener {
            GlobalScope.launch {

                // we are doing this to keep the key unique
                Paper.book().write("$angle-${colors.map { it.toHexa() }}", gradient)

                runOnUiThread {
                    toast("gradient bookmarked")
                }
            }
        }
    }
}