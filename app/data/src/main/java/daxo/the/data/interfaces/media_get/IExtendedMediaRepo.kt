package daxo.the.data.interfaces.media_get

import androidx.annotation.IdRes
import daxo.the.data.api.explore.LoadMediaPagesParams
import daxo.core.model.media.ExtendedMediaCard

interface IExtendedMediaRepo {
    suspend fun loadPages(params: LoadMediaPagesParams): List<ExtendedMediaCard>?
    suspend fun loadById(@IdRes id: Int): ExtendedMediaCard?
}