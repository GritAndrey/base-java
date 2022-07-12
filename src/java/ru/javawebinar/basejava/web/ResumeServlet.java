package ru.javawebinar.basejava.web;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResumeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
/*        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");*/
//      response.setHeader("Content-Type", "text/html; charset=UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        String name = request.getParameter("name");
        response.getWriter().write(name == null ? "Hello Resumes!" : "Hello " + name + "!!!");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
    }
}
