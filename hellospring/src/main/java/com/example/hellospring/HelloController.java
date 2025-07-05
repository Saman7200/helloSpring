package com.example.hellospring;

import jakarta.validation.Valid;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class HelloController {

    private final PersonRepository repository;

    public HelloController(PersonRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/")
    public String showForm(Model model) {
        model.addAttribute("person", new Person());
        return "form";
    }

    @PostMapping("/hello")
    public String sayhello(@Valid Person person, BindingResult result, Model model){
        if (result.hasErrors()) {
            return "form";
        }
        repository.save(person);
        model.addAttribute("message", "hi " + person.getName() + "!");
        return "result";
    }

    @GetMapping("/list")
    public String showList(Model model) {
        model.addAttribute("people", repository.findAll());
        return "list";
    }

    @GetMapping("/delete/{id}")
    public String deletePerson(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        }
        return "redirect:/list";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model){
        Person person = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid Id: " + id));
        model.addAttribute("person",person);
        return "edit";
    }

    @PostMapping("/update")
    public String updatePerson(@Valid Person person, BindingResult result, Model model){

        if (result.hasErrors()) {
            return "edit";

        }
        repository.save(person);
        return "redirect:/list";
    }

    @GetMapping("/search")
    public String searchForm(){
        return "search";
    }

    @PostMapping("/search")
    public String searchResult(@RequestParam String keyword, Model model){
        List<Person> result = repository.findByNameContainingIgnoreCase(keyword);
        model.addAttribute("result", result);
        model.addAttribute("keyword", keyword);
        return "search";
    }
}
