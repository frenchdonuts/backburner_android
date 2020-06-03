package io.onedonut.backburner

import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import org.junit.Test
import org.junit.Assert.*

class DatabaseTest {

    private val inMemorySQLDriver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY).apply {
        Database.Schema.create(this)
    }

    private val queries = Database(inMemorySQLDriver).noteQueries

    @Test
    fun smokeTest() {
        val empty = queries.selectAll().executeAsList()
        assertEquals(empty.size, 0)
    }

    @Test
    fun addNote() {
        val rumiQuote =
            """
                |Out beyond ideas of wrongdoing and rightdoing there is a field. 
                |I'll meet you there. When the soul lies down in that grass the 
                |world is too full to talk about.
            """.trimMargin()
        queries.insertNote(rumiQuote)

        val oneItem = queries.selectAll().executeAsList()
        assertEquals(oneItem.size, 1)
        assertEquals(oneItem[0].text, rumiQuote)
    }

    @Test
    fun searchNotes() {
        val rumiQuote =
            """
                |Out beyond ideas of wrongdoing and rightdoing there is a field. 
                |I'll meet you there. When the soul lies down in that grass the 
                |world is too full to talk about.
            """.trimMargin()
        val randomNote = "Hello world"
        queries.insertNote(rumiQuote)
        queries.insertNote(randomNote)

        val oneItem = queries.search("when").executeAsList()
        assertEquals(oneItem.size, 1)
        assertEquals(oneItem[0].text, rumiQuote)

        val twoItems = queries.search("wo*").executeAsList()
            .map { it.text }
        assertEquals(twoItems.size, 2)
        assert(twoItems.containsAll(listOf(randomNote, rumiQuote)))
    }
}