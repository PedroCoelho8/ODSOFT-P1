package pt.psoft.g1.psoftg1.lendingmanagement.model;

import org.junit.jupiter.api.Test;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LendingNumberTest {

    @Test
    void ensureLendingNotNull_thenThrowsExcpetion() {
        // Arrange
        String lendingNumber = null;

        String expectedMessage = "Lending number cannot be null";

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new LendingNumber(lendingNumber));

        String actualMessage =exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void ensureLendingNumberNotBlank(){
        assertThrows(IllegalArgumentException.class, () -> new LendingNumber(""));
    }

    @Test
    void ensureLendingNotBlankl_thenThrowsExcpetion() {
        // Arrange
        String blankLendingNumber = "";

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new LendingNumber(blankLendingNumber));
    }


    @Test
    void ensureLendingNumberNotWrongFormat() {
        // Arrange
        String[] invalidFormats = { "1/2024", "24/1", "2024-1", "2024\\1" };

        // Act & Assert
        for (String format : invalidFormats) {
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new LendingNumber(format),
                    "Expected IllegalArgumentException for format: " + format);

            // Assert (optional, can be used if you have specific messages to check)
            // assertEquals("Expected error message", exception.getMessage());
        }
    }

    @Test
    void ensureLendingNumberIsSetWithString() {
        // Arrange
        String expectedLendingNumber = "2024/1";

        // Act
        LendingNumber ln = new LendingNumber(expectedLendingNumber);
        String actualLendingNumber = ln.toString();

        // Assert
        assertEquals(expectedLendingNumber, actualLendingNumber, "Lending number should match the initialized value");
    }

    @Test
    void ensureLendingNumberIsSetWithSequential() {
        // Arrange
        int expectedSequential = 1;
        String expectedLendingNumber = LocalDate.now().getYear() + "/" + expectedSequential;

        // Act
        LendingNumber ln = new LendingNumber(1);
        String actualLendingNumber = ln.toString();

        // Assert
        assertNotNull(ln, "LendingNumber instance should not be null");
        assertEquals(expectedLendingNumber, actualLendingNumber, "Lending number should match the expected format");
    }

    @Test
    void ensureLendingNumberIsSetWithYearAndSequential() {
        // Arrange
        int year = 2024;
        int sequential = 1;

        // Act
        LendingNumber ln = new LendingNumber(year, sequential);

        // Assert
        assertNotNull(ln, "LendingNumber instance should not be null");
        // Optionally assert the expected string format
        assertEquals(year + "/" + sequential, ln.toString(), "Lending number should match the expected format");
    }

    @Test
    void ensureSequentialCannotBeNegative() {
        // Arrange
        int year = 2024;
        int negativeSequential = -1;
        String expectedMessage = "Sequencial component cannot be negative";

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new LendingNumber(year, negativeSequential));
        String actualMessage =exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void ensureYearCannotBeInTheFuture() {
        // Arrange
        int futureYear = LocalDate.now().getYear() + 1;
        int sequential = 1;

        String expectedMessage = "";

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new LendingNumber(futureYear, sequential));
        String actualMessage =exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }


}