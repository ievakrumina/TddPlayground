package fibonacci

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.example.fibonacci.Fibonacci

class FibonacciTest {

    @Test
    fun fibonacci() {
        val fib = Fibonacci()
        val testCases = listOf(
            Pair(0,0),
            Pair(1,1),
            Pair(2,1),
            Pair(3, 2),
        )
        testCases.forEach {
            assertEquals(it.second, fib.f(it.first))
        }
    }
}