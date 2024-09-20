package passwordvalidation

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class PasswordValidationTest {

    /**
     * Create a function that can be used as a validator for the password field of a user registration form.
     * The validation function takes a string as an input and returns a validation result.
     * The validation result should contain a boolean indicating if the password is valid or not,
     * and also a field with the possible validation errors.
     *
     * Requirements
     * []1. The password must be at least 8 characters long. If it is not met,
     * then the following error message should be returned: “Password must be at least 8 characters”
     *
     * []2. The password must contain at least 2 numbers. If it is not met,
     * then the following error message should be returned: “The password must contain at least 2 numbers”
     *
     * []3. The validation function should handle multiple validation errors.
     * For example, “somepassword” should an error message:
     * “Password must be at least 8 characters\nThe password must contain at least 2 numbers”
     *
     * [] 4. The password must contain at least one capital letter. If it is not met,
     * then the following error message should be returned: “password must contain at least one capital letter”
     *
     * []5. The password must contain at least one special character. If it is not met,
     * then the following error message should be returned: “password must contain at least one special character”
     */

    @Test
    fun shouldReturnSuccess_whenPasswordIsAtLeastEightCharacters() {
        assertEquals(PasswordValidator.Valid, validatePassword("Password@123"))
    }

    @ParameterizedTest(name = "Should return error {0}, when input is {1}")
    @MethodSource("sourceWithError")
    fun validatePassword( expectedResult: String, password: String) {
        val actualResult = validatePassword(password)
        val expectedResult = PasswordValidator.Invalid(expectedResult)
        assertEquals(expectedResult, actualResult)
    }

    companion object {
        @JvmStatic
        fun sourceWithError() = listOf(
            Arguments.of("Password must be at least 8 characters", "1234567")
        )
    }

    private fun validatePassword(password: String): PasswordValidator {
        return when {
            password.length >= 8 -> PasswordValidator.Valid
            else -> PasswordValidator.Invalid("Password must be at least 8 characters")
        }
    }

}

sealed class PasswordValidator{
    data object Valid: PasswordValidator()
    data class Invalid(val error: String? = null): PasswordValidator()
}


