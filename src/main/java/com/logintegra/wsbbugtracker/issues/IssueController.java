package com.logintegra.wsbbugtracker.issues;

import com.logintegra.wsbbugtracker.people.PersonRepository;
import com.logintegra.wsbbugtracker.projects.ProjectRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/issue")
public class IssueController {

    final IssueRepository issueRepository;
    final ProjectRepository projectRepository;
    final PersonRepository personRepository;

    public IssueController(IssueRepository issueRepository, ProjectRepository projectRepository, PersonRepository personRepository) {
        this.issueRepository = issueRepository;
        this.projectRepository = projectRepository;
        this.personRepository = personRepository;
    }

    @GetMapping
    ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("issue/index");

        modelAndView.addObject("issues", issueRepository.findAll());

        return modelAndView;
    }

    @GetMapping("{id}")
    ModelAndView show(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("issue/show");

        modelAndView.addObject("issue", issueRepository.getOne(id));
        modelAndView.addObject("projects", projectRepository.findAll());
        modelAndView.addObject("people", personRepository.findAll());

        return modelAndView;
    }

    @GetMapping("/create")
    ModelAndView create() {
        ModelAndView modelAndView = new ModelAndView("issue/show");

        modelAndView.addObject("issue", new Issue());
        modelAndView.addObject("projects", projectRepository.findAll());
        modelAndView.addObject("people", personRepository.findAll());

        return modelAndView;
    }

    @PostMapping
    String save(@ModelAttribute Issue issue) {
        Issue savedIssue = issueRepository.save(issue);

        return "redirect:/issue/" + savedIssue.getId();
    }

    @GetMapping("/delete/{id}")
    String delete(@PathVariable Long id) {
        Issue issue = issueRepository.getOne(id);
        issueRepository.delete(issue);

        return "redirect:/issue";
    }
}
