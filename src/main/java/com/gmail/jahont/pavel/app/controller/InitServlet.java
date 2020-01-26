package com.gmail.jahont.pavel.app.controller;

import java.lang.invoke.MethodHandles;
import javax.servlet.http.HttpServlet;

import com.gmail.jahont.pavel.app.service.TableService;
import com.gmail.jahont.pavel.app.service.impl.TableServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class InitServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private static TableService tableService = TableServiceImpl.getInstance();

    @Override
    public void init() {
        logger.info("Delete all tables: ");
        tableService.deleteAllTables();
        logger.info("Create all tables: ");
        tableService.createAllTables();
    }
}