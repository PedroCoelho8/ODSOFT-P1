package pt.psoft.g1.psoftg1.readermanagement.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pt.psoft.g1.psoftg1.exceptions.ConflictException;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;
import pt.psoft.g1.psoftg1.readermanagement.services.UpdateReaderRequest;
import pt.psoft.g1.psoftg1.usermanagement.model.Reader;

import java.nio.file.InvalidPathException;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReaderDetailsTest {

    private Reader mockReader;
    private ReaderDetails readerDetails;

    @BeforeEach
    void setUp() {
        // Arrange
        mockReader = Mockito.mock(Reader.class);
        // Corrigido para usar o formato de data esperado
        readerDetails = new ReaderDetails(1, mockReader, "1990-01-01", "910000000", true, true, true, "photoURI", Collections.emptyList());
    }

    @Test
    void testConstructorWithNullReaderThrowsException() {
        // Arrange & Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new ReaderDetails(1, null, "1990-01-01", "910000000", true, true, true, "photoURI", Collections.emptyList());
        });

        // Assert
        assertEquals("Provided argument resolves to null object", exception.getMessage());
    }

    @Test
    void testConstructorWithNullPhoneNumberThrowsException() {
        // Arrange & Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new ReaderDetails(1, mockReader, "1990-01-01", null, true, true, true, "photoURI", Collections.emptyList());
        });

        // Assert
        assertEquals("Provided argument resolves to null object", exception.getMessage());
    }

    @Test
    void testConstructorWithGdprFalseThrowsException() {
        // Arrange & Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new ReaderDetails(1, mockReader, "1990-01-01", "910000000", false, true, true, "photoURI", Collections.emptyList());
        });

        // Assert
        assertEquals("Readers must agree with the GDPR rules", exception.getMessage());
    }

    /*
    @Test
    void testApplyPatchWithMismatchedVersionThrowsConflictException() {
        // Arrange
        UpdateReaderRequest request = mock(UpdateReaderRequest.class);
        when(request.getBirthDate()).thenReturn("1992-02-02");
        when(request.getPhoneNumber()).thenReturn("910000000");

        // Act
        Exception exception = assertThrows(ConflictException.class, () -> {
            readerDetails.applyPatch(2, request, "newPhotoURI", null);
        });

        // Assert
        assertEquals("Provided version does not match latest version of this object", exception.getMessage());
    }
    */

/*
    @Test
    void testRemovePhotoWithMismatchedVersionThrowsConflictException() {
        // Arrange & Act
        Exception exception = assertThrows(NullPointerException.class, () -> {
            readerDetails.removePhoto(2);
        });

        // Assert
        assertEquals("Provided version does not match latest version of this object", exception.getMessage());
    }
    */
/*
    @Test
    void testApplyPatchUpdatesFields() {
        // Arrange
        UpdateReaderRequest request = mock(UpdateReaderRequest.class);
        when(request.getBirthDate()).thenReturn("1992-02-02");
        when(request.getPhoneNumber()).thenReturn("910000000");
        when(request.getMarketing()).thenReturn(false);
        when(request.getThirdParty()).thenReturn(false);
        when(request.getFullName()).thenReturn("New Name");

        // Act
        readerDetails.applyPatch(1L, request, null, null);

        // Assert
        assertEquals("New Name", mockReader.getName());
        assertEquals("1992-02-02", readerDetails.getBirthDate().toString());
        assertEquals("910000000", readerDetails.getPhoneNumber());
        assertFalse(readerDetails.isMarketingConsent());
        assertFalse(readerDetails.isThirdPartySharingConsent());
    }
*/

    @Test
    void testConstructorInitializesRequiredFields() {
        // Assert
        assertNotNull(readerDetails.getReader());
        assertNotNull(readerDetails.getBirthDate());
        assertNotNull(readerDetails.getPhoneNumber());
        assertTrue(readerDetails.isGdprConsent());
        assertNotNull(readerDetails.getInterestList());
        assertNotNull(readerDetails.getPhoto());
    }


}
