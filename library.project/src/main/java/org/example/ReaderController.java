package org.example;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/reader")
public class ReaderController {
    private final LibraryXmlService service;
    private final ReaderAccountService accountService;

    public ReaderController(LibraryXmlService service, ReaderAccountService accountService) {
        this.service = service;
        this.accountService = accountService;
    }

    @GetMapping("/menu")
    public String menu() {
        return "reader_menu";
    }

    @GetMapping("/books")
    public String books(Model model) {
        model.addAttribute("books", service.listAll());
        return "books";
    }

    @GetMapping("/search")
    public String searchForm() {
        return "reader_search";
    }

    @GetMapping("/search/run")
    public String search(@RequestParam String type,
                         @RequestParam String value,
                         Model model) {
        String tag;
        switch (type) {
            case "author":
                tag = "author";
                break;
            case "year":
                tag = "year";
                break;
            case "category":
                tag = "category";
                break;
            default:
                tag = "title";
                break;
        }
        model.addAttribute("books", service.searchEquals(tag, value));
        return "books";
    }

    @GetMapping("/account")
    public String account(Authentication auth, Model model) {
        model.addAttribute("username", auth.getName());
        model.addAttribute("issued", accountService.listIssued(auth.getName()));
        return "reader_account";
    }
}
