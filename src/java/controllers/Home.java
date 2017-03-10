/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import business.Business;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ibrahiem
 */
public class Home extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String page = request.getParameter("page");
        Business business = new Business();
        List<String> categories = business.getCategories();
        request.setAttribute("categories", categories);

        if (page == null) {
            page = "1";
        }
        int pageNo = Integer.parseInt(page);
        List<model.Product> products = business.retriveProducts();
        System.out.println(products.size());
        Stream<model.Product> stream = products.stream().filter((t) -> t.getStockQuantity() > 0);
        if (pageNo == 1) {
            stream = stream.limit(12);
        } else {
            stream = stream.skip((pageNo - 1) * 12).limit(12);
        }
        List<model.Product> collect = stream.collect(Collectors.toList());
        System.out.println(collect.size());
        request.setAttribute("products", collect);
        request.setAttribute("pageNo", pageNo);
        request.setAttribute("pagesNo", (int) Math.ceil(products.size() / (double) 12));
        System.out.println((int) Math.ceil(products.size() / (double) 12));
        request.getRequestDispatcher("index.jsp").forward(request, response);

    }
}
