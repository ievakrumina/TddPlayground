package multicurrencymoney

import org.example.multicurrencymoney.Money
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class MultiCurrencyMoneyTest {

    /*
    [] $ 5 + 10 CHF = $10 if rate is 2:1

    [v] $5 * 2 = $10

    [v] Make amount private
    [v] Dollar side-effects?
    [] Money rounding?
    [v] equals()
    [] equals null
    [] equals other obj
    [] hashCode()
    [v] 5 CHF * 2 = 10 CHF
    [v] Dollar/Franc duplication
    [v] Common equals
    [] Common times
    [v] Compare francs vs dollars
    [v] Currency
    [v] Delete testFrancMultiplication?
     */

    @Test
    fun dollarMultiplication() {
        val five = Money.dollar(5)
        assertEquals(Money.dollar(10), five.times(2))
        assertEquals(Money.dollar(15), five.times(3))
    }

    @Test
    fun equality() {
        assertTrue(Money.dollar(5).equals(Money.dollar(5)))
        assertFalse(Money.dollar(5).equals(Money.dollar(6)))
        assertFalse(Money.franc(5).equals(Money.dollar(5)))
    }

    @Test
    fun currency() {
        assertEquals("USD", Money.dollar(1).currency())
        assertEquals("CHF", Money.franc(1).currency())
    }

}