package com.games.gamex.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class DetailGameResponse(

	@field:SerializedName("added")
	val added: Int? = null,

	@field:SerializedName("developers")
	val developers: List<DevelopersItemResponse>? = null,

	@field:SerializedName("name_original")
	val nameOriginal: String? = null,

	@field:SerializedName("rating")
	val rating: Any? = null,

	@field:SerializedName("game_series_count")
	val gameSeriesCount: Int? = null,

	@field:SerializedName("playtime")
	val playtime: Int? = null,

	@field:SerializedName("platforms")
	val platforms: List<DetailPlatformsItemResponse?>? = null,

	@field:SerializedName("rating_top")
	val ratingTop: Int? = null,

	@field:SerializedName("reviews_text_count")
	val reviewsTextCount: Int? = null,

	@field:SerializedName("publishers")
	val publishers: List<PublishersItemResponse>? = null,

	@field:SerializedName("achievements_count")
	val achievementsCount: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("parent_platforms")
	val parentPlatforms: List<ParentPlatformsItemResponse>? = null,

	@field:SerializedName("reddit_name")
	val redditName: String? = null,

	@field:SerializedName("ratings_count")
	val ratingsCount: Int? = null,

	@field:SerializedName("slug")
	val slug: String? = null,

	@field:SerializedName("released")
	val released: String? = null,

	@field:SerializedName("youtube_count")
	val youtubeCount: Int? = null,

	@field:SerializedName("movies_count")
	val moviesCount: Int? = null,

	@field:SerializedName("description_raw")
	val descriptionRaw: String? = null,

	@field:SerializedName("tags")
	val tags: List<TagsItemResponse>? = null,

	@field:SerializedName("background_image")
	val backgroundImage: String? = null,

	@field:SerializedName("tba")
	val tba: Boolean? = null,

	@field:SerializedName("dominant_color")
	val dominantColor: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("reddit_description")
	val redditDescription: String? = null,

	@field:SerializedName("reddit_logo")
	val redditLogo: String? = null,

	@field:SerializedName("updated")
	val updated: String? = null,

	@field:SerializedName("reviews_count")
	val reviewsCount: Int? = null,

	@field:SerializedName("metacritic")
	val metacritic: Int? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("metacritic_url")
	val metacriticUrl: String? = null,

	@field:SerializedName("alternative_names")
	val alternativeNames: List<Any?>? = null,

	@field:SerializedName("parents_count")
	val parentsCount: Int? = null,

	@field:SerializedName("user_game")
	val userGame: Any? = null,

	@field:SerializedName("metacritic_platforms")
	val metacriticPlatforms: List<Any?>? = null,

	@field:SerializedName("creators_count")
	val creatorsCount: Int? = null,

	@field:SerializedName("ratings")
	val ratings: List<RatingsItemResponse>? = null,

	@field:SerializedName("genres")
	val genres: List<GenresItemResponse>? = null,

	@field:SerializedName("saturated_color")
	val saturatedColor: String? = null,

	@field:SerializedName("added_by_status")
	val addedByStatus: AddedByStatusResponse? = null,

	@field:SerializedName("reddit_url")
	val redditUrl: String? = null,

	@field:SerializedName("reddit_count")
	val redditCount: Int? = null,

	@field:SerializedName("parent_achievements_count")
	val parentAchievementsCount: Int? = null,

	@field:SerializedName("website")
	val website: String? = null,

	@field:SerializedName("suggestions_count")
	val suggestionsCount: Int? = null,

	@field:SerializedName("stores")
	val stores: List<StoresItemResponse>? = null,

	@field:SerializedName("additions_count")
	val additionsCount: Int? = null,

	@field:SerializedName("twitch_count")
	val twitchCount: Int? = null,

	@field:SerializedName("background_image_additional")
	val backgroundImageAdditional: String? = null,

	@field:SerializedName("esrb_rating")
	val esrbRating: Any? = null,

	@field:SerializedName("screenshots_count")
	val screenshotsCount: Int? = null,

	@field:SerializedName("reactions")
	val reactions: ReactionsResponse? = null,

	@field:SerializedName("clip")
	val clip: Any? = null
)

data class PublishersItemResponse(

	@field:SerializedName("games_count")
	val gamesCount: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("image_background")
	val imageBackground: String? = null,

	@field:SerializedName("slug")
	val slug: String? = null
)

data class DetailPlatformsItemResponse(

	@field:SerializedName("requirements")
	val requirements: RequirementsResponse? = null,

	@field:SerializedName("released_at")
	val releasedAt: String? = null,

	@field:SerializedName("platform")
	val platform: PlatformResponse? = null
)

data class DevelopersItemResponse(

	@field:SerializedName("games_count")
	val gamesCount: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("image_background")
	val imageBackground: String? = null,

	@field:SerializedName("slug")
	val slug: String? = null
)

data class RequirementsResponse(

	@field:SerializedName("minimum")
	val minimum: String? = null
)

data class ReactionsResponse(

	@field:SerializedName("8")
	val jsonMember8: Int? = null
)
