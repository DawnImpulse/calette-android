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

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.android.synthetic.main.activity_gradient_list.*
import org.sourcei.calette.R
import org.sourcei.calette.ui.adapters.AdapterGradient
import org.sourcei.calette.utils.functions.F
import org.sourcei.calette.utils.reusables.OnLoadMoreListener

/**
 * @info -
 *
 * @author - Saksham
 * @note Last Branch Update - master
 *
 * @note Created on 2019-08-18 by Saksham
 * @note Updates :
 */
class GradientListActivity : AppCompatActivity(), OnLoadMoreListener {
    private lateinit var items: MutableList<Map<Int, Any>>
    private lateinit var adapter: AdapterGradient

    // on create
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gradient_list)

        items = generate()
        adapter = AdapterGradient(items, gradientRecycler)
        gradientRecycler.layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
        gradientRecycler.adapter = adapter

        adapter.setLoadMoreListener(this)
    }

    // load more listener
    override fun onLoadMore() {
        val count = items.size
        items.addAll(generate())
        adapter.notifyItemRangeInserted(count, 50)
        adapter.setLoaded()
    }

    // generate color, angle & height
    private fun generate(): MutableList<Map<Int, Any>> {
        val list = mutableListOf<Map<Int, Any>>()

        for (i in 1..50) {
            val map = mutableMapOf<Int, Any>()

            map[0] = F.randomGradient()
            map[1] = (0..180).random()
            map[2] = F.dpToPx((140..260).random(), this)

            list.add(map)
        }

        return list
    }
}