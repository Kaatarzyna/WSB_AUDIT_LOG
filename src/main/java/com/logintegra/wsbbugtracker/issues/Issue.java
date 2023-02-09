package com.logintegra.wsbbugtracker.issues;

import com.logintegra.wsbbugtracker.enums.State;
import com.logintegra.wsbbugtracker.people.Person;
import com.logintegra.wsbbugtracker.projects.Project;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
public class Issue {

    @Id
    @GeneratedValue
    Long id;

    @Audited
    @Column(nullable = false)
    String title;

    @Column(columnDefinition = "TEXT")
    String content;

    @Audited
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    State state = State.TODO;

    @ManyToOne()
    @JoinColumn(name = "assignee_id")
    Person assignee;

    @ManyToOne(optional = false)
    @JoinColumn(name = "project_id", nullable = false)
    Project project;

    @Column(updatable = false)
    @CreatedBy
    String createdBy;

    @Column(updatable = false)
    @CreatedDate
    Date createdDate;

    @LastModifiedBy
    String lastModifiedBy;

    @LastModifiedDate
    Date lastModifiedDate;

}
