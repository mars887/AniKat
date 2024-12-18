package daxo.the.anikat.fragments.mediapage.tabslayout

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnticipateInterpolator
import android.view.animation.OvershootInterpolator
import android.widget.FrameLayout
import androidx.core.animation.doOnEnd
import androidx.core.graphics.toColor
import androidx.fragment.app.Fragment
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.qualifiers.ActivityContext
import daxo.the.anikat.R
import daxo.the.anikat.databinding.MmpPagesTabsLayoutBinding
import daxo.the.anikat.fragments.mediapage.tabs.MmpCharactersTab
import daxo.the.anikat.fragments.mediapage.tabs.MmpOverviewTab
import daxo.the.anikat.fragments.mediapage.tabs.MmpWatchTab
import daxo.the.anikat.tests.StartUpFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import kotlin.concurrent.thread
import kotlin.math.abs
import kotlin.math.cbrt
import kotlin.math.min
import kotlin.random.Random

@Suppress("NAME_SHADOWING")
class TabsLayoutHelperV2 @AssistedInject constructor(
    @Assisted private val bars: List<Triple<View, String, Int>>,
    @Assisted private val scope: CoroutineScope,
    @ActivityContext private val context: Context,
) : ITabsLayoutHelper {
    private val animators = mutableListOf<Animator>()
    override var activeMargin = context.resources.getDimension(R.dimen.mmpTabsActiveWidthPadding).toInt()
    override var inactiveMargin = context.resources.getDimension(R.dimen.mmpTabsInactiveWidthPadding).toInt()
    override var activeColor = Color.valueOf(0f, 0f, 90f, 0f)
        set(value) {
            field = value
            underlineView?.setBackgroundColor(field.toArgb())
        }
    override var inactiveColor = Color.valueOf(0f, 0f, 0f, 0f)
        set(value) {
            field = setupInactiveColor(value)
        }

    var underlineView: View? = null

    override var colorDistanceShift: (Int) -> Int = { 0 }
    private var activeId: Int = -bars[0].third
    private var activePosition: Int = 0
    private val switchMutex = Mutex()

    init {
        initBars()
        instantSwitchTo(bars.first().third)
        scope.launch(Dispatchers.IO) {
            while (true) {
                delay(5)
                val bar = bars[activePosition]
                val tx2 = (bar.first.width + bar.first.x).toInt()
                if (tx2 != 0) break
            }
            withContext(Dispatchers.Main) {
                setupUnderline()
            }
        }
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
        underlineView?.setBackgroundColor(activeColor.toArgb())
    }

    private fun setupUnderline() {
        underlineView?.let { underlineView ->
            val bar = bars[activePosition]
            val tx1 = bar.first.x.toInt()
            val tx2 = (bar.first.width + bar.first.x).toInt()
            val lp = (underlineView.layoutParams as FrameLayout.LayoutParams)
            lp.marginStart = tx1
            lp.width = tx2 - tx1
            underlineView.layoutParams = lp
        }
    }

    override fun instantSwitchTo(toId: Int): Boolean = runBlocking {
        switchMutex.withLock {
            if (activeId == toId) return@withLock false
            if (bars.find { it.third == toId } == null) return@withLock false
            animators.forEach(Animator::cancel)

            activePosition = bars.indexOf(bars.find { it.third == toId })
            activeId = toId
            setupUnderline()
            true
        }
    }

    override fun switchTo(toId: Int): Boolean = runBlocking {
        switchMutex.withLock {
            if (activeId == toId) return@withLock false
            if (bars.find { it.third == toId } == null) return@withLock false
            animators.forEach(Animator::cancel)

            val toIndex = bars.indexOf(bars.find { it.third == toId })

            val bar = bars[toIndex]

            underlineView?.let { underlineView ->
                val lp = (underlineView.layoutParams as FrameLayout.LayoutParams)
                val cx1 = lp.marginStart
                val cx2 = cx1 + lp.width
                val tx1 = bar.first.x.toInt()
                val tx2 = (bar.first.width + bar.first.x).toInt()

                var x1 = cx1
                var x2 = tx1
                var x1c = false
                var x2c = false

                fun applyLP(noCheck: Boolean = false) {
                    if (noCheck || !x1c || !x2c) return
                    val lp = (underlineView.layoutParams as FrameLayout.LayoutParams)
                    lp.marginStart = x1
                    lp.width = x2 - x1
                    underlineView.layoutParams = lp
                    x1c = false
                    x2c = false
                }

                fun setFinalX() {
                    val lp = (underlineView.layoutParams as FrameLayout.LayoutParams)
                    lp.marginStart = tx1
                    lp.width = tx2 - tx1
                    underlineView.layoutParams = lp
                }

                animators += ValueAnimator.ofInt(cx1, tx1).apply {
                    interpolator = OvershootInterpolator(1.01f)
                    addUpdateListener {
                        x1 = animatedValue as Int
                        x1c = true
                        applyLP()
                    }
                    start()
                    doOnEnd {
                        setFinalX()
                    }
                }
                animators += ValueAnimator.ofInt(cx2, tx2).apply {
                    interpolator = OvershootInterpolator(1.01f)
                    addUpdateListener {
                        x2 = animatedValue as Int
                        x2c = true
                        applyLP()
                    }
                    start()
                    doOnEnd {
                        setFinalX()
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


    private fun map(x: Float, inMin: Float, inMax: Float, outMin: Float, outMax: Float): Float {
        return (x - inMin) * (outMax - outMin) / (inMax - inMin) + outMin
    }

    companion object {
        private const val TAG = "TabsLayoutHelper"
    }
}

@AssistedFactory
interface TabsLayoutHelperFactoryV2 {
    fun create(
        tabs: List<Triple<View, String, Int>>,
        scope: CoroutineScope
    ): TabsLayoutHelperV2
}