package guru.springframework.sdjpaintro.bootstrap;

import guru.springframework.sdjpaintro.domain.AuthorPrimaryKeyUuid;
import guru.springframework.sdjpaintro.domain.Book;
import guru.springframework.sdjpaintro.domain.BookNatural;
import guru.springframework.sdjpaintro.domain.BookUuid;
import guru.springframework.sdjpaintro.domain.composite.AuthorComposite;
import guru.springframework.sdjpaintro.domain.composite.NameId;
import guru.springframework.sdjpaintro.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * Created by jt on 6/12/21.
 */
@Profile({"local", "default"})
@Component
public class DataInitializer implements CommandLineRunner {

    private final BookRepository bookRepository;
    private final AuthorUuidRepository authorUuidRepository;
    private final BookUuidRepository bookUuidRepository;
    private final BookNaturalRepository bookNaturalRepository;
    private final AuthorCompositeRepository authorCompositeRepository;

    public DataInitializer(BookRepository bookRepository, AuthorUuidRepository authorUuidRepository, BookUuidRepository bookUuidRepository, BookNaturalRepository bookNaturalRepository, AuthorCompositeRepository authorCompositeRepository) {
        this.bookRepository = bookRepository;
        this.authorUuidRepository = authorUuidRepository;
        this.bookUuidRepository = bookUuidRepository;
        this.bookNaturalRepository = bookNaturalRepository;
        this.authorCompositeRepository = authorCompositeRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        bookRepository.deleteAll();

        Book bookDDD = new Book("Domain Driven Design", "123", "RandomHouse", null);
        Book savedDDD = bookRepository.save(bookDDD);

        Book bookSIA = new Book("Spring In Action", "234234", "Oriely", null);
        Book savedSIA = bookRepository.save(bookSIA);

        bookRepository.findAll().forEach(book -> {
            System.out.println("Book Id: " + book.getId());
            System.out.println("Book Title: " + book.getTitle());
        });

        AuthorPrimaryKeyUuid authorUuid = new AuthorPrimaryKeyUuid();
        authorUuid.setFirstName("John");
        authorUuid.setLastName("Doe");
        AuthorPrimaryKeyUuid savedAuthorUuid = authorUuidRepository.save(authorUuid);
        System.out.println("Saved AuthorUUID with id: " + savedAuthorUuid.getId());

        BookUuid bookUuid = new BookUuid();
        bookUuid.setTitle("Intro in UUID");
        bookUuid.setIsbn("23456");
        bookUuid.setPublisher("O'Reilly");
        bookUuid.setAuthorId(null);

        BookUuid savedBookedUuid = bookUuidRepository.save(bookUuid);
        System.out.println("Saved BookUuid with id: " + savedBookedUuid.getId());

        //Natural Key example: NOT recommended - Natural key is defining one of the props of the object als PK
        //Like in this example the title is PK - As develop we are responsible ofc always to set this ID

        BookNatural bookNatural = new BookNatural();
        bookNatural.setTitle("NOTRECOMMENDED");
        bookNatural.setIsbn("122345");
        bookNatural.setPublisher("NOTRECOMMENDED");
        BookNatural savedBN = bookNaturalRepository.save(bookNatural);
        System.out.println("The BookNatural is saved with ID, id is Title here: " + savedBN.getTitle());

        //Composite key is combining 2 properties in a key like firstName and lastname
        NameId nameId = new NameId("serdar", "okcira");
        AuthorComposite authComposite = new AuthorComposite();
        authComposite.setFirstName(nameId.getFirstName());
        authComposite.setLastName(nameId.getLastName());
        authComposite.setCountry("TR");
        authorCompositeRepository.save(authComposite);

        System.out.println("The composite author saved");
    }
}
