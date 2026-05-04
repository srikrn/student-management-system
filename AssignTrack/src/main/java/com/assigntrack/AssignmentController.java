package com.assigntrack;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

@WebServlet("/AssignmentController")
public class AssignmentController extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        String action = req.getParameter("action");

        try {
            Connection con = DBConnection.getConnection();

            if (con == null) {
                out.println("<h3>Database connection failed</h3>");
                return;
            }

            // ================= ADD =================
            if (action.equals("add")) {

                String title = req.getParameter("title");
                String desc = req.getParameter("description");

                PreparedStatement ps = con.prepareStatement(
                        "INSERT INTO assignments(title, description) VALUES (?, ?)");

                ps.setString(1, title);
                ps.setString(2, desc);
                ps.executeUpdate();

                out.println("<html><head>");
                out.println("<title>Success</title>");
                out.println("<link rel='stylesheet' href='style.css'>");
                out.println("</head><body>");

                out.println("<div class='container'>");
                out.println("<h2>Assignment Added Successfully</h2>");
                out.println("<a href='dashboard.html'>Back to Dashboard</a>");
                out.println("</div>");

                out.println("</body></html>");
            }

            // ================= VIEW =================
            else if (action.equals("view")) {

                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM assignments");

                out.println("<html><head>");
                out.println("<title>Assignments</title>");
                out.println("<link rel='stylesheet' href='style.css'>");
                out.println("</head><body>");

                out.println("<div class='container'>");
                out.println("<h2>Assignments List</h2>");

                out.println("<table>");
                out.println("<tr><th>ID</th><th>Title</th><th>Description</th></tr>");

                while (rs.next()) {
                    out.println("<tr>");
                    out.println("<td>" + rs.getInt("id") + "</td>");
                    out.println("<td>" + rs.getString("title") + "</td>");
                    out.println("<td>" + rs.getString("description") + "</td>");
                    out.println("</tr>");
                }

                out.println("</table>");
                out.println("<br><a href='dashboard.html'>Back to Dashboard</a>");
                out.println("</div>");

                out.println("</body></html>");
            }

            // ================= UPDATE =================
            else if (action.equals("update")) {

                int id = Integer.parseInt(req.getParameter("id"));
                String title = req.getParameter("title");

                PreparedStatement ps = con.prepareStatement(
                        "UPDATE assignments SET title=? WHERE id=?");

                ps.setString(1, title);
                ps.setInt(2, id);
                ps.executeUpdate();

                out.println("<html><head>");
                out.println("<title>Updated</title>");
                out.println("<link rel='stylesheet' href='style.css'>");
                out.println("</head><body>");

                out.println("<div class='container'>");
                out.println("<h2>Assignment Updated Successfully</h2>");
                out.println("<a href='dashboard.html'>Back to Dashboard</a>");
                out.println("</div>");

                out.println("</body></html>");
            }

            // ================= DELETE =================
            else if (action.equals("delete")) {

                int id = Integer.parseInt(req.getParameter("id"));

                PreparedStatement ps = con.prepareStatement(
                        "DELETE FROM assignments WHERE id=?");

                ps.setInt(1, id);
                ps.executeUpdate();

                out.println("<html><head>");
                out.println("<title>Deleted</title>");
                out.println("<link rel='stylesheet' href='style.css'>");
                out.println("</head><body>");

                out.println("<div class='container'>");
                out.println("<h2>Assignment Deleted Successfully</h2>");
                out.println("<a href='dashboard.html'>Back to Dashboard</a>");
                out.println("</div>");

                out.println("</body></html>");
            }

        } catch (Exception e) {
            e.printStackTrace();
            out.println("<h3>Error: " + e.getMessage() + "</h3>");
        }
    }
}