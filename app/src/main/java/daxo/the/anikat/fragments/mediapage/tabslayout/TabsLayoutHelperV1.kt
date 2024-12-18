package daxo.the.anikat.fragments.mediapage.tabslayout

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.graphics.toColor
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.qualifiers.ActivityContext
import daxo.the.anikat.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.math.abs
import kotlin.math.cbrt
import kotlin.math.min

class TabsLayoutHelperV1 @AssistedInject constructor(
    @Assisted private val bars: List<Triple<View, String, Int>>,
    @Assisted private val scope: CoroutineScope,
    @ActivityContext private val context: Context,
): ITabsLayoutHelper {
    private val animators = mutableListOf<Animator>()
    private val barsColor = mutableMapOf<View, Int>()
    override var activeMargin  = context.resources.getDimension(R.dimen.mmpTabsActiveWidthPadding).toInt()
    override var inactiveMargin = context.resources.getDimension(R.dimen.mmpTabsInactiveWidthPadding).toInt()
    override var activeColor = context.getColor(R.color.mmpTabsLayoutActiveColor).toColor()
    override var inactiveColor = context.getColor(R.color.mmpTabsLayoutInactiveColor).toColor()
        set(value) {
            field = setupInactiveColor(value)
            setupColors()
        }

    override var colorDistanceShift: (Int) -> Int = { it * (10 / cbrt(it.toDouble())).toInt() }
    private var activeId: Int = -bars[0].third
    private var activePosition: Int = 0
    private val switchMutex = Mutex()

    init {
        initBars()
        instantSwitchTo(bars.first().third)
    }

    override var clickListener: ITabsLayoutHelper.ClickListener? = null
        set(value) {
            field = value
            initBars()
        }

    private fun initBars() {
        bars.forEach { bar ->
            bar.first.setOnClickListener {
                val oldIndex = activePosition
                switchTo(bar.first)
                if (oldIndex == activePosition) return@setOnClickListener
                val direction = if (oldIndex < activePosition) 1 else -1
                clickListener?.invoke(bar.third, bar.first, direction)
            }
        }
    }

    override fun instantSwitchTo(toId: Int): Boolean = runBlocking {
        switchMutex.withLock {
            if (activeId == toId) return@withLock false
            if (bars.find { it.third == toId } == null) return@withLock false
            animators.forEach(Animator::cancel)

            activePosition = bars.indexOf(bars.find { it.third == toId })
            activeId = toId

            setupColors()
            setupWidth()
            true
        }
    }

    override fun switchTo(toId: Int): Boolean = runBlocking {
        switchMutex.withLock {
            if (activeId == toId) return@withLock false
            if (bars.find { it.third == toId } == null) return@withLock false
            animators.forEach(Animator::cancel)

            val fromId = activeId
            val fromIndex = activePosition
            // val toId = toId
            val toIndex = bars.indexOf(bars.find { it.third == toId })

            bars.forEachIndexed { index, triple ->
                val view = triple.first

                val distToOld = abs(index - fromIndex)
                val distToNew = abs(index - toIndex)

                // color animator
                val currentColor = barsColor[view] ?: view.solidColor
                val toColor =
                    if (distToNew == 0) activeColor.toArgb()
                    else colorBrightShift(inactiveColor, colorDistanceShift(distToNew))

                if (distToNew != distToOld || currentColor != toColor) {
                    animators += setupColorAnimator(currentColor, toColor, null) { color ->
                        view.setBackgroundColor(color)
                        barsColor[view] = color
                    }
                }

                // width animator
                val widthForView = if (toIndex == index) activeMargin else inactiveMargin

                if (widthForView != view.horizontalPadding) {
                    animators += setupIntAnimator(view.horizontalPadding, widthForView) { width ->
                        view.horizontalPadding = width
                    }
                }
            }
            activeId = toId
            activePosition = toIndex

            true
        }
    }


    override fun instantSwitchTo(view: View): Boolean {
        return bars.find { it.first == view }?.third?.let {
            instantSwitchTo(it)
        } ?: false
    }

    override fun switchTo(view: View): Boolean {
        return bars.find { it.first == view }?.third?.let {
            switchTo(it)
        } ?: false
    }

    private fun setupInactiveColor(value: Color): Color {
        val rgb = value.toArgb()
        val hsv = FloatArray(3)
        Color.colorToHSV(rgb, hsv)
        hsv[1] = map(hsv[1], 0f, 1f, 0f, 0.5f)
        hsv[2] = map(hsv[2], 0f, 1f, 0.9f, 1f)
        return Color.HSVToColor(hsv).toColor()
    }

    private fun setupColors() {
        bars.forEachIndexed { index, triple ->
            val view = triple.first

            val color = if (index == activePosition) {
                activeColor.toArgb()
            } else {
                val distance = abs(activePosition - index)
                colorBrightShift(inactiveColor, colorDistanceShift(distance))
            }
            view.setBackgroundColor(color)
            barsColor[view] = color
        }
    }

    private fun setupWidth() {
        bars.forEachIndexed { index, triple ->
            triple.first.horizontalPadding = if (index == activePosition) activeMargin else inactiveMargin
        }
    }

    private fun map(x: Float, inMin: Float, inMax: Float, outMin: Float, outMax: Float): Float {
        return (x - inMin) * (outMax - outMin) / (inMax - inMin) + outMin
    }
    companion object {
        private const val TAG = "TabsLayoutHelper"
    }
}

private var View.horizontalPadding: Int
    set(value) {
        setPadding(value, paddingTop, value, paddingBottom)
    }
    get() = paddingEnd

private fun colorBrightShift(activeColor: Color, colorShift: Int): Int {
    val hsv = FloatArray(3)
    Color.colorToHSV(activeColor.toArgb(), hsv)
    hsv[2] = min((hsv[2] * 255 - colorShift) / 255, 1f)
    return Color.HSVToColor(hsv)
}

private fun setupColorAnimator(from: Int, to: Int, animatorDuration: Int? = null, listener: (Int) -> Unit): Animator {
    return ValueAnimator.ofArgb(from, to).setupAnimator(animatorDuration, listener)
}

private fun setupIntAnimator(from: Int, to: Int, animatorDuration: Int? = null, listener: (Int) -> Unit): Animator {
    return ValueAnimator.ofInt(from, to).setupAnimator(animatorDuration, listener)
}

private fun ValueAnimator.setupAnimator(animatorDuration: Int? = null, listener: (Int) -> Unit): ValueAnimator {
    interpolator = AccelerateDecelerateInterpolator()
    animatorDuration?.let {
        duration = animatorDuration.toLong()
    }
    addUpdateListener {
        listener(animatedValue as Int)
    }
    start()
    return this
}

@AssistedFactory
interface TabsLayoutHelperFactoryV1 {
    fun create(
        tabs: List<Triple<View, String, Int>>,
        scope: CoroutineScope
    ): TabsLayoutHelperV1
}