package ru.gb.spring.homework.sem3.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.gb.spring.homework.sem3.model.Book;
import ru.gb.spring.homework.sem3.model.Issue;
import ru.gb.spring.homework.sem3.model.Reader;
import ru.gb.spring.homework.sem3.service.BookService;
import ru.gb.spring.homework.sem3.service.IssuerService;
import ru.gb.spring.homework.sem3.service.MaxAllowedBooksException;
import ru.gb.spring.homework.sem3.service.ReaderService;

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

    /**
     * Задание для 4 семинара
     * 1.1 /ui/books - на странице должен отобразиться список всех доступных книг в системе
     */
    @GetMapping("/books")
    public String getAllBooks(Model model) {
        model.addAttribute("books", bookService.findAll());
        return "books";
    }

    /**
     * Задание для 4 семинара
     * 1.2 /ui/reader - аналогично 1.1
     */
    @GetMapping("/readers")
    public String getAllReaders(Model model) {
        model.addAttribute("readers", readerService.findAll());
        return "readers";
    }

    /**
     * Задание для 4 семинара
     * 1.3 /ui/issues - на странице отображается таблица, в которой есть столбцы
     * (книга, читатель, когда взял, когда вернул (если не вернул - пустая ячейка)).
     */
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
     * Задание для 4 семинара
     * 1.4* /ui/reader/{id} - страница, где написано имя читателя с идентификатором id и перечислены книги,
     * которые на руках у этого читателя.
     */
    @GetMapping("/reader/{id}")
    public String getReaderById(@PathVariable("id") Long id, Model model) {
        Reader reader = readerService.findById(id);
        List<Book> books = issuerService.findBookByReaderAndReturnedAtNull(reader);
        model.addAttribute("reader", reader);
        model.addAttribute("books", books);
        return "reader";
    }

    @PostMapping("/issues")
    public String addIssues(@ModelAttribute("issueRequest") IssueRequest issueRequest) {
        try {
            issuerService.issue(issueRequest);
        } catch (NoSuchElementException | MaxAllowedBooksException e) {
            log.warn(e.getMessage());
        }
        return "redirect:/ui/issues";
    }
}
