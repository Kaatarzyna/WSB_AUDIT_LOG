package com.logintegra.wsbbugtracker.issues;

import com.logintegra.wsbbugtracker.enums.State;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IssueService {

    private final IssueRepository issueRepository;

    public void updateState(Long id, State state) {
        Issue issue = issueRepository.getReferenceById(id);
        issue.setState(state);
        issueRepository.save(issue);
    }

}
