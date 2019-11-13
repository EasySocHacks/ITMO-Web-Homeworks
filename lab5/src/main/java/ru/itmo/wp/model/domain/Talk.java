package ru.itmo.wp.model.domain;

import java.io.Serializable;
import java.util.Date;

public class Talk implements Serializable {
    public static final int MAX_TEXT_LENGTH = 255;

    long id;
    long sourceUserId;
    long targetUserId;
    String text;
    Date creationTime;

    public Talk() {}

    public Talk(long sourceUserId, long targetUserId, String text) {
        this.sourceUserId = sourceUserId;
        this.targetUserId = targetUserId;
        this.text = text;
    }

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public long getSourceUserId() { return sourceUserId; }

    public void setSourceUserId(long sourceUserId) { this.sourceUserId = sourceUserId; }

    public long getTargetUserId() { return targetUserId; }

    public void setTargetUserId(long targetUserId) { this.targetUserId = targetUserId; }

    public String getText() { return text; }

    public void setText(String text) { this.text = text; }

    public Date getCreationTime() { return creationTime; }

    public void setCreationTime(Date creationTime) { this.creationTime = creationTime; }
}
