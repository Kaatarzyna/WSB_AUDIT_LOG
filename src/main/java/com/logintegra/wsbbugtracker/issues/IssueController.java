package com.logintegra.wsbbugtracker.issues;

import com.logintegra.wsbbugtracker.audit.AuditDataDTO;
import com.logintegra.wsbbugtracker.enums.State;
import com.logintegra.wsbbugtracker.people.PersonRepository;
import com.logintegra.wsbbugtracker.projects.ProjectRepository;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import jakarta.persistence.EntityManager;

import java.util.List;

@Controller
@RequestMapping("/issue")
public class IssueController {

    final IssueRepository issueRepository;
    final IssueService issueService;
    final ProjectRepository projectRepository;
    final PersonRepository personRepository;
    final EntityManager entityManager;

    public IssueController(IssueRepository issueRepository, IssueService issueService, ProjectRepository projectRepository, PersonRepository personRepository, EntityManager entityManager) {
        this.issueRepository = issueRepository;
        this.issueService = issueService;
        this.projectRepository = projectRepository;
        this.personRepository = personRepository;
        this.entityManager = entityManager;
    }

    @GetMapping
    ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("issue/index");

        modelAndView.addObject("issues", issueRepository.findAll());

        return modelAndView;
    }

    @GetMapping("/{id}")
    ModelAndView show(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("issue/show");

        modelAndView.addObject("issue", issueRepository.getReferenceById(id));
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
        Issue issue = issueRepository.getReferenceById(id);
        issueRepository.delete(issue);

        return "redirect:/issue";
    }

    @GetMapping("/history/{id}")
    ModelAndView history(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("issue/history");

        AuditReader auditReader = AuditReaderFactory.get(entityManager);

        List<Object[]> rawRevisions = auditReader.createQuery()
                .forRevisionsOfEntity(Issue.class, false, true)
                .add(AuditEntity.id().eq(id))
                .getResultList();

        List<AuditDataDTO> revisions = rawRevisions.stream().map(AuditDataDTO::new).toList();
        modelAndView.addObject("revisions", revisions);

        return modelAndView;
    }

    @PatchMapping("/state/{id}")
    ResponseEntity<Void> updateState(@PathVariable Long id, @RequestBody State state) {
        issueService.updateState(id, state);
        return ResponseEntity.ok().build();
    }
}
