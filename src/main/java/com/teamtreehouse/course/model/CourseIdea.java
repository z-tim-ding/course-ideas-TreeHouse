package com.teamtreehouse.course.model;

/**
 * Created by yoyon on 9/20/2017.
 */
public class CourseIdea {
    private String title;
    private String creator;

    public CourseIdea(String title, String creator) {
        this.title = title;
        this.creator = creator;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CourseIdea that = (CourseIdea) o;

        if (!title.equals(that.title)) return false;
        return creator.equals(that.creator);
    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + creator.hashCode();
        return result;
    }

}
