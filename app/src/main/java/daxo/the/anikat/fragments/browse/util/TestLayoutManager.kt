package daxo.the.anikat.fragments.browse.util

import android.content.Context
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.abs

class TestLayoutManager(context: Context) : LinearLayoutManager(context, VERTICAL, false) {

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        super.onLayoutChildren(recycler, state)
        updateChildOffsets()
    }

    override fun scrollVerticallyBy(
        dy: Int,
        recycler: RecyclerView.Recycler?,
        state: RecyclerView.State?
    ): Int {
        val scrolled = super.scrollVerticallyBy(dy, recycler, state)
        updateChildOffsets()
        return scrolled
    }

    private fun updateChildOffsets() {
        val height = height  // Получаем высоту RecyclerView через LayoutManager

        for (i in 0 until childCount) {
            val view = getChildAt(i)
            val top = getDecoratedTop(view!!)
            val bottom = getDecoratedBottom(view)

            val center = abs(top + bottom) / 2
            val rootCenter = height / 2


            var test2: Int = 0

            if (center > rootCenter) {
                test2 = ((center - height) / 2) + rootCenter / 2
            } else {
                test2 = ((center - height) / 2) + rootCenter / 2
            }

            Log.d("tlm", "updateChildOffsets: $i -> $test2")

            val translationY = test2
            view.translationY = translationY.toFloat()
        }
    }
}
