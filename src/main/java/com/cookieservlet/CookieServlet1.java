/*
* 8b. Build a servlet program to create a cookie to get your name through text box and press submit button(
through HTML) to display the message by greeting Welcome back your name ! , you have visited this page
n times ( n = number of your visit ) along with the list of cookies and its setvalues and demonstrate the
expiry of cookie also.
 */
package com.cookieservlet;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

@WebServlet("/CookieServlet1")
public class CookieServlet1 extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String userName = request.getParameter("userName");

        String name = null;
        int count = 1;

        Cookie[] cookies = request.getCookies();

        // Read cookies
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals("user")) {
                    name = c.getValue();
                }
                if (c.getName().equals("count")) {
                    count = Integer.parseInt(c.getValue());
                    count++;
                }
            }
        }

        // If new user entered
        if (userName != null) {
            name = userName;

            Cookie userCookie = new Cookie("user", name);
            Cookie countCookie = new Cookie("count", String.valueOf(count));

            // Expiry (important for 8b demo)
            userCookie.setMaxAge(30);   // 30 seconds
            countCookie.setMaxAge(30);

            response.addCookie(userCookie);
            response.addCookie(countCookie);
        }

        out.println("<html><body>");

        if (name != null) {
            out.println("<h2 style='color:blue;'>Welcome back, " + name + "!</h2>");
            out.println("<h3 style='color:magenta;'>You have visited this page "
                    + count + " times!</h3>");
        } else {
            out.println("<h3 style='color:red;'>Welcome Guest! Enter your name.</h3>");
        }

        // ⭐ MAIN DIFFERENCE IN 8b
        out.println("<h3>List of Cookies with Values:</h3>");

        if (cookies != null) {
            for (Cookie c : cookies) {
                out.println("<b>Cookie Name:</b> " + c.getName() + "<br>");
                out.println("<b>Cookie Value:</b> " + c.getValue() + "<br><br>");
            }
        } else {
            out.println("No cookies found<br>");
        }

        out.println("<br><a href='index.html'>Go Back</a>");

        out.println("</body></html>");
    }
}