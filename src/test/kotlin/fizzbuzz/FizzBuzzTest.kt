package fizzbuzz

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class FizzBuzzTest {

    /**
     * Requirements
     * [v] 1. Write a “fizzBuzz” method that accepts a number as input and returns it as a String.
     * [v] 2. For multiples of three return “Fizz” instead of the number
     * [] 3. For the multiples of five return “Buzz”
     * [] 4. For numbers that are multiples of both three and five return “FizzBuzz”.
     */

    @Test
    fun shouldReturnNumberAsString_whenInputIsNumber() {
        assertEquals("0", fizzBuzz(0))
        assertEquals("1", fizzBuzz(1))
        assertEquals("2", fizzBuzz(2))
    }

    @Test
    fun shouldReturnFizz_whenInputMultipleOfThree() {
        assertEquals("Fizz", fizzBuzz(3))
        assertEquals("Fizz", fizzBuzz(3*2))
        assertEquals("Fizz", fizzBuzz(3*3))
    }

    @Test
    fun shouldReturnBuzz_whenInputIsMultipleOfFive() {
        assertEquals("Buzz", fizzBuzz(5))
    }

    private fun fizzBuzz(number: Int): String {
        return when {
            number == 5 -> "Buzz"
            number % 3 == 0 && number != 0 -> "Fizz"
            else -> number.toString()
        }
    }
}