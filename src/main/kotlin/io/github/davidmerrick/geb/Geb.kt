package io.github.davidmerrick.geb

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import java.io.File

private const val WEEK_COLUMN = "week"
private const val START_PAGE_COLUMN = "start_page"
private const val CHAPTER_COLUMN = "chapter_name"
private const val LAST_PAGE = 746

class Geb {

    private val reading: Map<Int, WeekReading>

    init {
        val file = File(this.javaClass.getResource("/weekly_reading.csv").path)
        val rows = csvReader().readAllWithHeader(file)

        // Get available weeks
        val weekNums = rows.map { it[WEEK_COLUMN]!!.toInt() }.toSet()

        reading = weekNums.sorted().associateWith {
            WeekReading(
                getChapters(rows, it),
                getStartPage(rows, it),
                getEndPage(rows, it)
            )
        }
    }

    private fun getNextWeekNum(rows: List<Map<String, String>>, weekNum: Int): Int {
        return rows.filter { it[WEEK_COLUMN]!!.toInt() > weekNum }
            .sortedBy { it[WEEK_COLUMN]!!.toInt() }
            .minByOrNull { it[WEEK_COLUMN]!!.toInt() }!![WEEK_COLUMN]!!.toInt()
    }

    private fun getEndPage(rows: List<Map<String, String>>, weekNum: Int): Int {
        if(weekNum == rows.maxByOrNull { it[WEEK_COLUMN]!!.toInt() }!![WEEK_COLUMN]!!.toInt()){
            return LAST_PAGE
        }

        // end page of current week is start page of next - 1
        val nextWeekNum = getNextWeekNum(rows, weekNum)
        return getStartPage(rows, nextWeekNum) - 1
    }

    private fun getStartPage(rows: List<Map<String, String>>, weekNum: Int): Int {
        return rows.filter { it[WEEK_COLUMN]!!.toInt() == weekNum }
            .minByOrNull { it[START_PAGE_COLUMN]!!.toInt() }!![START_PAGE_COLUMN]!!
            .toInt()
    }

    private fun getChapters(rows: List<Map<String, String>>, weekNum: Int): List<String> {
        return rows.filter { it[WEEK_COLUMN]!!.toInt() == weekNum }
            .map { it[CHAPTER_COLUMN]!!.trim() }
            .toList()
    }

    fun getReadingForWeek(weekNum: Int): WeekReading {
        return reading[weekNum]!!
    }
}

/**
 * Outputs the reading, given what week it is
 */
fun main() {
    val geb = Geb()
    val reading = geb.getReadingForWeek(18)
    val formattedOutput = ReadingPrinter.printReading(reading)
    println(formattedOutput)
}
