package com.teamtreehouse.course.model;

import com.github.slugify.Slugify;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by yoyon on 9/20/2017.
 */
public class CourseIdea {
    private String title;
    private String creator;
    private String slug;
    private Set<String> voters;

    public CourseIdea(String title, String creator) {
        voters = new HashSet<>();
        this.title = title;
        this.creator = creator;
        try {
            Slugify slugify = new Slugify();
            slug = slugify.slugify(title);
        }   catch (IOException e) {
            e.printStackTrace();
        }
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

    public String getSlug() {
        return slug;
    }

    public boolean addVoter(String voterUserName) {
        return voters.add(voterUserName);
    }

    public int getVoteCount() {
        return voters.size();
    }
}

