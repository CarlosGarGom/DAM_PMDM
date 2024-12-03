package com.example.ej02_examen_carlos

class SuperHeroProvider {
    companion object{
        val superHeroList = listOf<SuperHero>(
            SuperHero(
                "Carlos",
                "",
                "Garcia Gomez",
                "https://cursokotlin.com/wp-content/uploads/2017/07/spiderman.jpg"
            ),
            SuperHero(
                "Abel",
                "",
                "Blanco",
                "https://cursokotlin.com/wp-content/uploads/2017/07/batman.jpg"
            ),
            SuperHero(
                "Ismael",
                "",
                "",
                "https://cursokotlin.com/wp-content/uploads/2017/07/thor.jpg"
            ),
            SuperHero(
                "Daniel",
                "DC",
                "Bodero",
                "https://cursokotlin.com/wp-content/uploads/2017/07/flash.png"
            ),

            )
    }
}