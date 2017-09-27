package com.teamtreehouse.course.model;

import java.util.List;

/**
 * Created by yoyon on 9/20/2017.
 */
public interface CourseIdeaDAO {
    boolean add(CourseIdea idea);
    List<CourseIdea> findAll();
}
