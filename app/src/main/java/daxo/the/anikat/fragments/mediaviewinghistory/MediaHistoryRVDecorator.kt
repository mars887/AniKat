package daxo.the.anikat.fragments.mediaviewinghistory

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class MediaHistoryRVDecorator(val verticalMargin: Int,val horizontalMargin: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.left = verticalMargin
        outRect.right = verticalMargin
        outRect.bottom = horizontalMargin

//        val position = parent.getChildAdapterPosition(view)
//
//        if (position == itemCount - 1) {
//            outRect.right = margin
//        }
    }
}
