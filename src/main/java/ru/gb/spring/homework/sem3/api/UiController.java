package ru.gb.spring.homework.sem3.api;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.gb.spring.homework.sem3.model.Book;
import ru.gb.spring.homework.sem3.model.Issue;
import ru.gb.spring.homework.sem3.model.Reader;
import ru.gb.spring.homework.sem3.service.BookService;
import ru.gb.spring.homework.sem3.service.IssuerService;
import ru.gb.spring.homework.sem3.service.ReaderService;

import java.util.List;

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
        List<Issue> issues = issuerService.findAll();
        model.addAttribute("issues", issues);
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
}
