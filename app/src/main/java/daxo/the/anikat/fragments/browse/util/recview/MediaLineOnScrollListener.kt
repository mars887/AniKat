package daxo.the.anikat.fragments.browse.util.recview

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MediaLineOnScrollListener(
    val layoutManager: LinearLayoutManager,
    val requestPaginate: () -> Unit,
) : RecyclerView.OnScrollListener() {

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

        println("$visibleItemCount   $totalItemCount   $firstVisibleItemPosition")

        if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount) {
            requestPaginate()
        }
    }
}