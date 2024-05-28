package org.cyk.base.model.type
enum class ImageSortType(val code: Int) {
    UNKNOWN(0),
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9);

    companion object {
        fun getType(code: Int): ImageSortType {
            return entries.firstOrNull { it.code == code } ?: UNKNOWN
        }
    }

}
