package daxo.the.anikat.fragments.browse.util.decorator

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class CenteredRVDecorator(val topMargin: Int, val bottomMargin: Int) :
    RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        val itemCount = state.itemCount

        if (itemCount == 0) return


        if (position == 0) {
            outRect.top = topMargin
        }
    }
}
