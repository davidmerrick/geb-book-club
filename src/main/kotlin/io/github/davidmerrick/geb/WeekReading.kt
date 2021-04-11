package io.github.davidmerrick.geb

data class WeekReading(
    val chapters: List<String>,
    val startPage: Int,
    val endPage: Int
){
    fun totalPages(): Int = endPage - startPage + 1
}
