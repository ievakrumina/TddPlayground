package passwordvalidation

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

const val PASSWORD_MIN_LENGTH = 8
const val PASSWORD_MIN_NUMBER_COUNT = 2

class PasswordValidationTest {

    /**
     * Create a function that can be used as a validator for the password field of a user registration form.
     * The validation function takes a string as an input and returns a validation result.
     * The validation result should contain a boolean indicating if the password is valid or not,
     * and also a field with the possible validation errors.
     *
     * Requirements
     * [v]1. The password must be at least 8 characters long. If it is not met,
     * then the following error message should be returned: “Password must be at least 8 characters”
     *
     * [v]2. The password must contain at least 2 numbers. If it is not met,
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
    fun validatePassword( expectedError: String, password: String) {
        val actualResult = validatePassword(password)
        val expectedResult = PasswordValidator.Invalid(expectedError)
        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun stringContainsNumbers() {
        assertEquals(0, getNumberCount("abc"))
        assertEquals(1, getNumberCount("abc1"))
        assertEquals(2, getNumberCount("2bc1"))
    }

    private fun getNumberCount(input: String): Int {
        return input.count { it.toString().matches("[0-9]".toRegex()) }
    }

    companion object {
        @JvmStatic
        fun sourceWithError() = listOf(
            Arguments.of("Password must be at least 8 characters", "1234567"),
            Arguments.of("The password must contain at least 2 numbers", "Abcdefg7"),
            Arguments.of("Password must be at least 8 characters\nThe password must contain at least 2 numbers", "passwor"),
        )
    }

    private fun validatePassword(password: String): PasswordValidator {
        return when {
            getNumberCount(password) < PASSWORD_MIN_NUMBER_COUNT
                    && password.length < PASSWORD_MIN_LENGTH -> {
                PasswordValidator.Invalid("Password must be at least 8 characters\nThe password must contain at least 2 numbers")
                    }
            getNumberCount(password) < PASSWORD_MIN_NUMBER_COUNT -> PasswordValidator.Invalid("The password must contain at least 2 numbers")
            password.length >= PASSWORD_MIN_LENGTH -> PasswordValidator.Valid
            password.length < PASSWORD_MIN_LENGTH -> PasswordValidator.Invalid("Password must be at least 8 characters")
            else -> PasswordValidator.Invalid()
        }
    }

}

sealed class PasswordValidator{
    data object Valid: PasswordValidator()
    data class Invalid(val error: String? = null): PasswordValidator()
}


