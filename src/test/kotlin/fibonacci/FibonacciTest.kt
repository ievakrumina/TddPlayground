package fibonacci

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class FibonacciTest {

    @Test
    fun fibonacci() {
        val testCases = listOf(
            Pair(0,0),
            Pair(1,1),
            Pair(2,1),
            Pair(3,2),
        )
        testCases.forEach {
            assertEquals(it.second, fib(it.first))
        }
    }

    private fun fib(i: Int): Int {
        return when (i) {
            0 -> 0
            1 -> 1
            else -> fib(i - 1) + fib(i -2)
        }
    }
}