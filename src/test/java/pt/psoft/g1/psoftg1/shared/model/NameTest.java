package pt.psoft.g1.psoftg1.shared.model;

import org.junit.jupiter.api.Test;
import pt.psoft.g1.psoftg1.lendingmanagement.model.LendingNumber;

import static org.junit.jupiter.api.Assertions.*;

class NameTest {

    @Test
    void ensureNameNotNull_thenThrowsException() {
        // Arrange
        String nameNull = null;

        String expectedMessage = "Name cannot be null";

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Name(nameNull));

        String actualMessage =exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void ensureNameNotBlank_thenThrowsException() {
        // Arrange
        String nameBlank = "";

        String expectedMessage = "Name cannot be blank, nor only white spaces";

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Name(nameBlank));

        String actualMessage =exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void ensureNameNotOnlyWhiteSpaces_thenThrowsException() {
        // Arrange
        String nameBlankWhiteSpaces = "  ";

        String expectedMessage = "Name cannot be blank, nor only white spaces";

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Name(nameBlankWhiteSpaces));

        String actualMessage =exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void ensureNameMustOnlyBeAlphanumeric() {
        // Arrange
        String name = "Ricardo!";

        String expectedMessage = "Name can only contain alphanumeric characters";

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Name(name));

        String actualMessage =exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Text from <a href="https://www.lipsum.com/">Lorem Ipsum</a> generator.
     */

    @Test
    void ensureNameMustNotBeOversize() {
        // Arrange
        String name = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut fermentum venenatis augue, a congue turpis eleifend ut. Etiam fringilla ex nulla, id quis.";

        String expectedMessage = "";

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Name(name));

        String actualMessage =exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void ensureNameIsSet() {
        // Arrange
        final var expectedName = "Some name";

        // Act
        final var name = new Name(expectedName);

        // Assert
        assertEquals(expectedName, name.toString(), "Name should be set correctly");
    }

    @Test
    void ensureNameIsChanged() {
        // Arrange
        final var name = new Name("Some name");
        final var newName = "Some other name";

        // Act
        name.setName(newName);

        // Assert
        assertEquals(newName, name.toString(), "Name should be changed correctly");
    }
}
