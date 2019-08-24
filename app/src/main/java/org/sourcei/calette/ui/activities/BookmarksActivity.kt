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
import io.paperdb.Paper
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_gradient_list.*
import org.sourcei.calette.R
import org.sourcei.calette.ui.adapters.AdapterGradient
import org.sourcei.calette.ui.pojo.PojoColor
import org.sourcei.calette.utils.functions.RxBusMap
import org.sourcei.calette.utils.reusables.POSITION

/**
 * @info -
 *
 * @author - Saksham
 * @note Last Branch Update - master
 *
 * @note Created on 2019-08-22 by Saksham
 * @note Updates :
 */
class BookmarksActivity : AppCompatActivity() {
    private var items: MutableList<Pair<Int, Any>?> = mutableListOf()
    private val disposables by lazy { CompositeDisposable() }
    private lateinit var adapter: AdapterGradient
    private lateinit var bookmarks: MutableList<String>

    // on create
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gradient_list)

        bookmarks = Paper.book().allKeys.toMutableList()

        // add to items list either as gradient or color
        bookmarks.forEach {
            //it is a gradient
            if (it.substring(0, 1) != "#") {
                val item: Map<Int, Any> = Paper.book().read(it)
                items.add(Pair(0, item))

                // it is a color
            } else {
                val item: PojoColor = Paper.book().read(it)
                items.add(Pair(1, item))
            }
        }

        // adding to recycler
        adapter = AdapterGradient(items, gradientRecycler,true)
        gradientRecycler.layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
        gradientRecycler.adapter = adapter

        //event handling
        disposables.add(RxBusMap.subscribe { event(it) })
    }

    // clearing disposables
    override fun onDestroy() {
        super.onDestroy()

        disposables.clear()
    }

    // event handling
    fun event(it: Map<String, Any>) {

        // remove from list & Paper
        if (it.isNotEmpty()) {
            val pos = it[POSITION] as Int
            val key = bookmarks[pos]

            items.removeAt(pos)
            bookmarks.removeAt(pos)
            Paper.book().delete(key)
            runOnUiThread {
                adapter.notifyItemRemoved(pos)
            }
        }
    }
}