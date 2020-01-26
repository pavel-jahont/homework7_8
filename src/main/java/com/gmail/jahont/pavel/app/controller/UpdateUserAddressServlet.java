package com.gmail.jahont.pavel.app.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.invoke.MethodHandles;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gmail.jahont.pavel.app.service.UserService;
import com.gmail.jahont.pavel.app.service.impl.UserServiceImpl;
import com.gmail.jahont.pavel.app.service.model.UpdateUserDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UpdateUserAddressServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private UserService userService = UserServiceImpl.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");
        String address = req.getParameter("address");
        UpdateUserDTO userDTO = new UpdateUserDTO();
        userDTO.setAddress(address);
        try {
            int idInt = Integer.parseInt(id);
            userDTO.setId(idInt);
            userService.update(userDTO);
            try (PrintWriter out = resp.getWriter()) {
                out.println("<html><body>");
                out.println("User has updated successfully");
                out.println("</body></html>");
            }
        } catch (NumberFormatException e) {
            logger.error("Incorrect format", e);
        }
    }
}