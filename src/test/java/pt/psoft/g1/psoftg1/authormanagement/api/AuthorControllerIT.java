package pt.psoft.g1.psoftg1.authormanagement.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.authormanagement.services.AuthorService;
import pt.psoft.g1.psoftg1.authormanagement.services.CreateAuthorRequest;
import pt.psoft.g1.psoftg1.authormanagement.services.UpdateAuthorRequest;
import pt.psoft.g1.psoftg1.shared.services.ConcurrencyService;
import pt.psoft.g1.psoftg1.shared.services.FileStorageService;
import pt.psoft.g1.psoftg1.bookmanagement.api.BookViewMapper; // Não esqueça de importar

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AuthorController.class)
@AutoConfigureMockMvc
public class AuthorControllerIT {
/*
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private AuthorViewMapper authorViewMapper;

    @MockBean
    private BookViewMapper bookViewMapper; // Adicione esta linha

    @MockBean
    private ConcurrencyService concurrencyService;

    @MockBean
    private FileStorageService fileStorageService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void whenCreateAuthor_thenReturnCreatedAuthor() throws Exception {
        CreateAuthorRequest request = new CreateAuthorRequest("New Author", "Bio of New Author", null, null);
        Author author = new Author("New Author", "Bio of New Author", null);
        given(authorService.create(request)).willReturn(author);

        mockMvc.perform(post("/api/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"New Author\",\"biography\":\"Bio of New Author\"}"))
                .andExpect(status().isCreated());
    }

    @Test
    public void whenFindAuthorByNumber_thenReturnAuthor() throws Exception {
        Author author = new Author("Existing Author", "Biography", null);
        given(authorService.findByAuthorNumber(author.getAuthorNumber())).willReturn(Optional.of(author));

        mockMvc.perform(get("/api/authors/{authorNumber}", author.getAuthorNumber()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(author.getName()));
    }

    @Test
    public void whenUpdateAuthor_thenReturnUpdatedAuthor() throws Exception {
        Author author = new Author("Author to Update", "Old Bio", null);
        UpdateAuthorRequest updateRequest = new UpdateAuthorRequest("Updated Author", "Updated Bio", null, null);
        given(authorService.partialUpdate(author.getAuthorNumber(), updateRequest, author.getVersion()))
                .willReturn(author);

        mockMvc.perform(patch("/api/authors/{authorNumber}", author.getAuthorNumber())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Updated Author\",\"biography\":\"Updated Bio\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Author"));
    }
*/
    /*
    @Test
    public void whenDeleteAuthorPhoto_thenReturnOk() throws Exception {
        Author author = new Author("Author With Photo", "Bio", new Photo("photoFile.jpg"));
        given(authorService.findByAuthorNumber(author.getAuthorNumber())).willReturn(Optional.of(author));

        mockMvc.perform(delete("/api/authors/{authorNumber}/photo", author.getAuthorNumber()))
                .andExpect(status().isOk());
    }
     */

    /*
    @Test
    public void whenGetBooksByAuthorNumber_thenReturnBooks() throws Exception {
        Author author = new Author("Author With Books", "Bio", null);
        // Simule o autorNumber como se fosse gerado automaticamente
        ReflectionTestUtils.setField(author, "authorNumber", 1L); // Usando ReflectionTestUtils para definir o valor
        given(authorService.findByAuthorNumber(author.getAuthorNumber())).willReturn(Optional.of(author));

        mockMvc.perform(get("/api/authors/{authorNumber}/books", author.getAuthorNumber()))
                .andExpect(status().isOk());
    }

*/
}
