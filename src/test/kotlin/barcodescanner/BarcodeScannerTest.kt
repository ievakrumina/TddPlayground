package barcodescanner

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class BarcodeScannerTest {
    /**
     * Create a simple app for scanning bar codes to sell products.
     *
     * Requirements
     * [v]1. Barcode ‘12345’ should display price ‘$7.25’
     *
     * [v]2. Barcode ‘23456’ should display price ‘$12.50’
     *
     * []3. Barcode ‘99999’ should display ‘Error: barcode not found’
     *
     * []4. Empty barcode should display ‘Error: empty barcode’
     *
     * []5. Introduce a concept of total command where it is possible to scan multiple items.
     * The command would display the sum of the scanned product prices
     */

    @ParameterizedTest(name = "Should display price {0}, when barcode is {1}")
    @MethodSource("source")
    fun testScanBarcode(expectedPrice: String, barcode: String) {
        assertEquals(expectedPrice, scanBarcode(barcode = barcode))
    }

    companion object {
        @JvmStatic
        fun source() = listOf(
            Arguments.of("$7.25", "12345"),
            Arguments.of("$12.50", "23456"),
        )
    }

    private fun scanBarcode(barcode: String): String {
        return validBarcodePriceMap[barcode].orEmpty()
    }

    val validBarcodePriceMap = mapOf(
        Pair("12345", "$7.25"),
        Pair("23456", "$12.50")
    )
}