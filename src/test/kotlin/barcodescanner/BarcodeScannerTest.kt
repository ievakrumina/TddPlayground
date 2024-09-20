package barcodescanner

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
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
     * [v]3. Barcode ‘99999’ should display ‘Error: barcode not found’
     *
     * [v]4. Empty barcode should display ‘Error: empty barcode’
     *
     * [v]5. Introduce a concept of total command where it is possible to scan multiple items.
     * The command would display the sum of the scanned product prices
     */

    @ParameterizedTest(name = "Should display price {0}, when barcode is {1}")
    @MethodSource("source")
    fun testScanBarcode(expectedPrice: String, barcode: String) {
        assertEquals(expectedPrice, scanBarcode(barcode = barcode))
    }

    @ParameterizedTest(name = "Should display total {0}, when these barcodes are scanned {1}")
    @MethodSource("totalSource")
    fun testScanMultipleBarcodes(expectedTotal: String, barcodes: List<String>) {
        assertEquals(expectedTotal, scanMultipleBarcodes(barcodes))
    }

    @Test
    fun convertPriceToNumber() {
        assertEquals(7.25, priceToNumber("$7.25"))
        assertEquals(7.20, priceToNumber("$7.20"))
        assertEquals(0.0, priceToNumber("A"))
    }

    @Test
    fun formatNumberToPrice() {
        assertEquals("$7.25", formatToPrice(7.25))
        assertEquals("$7.00", formatToPrice(7.0))
    }

    companion object {
        @JvmStatic
        fun source() = listOf(
            Arguments.of("$7.25", "12345"),
            Arguments.of("$12.50", "23456"),
            Arguments.of("Error: barcode not found", "999999"),
            Arguments.of("Error: empty barcode", ""),
        )

        @JvmStatic
        fun totalSource() = listOf(
            Arguments.of("$7.25", listOf("12345")),
            Arguments.of("$19.75", listOf("12345", "23456")),
        )
    }

    private fun scanMultipleBarcodes(barcodes: List<String>): String {
        val total = barcodes.map { scanBarcode(it) }.sumOf { priceToNumber(it) }
        return formatToPrice(total)
    }

    private fun scanBarcode(barcode: String): String {
        return validBarcodePriceMap.getOrElse(barcode) {
            if (barcode.isBlank()) {
                "Error: empty barcode"
            } else {
                "Error: barcode not found"
            }
        }
    }

    private fun formatToPrice(number: Double): String {
        return "$${"%.2f".format(number)}"
    }

    private fun priceToNumber(price: String): Double {
        return try {
            price.removePrefix("$").toDouble()
        } catch (e: NumberFormatException) {
            0.0
        }
    }

    val validBarcodePriceMap = mapOf(
        Pair("12345", "$7.25"),
        Pair("23456", "$12.50")
    )
}