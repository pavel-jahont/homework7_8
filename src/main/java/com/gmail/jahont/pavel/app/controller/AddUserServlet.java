package com.gmail.jahont.pavel.app.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.invoke.MethodHandles;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gmail.jahont.pavel.app.service.UserService;
import com.gmail.jahont.pavel.app.service.impl.UserServiceImpl;
import com.gmail.jahont.pavel.app.service.model.UserDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AddUserServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private UserService userService = UserServiceImpl.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("Add user");
        String userName = req.getParameter("userName");
        String password = req.getParameter("password");
        String age = req.getParameter("age");
        String address = req.getParameter("address");
        String telephone = req.getParameter("telephone");

        UserDTO userDTO = new UserDTO();
        userDTO.setUserName(userName);
        userDTO.setPassword(password);
        userDTO.setAddress(address);
        userDTO.setTelephone(telephone);

        try {
            int ageInt = Integer.parseInt(age);
            userDTO.setAge(ageInt);
            UserDTO addedUserDTO = userService.add(userDTO);
            try (PrintWriter out = resp.getWriter()) {
                out.println("<html><body>");
                out.println("New user has added : <br>");
                out.println("Id : " + addedUserDTO.getId() + "<br>");
                out.println("User name : " + addedUserDTO.getUserName() + " <br>");
                out.println("Password : " + addedUserDTO.getPassword() + " <br>");
                out.println("Age : " + addedUserDTO.getAge() + " <br>");
                out.println("Address : " + addedUserDTO.getAddress() + " <br>");
                out.println("Telephone : " + addedUserDTO.getTelephone() + " <br>");
                out.println("</body></html>");
            }
        } catch (NumberFormatException e) {
            logger.error("Incorrect format", e);
        }
    }
}