import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
public class ListPublication extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
 
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
 
        String title = request.getParameter("title");
 
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/article", Config.USER, Config.PASS);
            String query = "select * from article_table where title = '" + title + "';";
            Statement stmt = con.createStatement();
            ResultSet res = stmt.executeQuery(query);
            out.println("Details of " + title + ":<br>");
            while(res.next()){
            	out.println("Title: " + res.getString("title") + "<br>");
            	out.println("Mdate: " + res.getString("mdate") + "<br>");
            	out.println("Keywords: " + res.getString("keywords") + "<br>");
            	out.println("Authors: " + res.getString("authors").replace('@', ',') + "<br>");
            	out.println("Pages: " + res.getString("pages") + "<br>");
            	out.println("Year: " + res.getString("year") + "<br>");
            	out.println("Volume: " + res.getString("volume") + "<br>");
            	out.println("Journal: " + res.getString("journal") + "<br>");
            	out.println("Number: " + res.getString("number") + "<br>");
            	out.println("EE: " + res.getString("ee") + "<br>");
            	out.println("URL: " + res.getString("url") + "<br>");
            	break;
            }
        } catch (Exception e2) {
            System.out.println(e2);
        }
        out.close();
    }
 
}