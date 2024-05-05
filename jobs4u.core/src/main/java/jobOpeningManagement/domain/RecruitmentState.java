package jobOpeningManagement.domain;

public enum RecruitmentState {
    APPLICATION("Application"),
    SCREENING("Screening"),
    INTERVIEWS("InterviewS"),
    ANALYSIS("Analysis"),
    RESULT("Result");
    
    private final String state;

    RecruitmentState(String state){
        this.state = state;
    }

    public String getState(){
        return state;
    }

}
