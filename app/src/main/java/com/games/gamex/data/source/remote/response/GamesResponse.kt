package com.games.gamex.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class GamesResponse(

	@field:SerializedName("next")
	val next: String? = null,

	@field:SerializedName("nofollow")
	val noFollow: Boolean? = null,

	@field:SerializedName("noindex")
	val noIndex: Boolean? = null,

	@field:SerializedName("nofollow_collections")
	val noFollowCollections: List<String>? = null,

	@field:SerializedName("previous")
	val previous: Any? = null,

	@field:SerializedName("count")
	val count: Int? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("seo_h1")
	val seoH1: String? = null,

	@field:SerializedName("filters")
	val filters: FiltersResponse? = null,

	@field:SerializedName("seo_title")
	val seoTitle: String? = null,

	@field:SerializedName("seo_description")
	val seoDescription: String? = null,

	@field:SerializedName("results")
	val results: List<GamesResultItemResponse>? = null,

	@field:SerializedName("seo_keywords")
	val seoKeywords: String? = null
)

data class AddedByStatusResponse(

	@field:SerializedName("owned")
	val owned: Int? = null,

	@field:SerializedName("beaten")
	val beaten: Int? = null,

	@field:SerializedName("dropped")
	val dropped: Int? = null,

	@field:SerializedName("yet")
	val yet: Int? = null,

	@field:SerializedName("playing")
	val playing: Int? = null,

	@field:SerializedName("toplay")
	val toPlay: Int? = null
)

data class GenresItemResponse(

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

data class EsrbRatingResponse(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("slug")
	val slug: String? = null
)

data class RatingsItemResponse(

	@field:SerializedName("count")
	val count: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("percent")
	val percent: Double? = null
)

data class FiltersResponse(

	@field:SerializedName("years")
	val years: List<YearsItemResponse>? = null
)

data class ParentPlatformsItemResponse(

	@field:SerializedName("platform")
	val platform: PlatformResponse? = null
)

data class ShortScreenshotsItemResponse(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)

data class StoresItemResponse(

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("store")
	val store: StoreResponse? = null
)

data class TagsItemResponse(

	@field:SerializedName("games_count")
	val gamesCount: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("language")
	val language: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("image_background")
	val imageBackground: String? = null,

	@field:SerializedName("slug")
	val slug: String? = null
)

data class PlatformsItemResponse(

	@field:SerializedName("requirements_ru")
	val requirementsRu: Any? = null,

	@field:SerializedName("requirements_en")
	val requirementsEn: Any? = null,

	@field:SerializedName("released_at")
	val releasedAt: String? = null,

	@field:SerializedName("platform")
	val platform: PlatformResponse? = null
)

data class GamesResultItemResponse(

	@field:SerializedName("added")
	val added: Int? = null,

	@field:SerializedName("rating")
	val rating: Any? = null,

	@field:SerializedName("metacritic")
	val metacritic: Int? = null,

	@field:SerializedName("playtime")
	val playtime: Int? = null,

	@field:SerializedName("short_screenshots")
	val shortScreenshots: List<ShortScreenshotsItemResponse>? = null,

	@field:SerializedName("platforms")
	val platforms: List<PlatformsItemResponse>? = null,

	@field:SerializedName("user_game")
	val userGame: Any? = null,

	@field:SerializedName("rating_top")
	val ratingTop: Int? = null,

	@field:SerializedName("reviews_text_count")
	val reviewsTextCount: Int? = null,

	@field:SerializedName("ratings")
	val ratings: List<RatingsItemResponse>? = null,

	@field:SerializedName("genres")
	val genres: List<GenresItemResponse>? = null,

	@field:SerializedName("saturated_color")
	val saturatedColor: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("added_by_status")
	val addedByStatus: AddedByStatusResponse? = null,

	@field:SerializedName("parent_platforms")
	val parentPlatforms: List<ParentPlatformsItemResponse>? = null,

	@field:SerializedName("ratings_count")
	val ratingsCount: Int? = null,

	@field:SerializedName("slug")
	val slug: String? = null,

	@field:SerializedName("released")
	val released: String? = null,

	@field:SerializedName("suggestions_count")
	val suggestionsCount: Int? = null,

	@field:SerializedName("stores")
	val stores: List<StoresItemResponse>? = null,

	@field:SerializedName("tags")
	val tags: List<TagsItemResponse>? = null,

	@field:SerializedName("background_image")
	val backgroundImage: String? = null,

	@field:SerializedName("tba")
	val tba: Boolean? = null,

	@field:SerializedName("dominant_color")
	val dominantColor: String? = null,

	@field:SerializedName("esrb_rating")
	val esrbRating: EsrbRatingResponse? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("updated")
	val updated: String? = null,

	@field:SerializedName("clip")
	val clip: Any? = null,

	@field:SerializedName("reviews_count")
	val reviewsCount: Int? = null
)

data class StoreResponse(

	@field:SerializedName("games_count")
	val gamesCount: Int? = null,

	@field:SerializedName("domain")
	val domain: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("image_background")
	val imageBackground: String? = null,

	@field:SerializedName("slug")
	val slug: String? = null
)

data class YearsItemResponse(

	@field:SerializedName("filter")
	val filter: String? = null,

	@field:SerializedName("nofollow")
	val nofollow: Boolean? = null,

	@field:SerializedName("decade")
	val decade: Int? = null,

	@field:SerializedName("count")
	val count: Int? = null,

	@field:SerializedName("from")
	val from: Int? = null,

	@field:SerializedName("to")
	val to: Int? = null,

	@field:SerializedName("years")
	val years: List<YearsItemResponse>? = null,

	@field:SerializedName("year")
	val year: Int? = null
)

data class PlatformResponse(

	@field:SerializedName("image")
	val image: Any? = null,

	@field:SerializedName("games_count")
	val gamesCount: Int? = null,

	@field:SerializedName("year_end")
	val yearEnd: Any? = null,

	@field:SerializedName("year_start")
	val yearStart: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("image_background")
	val imageBackground: String? = null,

	@field:SerializedName("slug")
	val slug: String? = null
)
