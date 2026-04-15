package com.example.recipe_app.screens.detailsScreen.dto

import com.example.recipe_app.screens.homeScreen.dto.Meal

data class RecipeIngredient(
    val name: String,
    val measure: String,
    val image : String
)

fun Meal.getIngredients() : List<RecipeIngredient> {

    val imageUrl = "https://www.themealdb.com/images/ingredients/"
    val ingredients = listOf(
        strIngredient1, strIngredient2, strIngredient3, strIngredient4, strIngredient5,
        strIngredient6, strIngredient7, strIngredient8, strIngredient9, strIngredient10,
        strIngredient11, strIngredient12, strIngredient13, strIngredient14, strIngredient15,
        strIngredient16, strIngredient17, strIngredient18, strIngredient19, strIngredient20
    ).filter { !it.isNullOrEmpty()}

    val measures = listOf(
        strMeasure1, strMeasure2, strMeasure3, strMeasure4, strMeasure5,
        strMeasure6, strMeasure7, strMeasure8, strMeasure9, strMeasure10,
        strMeasure11, strMeasure12, strMeasure13, strMeasure14, strMeasure15,
        strMeasure16, strMeasure17, strMeasure18, strMeasure19, strMeasure20
    ).filter { !it.isNullOrEmpty()}

    return ingredients.zip(measures){ i,m ->
        if(!i.isNullOrEmpty() && !m.isNullOrEmpty()){
        val image =  "$imageUrl$i-small.png"
        RecipeIngredient(i,m, image.replace(' ','_'))
        }else null
    }.filterNotNull()
}
