package io.github.davidmerrick.geb

object ReadingPrinter {
    fun printReading(reading: WeekReading): String {
        val joinedChapters = reading.chapters.joinToString(" and ") { "\"$it\"" }
        return """
            $joinedChapters (pg ${reading.startPage}-${reading.endPage}; ${reading.totalPages()} pages)
        """.trimIndent()
    }
}