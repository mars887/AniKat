package daxo.the.data.sqlrepo.entity.converters

import androidx.room.TypeConverter
import daxo.core.model.media.enums.MediaFormat
import daxo.core.model.media.enums.MediaSeason
import daxo.core.model.media.minis.CoverImage
import daxo.core.model.media.minis.MediaTitle
import daxo.core.model.media.minis.NextAiringEpisode
import daxo.core.model.media.minis.Studios

object MinisConverter {

    @TypeConverter
    fun mediaTitleTo(mediaTitle: MediaTitle): String =
        GsonObject.gson.toJson(mediaTitle)

    @TypeConverter
    fun mediaTitleFrom(string: String): MediaTitle =
        GsonObject.gson.fromJson(string, MediaTitle::class.java)

    @TypeConverter
    fun studiosTo(studios: Studios): String =
        GsonObject.gson.toJson(studios)

    @TypeConverter
    fun studiosFrom(string: String): Studios =
        GsonObject.gson.fromJson(string, Studios::class.java)

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
    fun coverImageTo(coverImage: CoverImage): String =
        GsonObject.gson.toJson(coverImage)

    @TypeConverter
    fun coverImageFrom(string: String): CoverImage =
        GsonObject.gson.fromJson(string,CoverImage::class.java)
}