package org.example;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Controller
@RequestMapping("/librarian")
public class LibrarianController {
    private final LibraryXmlService service;

    public LibrarianController(LibraryXmlService service) {
        this.service = service;
    }

    @GetMapping("/menu")
    public String menu() {
        return "librarian_menu";
    }

    @GetMapping("/books")
    public String books(Model model) {
        model.addAttribute("books", service.listAll());
        return "books";
    }

    @GetMapping("/add")
    public String addForm() {
        return "librarian_add";
    }

    @PostMapping("/add")
    public String add(@RequestParam int id,
                      @RequestParam String title,
                      @RequestParam String author,
                      @RequestParam int year,
                      @RequestParam BigDecimal price,
                      @RequestParam String category,
                      @RequestParam int copies,
                      @RequestParam int available,
                      Model model) {
        try {
            service.addBook(id, title, author, year, price, category, copies, available);
            return "redirect:/librarian/books";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "librarian_add";
        }
    }

    @GetMapping("/price")
    public String priceForm() {
        return "librarian_price";
    }

    @PostMapping("/price")
    public String changePrice(@RequestParam int id,
                              @RequestParam BigDecimal newPrice,
                              Model model) {
        try {
            service.changePrice(id, newPrice);
            return "redirect:/librarian/books";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "librarian_price";
        }
    }

    @GetMapping("/issue")
    public String issueForm() {
        return "librarian_issue_assign";
    }

    @PostMapping("/issue")
    public String issue(@RequestParam int id, Model model) {
        try {
            service.issue(id);
            model.addAttribute("message", "Книга выдана");
            return "librarian_issue_assign";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "librarian_issue_assign";
        }
    }
}
