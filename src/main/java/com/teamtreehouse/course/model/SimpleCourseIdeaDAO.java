package com.teamtreehouse.course.model;

import java.security.acl.AclNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yoyon on 9/20/2017.
 */
public class SimpleCourseIdeaDAO implements CourseIdeaDAO {
    private List<CourseIdea> ideas;

    public SimpleCourseIdeaDAO() {
        ideas = new ArrayList<>();
    }

    @Override
    public boolean add(CourseIdea idea) {
        return ideas.add(idea);
    }

    @Override
    public List<CourseIdea> findAll() {
        return new ArrayList<>(ideas);
    }

    @Override
    public CourseIdea findBySlug(String slug) {
        return ideas.stream()
                .filter(idea -> idea.getSlug().equals(slug))
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }
}
