package com.info_turrim.polandnews.utils

import com.info_turrim.polandnews.R


private const val BREAKING_NEWS_ID = 1
private const val BUSINESS_ID = 2
private const val EDUCATION_ID = 15
private const val HEALTH_ID = 8
private const val LATEST_ID = 10
private const val OPINION_ID = 12
private const val POLITICS_ID = 6
private const val SCIENCE_ID = 4
private const val SPORT_ID = 7
private const val TECHNOLOGY_ID = 3
private const val TRAVEL_ID = 9
private const val UK_ID = 14
private const val WEATHER_ID = 5
private const val WORLD_ID = 13
private const val COOKING_ID = 19
private const val ENTERTAINMENT_ID = 17
private const val GAMING_ID = 18
private const val POLAND_ID = 16


fun getCategoryIcon(id: Int): Int {
    return when (id) {
        BREAKING_NEWS_ID -> R.drawable.ic_section_category_breaking_news
        BUSINESS_ID -> R.drawable.ic_section_category_business
        EDUCATION_ID -> R.drawable.ic_section_category_education
        HEALTH_ID -> R.drawable.ic_section_category_health
        LATEST_ID -> R.drawable.ic_section_category_latest
        OPINION_ID -> R.drawable.ic_section_category_opinion
        POLITICS_ID -> R.drawable.ic_section_category_politics
        SCIENCE_ID -> R.drawable.ic_section_category_science
        SPORT_ID -> R.drawable.ic_section_category_sport
        TECHNOLOGY_ID -> R.drawable.ic_section_category_technology
        TRAVEL_ID -> R.drawable.ic_section_category_travel
        WEATHER_ID -> R.drawable.ic_section_category_weather
        POLAND_ID -> R.drawable.ic_section_category_pl
        COOKING_ID -> R.drawable.ic_section_category_cooking
        GAMING_ID -> R.drawable.ic_section_category_gaming
        ENTERTAINMENT_ID -> R.drawable.ic_section_category_entertainment
        else -> R.drawable.ic_section_category_world
    }
}