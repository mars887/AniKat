package daxo.the.anikat.core.repo

import daxo.the.anikat.core.entity.TitleType
import javax.inject.Inject


class SettingsRepo @Inject constructor() {

    fun getTitleLanguage(): TitleType = TitleType.ENGLISH
}