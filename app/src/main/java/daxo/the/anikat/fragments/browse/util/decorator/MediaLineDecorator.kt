package daxo.the.anikat.fragments.browse.util.decorator

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class MediaLineDecorator(private val margin: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.left = margin

        val position = parent.getChildAdapterPosition(view)
        val itemCount = state.itemCount

        if (itemCount == 0) return

        if (position == itemCount - 1) {
            outRect.right = margin
        }
    }
}