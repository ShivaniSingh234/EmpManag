package com.project;

import java.io.*;
import java.util.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/employee")
public class EmployeeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private List<Employee> employees = new ArrayList<>();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String action = request.getParameter("action");

        if ("add".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            String name = request.getParameter("name");
            String dept = request.getParameter("department");
            employees.add(new Employee(id, name, dept));
            response.sendRedirect("employee?action=view");
        } 
        else if ("delete".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            employees.removeIf(e -> e.getId() == id);
            response.sendRedirect("employee?action=view");
        } 
        else if ("update".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            String name = request.getParameter("name");
            String dept = request.getParameter("department");

            for (Employee e : employees) {
                if (e.getId() == id) {
                    e.setName(name);
                    e.setDepartment(dept);
                    break;
                }
            }
            response.sendRedirect("employee?action=view");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String action = request.getParameter("action");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        if ("view".equals(action)) {
            // for Employee List
            out.println("<!DOCTYPE html><html><head><title>Employee List</title>");
            out.println("<link rel='stylesheet' href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css'>");
            out.println("<style>");
            out.println("body { background-color: #f2f2f2; }");
            out.println(".container-box { background: #fff; padding: 30px; border-radius: 12px; box-shadow: 0 4px 10px rgba(0,0,0,0.1); }");
            out.println("</style>");
            out.println("</head><body><div class='container mt-5'><div class='container-box'>");
            out.println("<h2 class='mb-4 text-center'>Employee List</h2>");
            out.println("<table class='table table-striped table-hover table-bordered'><thead class='table-dark'><tr>");
            out.println("<th>ID</th><th>Name</th><th>Department</th><th>Actions</th></tr></thead><tbody>");

            for (Employee e : employees) {
                out.println("<tr>");
                out.println("<td>" + e.getId() + "</td>");
                out.println("<td>" + e.getName() + "</td>");
                out.println("<td>" + e.getDepartment() + "</td>");
                out.println("<td>");
                // Edit button
                out.println("<form action='employee' method='get' style='display:inline'>");
                out.println("<input type='hidden' name='id' value='" + e.getId() + "'/>");
                out.println("<input type='hidden' name='action' value='edit'/>");
                out.println("<button type='submit' class='btn btn-warning btn-sm me-2'>Edit</button>");
                out.println("</form>");
                // Delete button
                out.println("<form action='employee' method='post' style='display:inline'>");
                out.println("<input type='hidden' name='id' value='" + e.getId() + "'/>");
                out.println("<input type='hidden' name='action' value='delete'/>");
                out.println("<button type='submit' class='btn btn-danger btn-sm'>Delete</button>");
                out.println("</form>");
                out.println("</td>");
                out.println("</tr>");
            }

            out.println("</tbody></table>");
            out.println("<div class='text-center'>");
            out.println("<a href='addEmployee.html' class='btn btn-primary m-2'>Add New Employee</a>");
            out.println("<a href='index.html' class='btn btn-secondary m-2'>Back</a>");
            out.println("</div>");
            out.println("</div></div></body></html>");
        }
        else if ("edit".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            Employee emp = null;
            for (Employee e : employees) {
                if (e.getId() == id) {
                    emp = e;
                    break;
                }
            }

            if (emp != null) {
                out.println("<!DOCTYPE html><html><head><title>Edit Employee</title>");
                out.println("<link rel='stylesheet' href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css'>");
                out.println("</head><body class='bg-light'><div class='container mt-5'>");
                out.println("<h2 class='mb-4'>Edit Employee</h2>");
                out.println("<form action='employee' method='post' class='card p-4 shadow'>");
                out.println("<div class='mb-3'><label class='form-label'>Employee ID</label>");
                out.println("<input type='text' name='id' value='" + emp.getId() + "' class='form-control' readonly></div>");
                out.println("<div class='mb-3'><label class='form-label'>Name</label>");
                out.println("<input type='text' name='name' value='" + emp.getName() + "' class='form-control' required></div>");
                out.println("<div class='mb-3'><label class='form-label'>Department</label>");
                out.println("<input type='text' name='department' value='" + emp.getDepartment() + "' class='form-control' required></div>");
                out.println("<input type='hidden' name='action' value='update'>");
                out.println("<button type='submit' class='btn btn-success w-100'>Update Employee</button>");
                out.println("</form>");
                out.println("<a href='employee?action=view' class='btn btn-secondary mt-3'>Back</a>");
                out.println("</div></body></html>");
            }
        }
        else {
            response.sendRedirect("index.html");
        }
    }
}
