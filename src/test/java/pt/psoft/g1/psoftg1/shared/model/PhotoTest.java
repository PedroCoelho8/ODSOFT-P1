package pt.psoft.g1.psoftg1.shared.model;

import org.junit.jupiter.api.Test;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class PhotoTest {
    @Test
    void ensurePathMustNotBeNull() {
        assertThrows(NullPointerException.class, () -> new Photo(null));
    }

    @Test
    void ensurePathIsValidToLocalFile() {
        // Arrange
        Path expectedPath = Paths.get("photoTest.jpg");
        Photo photo = new Photo(expectedPath);

        // Act
        String actualPhotoFile = photo.getPhotoFile();

        // Assert
        assertEquals(expectedPath.getFileName().toString(), actualPhotoFile, "Photo file should match the expected file name");
    }
}
