package daxo.the.anikat.fragments.mediapage.tabslayout

import android.graphics.Color
import android.view.View
import androidx.fragment.app.Fragment
import daxo.the.anikat.fragments.mediapage.tabs.MmpCharactersTab
import daxo.the.anikat.fragments.mediapage.tabs.MmpOverviewTab
import daxo.the.anikat.fragments.mediapage.tabs.MmpWatchTab
import daxo.the.anikat.tests.StartUpFragment

interface ITabsLayoutHelper {
    fun instantSwitchTo(toId: Int): Boolean
    fun switchTo(toId: Int): Boolean
    fun instantSwitchTo(view: View): Boolean
    fun switchTo(view: View): Boolean

    var activeMargin: Int
    var inactiveMargin: Int
    var activeColor: Color
    var inactiveColor: Color

    var colorDistanceShift: (Int) -> Int
    var clickListener: ClickListener?

    fun interface ClickListener {
        operator fun invoke(id: Int, view: View, direction: Int)
    }

    enum class Tabs(val id: Int, val title: String, val klass: Class<out Fragment>) {
        OVERVIEW(1, "Overview", MmpOverviewTab::class.java),
        WATCH(2, "Watch", MmpWatchTab::class.java),
        CHARACTERS(3, "Characters", MmpCharactersTab::class.java),
        STAFF(4, "Staff", StartUpFragment::class.java),
        REVIEWS(5, "Reviews", StartUpFragment::class.java),
        STATS(6, "Stats", StartUpFragment::class.java),
        SOCIAL(7, "Social", StartUpFragment::class.java),
    }
}