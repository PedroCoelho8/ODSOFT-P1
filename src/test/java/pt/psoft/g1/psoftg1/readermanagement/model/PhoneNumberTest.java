package pt.psoft.g1.psoftg1.readermanagement.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PhoneNumberTest {

    @Test
    void ensureValidMobilePhoneNumberIsAccepted() {
        // Act & Assert
        assertDoesNotThrow(() -> new PhoneNumber("912345678"), "Should not throw an exception for a valid mobile phone number");
    }

    @Test
    void ensureValidFixedPhoneNumberIsAccepted() {
        // Act & Assert
        assertDoesNotThrow(() -> new PhoneNumber("212345678"), "Should not throw an exception for a valid fixed phone number");
    }

    @Test
    void ensureInvalidPhoneNumberThrowsException() {
        // Arrange
        String[] invalidNumbers = {
                "12345678",     // Too short
                "00123456789",  // Too long
                "abcdefghij",   // Non-numeric
                "512345678",    // Invalid start digit
                "91234567",     // Too short by one digit
                "21234567"      // Too short by one digit
        };

        // Act & Assert
        for (String number : invalidNumbers) {
            assertThrows(IllegalArgumentException.class, () -> new PhoneNumber(number),
                    "Should throw an exception for invalid phone number: " + number);
        }
    }

    @Test
    void ensureCorrectStringRepresentation() {
        // Arrange
        PhoneNumber mobilePhoneNumber = new PhoneNumber("912345678");
        PhoneNumber fixedPhoneNumber = new PhoneNumber("212345678");

        // Act & Assert
        assertEquals("912345678", mobilePhoneNumber.toString(), "String representation for mobile phone number should match");
        assertEquals("212345678", fixedPhoneNumber.toString(), "String representation for fixed phone number should match");
    }
}

