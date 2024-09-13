package org.CCristian.apiservlet.webapp.controllers;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.CCristian.apiservlet.webapp.models.Factura;

import java.io.IOException;

@WebServlet({"/index.html","/factura"})
public class FacturaController extends HttpServlet {
    // Inyectar el componente Factura
    @Inject
    private Factura factura;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("factura", factura);
        getServletContext().getRequestDispatcher("/factura.jsp").forward(req, resp);
    }
}
