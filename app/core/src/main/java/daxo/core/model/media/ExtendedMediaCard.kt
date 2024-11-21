package daxo.core.model.media

import android.os.Parcelable
import daxo.core.model.media.enums.MediaFormat
import daxo.core.model.media.enums.MediaSeason
import daxo.core.model.media.minis.CoverImage
import daxo.core.model.media.minis.MediaTitle
import daxo.core.model.media.minis.NextAiringEpisode
import daxo.core.model.media.minis.Studios
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class ExtendedMediaCard(
    val mediaId: Int,
    val title: MediaTitle?,
    val studios: Studios?,
    val season: MediaSeason?,
    val seasonYear: Int?,
    val nextAiringEpisode: NextAiringEpisode?,
    val format: MediaFormat?,
    val description: String?,
    val episodes: Int?,
    val genres: List<String>?,
    val averageScore: Int?,
    val favourites: Int?,
    val coverImage: CoverImage?,
    val bannerImage: String?,
    val lastUpdate: Date,
): Parcelable