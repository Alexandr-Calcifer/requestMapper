package ru.pupenkov.spb.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.pupenkov.spb.dao.PersonDao;
import ru.pupenkov.spb.model.Person;

import javax.validation.Valid;
import java.io.FileWriter;
import java.io.IOException;

@Controller
@RequestMapping("/home")
public class Greeting {

    private final PersonDao personDao;

    @Autowired
    public Greeting(PersonDao personDao) {
        this.personDao = personDao;
    }

    @GetMapping("/greeting")
    public String sayGreeting(
            @RequestParam(value = "name", required = false) String firstName,
            @RequestParam(value = "mame", required = false) String lastName,
            Model model){
        model.addAttribute("message", "Params in model: " + firstName + " " + lastName);
        return "main/greeting";
    }

    @GetMapping("/greeting/getUsers")
    public String sayGreeting(Model model){
        model.addAttribute("person", personDao.getPerson());
        return "main/usersShow";
    }

    @GetMapping("/{id}")
    public String getPersonById(@PathVariable("id") int id, Model model){
        model.addAttribute("person", personDao.getPersonById(id));
        return "main/userPage";
    }

    @GetMapping("/newPerson")
    public String newPerson(@ModelAttribute("person") Person person){
        return "main/newPerson";
    }

    @PostMapping("/create")
    public String createPerson(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "main/newPerson";
        }
        personDao.save(person);
        return "redirect:/home/greeting/getUsers";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model){
        model.addAttribute("person", personDao.getPersonById(id));
        return "main/edit";
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable("id") int id, @ModelAttribute("person") @Valid Person person, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "main/newPerson";
        }
        personDao.update(id, person);
        return "redirect:/home/{id}";
    }

    @GetMapping("/calculater")
    public String calculate(
            @RequestParam(value = "x", required = false) int x,
            @RequestParam(value = "y", required = false) int y,
            @RequestParam(value = "action", required = false) char action,
            Model model){
        double sum = 0;
        switch (action){
            case '*': sum = x * y;
                break;
            case '/': sum = x / (double)y;
                break;
            case '+': sum = x + y;
                break;
            case '-': sum = x - y;
                break;
            default: sum = 0;
                break;
        }
        model.addAttribute("message", "sum = " + sum);
        return "main/greeting";
    }

    @GetMapping("/logout")
    public String logout(){

        return "main/logout";
    }

    public static void main(String[] args) {
        try {
            FileWriter fr = new FileWriter("D:\\Programs\\oper.csv");
            for (int i = 0; i < 10000; i++) {
                fr.write("01 22" + "," + (int)(111111 + i) + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
