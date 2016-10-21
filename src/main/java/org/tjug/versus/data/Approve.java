package org.tjug.versus.data;

import java.util.Date;

public class Approve {

    private final Integer userId;

    private final Integer approveValue;

    private final Date grantDate;

    public Approve(Integer userId, Integer approveValue, Date grantDate) {
        this.userId = userId;
        this.approveValue = approveValue;
        this.grantDate = grantDate;
    }

    public Integer getUserId() {
        return userId;
    }

    public Integer getApproveValue() {
        return approveValue;
    }

    public Date getGrantDate() {
        return grantDate;
    }
}
