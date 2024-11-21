package daxo.the.anikat.tests

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.widget.TextView

class TextViewWithDivider @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : androidx.appcompat.widget.AppCompatTextView(context, attrs, defStyleAttr) {
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawRoundRect(
            paddingStart.toFloat(), height / 2 - 1f,
            width - paddingEnd.toFloat(), height / 2 + 1f, 1f, 1f, paint
        )
    }
}