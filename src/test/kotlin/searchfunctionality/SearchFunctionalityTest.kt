package searchfunctionality

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class SearchFunctionalityTest {

    /**
     * Implement a city search functionality.
     * The function takes a string (search text) as input and returns the found cities which corresponds to the search text.
     *
     * Prerequisites
     * Create a collection of strings that will act as a database for the city names.
     * City names: Paris, Budapest, Skopje, Rotterdam, Valencia, Vancouver, Amsterdam, Vienna,
     * , New York City, London, Bangkok, Hong Kong, Dubai, Rome, Istanbul
     *
     * Requirements
     * [v]1. If the search text is fewer than 2 characters, then should return no results.
     * (It is an optimization feature of the search functionality.)
     *
     * [v]2. If the search text is equal to or more than 2 characters, then it should return
     * all the city names starting with the exact search text.
     * For example for search text “Va”, the function should return Valencia and Vancouver
     *
     * [v]3. The search functionality should be case insensitive
     *
     * [v]4. The search functionality should work also when the search text is just a part of a city name
     * For example “ape” should return “Budapest” city
     *
     * []5. If the search text is a “*” (asterisk), then it should return all the city names.
     */

    @ParameterizedTest(name = "Should return {0}, when search text is {1}")
    @MethodSource("source")
    fun search(expectedResult: List<String>, searchText: String) {
        assertEquals(expectedResult, search(searchText))
    }

    companion object {
        @JvmStatic
        fun source() = listOf(
            Arguments.of(listOf("Rome"), "Rome"),
            Arguments.of(emptyList<String>(), "r"),
            Arguments.of(emptyList<String>(), ""),
            Arguments.of(listOf("Valencia", "Vancouver"), "Va"),
            Arguments.of(listOf("Valencia", "Vancouver"), "va"),
            Arguments.of(listOf("Budapest"), "ape"),
        )
    }


    val cityNames = listOf(
        "Paris", "Budapest", "Skopje", "Rotterdam", "Valencia", "Vancouver", "Amsterdam", "Vienna", "New York City",
        "London", "Bangkok", "Hong Kong", "Dubai", "Rome", "Istanbul"
    )

    private fun search(text: String): List<String> {
        return if (text.length < 2) {
            emptyList()
        } else {
            cityNames.filter { it.lowercase().contains(text.lowercase()) }
        }
    }
}