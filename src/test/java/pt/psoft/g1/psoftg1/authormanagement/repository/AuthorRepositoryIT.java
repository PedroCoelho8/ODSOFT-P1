package pt.psoft.g1.psoftg1.authormanagement.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.authormanagement.repositories.AuthorRepository;

import java.util.ArrayList;
import java.util.List;

import static org.aspectj.bridge.MessageUtil.fail;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Based on https://www.baeldung.com/spring-boot-testing
 * <p>Adaptations to Junit 5 with ChatGPT
 */
@ExtendWith(SpringExtension.class)
@DataJpaTest
public class AuthorRepositoryIT {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private AuthorRepository authorRepository;

    @Test
    public void whenFindByName_thenReturnAuthor() {
        // given
        Author alex = new Author("Alex", "O Alex escreveu livros", null);
        entityManager.persist(alex);
        entityManager.flush();

        // when
        List<Author> list = authorRepository.searchByNameName(alex.getName());

        // then
        assertThat(list).isNotEmpty();
        assertThat(list.get(0).getName())
                .isEqualTo(alex.getName());
    }

    @Test
    public void whenFindByNameNonExistent_thenReturnEmptyList() {
        // when
        List<Author> list = authorRepository.searchByNameName("Nonexistent");

        // then
        assertThat(list).isEmpty();
    }

    @Test
    public void whenFindAll_thenReturnAllAuthors() {
        // given
        Author author1 = new Author("Author One", "Bio One", null);
        Author author2 = new Author("Author Two", "Bio Two", null);
        entityManager.persist(author1);
        entityManager.persist(author2);
        entityManager.flush();

        // when
        Iterable<Author> iterableAuthors = authorRepository.findAll();
        List<Author> authors = new ArrayList<>();
        iterableAuthors.forEach(authors::add); // Converte Iterable para List

        // then
        assertThat(authors).hasSize(2);
    }

    @Test
    public void whenDeleteAuthor_thenShouldNotFindAuthor() {
        // given
        Author author = new Author("To Be Deleted", "Bio", null);
        entityManager.persist(author);
        entityManager.flush();

        // when
        authorRepository.delete(author);
        entityManager.flush(); // Persistir a mudança

        // then
        List<Author> list = authorRepository.searchByNameName("To Be Deleted");
        assertThat(list).isEmpty();
    }

    @Test
    public void whenFindById_thenReturnAuthor() {
        // given
        Author author = new Author("Unique Author", "Bio", null);
        entityManager.persist(author);
        entityManager.flush();

        // when
        Author foundAuthor = authorRepository.findByAuthorNumber(author.getId()).orElse(null);

        // then
        assertThat(foundAuthor).isNotNull();
        assertThat(foundAuthor.getName()).isEqualTo(author.getName());
    }

    @Test
    public void whenUpdateAuthor_thenAuthorIsUpdated() {
        // given
        Author author = new Author("Old Name", "Old Bio", null);
        entityManager.persist(author);
        entityManager.flush();

        // when
        author.setName("New Name");
        author.setBio("New Bio");
        authorRepository.save(author);

        // then
        Author updatedAuthor = authorRepository.findByAuthorNumber(author.getId()).orElse(null);
        assertThat(updatedAuthor).isNotNull();
        assertThat(updatedAuthor.getName()).isEqualTo("New Name");
        assertThat(updatedAuthor.getBio()).isEqualTo("New Bio");
    }

    @Test
    public void whenInsertAuthorIfNotExists_thenAuthorIsInserted() {
        // given
        String authorName = "New Author";
        List<Author> existingAuthors = authorRepository.searchByNameName(authorName);

        if (!existingAuthors.isEmpty()) {
            fail("Author already exists in the database.");
        }

        Author newAuthor = new Author(authorName, "Bio for New Author", null);
        entityManager.persist(newAuthor);
        entityManager.flush();

        // when
        List<Author> authorsAfterInsert = authorRepository.searchByNameName(authorName);

        // then
        assertThat(authorsAfterInsert).isNotEmpty();
        assertThat(authorsAfterInsert.get(0).getName()).isEqualTo(newAuthor.getName());
    }

}