package io.onedonut.backburner.model


data class Item(val id: String,
                val text: String) {
    companion object {
        fun from(meditation: io.onedonut.backburner.Item): Item {
            return Item(
                meditation.id.toString(),
                meditation.text
            )
        }
    }
}