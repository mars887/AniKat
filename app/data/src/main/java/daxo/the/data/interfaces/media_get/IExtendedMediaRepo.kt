package daxo.the.data.interfaces.media_get

import androidx.annotation.IdRes
import daxo.core.model.media.media.extended.ExtendedMediaCard

interface IExtendedMediaRepo {
    suspend fun loadPages(params: LoadMediaPagesParams): List<ExtendedMediaCard>?
    suspend fun loadById(@IdRes id: Int): ExtendedMediaCard?
}