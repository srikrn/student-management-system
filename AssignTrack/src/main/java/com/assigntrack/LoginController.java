package com.assigntrack;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

@WebServlet("/LoginController")
public class LoginController extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String action = req.getParameter("action");

        try {
            Connection con = DBConnection.getConnection();

            // ================= LOGIN =================
            if ("login".equals(action)) {

                String user = req.getParameter("username");
                String pass = req.getParameter("password");

                PreparedStatement ps = con.prepareStatement(
                        "SELECT * FROM users WHERE username=? AND password=?");

                ps.setString(1, user);
                ps.setString(2, pass);

                ResultSet rs = ps.executeQuery();

                if (rs.next()) {

                    HttpSession session = req.getSession();
                    session.setAttribute("user", user);
                    res.sendRedirect("dashboard.html");
                } else {
                    res.sendRedirect("invalid.html");
                }
            }

            // ================= REGISTER =================
            else if (action.equals("register")) {

                String user = req.getParameter("username");
                String pass = req.getParameter("password");

                PreparedStatement ps = con.prepareStatement(
                        "INSERT INTO users(username, password) VALUES (?, ?)");

                ps.setString(1, user);
                ps.setString(2, pass);

                ps.executeUpdate();

                res.setContentType("text/html");
                PrintWriter out = res.getWriter();

                out.println("<html><head>");
                out.println("<link rel='stylesheet' href='style.css'>");
                out.println("</head><body>");

                out.println("<div class='container'>");
                out.println("<h2>Account Created Successfully</h2>");
                out.println("<a href='login.html'>Go to Login</a>");
                out.println("</div>");

                out.println("</body></html>");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}