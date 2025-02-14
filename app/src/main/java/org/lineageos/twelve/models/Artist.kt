/*
 * SPDX-FileCopyrightText: 2024-2025 The LineageOS Project
 * SPDX-License-Identifier: Apache-2.0
 */

package org.lineageos.twelve.models

import android.content.Context
import android.net.Uri
import androidx.media3.common.MediaMetadata
import org.lineageos.twelve.R
import org.lineageos.twelve.ext.buildMediaItem
import org.lineageos.twelve.ext.toByteArray

/**
 * An artist.
 *
 * @param name The name of the artist
 */
data class Artist(
    override val uri: Uri,
    override val thumbnail: Thumbnail?,
    val name: String?,
) : MediaItem<Artist> {
    override val mediaType = MediaType.ARTIST

    override fun areContentsTheSame(other: Artist) = compareValuesBy(
        this, other,
        Artist::thumbnail,
        Artist::name,
    ) == 0

    override fun toMedia3MediaItem(context: Context) = buildMediaItem(
        title = name ?: context.getString(R.string.artist_unknown),
        mediaId = uri.toString(),
        isPlayable = false,
        isBrowsable = true,
        mediaType = MediaMetadata.MEDIA_TYPE_ARTIST,
        sourceUri = uri,
        artworkData = thumbnail?.bitmap?.toByteArray(),
        artworkType = thumbnail?.type?.media3Value,
        artworkUri = thumbnail?.uri,
    )

    class Builder(uri: Uri) : MediaItem.Builder<Builder, Artist>(uri) {
        private var name: String? = null

        /**
         * @see Artist.name
         */
        fun setName(name: String?) = this.also {
            this.name = name
        }

        override fun build() = Artist(
            uri = uri,
            thumbnail = thumbnail,
            name = name,
        )
    }
}
