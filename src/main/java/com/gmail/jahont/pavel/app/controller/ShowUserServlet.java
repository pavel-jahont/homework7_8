package com.gmail.jahont.pavel.app.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.invoke.MethodHandles;
import java.util.List;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gmail.jahont.pavel.app.service.UserService;
import com.gmail.jahont.pavel.app.service.impl.UserServiceImpl;
import com.gmail.jahont.pavel.app.service.model.UserDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ShowUserServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private UserService userService = UserServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<UserDTO> users = userService.findAll();
        resp.setContentType("text/html");
        logger.info("Show user");
        try (PrintWriter out = resp.getWriter()) {
            resp.setContentType("text/html");
            out.println("<html><body>");
            for (UserDTO element : users) {
                out.println("id: " + element.getId() + "<br>");
                out.println("userName: " + element.getUserName() + " <br>");
                out.println("password: " + element.getPassword() + " <br>");
                out.println("age: " + element.getAge() + " <br>");
                out.println("isActive: " + element.isActive() + " <br>");
                out.println("address: " + element.getAddress() + " <br>");
                out.println("telephone: " + element.getTelephone() + " <br>");
                out.println("<br><br>");
            }
            out.println("</body></html>");
        }
    }
}