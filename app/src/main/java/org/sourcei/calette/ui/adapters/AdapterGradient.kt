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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import org.sourcei.calette.R
import org.sourcei.calette.ui.pojo.PojoColor
import org.sourcei.calette.ui.viewholders.HolderGradient
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
class AdapterGradient(
    val items: List<Pair<Int, Any>?>,
    recyclerView: RecyclerView
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var lastVisibleItem: Int = 0
    private var totalItemCount: Int = 0
    private val visibleThreshold = 5
    private var isLoading: Boolean = false
    private var loadMoreListener: OnLoadMoreListener? = null

    private val VIEW_ITEM = 0
    private val VIEW_LOADING = 1

    // initialization for Load More Listener
    init {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (!isLoading && loadMoreListener != null) {
                    val layoutManager = recyclerView.layoutManager

                    // last visible item for staggered grid
                    fun getLastVisibleItem(lastVisibleItemPositions: List<Int>): Int {
                        var maxSize = 0
                        lastVisibleItemPositions.forEachIndexed { index, _ ->
                            if (index == 0) {
                                maxSize = lastVisibleItemPositions[index]
                            } else if (lastVisibleItemPositions[index] > maxSize) {
                                maxSize = lastVisibleItemPositions[index]
                            }
                        }

                        return maxSize
                    }

                    totalItemCount = layoutManager!!.itemCount

                    if (layoutManager is StaggeredGridLayoutManager)
                        lastVisibleItem =
                            getLastVisibleItem(layoutManager.findLastVisibleItemPositions(null).toList())

                    if (layoutManager is LinearLayoutManager)
                        lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                    if (totalItemCount <= lastVisibleItem + visibleThreshold) {
                        isLoading = true
                        loadMoreListener!!.onLoadMore()
                    }
                }
            }
        })
    }

    // return 50 items
    override fun getItemCount(): Int {
        return items.size
    }

    // return view type
    override fun getItemViewType(position: Int): Int {
        return if (items[position] == null) VIEW_LOADING else VIEW_ITEM
    }

    // creating views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_ITEM)
            HolderGradient(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.inflator_gradient,
                    parent,
                    false
                )
            )
        else
            HolderGradient(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.inflator_loading,
                    parent,
                    false
                )
            )
    }

    // binding views
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is HolderGradient) {
            val item = items[position]!!
            if (item.first == 0)
                holder.setGradient(item.second as Map<Int, Any>)
            else
                holder.setColor(item.second as PojoColor)
        }
    }

    // set loaded
    fun setLoaded() {
        isLoading = false
    }

    // set load more listener
    fun setLoadMoreListener(loadMoreListener: OnLoadMoreListener) {
        this.loadMoreListener = loadMoreListener
    }

}