package guru.springframework.sdjpaintro;

import guru.springframework.sdjpaintro.domain.AuthorPrimaryKeyUuid;
import guru.springframework.sdjpaintro.domain.BookNatural;
import guru.springframework.sdjpaintro.domain.BookUuid;
import guru.springframework.sdjpaintro.domain.composite.AuthorComposite;
import guru.springframework.sdjpaintro.domain.composite.NameId;
import guru.springframework.sdjpaintro.repositories.*;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * Created by jt on 7/4/21.
 */
@ActiveProfiles("local")
@DataJpaTest
@ComponentScan(basePackages = {"guru.springframework.sdjpaintro.bootstrap"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MySQLIntegrationTest {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    BookUuidRepository bookUuidRepository;

    @Autowired
    AuthorUuidRepository authorUuidRepository;

    @Autowired
    BookNaturalRepository bookNaturalRepository;
    
    @Autowired
    AuthorCompositeRepository authorCompositeRepository;

    @Test
    void testAuthorComposite() {
        NameId nameId = new NameId("TestFirst", "TestLast");
        AuthorComposite authorComposite = new AuthorComposite();
        authorComposite.setFirstName(nameId.getFirstName());
        authorComposite.setLastName(nameId.getLastName());
        authorComposite.setCountry("TR");

        AuthorComposite saved = authorCompositeRepository.save(authorComposite);
        assertThat(saved).isNotNull();

        AuthorComposite fetched = authorCompositeRepository.getReferenceById(nameId);
        assertThat(fetched).isNotNull();
    }

    @Test
    void testMySQL() {
        long countBefore = bookRepository.count();
        assertThat(countBefore).isEqualTo(2);

    }

    @Test
    void testBookNaturalRepository() {
        BookNatural bookNatural = new BookNatural();
        bookNatural.setTitle("my book");
        BookNatural saved = bookNaturalRepository.save(bookNatural);
        assertThat(saved).isNotNull();

        BookNatural fetched = bookNaturalRepository.getReferenceById(bookNatural.getTitle());
        assertThat(fetched).isNotNull();
        assertThat(fetched.getTitle()).isEqualToIgnoringCase(saved.getTitle());

    }

    @Test
    void testBookUidRepository() {
        BookUuid bookUuid = bookUuidRepository.save(new BookUuid());
        assertThat(bookUuid).isNotNull();
        assertThat(bookUuid.getId()).isNotNull();

        BookUuid fetched = bookUuidRepository.getReferenceById(bookUuid.getId());
        assertThat(fetched).isNotNull();
        assertThat(fetched.getId()).isEqualTo(bookUuid.getId());
    }

    @Test
    void testAuthorUuidRepository() {
        AuthorPrimaryKeyUuid authorUuid = authorUuidRepository.save(new AuthorPrimaryKeyUuid());
        assertThat(authorUuid).isNotNull();
        assertThat(authorUuid.getId()).isNotNull();

        AuthorPrimaryKeyUuid fetched = authorUuidRepository.getReferenceById(authorUuid.getId());
        assertThat(fetched).isNotNull();

        assertThat(fetched.getId()).isEqualTo(authorUuid.getId());
    }

}


