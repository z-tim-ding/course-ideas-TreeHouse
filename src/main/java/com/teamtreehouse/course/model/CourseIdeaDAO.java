package com.teamtreehouse.course.model;

import org.eclipse.jetty.util.statistic.CounterStatistic;

import java.util.List;

/**
 * Created by yoyon on 9/20/2017.
 */
public interface CourseIdeaDAO {
    boolean add(CourseIdea idea);
    List<CourseIdea> findAll();
    CourseIdea findBySlug(String slug);
}
