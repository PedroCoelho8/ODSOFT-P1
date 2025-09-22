package pt.psoft.g1.psoftg1.bookmanagement.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.authormanagement.repositories.AuthorRepository;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.bookmanagement.repositories.BookRepository;
import pt.psoft.g1.psoftg1.exceptions.ConflictException;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;
import pt.psoft.g1.psoftg1.genremanagement.repositories.GenreRepository;
import pt.psoft.g1.psoftg1.shared.services.Page;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class BookServiceImplIT {

    @Autowired
    private BookService bookService;

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private GenreRepository genreRepository;

    @MockBean
    private AuthorRepository authorRepository;

    private Book testBook;
    private Author testAuthor;
    private Genre testGenre;

    @BeforeEach
    public void initialize() {
        testGenre = new Genre("Fiction");

        testAuthor = new Author("Sample Author", "Bio of Author", null);

        testBook = new Book("9782826012092", "Sample Book", "Sample Description", testGenre, List.of(testAuthor), null);

        when(bookRepository.findByIsbn("9782826012092")).thenReturn(Optional.of(testBook));
        when(genreRepository.findByString("Fiction")).thenReturn(Optional.of(testGenre));
        when(authorRepository.findByAuthorNumber(1L)).thenReturn(Optional.of(testAuthor));
    }

    @Test
    public void shouldThrowConflictExceptionWhenBookAlreadyExists() {
        CreateBookRequest request = new CreateBookRequest();
        when(bookRepository.findByIsbn("9782826012092")).thenReturn(Optional.of(testBook));

        assertThrows(ConflictException.class, () -> bookService.create(request, "9782826012092"));
    }

    @Test
    public void shouldReturnBooksByGenre() {
        when(bookRepository.findByGenre("Fiction")).thenReturn(List.of(testBook));

        List<Book> books = bookService.findByGenre("Fiction");

        assertEquals(1, books.size());
        assertEquals("Fiction", books.get(0).getGenre().toString());
    }

    @Test
    public void shouldReturnBooksByTitle() {
        when(bookRepository.findByTitle("Sample Book")).thenReturn(List.of(testBook));

        List<Book> books = bookService.findByTitle("Sample Book");

        assertEquals(1, books.size());
        assertEquals("Sample Book", books.get(0).getTitle().toString());
    }

    @Test
    public void shouldRetrieveBooksByAuthorName() {
        when(bookRepository.findByAuthorName("Sample%")).thenReturn(List.of(testBook));

        List<Book> books = bookService.findByAuthorName("Sample");

        assertEquals(1, books.size());
        assertEquals("Sample Author", books.get(0).getAuthors().get(0).getName());
    }

    @Test
    public void shouldFindBookByIsbn() {
        Book foundBook = bookService.findByIsbn("9782826012092");

        assertEquals("9782826012092", foundBook.getIsbn());
    }

    @Test
    public void shouldSearchBooksUsingQuery() {
        Page page = new Page(1, 10);
        SearchBooksQuery query = new SearchBooksQuery("Sample", "Sample Author", "Fiction");
        when(bookRepository.searchBooks(any(Page.class), any(SearchBooksQuery.class))).thenReturn(List.of(testBook));

        List<Book> result = bookService.searchBooks(page, query);

        assertEquals(1, result.size());
        assertEquals("Sample Book", result.get(0).getTitle().toString());
    }

    @Test
    public void shouldRemoveBookPhoto() {
        testBook.setVersion(1L); // Inicializa a versão para evitar NullPointerException
        testBook.setPhoto("test_photo_uri");
        when(bookRepository.save(any(Book.class))).thenReturn(testBook);

        Book updatedBook = bookService.removeBookPhoto("9782826012092", 1L);

        assertNull(updatedBook.getPhoto());
        verify(bookRepository, times(1)).save(any(Book.class));
    }

}
