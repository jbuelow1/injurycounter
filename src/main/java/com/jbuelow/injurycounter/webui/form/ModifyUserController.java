package com.jbuelow.injurycounter.webui.form;

import com.jbuelow.injurycounter.data.entity.Person;
import com.jbuelow.injurycounter.data.repo.PersonRepository;
import com.jbuelow.injurycounter.data.repo.TeamRepository;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Objects;
import java.util.Optional;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ModifyUserController {

  private final PersonRepository personRepo;
  private final TeamRepository teamRepo;

  public ModifyUserController(PersonRepository personRepo,
      TeamRepository teamRepo) {
    this.personRepo = personRepo;
    this.teamRepo = teamRepo;
  }

  @InitBinder
  public void initBinder(WebDataBinder binder) {
    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    binder.registerCustomEditor(Date.class,
        new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true, 10));
  }

  @GetMapping("/person")
  public String getUserForm(Model model, @RequestParam(required = false) Long id) {
    if (Objects.isNull(id)) {
      model.addAttribute("command", new Person());
    } else {
      Optional<Person> p = personRepo.findById(id);
      if (p.isPresent()) {
        model.addAttribute("userFound", true);
        model.addAttribute("command", p.get());
      } else {
        model.addAttribute("userNotFound", true);
        model.addAttribute("command", new Person());
      }
    }

    model.addAttribute("teams", teamRepo.findAll());

    return "addUser";
  }

  @PostMapping("/person")
  public String postModifiedUser(Model model, Person person) {
    personRepo.save(person);
    model.addAttribute("command", person);
    model.addAttribute("submitted", true);
    model.addAttribute("teams", teamRepo.findAll());
    return "addUser";
  }

}
