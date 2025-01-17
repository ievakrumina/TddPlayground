package passwordvalidation

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

const val PASSWORD_MIN_LENGTH = 8
const val PASSWORD_MIN_NUMBER_COUNT = 2
const val PASSWORD_MIN_CAPITAL_LETTER_COUNT = 1

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
     * [v]3. The validation function should handle multiple validation errors.
     * For example, “somepassword” should an error message:
     * “Password must be at least 8 characters\nThe password must contain at least 2 numbers”
     *
     * [v] 4. The password must contain at least one capital letter. If it is not met,
     * then the following error message should be returned: “password must contain at least one capital letter”
     *
     * [v]5. The password must contain at least one special character. If it is not met,
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

    private fun getCount(input: String, regex: Regex): Int {
        return input.count { it.toString().matches(regex) }
    }

    companion object {
        @JvmStatic
        fun sourceWithError() = listOf(
            Arguments.of("Password must be at least 8 characters", "A2345(7"),
            Arguments.of("The password must contain at least 2 numbers", "Abcdef$7"),
            Arguments.of("Password must be at least 8 characters\nThe password must contain at least 2 numbers", "Passw#r"),
            Arguments.of("The password must contain at least one capital letter", "abcdefg78#"),
            Arguments.of("The password must contain at least one special character", "Abcdefg78"),
        )
    }

    private fun validatePassword(password: String): PasswordValidator {
        var errors = ""
        val divider = "\n"
        if (password.length < PASSWORD_MIN_LENGTH) errors += "Password must be at least 8 characters"
        if (getCount(password, "[0-9]".toRegex()) < PASSWORD_MIN_NUMBER_COUNT) {
            errors = addStringIfNotBlank(errors, divider)
            errors += "The password must contain at least 2 numbers"
        }
        if (getCount(password, "[A-Z]".toRegex()) < PASSWORD_MIN_CAPITAL_LETTER_COUNT) {
            errors = addStringIfNotBlank(errors, divider)
            errors += "The password must contain at least one capital letter"
        }
        if (getCount(password, "[^A-Za-z0-9]".toRegex()) < PASSWORD_MIN_CAPITAL_LETTER_COUNT) {
            errors = addStringIfNotBlank(errors, divider)
            errors += "The password must contain at least one special character"
        }
        return if (errors.isBlank()) PasswordValidator.Valid else PasswordValidator.Invalid(errors)
    }

    private fun addStringIfNotBlank(input: String, stringToAdd: String): String {
        var str = input
        if (str.isNotBlank()) str += stringToAdd
        return str
    }
}

sealed class PasswordValidator{
    data object Valid: PasswordValidator()
    data class Invalid(val error: String? = null): PasswordValidator()
}


