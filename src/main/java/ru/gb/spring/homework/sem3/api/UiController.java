package ru.gb.spring.homework.sem3.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.gb.spring.homework.sem3.model.Book;
import ru.gb.spring.homework.sem3.model.Issue;
import ru.gb.spring.homework.sem3.model.Reader;
import ru.gb.spring.homework.sem3.service.*;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Controller
@RequestMapping("/ui")
@RequiredArgsConstructor
public class UiController {

    private final BookService bookService;
    private final ReaderService readerService;
    private final IssuerService issuerService;
    private final UserService userService;

    /**
     * Задание для 4 семинара
     * 1.1 /ui/books - на странице должен отобразиться список всех доступных книг в системе
     * Задание для 7 семинара
     * Ресурсы книг (books) доступны всем авторизованным пользователям
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/books")
    public String getAllBooks(Model model) {
        BookRequest bookRequest = new BookRequest();
        model.addAttribute("bookRequest", bookRequest);
        model.addAttribute("books", bookService.findAll());
        log.warn(userService.findByLogin("admin").getRoles().toString());
        return "books";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/books")
    public String addBooks(@ModelAttribute("bookRequest") BookRequest bookRequest) {
        bookService.add(bookRequest);
        return "redirect:/ui/books";
    }

    /**
     * Задание для 4 семинара
     * 1.2 /ui/reader - аналогично 1.1
     * Задание для 7 семинара
     * 3.4* Ресурсы читателей (reader) доступны всем обладателям роли reader
     */
    @PreAuthorize("hasAuthority('READER')")
    @GetMapping("/readers")
    public String getAllReaders(Model model) {
        ReaderRequest readerRequest = new ReaderRequest();
        model.addAttribute("readers", readerService.findAll());
        model.addAttribute("readerRequest", readerRequest);

        return "readers";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/readers")
    public String addReader(@ModelAttribute("readerRequest") ReaderRequest readerRequest) {
        readerService.add(readerRequest);
        return "redirect:/ui/readers";
    }

    /**
     * Задание для 4 семинара
     * 1.3 /ui/issues - на странице отображается таблица, в которой есть столбцы
     * (книга, читатель, когда взял, когда вернул (если не вернул - пустая ячейка)).
     * Задание для 7 семинара
     * 3.3* Ресурсы выдачей (issue) доступны обладателям роли admin
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/issues")
    public String getAllIssues(Model model) {
        IssueRequest issueRequest = new IssueRequest();
        List<Issue> issues = issuerService.findAll();
        List<Reader> readers = readerService.findAll();
        List<Book> books = bookService.findAll();
        model.addAttribute("issues", issues);
        model.addAttribute("readers", readers);
        model.addAttribute("books", books);
        model.addAttribute("issueRequest", issueRequest);
        return "issues";
    }

    /**
     * Задание для 7 семинара
     * 3.4* Ресурсы читателей (reader) доступны всем обладателям роли reader
     */
    @PreAuthorize("hasAuthority('READER')")
    @PostMapping("/issues/{bookId}/{readerId}")
    public String updateIssueReturnedAt(@PathVariable("bookId") Long bookId, @PathVariable("readerId") Long readerId) {
        List<Issue> issues = issuerService.findIssueByBookIdAndReaderIdAndReturnedAtNull(bookId, readerId);
        Issue issue = issues.getFirst();
        try {
            issuerService.updateIssueReturnedAt(issue.getId());
        } catch (NoSuchElementException e) {
            log.warn(e.getMessage());
        }

        return "redirect:/ui/reader/" + readerId;
    }

    /**
     * Задание для 4 семинара
     * 1.4* /ui/reader/{id} - страница, где написано имя читателя с идентификатором id и перечислены книги,
     * которые на руках у этого читателя.
     * Задание для 7 семинара
     * 3.4* Ресурсы читателей (reader) доступны всем обладателям роли reader
     */
    @PreAuthorize("hasAuthority('READER')")
    @GetMapping("/reader/{id}")
    public String getReaderById(@PathVariable("id") Long id, Model model) {
        Reader reader = readerService.findById(id);
        List<Book> books = issuerService.findBookByReaderAndReturnedAtNull(reader);
        model.addAttribute("reader", reader);
        model.addAttribute("books", books);
        return "reader";
    }

    /**
     * Задание для 7 семинара
     * 3.3* Ресурсы выдачей (issue) доступны обладателям роли admin
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/issues")
    public String addIssues(@ModelAttribute("issueRequest") IssueRequest issueRequest) {
        try {
            issuerService.issue(issueRequest);
        } catch (NoSuchElementException | MaxAllowedBooksException e) {
            log.warn(e.getMessage());
        }
        return "redirect:/ui/issues";
    }

    @GetMapping("/403")
    public String page403() {
        return "403";
    }
}
