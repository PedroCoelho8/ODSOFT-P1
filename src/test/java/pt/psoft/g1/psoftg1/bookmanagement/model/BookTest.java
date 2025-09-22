package pt.psoft.g1.psoftg1.bookmanagement.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.authormanagement.model.Bio;
import pt.psoft.g1.psoftg1.bookmanagement.services.CreateBookRequest;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class BookTest {
    private final String validIsbn = "9782826012092";
    private final String validTitle = "Encantos de contar";
    private final Author validAuthor1 = new Author("João Alberto", "O João Alberto nasceu em Chaves e foi pedreiro a maior parte da sua vida.", null);
    private final Author validAuthor2 = new Author("Maria José", "A Maria José nasceu em Viseu e só come laranjas às segundas feiras.", null);
    private final Genre validGenre = new Genre("Fantasia");
    private ArrayList<Author> authors = new ArrayList<>();

    @BeforeEach
    void setUp(){
        authors.clear();
    }

    @Test
    void ensureIsbnNotNull(){
        authors.add(validAuthor1);
        assertThrows(IllegalArgumentException.class, () -> new Book(null, validTitle, null, validGenre, authors, null));
    }

    @Test
    void ensureIsbnNotNull_thenThrowsException() {
        // Arrange
        authors.add(validAuthor1);

        String expectedMessage = "Isbn cannot be null";

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Book(null, validTitle, null, validGenre, authors, null));

        String actualMessage =exception.getMessage();

        // Verifica se a mensagem de erro é a esperada
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void ensureTitleNotNull(){
        authors.add(validAuthor1);
        assertThrows(IllegalArgumentException.class, () -> new Book(validIsbn, null, null, validGenre, authors, null));
    }

    @Test
    void ensureTitleNotNull_thenThrowsException() {
        // Arrange
        authors.add(validAuthor1);

        String expectedMessage = "Title cannot be null";

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Book(validIsbn, null, null, validGenre, authors, null));

        String actualMessage =exception.getMessage();

        // Verifica se a mensagem de erro é a esperada
        assertTrue(actualMessage.contains(expectedMessage));
    }


    @Test
    void ensureGenreNotNull(){
        authors.add(validAuthor1);
        assertThrows(IllegalArgumentException.class, () -> new Book(validIsbn, validTitle, null,null, authors, null));
    }

    @Test
    void ensureGenreNotNull_thenThrowsException() {
        // Arrange
        authors.add(validAuthor1);

        String expectedMessage = "Genre cannot be null";

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Book(validIsbn, validTitle, null, null, authors, null));

        String actualMessage = exception.getMessage();

        // Verifica se a mensagem de erro é a esperada
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void ensureAuthorsNotNull(){
        authors.add(validAuthor1);
        assertThrows(IllegalArgumentException.class, () -> new Book(validIsbn, validTitle, null, validGenre, null, null));
    }

    @Test
    void ensureAuthorsNotNull_thenThrowsException() {
        // Arrange
        authors.add(validAuthor1);

        String expectedMessage = "Author list is null";

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Book(validIsbn, validTitle, null, validGenre, null, null));

        String actualMessage =exception.getMessage();

        // Verifica se a mensagem de erro é a esperada
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void ensureAuthorsNotEmpty(){
        assertThrows(IllegalArgumentException.class, () -> new Book(validIsbn, validTitle, null, validGenre, authors, null));
    }

    @Test
    void ensureAuthorsNotEmpty_thenThrowsException() {
        // Arrange
        authors.add(validAuthor1);

        String expectedMessage = "Author list is empty";

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Book(validIsbn, validTitle, null, validGenre, new ArrayList<>(), null));

        String actualMessage =exception.getMessage();

        // Verifica se a mensagem de erro é a esperada
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void ensureBookCreatedWithMultipleAuthors() {
        authors.add(validAuthor1);
        authors.add(validAuthor2);
        assertDoesNotThrow(() -> new Book(validIsbn, validTitle, null, validGenre, authors, null));
    }

    @Test
    void ensureBookCreatedWithMultipleAuthorsSuccessfully() {
        // Arrange
        authors.add(validAuthor1);
        authors.add(validAuthor2);

        //Act
        Book book = new Book(validIsbn,validTitle,null,validGenre,authors,null);

        //Assert
        assertNotNull(book);
        assertEquals(2, book.getAuthors().size());
        assertTrue(book.getAuthors().contains(validAuthor1));
        assertTrue(book.getAuthors().contains(validAuthor2));
    }

    @Test
    void ensureBookCreatedWithDescription() {
        // Arrange
        authors.add(validAuthor1);
        String description = "Uma história mágica.";

        // Act
        Book book = new Book(validIsbn, validTitle, description, validGenre, authors, null);

        // Assert
        assertNotNull(book);
        assertEquals(description, book.getDescription());
    }

    @Test
    void whenCreatingBookWithMockedIsbn_thenIsbnIsCorrect() {
        // Arrange
        Genre genre = mock(Genre.class);
        Author author = mock(Author.class);
        ArrayList<Author> authors = new ArrayList<>();
        authors.add(author);

        // Act
        Book book = new Book(validIsbn, validTitle, null, genre, authors, null);

        // Assert
        assertEquals(validIsbn, book.getIsbn());
    }

}