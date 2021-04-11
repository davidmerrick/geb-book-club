package io.github.davidmerrick.geb

import io.kotlintest.shouldBe
import org.junit.jupiter.api.Test

class GebTest {

    @Test
    fun `Week 11 reading`(){
        val geb = Geb()
        val reading = geb.getReadingForWeek(11)
        reading.startPage shouldBe 366
        reading.endPage shouldBe 390
        reading.totalPages() shouldBe 25
    }

    @Test
    fun `Week 16 reading`(){
        val geb = Geb()
        val reading = geb.getReadingForWeek(16)
        reading.startPage shouldBe 549
        reading.endPage shouldBe 585
        reading.chapters.size shouldBe 2
    }

    @Test
    fun `Week 20 reading`(){
        val geb = Geb()
        val reading = geb.getReadingForWeek(20)
        reading.startPage shouldBe 720
        reading.endPage shouldBe 745
        reading.chapters.size shouldBe 2
    }
}