package com.games.gamex.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ScreenshotsGameResponse(

	@field:SerializedName("next")
	val next: Any? = null,

	@field:SerializedName("previous")
	val previous: Any? = null,

	@field:SerializedName("count")
	val count: Int? = null,

	@field:SerializedName("results")
	val results: List<ScreenshotsResultItemResponse>? = null
)

data class ScreenshotsResultItemResponse(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("is_deleted")
	val isDeleted: Boolean? = null,

	@field:SerializedName("width")
	val width: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("height")
	val height: Int? = null
)
