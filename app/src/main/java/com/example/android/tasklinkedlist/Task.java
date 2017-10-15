package com.example.android.tasklinkedlist;

import java.util.Date;
import java.util.UUID;

/**
 * Project: TaskLinkedList File: Task.java
 * Created by G.E. Eidsness on 2017-08-15.
 */

class Task {

    private int mpId;
    private UUID mId;
    private Date mDate;
    private String mTitle;
    private String mDescription;
    private boolean mTasked;
    private String mCategory;
    private String mPriority;

    Task() {
        mpId = (int )Math.floor((Math.random() * 100000) + 1);
        mId = UUID.randomUUID();
        mDate = new Date();
    }

    public int getIntId() { return mpId; }

    public UUID getId() { return mId; }

    Date getDate() { return mDate; }
    void setDate(Date date) { mDate = date; }

    String getTitle() { return mTitle; }
    void setTitle(String title) { mTitle = title; }

    String getDescription() {  return mDescription;  }
    void setDescription(String Description) { mDescription = Description; }

    boolean isTasked() { return mTasked; }
    void setTasked(boolean solved) { mTasked = solved; }

    String getPriority() { return mPriority; }
    void setPriority(String Priority) {mPriority = Priority; }

    String getCategory() { return mCategory; }
    void setCategory(String Category) { mCategory = Category; }

     @Override
    public String toString() {
        return String.format(
                "%s [mpId=%s, mId=%s, mDate=%s, mTitle=%s, mDescription=%s, mTasked=%s, mCategory=%s, mPriority=%s]",
                getClass().getSimpleName(), mpId, mId, mDate, mTitle, mDescription, mTasked, mCategory, mPriority);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((mCategory == null) ? 0 : mCategory.hashCode());
        result = prime * result + ((mDate == null) ? 0 : mDate.hashCode());
        result = prime * result + ((mDescription == null) ? 0 : mDescription.hashCode());
        result = prime * result + ((mId == null) ? 0 : mId.hashCode());
        result = prime * result + ((mPriority == null) ? 0 : mPriority.hashCode());
        result = prime * result + (mTasked ? 1231 : 1237);
        result = prime * result + ((mTitle == null) ? 0 : mTitle.hashCode());
        result = prime * result + mpId;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Task other = (Task) obj;
        if (mCategory == null) {
            if (other.mCategory != null)
                return false;
        } else if (!mCategory.equals(other.mCategory))
            return false;
        if (mDate == null) {
            if (other.mDate != null)
                return false;
        } else if (!mDate.equals(other.mDate))
            return false;
        if (mDescription == null) {
            if (other.mDescription != null)
                return false;
        } else if (!mDescription.equals(other.mDescription))
            return false;
        if (mId == null) {
            if (other.mId != null)
                return false;
        } else if (!mId.equals(other.mId))
            return false;
        if (mPriority == null) {
            if (other.mPriority != null)
                return false;
        } else if (!mPriority.equals(other.mPriority))
            return false;
        if (mTasked != other.mTasked)
            return false;
        if (mTitle == null) {
            if (other.mTitle != null)
                return false;
        } else if (!mTitle.equals(other.mTitle))
            return false;
        return mpId == other.mpId;
    }
}

