package com.teamtreehouse.course;

import com.teamtreehouse.course.model.CourseIdea;
import com.teamtreehouse.course.model.CourseIdeaDAO;
import com.teamtreehouse.course.model.NotFoundException;
import com.teamtreehouse.course.model.SimpleCourseIdeaDAO;
import spark.ModelAndView;
import spark.Request;
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
    private static final String FLASH_MESSAGE_KEY = "flash_message";

    public static void main(String[] args) {

        staticFileLocation("/public");

        CourseIdeaDAO dao = new SimpleCourseIdeaDAO();

        before ((req, res) -> {
           if (req.cookie("username") != null) {
               req.attribute("usernameAttribute", req.cookie("username"));
           }
        });

        before("/ideas", (req, res) -> {
            if (req.attribute("usernameAttribute") == null) {
                setFlashMessage(req, "Whoops, please sign in first");
                res.redirect("/");
                halt();
            }
        });

        get("/hello", (req, res) -> "Hello World");

        get("/", (req, res) -> {
            Map<String, String> model = new HashMap<>();
            model.put("username", req.attribute("usernameAttribute"));
            model.put("flashMessage", captureFlashMessage(req));
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
            model.put("flashMessage", captureFlashMessage(req));
            return new ModelAndView(model, "ideas.hbs");
        }, new HandlebarsTemplateEngine());

        post("/ideas", (req, res) -> {
            String title = req.queryParams("title");
            CourseIdea courseIdea = new CourseIdea(title, req.attribute("usernameAttribute"));
            dao.add(courseIdea);
            res.redirect("/ideas");
            return null;
        });

        post("/ideas/:slug/vote", (req, res) -> {
            CourseIdea idea = dao.findBySlug(req.params("slug"));
            boolean added = idea.addVoter(req.attribute("usernameAttribute"));
            if (added) {
                setFlashMessage(req, "Thanks for your vote");
            } else {
                setFlashMessage(req, "You already voted");
            }
            res.redirect("/ideas");
            return null;
        });

        get("/ideas/:slug", (req, res) -> {
            CourseIdea idea = dao.findBySlug(req.params("slug"));
            Map<String, Object> model = new HashMap<>();
            model.put("idea", idea);
            return new ModelAndView(model, "idea.hbs");
        }, new HandlebarsTemplateEngine());

        exception(NotFoundException.class, (exc, req, res) -> {
            res.status(404);
            HandlebarsTemplateEngine engine = new HandlebarsTemplateEngine();
            String html = engine.render(
                    new ModelAndView(null, "not-found.hbs")
            );
            res.body(html);
        });
    }

    private static void setFlashMessage(Request req, String message) {
        req.session().attribute(FLASH_MESSAGE_KEY, message);
    }

    private static String getFlashMessage(Request req) {
        if (req.session(false) == null) {
            return null;
        }
        if (!req.session().attributes().contains(FLASH_MESSAGE_KEY)) {
            return null;
        }
        return (String) req.session().attribute(FLASH_MESSAGE_KEY);
    }

    private static String captureFlashMessage(Request req) {
        String message = getFlashMessage(req);
        if (message != null) {
            req.session().removeAttribute(FLASH_MESSAGE_KEY);
        }
        return message;
    }
}
