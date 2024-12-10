package daxo.the.data.sqlrepo.entity.converters

import androidx.room.TypeConverter
import daxo.core.model.media.enums.MediaFormat
import daxo.core.model.media.enums.MediaSeason
import daxo.core.model.byApollo.media.BasicStudio
import daxo.core.model.byApollo.media.BasicStudioQuery
import daxo.core.model.byApollo.media.MediaCoverImage
import daxo.core.model.byApollo.media.MediaTitle
import daxo.core.model.byApollo.media.NextAiringEpisode

object MinisConverter {

    @TypeConverter
    fun mediaTitleTo(mediaTitle: MediaTitle): String =
        GsonObject.gson.toJson(mediaTitle)

    @TypeConverter
    fun mediaTitleFrom(string: String): MediaTitle =
        GsonObject.gson.fromJson(string, MediaTitle::class.java)

    @TypeConverter
    fun studiosTo(studios: BasicStudio): String =
        GsonObject.gson.toJson(studios)

    @TypeConverter
    fun studiosFrom(string: String): BasicStudio =
        GsonObject.gson.fromJson(string, BasicStudio::class.java)

    @TypeConverter
    fun mediaSeasonTo(season: MediaSeason): String =
        season.rawValue

    @TypeConverter
    fun mediaSeasonFrom(string: String): MediaSeason =
        MediaSeason.valueOf(string)

    @TypeConverter
    fun nextAiringEpisodeTo(nextAiringEpisode: NextAiringEpisode): String =
        GsonObject.gson.toJson(nextAiringEpisode)

    @TypeConverter
    fun nextAiringEpisodeFrom(string: String): NextAiringEpisode =
        GsonObject.gson.fromJson(string, NextAiringEpisode::class.java)

    @TypeConverter
    fun mediaFormatTo(mediaFormat: MediaFormat): String =
        mediaFormat.rawValue

    @TypeConverter
    fun mediaFormatFrom(string: String): MediaFormat =
        MediaFormat.valueOf(string)

    @TypeConverter
    fun coverImageTo(coverImage: MediaCoverImage): String =
        GsonObject.gson.toJson(coverImage)

    @TypeConverter
    fun coverImageFrom(string: String): MediaCoverImage =
        GsonObject.gson.fromJson(string, MediaCoverImage::class.java)

    @TypeConverter
    fun basicStudioQueryTo(studios: BasicStudioQuery): String =
        GsonObject.gson.toJson(studios)

    @TypeConverter
    fun basicStudioQueryFrom(string: String): BasicStudioQuery =
        GsonObject.gson.fromJson(string, BasicStudioQuery::class.java)
}