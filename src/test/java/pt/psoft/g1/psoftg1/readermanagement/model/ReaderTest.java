package pt.psoft.g1.psoftg1.readermanagement.model;

import org.junit.jupiter.api.Test;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;
import pt.psoft.g1.psoftg1.shared.model.Photo;
import pt.psoft.g1.psoftg1.usermanagement.model.Reader;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class ReaderTest {
    @Test
    void ensureValidReaderDetailsAreCreated() {
        // Arrange
        Reader mockReader = mock(Reader.class);

        // Act & Assert
        assertDoesNotThrow(() -> new ReaderDetails(123, mockReader, "2010-01-01", "912345678", true, false, false, null, null),
                "Should not throw an exception for valid reader details");
    }

    @Test
    void ensureExceptionIsThrownForNullReader() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new ReaderDetails(123, null, "2010-01-01", "912345678", true, false, false, null, null),
                "Should throw an exception for null reader");
    }

    @Test
    void ensureExceptionIsThrownForNullPhoneNumber() {
        // Arrange
        Reader mockReader = mock(Reader.class);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new ReaderDetails(123, mockReader, "2010-01-01", null, true, false, false, null, null),
                "Should throw an exception for null phone number");
    }

    @Test
    void ensureExceptionIsThrownForNoGdprConsent() {
        // Arrange
        Reader mockReader = mock(Reader.class);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new ReaderDetails(123, mockReader, "2010-01-01", "912345678", false, false, false, null, null),
                "Should throw an exception for missing GDPR consent");
    }

    @Test
    void ensureGdprConsentIsTrue() {
        // Arrange
        Reader mockReader = mock(Reader.class);
        ReaderDetails readerDetails = new ReaderDetails(123, mockReader, "2010-01-01", "912345678", true, false, false, null, null);

        // Act & Assert
        assertTrue(readerDetails.isGdprConsent(), "GDPR consent should be true");
    }

    @Test
    void ensurePhotoCanBeNull_AkaOptional() {
        // Arrange
        Reader mockReader = mock(Reader.class);
        ReaderDetails readerDetails = new ReaderDetails(123, mockReader, "2010-01-01", "912345678", true, false, false, null, null);

        // Act & Assert
        assertNull(readerDetails.getPhoto(), "Photo should be null");
    }

    @Test
    void ensureValidPhoto() {
        // Arrange
        Reader mockReader = mock(Reader.class);
        ReaderDetails readerDetails = new ReaderDetails(123, mockReader, "2010-01-01", "912345678", true, false, false, "readerPhotoTest.jpg", null);

        // Act
        Photo photo = readerDetails.getPhoto();

        // Assert
        assertNotNull(photo, "Photo should not be null");
        assertEquals("readerPhotoTest.jpg", photo.getPhotoFile(), "Photo file name should match");
    }

    @Test
    void ensureInterestListCanBeNullOrEmptyList_AkaOptional() {
        // Arrange
        Reader mockReader = mock(Reader.class);

        // Act
        ReaderDetails readerDetailsNullInterestList = new ReaderDetails(123, mockReader, "2010-01-01", "912345678", true, false, false, "readerPhotoTest.jpg", null);

        // Assert
        assertNull(readerDetailsNullInterestList.getInterestList(), "Interest list should be null");

        // Act
        ReaderDetails readerDetailsInterestListEmpty = new ReaderDetails(123, mockReader, "2010-01-01", "912345678", true, false, false, "readerPhotoTest.jpg", new ArrayList<>());

        // Assert
        assertEquals(0, readerDetailsInterestListEmpty.getInterestList().size(), "Interest list should be empty");
    }

    @Test
    void ensureInterestListCanTakeAnyValidGenre() {
        // Arrange
        Reader mockReader = mock(Reader.class);
        Genre g1 = new Genre("genre1");
        Genre g2 = new Genre("genre2");
        List<Genre> genreList = new ArrayList<>();
        genreList.add(g1);
        genreList.add(g2);

        // Act
        ReaderDetails readerDetails = new ReaderDetails(123, mockReader, "2010-01-01", "912345678", true, false, false, "readerPhotoTest.jpg", genreList);

        // Assert
        assertEquals(2, readerDetails.getInterestList().size(), "Interest list should contain two genres");
    }
}
