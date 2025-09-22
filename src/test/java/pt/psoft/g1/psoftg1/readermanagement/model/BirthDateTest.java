package pt.psoft.g1.psoftg1.readermanagement.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.nio.file.AccessDeniedException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class BirthDateTest {

    @Test
    void ensureBirthDateCanBeCreatedWithValidDate() {
        // Act & Assert
        assertDoesNotThrow(() -> new BirthDate(2000, 1, 1), "Should not throw an exception for a valid birth date");
    }

    @Test
    void ensureBirthDateCanBeCreatedWithValidStringDate() {
        // Act & Assert
        assertDoesNotThrow(() -> new BirthDate("2000-01-01"), "Should not throw an exception for a valid string date");
    }

    @Test
    void ensureExceptionIsThrownForInvalidStringDateFormat() {
        // Arrange
        String invalidDate = "01-01-2000";
        String expectedMessage = "Provided birth date is not in a valid format. Use yyyy-MM-dd";

        // Act
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new BirthDate(invalidDate));

        // Assert
        assertEquals(expectedMessage, exception.getMessage(), "Exception message should match the expected format error");
    }

    @Test
    void ensureCorrectStringRepresentation() {
        // Arrange
        BirthDate birthDate = new BirthDate(2000, 1, 1);
        String expectedString = "2000-1-1";

        // Act
        String actualString = birthDate.toString();

        // Assert
        assertEquals(expectedString, actualString, "String representation should match the expected format");
    }

}
