package jobOpeningManagement.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Date;

public enum RecruitmentState {
    APPLICATION("Application", null, null),
    SCREENING("Screening", null, null),
    INTERVIEWS("Interviews", null, null),
    ANALYSIS("Analysis", null, null),
    RESULT("Result", null, null);

    @Getter
    private final String state;

    @Getter
    @Setter
    private Date startDate;

    @Getter
    @Setter
    private Date endDate;

    RecruitmentState(String state, Date startDate, Date endDate){
        this.state = state;
        this.startDate = Date.from(Instant.now());
        this.endDate = null;
    }

}
