package com.teamtreehouse.course;

import com.teamtreehouse.course.model.CourseIdea;
import com.teamtreehouse.course.model.CourseIdeaDAO;
import com.teamtreehouse.course.model.SimpleCourseIdeaDAO;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.*;

/**
 * Created by yoyon on 9/19/2017.
 */
public class Main {
    public static void main(String[] args) {

        staticFileLocation("/public");

        CourseIdeaDAO dao = new SimpleCourseIdeaDAO();

        before ((req, res) -> {
           if (req.cookie("username") != null) {
               req.attribute("usernameAttribute", req.cookie("username"));
           }
        });

        before("/ideas", (req, res) -> {
            // TODO : Send message about redirect >> somehow
            if (req.attribute("usernameAttribute") == null) {
                res.redirect("/");
                halt();
            }
        });

        get("/hello", (req, res) -> "Hello World");

        get("/", (req, res) -> {
            Map<String, String> model = new HashMap<>();
            model.put("username", req.attribute("usernameAttribute"));
            return new ModelAndView(model,"index.hbs");
        }, new HandlebarsTemplateEngine());

        /* rewritten this method below -> using res.redirect now
        post("/sign-in", (req, res) -> {
            Map<String, String> model = new HashMap<>();
            String username = req.queryParams("username");
            res.cookie("username", username);
            model.put("username", username);
            return new ModelAndView(model, "sign-in.hbs");
        }, new HandlebarsTemplateEngine());
        */

        post("/sign-in", (req, res) -> {
            Map<String, String> model = new HashMap<>();
            String username = req.queryParams("username");
            res.cookie("username", username);
            model.put("username", username);
            res.redirect("/");
            return null;
        });

        get("/ideas", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("ideas", dao.findAll());
            return new ModelAndView(model, "ideas.hbs");
        }, new HandlebarsTemplateEngine());

        post("/ideas", (req, res) -> {
            String title = req.queryParams("title");
            CourseIdea courseIdea = new CourseIdea(title, req.attribute("usernameAttribute"));
            dao.add(courseIdea);
            res.redirect("/ideas");
            return null;
        });
    }
}
