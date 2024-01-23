package ru.gb.spring.homework.sem3.api;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.gb.spring.homework.sem3.model.dto.IssueDto;
import ru.gb.spring.homework.sem3.service.BookService;
import ru.gb.spring.homework.sem3.service.IssuerService;
import ru.gb.spring.homework.sem3.service.ReaderService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/ui")
@RequiredArgsConstructor
public class UiController {

    private final BookService bookService;
    private final ReaderService readerService;
    private final IssuerService issuerService;

    /**
     * 1.1 /ui/books - на странице должен отобразиться список всех доступных книг в системе
     */
    @GetMapping("/books")
    public String getAllBooks(Model model) {
        model.addAttribute("books", bookService.getBooksAll());
        return "books";
    }

    /**
     * 1.2 /ui/reader - аналогично 1.1
     */
    @GetMapping("/readers")
    public String getAllReaders(Model model) {
        model.addAttribute("readers", readerService.getReadersAll());
        return "readers";
    }

    /**
     * 1.3 /ui/issues - на странице отображается таблица, в которой есть столбцы
     * (книга, читатель, когда взял, когда вернул (если не вернул - пустая ячейка)).
     */
    @GetMapping("/issues")
    public String getAllIssues(Model model) {
        List<IssueDto> issueDtoList = issuerService.getIssuesAll().stream()
                        .map(issuerService::getIssueDto)
                                .collect(Collectors.toCollection(ArrayList::new));
        model.addAttribute("issues", issueDtoList);
        return "issues";
    }

    /**
     * 1.4* /ui/reader/{id} - страница, где написано имя читателя с идентификатором id и перечислены книги,
     * которые на руках у этого читателя.
     */
    @GetMapping("/reader/{id}")
    public String getReaderById(@PathVariable("id") Long id, Model model) {
        model.addAttribute("reader", readerService.getReaderById(id));
        model.addAttribute("books", issuerService.getBooksWhichHaveNotBeenReturnedByReaderId(id));
        return "reader";
    }
}
