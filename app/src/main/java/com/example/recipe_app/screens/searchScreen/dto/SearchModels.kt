package com.example.recipe_app.screens.searchScreen.dto

data class Country(
    val strArea: String
)

data class CountryResponse(
    val meals: List<Country>
)

data class Ingredient(
    val idIngredient: String,
    val strIngredient: String
)

data class IngredientResponse(
    val meals: List<Ingredient>
)

fun getFlagEmoji(country: String): String {
    return when (country) {
        "American"   -> "🇺🇸"
        "British"    -> "🇬🇧"
        "Canadian"   -> "🇨🇦"
        "Chinese"    -> "🇨🇳"
        "Croatian"   -> "🇭🇷"
        "Dutch"      -> "🇳🇱"
        "Egyptian"   -> "🇪🇬"
        "Filipino"   -> "🇵🇭"
        "French"     -> "🇫🇷"
        "Greek"      -> "🇬🇷"
        "Indian"     -> "🇮🇳"
        "Irish"      -> "🇮🇪"
        "Italian"    -> "🇮🇹"
        "Jamaican"   -> "🇯🇲"
        "Japanese"   -> "🇯🇵"
        "Kenyan"     -> "🇰🇪"
        "Malaysian"  -> "🇲🇾"
        "Mexican"    -> "🇲🇽"
        "Moroccan"   -> "🇲🇦"
        "Polish"     -> "🇵🇱"
        "Portuguese" -> "🇵🇹"
        "Russian"    -> "🇷🇺"
        "Spanish"    -> "🇪🇸"
        "Thai"       -> "🇹🇭"
        "Tunisian"   -> "🇹🇳"
        "Turkish"    -> "🇹🇷"
        "Ukrainian"  -> "🇺🇦"
        "Vietnamese" -> "🇻🇳"
        else         -> "🌍"
    }
}