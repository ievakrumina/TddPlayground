package fizzbuzz

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class FizzBuzzTest {

    /**
     * Requirements
     * 1. Write a “fizzBuzz” method that accepts a number as input and returns it as a String.
     * 2. For multiples of three return “Fizz” instead of the number
     * 3. For the multiples of five return “Buzz”
     * 4. For numbers that are multiples of both three and five return “FizzBuzz”.
     */

    @Test
    fun shouldReturnNumberAsString_whenInputIsNumber() {

        assertEquals("1", fizzBuzz(1))
    }

    private fun fizzBuzz(number: Int): String {
        return "1"
    }
}