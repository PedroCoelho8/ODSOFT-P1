package pt.psoft.g1.psoftg1.lendingmanagement.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;
import pt.psoft.g1.psoftg1.readermanagement.model.ReaderDetails;
import pt.psoft.g1.psoftg1.usermanagement.model.Reader;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@PropertySource({"classpath:config/library.properties"})
class LendingTest {
    private static final ArrayList<Author> authors = new ArrayList<>();
    private static Book book;
    private static ReaderDetails readerDetails;
    @Value("${lendingDurationInDays}")
    private int lendingDurationInDays;
    @Value("${fineValuePerDayInCents}")
    private int fineValuePerDayInCents;

    @BeforeAll
    public static void setup(){
        Author author = new Author("Manuel Antonio Pina",
                "Manuel António Pina foi um jornalista e escritor português, premiado em 2011 com o Prémio Camões",
                null);
        authors.add(author);
        book = new Book("9782826012092",
                "O Inspetor Max",
                "conhecido pastor-alemão que trabalha para a Judiciária, vai ser fundamental para resolver um importante caso de uma rede de malfeitores que quer colocar uma bomba num megaconcerto de uma ilustre cantora",
                new Genre("Romance"),
                authors,
                null);
        readerDetails = new ReaderDetails(1,
                Reader.newReader("manuel@gmail.com", "Manuelino123!", "Manuel Sarapinto das Coives"),
                "2000-01-01",
                "919191919",
                true,
                true,
                true,
                null,
                null);
    }

    @Test
    void ensureBookNotNull_thenThrowsException() {
        // Arrange
        Book nullBook = null;

        String expectedMessage = "Null objects passed to lending";

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Lending(nullBook, readerDetails, 1, lendingDurationInDays, fineValuePerDayInCents));

        String actualMessage =exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void ensureReaderNotNull_thenThrowsException() {
        // Arrange
        ReaderDetails nullReaderDetails = null;

        String expectedMessage = "Null objects passed to lending";

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Lending(book, nullReaderDetails, 1, lendingDurationInDays, fineValuePerDayInCents));

        String actualMessage =exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void ensureValidReaderNumber() {
        // Arrange
        int invalidReaderNumber = -1;

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Lending(book, readerDetails, invalidReaderNumber, lendingDurationInDays, fineValuePerDayInCents),
                "Reader number must not be negative");
    }

    @Test
    void testSetReturned() {
        // Arrange
        Lending lending = new Lending(book, readerDetails, 1, lendingDurationInDays, fineValuePerDayInCents);

        // Act
        lending.setReturned(0, null);

        // Assert
        assertEquals(LocalDate.now(), lending.getReturnedDate(), "Returned date should match the current date");
    }

    @Test
    void testGetDaysDelayed() {
        // Arrange
        Lending lending = new Lending(book, readerDetails, 1, lendingDurationInDays, fineValuePerDayInCents);

        // Act
        int daysDelayed = lending.getDaysDelayed();

        // Assert
        assertEquals(0, daysDelayed, "Days delayed should be zero");
    }

    @Test
    void testGetDaysUntilReturn() {
        // Arrange
        Lending lending = new Lending(book, readerDetails, 1, lendingDurationInDays, fineValuePerDayInCents);

        // Act
        Optional<Integer> daysUntilReturn = lending.getDaysUntilReturn();

        // Assert
        assertEquals(Optional.of(lendingDurationInDays), daysUntilReturn, "Days until return should match the lending duration");
    }

    @Test
    void testGetDaysOverDue() {
        // Arrange
        Lending lending = new Lending(book, readerDetails, 1, lendingDurationInDays, fineValuePerDayInCents);

        // Act
        Optional<Integer> daysOverdue = lending.getDaysOverdue();

        // Assert
        assertEquals(Optional.empty(), daysOverdue, "Days overdue should be empty");
    }

    @Test
    void testGetTitle() {
        // Arrange
        Lending lending = new Lending(book, readerDetails, 1, lendingDurationInDays, fineValuePerDayInCents);

        // Act
        String title = lending.getTitle();

        // Assert
        assertEquals("O Inspetor Max", title, "Title should match the expected value");
    }

    @Test
    void testGetLendingNumber() {
        // Arrange
        Lending lending = new Lending(book, readerDetails, 1, lendingDurationInDays, fineValuePerDayInCents);
        String expectedLendingNumber = LocalDate.now().getYear() + "/1";

        // Act
        String actualLendingNumber = lending.getLendingNumber();

        // Assert
        assertEquals(expectedLendingNumber, actualLendingNumber, "Lending number should match the expected format");
    }

    @Test
    void testGetBook() {
        // Arrange
        Lending lending = new Lending(book, readerDetails, 1, lendingDurationInDays, fineValuePerDayInCents);

        // Act
        Book actualBook = lending.getBook();

        // Assert
        assertEquals(book, actualBook, "Book should match the original book");
    }

    @Test
    void testGetReaderDetails() {
        // Arrange
        Lending lending = new Lending(book, readerDetails, 1, lendingDurationInDays, fineValuePerDayInCents);

        // Act
        ReaderDetails actualReaderDetails = lending.getReaderDetails();

        // Assert
        assertEquals(readerDetails, actualReaderDetails, "Reader details should match the original details");
    }

    @Test
    void testGetStartDate() {
        // Arrange
        Lending lending = new Lending(book, readerDetails, 1, lendingDurationInDays, fineValuePerDayInCents);

        // Act
        LocalDate startDate = lending.getStartDate();

        // Assert
        assertEquals(LocalDate.now(), startDate, "Start date should be the current date");
    }

    @Test
    void testGetLimitDate() {
        // Arrange
        Lending lending = new Lending(book, readerDetails, 1, lendingDurationInDays, fineValuePerDayInCents);
        LocalDate expectedLimitDate = LocalDate.now().plusDays(lendingDurationInDays);

        // Act
        LocalDate actualLimitDate = lending.getLimitDate();

        // Assert
        assertEquals(expectedLimitDate, actualLimitDate, "Limit date should be the current date plus the lending duration");
    }

    @Test
    void testGetReturnedDate() {
        // Arrange
        Lending lending = new Lending(book, readerDetails, 1, lendingDurationInDays, fineValuePerDayInCents);

        // Act
        LocalDate returnedDate = lending.getReturnedDate();

        // Assert
        assertNull(returnedDate, "Returned date should be null initially");
    }


}
