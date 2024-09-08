package stringcalculator

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.MethodSource

class StringCalculatorTest {

    /**
     * Create a simple calculator that takes a String and returns a integer
     * Signature (pseudo code):
     * int Add(string numbers)
     *
     * Requirements:
     * [v] 1. The method can take up to two numbers, separated by commas, and will return their sum as a result.
     * So the inputs can be: “”, “1”, “1,2”. For an empty string, it will return 0.
     *
     * [v] 2. Allow the add method to handle an unknown number of arguments
     *
     * 3. Allow the add method to handle newlines as separators, instead of comas
     * [v]“1,2\n3” should return “6”
     * [v]“2,\n3” is invalid, but no need to clarify it with the program
     *
     * [v]4. Add validation to not to allow a separator at the end
     * For example “1,2,” should return an error (or throw an exception)
     *
     * 5. Allow the add method to handle different delimiters
     *
     * To change the delimiter, the beginning of the input will contain a separate line that looks like this:
     * //[delimiter]\n[numbers]
     * [v]“//;\n1;3” should return “4”
     * [v]“//|\n1|2|3” should return “6”
     * [v]“//sep\n2sep5” should return “7”
     * [v]“//|\n1|2,3” is invalid and should return an error (or throw an exception) with the message “‘|’ expected
     * but ‘,’ found at position 3.”
     * [v]Should make delimiter literal if one of regex escape chars
     */

    @ParameterizedTest(name = "Should return {0}, when input is {1}")
    @MethodSource("source")
    fun testAdd(expectedResult: Int, input: String) {
        assertEquals(expectedResult, add(number = input))
    }

    @ParameterizedTest(name = "Should throw error {0}, when input is {1}")
    @MethodSource("sourceWithError")
    fun testAddReturnsError(expectedResult: String, input: String) {

        val exception = assertThrows<Throwable> {
            add(input)
        }
        assertEquals(expectedResult, exception.message)
    }

    @Test
    fun shouldReturnSpecificDelimiter_whenDefinedInString() {
        assertEquals(",", getDelimiter(""))
        assertEquals(",", getDelimiter("1"))
        assertEquals(",", getDelimiter(","))
        assertEquals(";", getDelimiter("//;\n"))
        assertEquals("sep", getDelimiter("//sep\n2sep5"))
    }

    private fun getDelimiter(input: String): String {
        return when {
            input.startsWith("//") -> input.substringBefore("\n").substringAfter("//")
            else -> ","
        }
    }

    companion object {
        @JvmStatic
        fun source() = listOf(
            Arguments.of(0, ""),
            Arguments.of(1, "1"),
            Arguments.of(2, "2"),
            Arguments.of(3, "1,2"),
            Arguments.of(4, "2,2"),
            Arguments.of(5, "1,1,1,1,1"),
            Arguments.of(6, "1,2\n3"),
            Arguments.of(4, "//;\n1;3"),
            Arguments.of(6, "//|\n1|2|3"),
            Arguments.of(7, "//sep\n2sep5"),
            Arguments.of(7, "//?\n2?5"),
        )

        @JvmStatic
        fun sourceWithError() = listOf(
            Arguments.of("Not a number", "1,\n2"),
            Arguments.of("Not a number", "1,2,"),
            Arguments.of("'|' expected but ',' found at position 3.", "//|\n1|2,3"),
            Arguments.of("'|' expected but ',' found at position 3.", "//|\n1|23,3"),
        )
    }

    //"\\.\\*\\+\\?\\^\\$\\{\\}\\(\\)\\[\\]\\\\\\|"
    private fun add(number: String): Int {
        return when {
            number == "" -> 0
            else -> {
                val delimiter = getDelimiter(input = number)
                val stripedNumber = number.removePrefix("//$delimiter\n")
                val adjustedDelimiter = if (delimiter.matches("(\\.|\\*|\\+|\\?|\\^|\\$|\\{|\\(|\\)|\\[|\\\\|\\|)".toRegex())) "\\$delimiter" else delimiter
                val inputToList = stripedNumber.split("(${adjustedDelimiter}|\\n)".toRegex())
                inputToList
                    .mapIndexed { index, s ->
                        try {
                            s.toInt()
                        } catch (e: NumberFormatException) {
                            if (s.contains("[0-9]".toRegex())) {
                                val findChar = Regex("[^0-9]").find(s)?.value
                                throw Throwable("'$delimiter' expected but '$findChar' found at position ${index + 2}.")
                            }
                            throw Throwable("Not a number")
                        }
                    }.sum()
            }
        }
    }
}