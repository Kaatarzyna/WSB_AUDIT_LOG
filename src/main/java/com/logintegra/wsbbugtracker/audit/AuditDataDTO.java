package com.logintegra.wsbbugtracker.audit;

import com.logintegra.wsbbugtracker.enums.State;
import com.logintegra.wsbbugtracker.issues.Issue;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.RevisionType;

import java.util.Date;

@Getter
@Setter
public class AuditDataDTO {

    Date date;
    String actor;

    RevisionType revisionType;

    String title;
    State state;

    public AuditDataDTO(Object[] revision) {
        AuditedRevisionEntity auditedRevisionEntity = (AuditedRevisionEntity) revision[1];

        this.date = new Date(auditedRevisionEntity.getTimestamp());
        this.actor = auditedRevisionEntity.getActor();

        this.revisionType = (RevisionType) revision[2];

        Issue issue = (Issue) revision[0];
        this.title = issue.getTitle();
        this.state = issue.getState();
    }
}
