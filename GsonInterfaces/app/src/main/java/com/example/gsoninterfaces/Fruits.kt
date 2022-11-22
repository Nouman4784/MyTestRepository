package com.example.gsoninterfaces

interface Fruit {
    val isSweet: Boolean
    val color: String
}

data class Lemon(
    val acidity: Int,
    override val isSweet: Boolean = false,
    override val color: String = "yellow"
) : Fruit {
    override fun toString(): String =
        "Lemon: acidity = $acidity , isSweet = $isSweet , color = $color"
}

data class Watermelon(
    val isSeedless: Boolean,
    override val isSweet: Boolean = true,
    override val color: String
) : Fruit {
    override fun toString(): String =
        "Watermelon: isSeedless = $isSeedless , isSweet = $isSweet, color = $color"
}
