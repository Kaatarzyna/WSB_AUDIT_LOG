package com.logintegra.wsbbugtracker.audit;

import lombok.experimental.UtilityClass;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@UtilityClass
public class AuditDataMapper {

    public List<AuditDataDTO> map(List<Object[]> rawRevisions) {
        return rawRevisions.stream()
                .map(r -> new AuditDataDTO((Object[]) r))
                .collect(Collectors.toList());
    }

}
