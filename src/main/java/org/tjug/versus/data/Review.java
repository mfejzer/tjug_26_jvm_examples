package org.tjug.versus.data;

import java.util.Date;
import java.util.List;

public class Review {

    private final ReviewStatus reviewStatus;

    private final List<Approve> approveHistory;

    private final Date submitDate;

    private final Integer changeId;

    private final String project;

    private final Date closeDate;

    private final List<String> files;

    public Review(ReviewStatus reviewStatus, List<Approve> approveHistory, Date submitDate, Integer changeId, String project, Date closeDate, List<String> files) {
        this.reviewStatus = reviewStatus;
        this.approveHistory = approveHistory;
        this.submitDate = submitDate;
        this.changeId = changeId;
        this.project = project;
        this.closeDate = closeDate;
        this.files = files;
    }

    public ReviewStatus getReviewStatus() {
        return reviewStatus;
    }

    public List<Approve> getApproveHistory() {
        return approveHistory;
    }

    public Date getSubmitDate() {
        return submitDate;
    }

    public Integer getChangeId() {
        return changeId;
    }

    public String getProject() {
        return project;
    }

    public Date getCloseDate() {
        return closeDate;
    }

    public List<String> getFiles() {
        return files;
    }
}
