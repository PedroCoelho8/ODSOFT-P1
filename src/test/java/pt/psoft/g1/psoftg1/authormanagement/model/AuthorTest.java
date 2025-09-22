package pt.psoft.g1.psoftg1.authormanagement.model;

import org.hibernate.StaleObjectStateException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.psoft.g1.psoftg1.authormanagement.services.CreateAuthorRequest;
import pt.psoft.g1.psoftg1.authormanagement.services.UpdateAuthorRequest;
import pt.psoft.g1.psoftg1.exceptions.ConflictException;
import pt.psoft.g1.psoftg1.shared.model.EntityWithPhoto;
import pt.psoft.g1.psoftg1.shared.model.Photo;


import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthorTest {
    private final String validName = "João Alberto";
    private final String validBio = "O João Alberto nasceu em Chaves e foi pedreiro a maior parte da sua vida.";

    private final UpdateAuthorRequest request = new UpdateAuthorRequest(validName, validBio, null, null);

    private static class EntityWithPhotoImpl extends EntityWithPhoto { }
    @BeforeEach
    void setUp() {
    }
    @Test
    void ensureNameNotNull(){
        assertThrows(IllegalArgumentException.class, () -> new Author(null,validBio, null));
    }

    @Test
    void whenNewNullNameAuthor_thenThrowsException() {
        // arrange
        String expectedMessage = "Name cannot be null";

        // act + assert
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new Author( null, "Bio do Autor", null)
        );

        // assert
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void ensureBioNotNull(){
        assertThrows(IllegalArgumentException.class, () -> new Author(validName,null, null));
    }

    @Test
    void whenNewNullBioAuthor_thenThrowsException() {
        // arrange
        String expectedMessage = "Bio cannot be null";

        // act + assert
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new Author( "Autor", null, null)
        );

        // assert
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void whenVersionIsStaleItIsNotPossibleToPatch() {
        final var subject = new Author(validName,validBio, null);

        assertThrows(StaleObjectStateException.class, () -> subject.applyPatch(999, request));
    }

    @Test
    void whenVersionIsStaleItIsNotPossibleToPatch_thenThrowsStaleObjectStateException() {
        // Arrange
        final var subject = new Author(validName, validBio, null);
        long staleVersion = 999;
        UpdateAuthorRequest request = new UpdateAuthorRequest();

        // Act + Assert
        StaleObjectStateException exception = assertThrows(StaleObjectStateException.class, () ->
                subject.applyPatch(staleVersion, request)
        );

        // Assert
        String expectedMessage = "Object was already modified by another user";
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    void testCreateAuthorWithoutPhoto() {
        Author author = new Author(validName, validBio, null);
        assertNotNull(author);
        assertNull(author.getPhoto());
    }

    @Test
    void whenCreatingAuthorWithoutPhoto_thenAuthorShouldBeCreatedAndPhotoShouldBeNull() {
        // Arrange
        Author author = new Author(validName, validBio, null);

        // Act
        Photo photoURI = author.getPhoto();

        // Assert
        assertNotNull(author);
        assertNull(photoURI);
    }

    @Test
    void testCreateAuthorRequestWithPhoto() {
        CreateAuthorRequest request = new CreateAuthorRequest(validName, validBio, null, "photoTest.jpg");
        Author author = new Author(request.getName(), request.getBio(), "photoTest.jpg");
        assertNotNull(author);
        assertEquals(request.getPhotoURI(), author.getPhoto().getPhotoFile());
    }

    @Test
    void testCreateAuthorWithPhotoSuccessfully() {
        // Arrange
        CreateAuthorRequest request = new CreateAuthorRequest(validName, validBio, null, "photoTest.jpg");

        // Act
        Author author = new Author(request.getName(), request.getBio(), request.getPhotoURI());

        // Assert
        assertNotNull(author);
        assertEquals(request.getName(), author.getName());
        assertEquals(request.getBio(), author.getBio());
        assertNotNull(author.getPhoto());
        assertEquals(request.getPhotoURI(), author.getPhoto().getPhotoFile());
    }

    @Test
    void testCreateAuthorRequestWithoutPhoto() {
        CreateAuthorRequest request = new CreateAuthorRequest(validName, validBio, null, null);
        Author author = new Author(request.getName(), request.getBio(), null);
        assertNotNull(author);
        assertNull(author.getPhoto());
    }

    @Test
    void testCreateAuthorWithNullPhotoSuccessfully() {
        // Arrange
        CreateAuthorRequest request = new CreateAuthorRequest(validName, validBio, null, null);

        // Act
        Author author = new Author(request.getName(), request.getBio(), null);

        // Assert
        assertNotNull(author);
        assertEquals(request.getName(), author.getName());
        assertEquals(request.getBio(), author.getBio());
        assertNull(author.getPhoto());
    }

    @Test
    void testEntityWithPhotoSetPhotoInternalWithValidURI() {
        EntityWithPhoto entity = new EntityWithPhotoImpl();
        String validPhotoURI = "photoTest.jpg";
        entity.setPhoto(validPhotoURI);
        assertNotNull(entity.getPhoto());
    }

    @Test
    void ensurePhotoCanBeNull_AkaOptional() {
        Author author = new Author(validName, validBio, null);
        assertNull(author.getPhoto());
    }

    @Test
    void testAuthorCreationWithoutPhotoResultsInNullPhoto() {
        // Arrange
        Author author = new Author(validName, validBio, null);

        // Act
        Photo photo = author.getPhoto();

        // Assert
        assertNull(photo, "Expected photo to be null when author is created without a photo");
    }

    @Test
    void ensureValidPhoto() {
        Author author = new Author(validName, validBio, "photoTest.jpg");
        Photo photo = author.getPhoto();
        assertNotNull(photo);
        assertEquals("photoTest.jpg", photo.getPhotoFile());
    }

    @Test
    void testAuthorPhotoIsSetCorrectly() {
        // Arrange
        Author author = new Author(validName, validBio, "photoTest.jpg");

        // Act
        Photo photo = author.getPhoto();

        // Assert
        assertNotNull(photo);
        assertEquals("photoTest.jpg", photo.getPhotoFile());
    }

    @Test
    void shouldCreateAAuthorWithCorrectAttributes() {
        Author author = new Author("Fernando Pessoa","Fernando António Nogueira Pessoa foi um poeta, dramaturgo, ensaísta, tradutor, publicitário, astrólogo, inventor, empresário, correspondente comercial, crítico literário e comentador político português. Fernando Pessoa já foi considerado por especialistas de sua obra como o mais universal poeta português.",null);

        assertEquals(author.getName(), "Fernando Pessoa");
        assertEquals(author.getBio(), "Fernando António Nogueira Pessoa foi um poeta, dramaturgo, ensaísta, tradutor, publicitário, astrólogo, inventor, empresário, correspondente comercial, crítico literário e comentador político português. Fernando Pessoa já foi considerado por especialistas de sua obra como o mais universal poeta português.");
        assertNull(author.getPhoto());
    }

    //new test
    @Test
    void whenNewEmptyNameAuthor_thenThrowsException() {
        // arrange
        String expectedMessage = "Name cannot be blank, nor only white spaces";

        // act + assert
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new Author("", "Bio do Autor", null)
        );

        // assert
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    //new test
    @Test
    void whenNewNonAlphanumericNameAuthor_thenThrowsException() {
        // arrange
        String expectedMessage = "Name can only contain alphanumeric characters";

        // act + assert
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new Author( "@-.,", "Bio do Autor", null)
        );

        // assert
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void whenRemovingPhotoWithIncorrectVersion_thenThrowsConflictException() {
        Author author = new Author(validName, validBio, "photoTest.jpg");
        long incorrectVersion = author.getVersion() + 1;

        ConflictException exception = assertThrows(ConflictException.class, () ->
                author.removePhoto(incorrectVersion)
        );

        assertEquals("Provided version does not match latest version of this object", exception.getMessage());
    }

    @Test
    void whenAuthorCreatedWithNullPhoto_thenPhotoIsSetToNull() {
        // Act
        Author author = new Author(validName, validBio, null);

        // Assert
        assertNull(author.getPhoto(), "Photo should be null when created with a null photo URI");
    }

    @Test
    void whenApplyingPatchWithOnlyName_thenOnlyNameIsUpdated() {
        // Arrange
        Author author = new Author(validName, validBio, null);
        UpdateAuthorRequest request = new UpdateAuthorRequest(null, "Updated Name", null, null);

        // Act
        author.applyPatch(author.getVersion(), request);

        // Assert
        assertEquals("Updated Name", author.getName());
        assertEquals(validBio, author.getBio());
        assertNull(author.getPhoto());
    }


    @Test
    void whenApplyingPatchWithNoFields_thenNoChangesOccur() {
        // Arrange
        Author author = new Author(validName, validBio, null);
        UpdateAuthorRequest request = new UpdateAuthorRequest(null, null, null, null);

        // Act
        author.applyPatch(author.getVersion(), request);

        // Assert
        assertEquals(validName, author.getName());
        assertEquals(validBio, author.getBio());
    }

    @Test
    void whenApplyingPatchWithBothFieldsNull_thenNoUpdateOccurs() {
        // Arrange
        Author author = new Author(validName, validBio, null);
        UpdateAuthorRequest request = new UpdateAuthorRequest(null, null, null, null);

        // Act + Assert
        assertDoesNotThrow(() -> author.applyPatch(author.getVersion(), request));
    }




    @Test
    void whenRemovingPhotoWithCorrectVersion_thenPhotoIsRemoved() {
        // Arrange
        Author author = new Author(validName, validBio, "photoTest.jpg");

        // Act
        author.removePhoto(author.getVersion());

        // Assert
        assertNull(author.getPhoto(), "Photo should be null after successful removal");
    }


    @Test
    void whenRemovingPhotoAndPhotoIsAlreadyNull_thenNoExceptionThrown() {
        // Arrange
        Author author = new Author( validName, validBio, null);

        // Act + Assert
        assertDoesNotThrow(() -> author.removePhoto(author.getVersion()));
    }


    @Test
    void whenCallingGetNameAndGetBio_thenReturnsCorrectFormat() {
        // Arrange
        Author author = new Author(validName, validBio, null);

        // Act + Assert
        assertEquals(validName, author.getName());
        assertEquals(validBio, author.getBio());
    }



    @Test
    void whenAuthorIsUpdated_thenVersionRemainsConsistent() {
        // Arrange
        Author author = new Author(validName, validBio, null);
        long initialVersion = author.getVersion();

        // Act
        author.applyPatch(initialVersion, new UpdateAuthorRequest("Updated Name", null, null, null));

        // Assert
        assertEquals(initialVersion, author.getVersion(), "Version should be checked for consistency");
    }



    @Test
    void whenConcurrentModificationsOccur_thenThrowsStaleObjectStateException() {
        // Arrange
        Author author = new Author(validName, validBio, null);
        long staleVersion = author.getVersion() + 1;

        // Act + Assert
        StaleObjectStateException exception = assertThrows(StaleObjectStateException.class, () ->
                author.applyPatch(staleVersion, new UpdateAuthorRequest("Updated Name", null, null, null))
        );
        assertTrue(exception.getMessage().contains("Object was already modified by another user"));
    }


    @Test
    void whenNameContainsSpecialCharacters_thenReturnsExpectedString() {
        // Arrange
        String specialName = "Ana-Maria da Silva";
        Author author = new Author(specialName, validBio, null);

        // Act + Assert
        assertEquals(specialName, author.getName());
    }

    @Test
    void whenCreatingAuthorWithPhoto_thenSetPhotoIsCalled() {
        // Arrange
        Author author = spy(new Author(validName, validBio, null));

        // Act
        author.setPhoto("photoTest.jpg");

        // Assert
        verify(author).setPhoto("photoTest.jpg");
    }

    @Test
    void whenUpdatingBio_thenSetBioIsCalled() {
        // Arrange
        Author author = spy(new Author(validName, validBio, null));
        String newBio = "Nova biografia do autor.";

        // Act
        author.setBio(newBio);

        // Assert
        verify(author).setBio(newBio);
    }

    @Test
    void whenGettingPhoto_thenGetPhotoIsCalled() {
        // Arrange
        Author author = spy(new Author(validName, validBio, "photoTest.jpg"));

        // Act
        Photo photo = author.getPhoto();

        // Assert
        assertNotNull(photo);
        assertEquals("photoTest.jpg", photo.getPhotoFile());

        verify(author).getPhoto();

    }

    @Test
    void whenGettingPhoto_thenGetPhotoIsCalled_withMock() {
        // Arrange
        Author author = mock(Author.class);
        Photo mockPhoto = new Photo(Path.of("photoTest.jpg"));

        when(author.getPhoto()).thenReturn(mockPhoto);

        // Act
        Photo photo = author.getPhoto();

        // Assert
        assertNotNull(photo);
        assertEquals("photoTest.jpg", photo.getPhotoFile());

        verify(author).getPhoto();
    }

    @Test
    void whenSettingBio_thenSetBioIsCalledOnce() {
        // Arrange
        Author author = spy(new Author(validName, validBio, null));
        String updatedBio = "Updated bio";

        // Act
        author.setBio(updatedBio);

        // Assert
        verify(author, times(1)).setBio(updatedBio);
    }

    @Test
    void whenApplyPatchCalled_thenVerifyApplyPatchWithMock() {
        // Arrange
        Author author = mock(Author.class);
        UpdateAuthorRequest request = new UpdateAuthorRequest("Mocked Name", "Mocked Bio", null, null);

        // Act
        author.applyPatch(1L, request);

        // Assert
        verify(author).applyPatch(1L, request);
    }

    @Test
    void whenGettingPhoto_thenReturnsMockedPhoto() {
        // Arrange
        Author author = mock(Author.class);
        Photo mockPhoto = mock(Photo.class);
        when(author.getPhoto()).thenReturn(mockPhoto);
        when(mockPhoto.getPhotoFile()).thenReturn("mockedPhoto.jpg");

        // Act
        Photo photo = author.getPhoto();

        // Assert
        assertNotNull(photo);
        assertEquals("mockedPhoto.jpg", photo.getPhotoFile());
        verify(author).getPhoto();
    }

    @Test
    void whenSettingPhotoWithMock_thenVerifySetPhotoCalled() {
        // Arrange
        Author author = mock(Author.class);

        // Act
        author.setPhoto("mockedPhoto.jpg");

        // Assert
        verify(author).setPhoto("mockedPhoto.jpg");
    }


}