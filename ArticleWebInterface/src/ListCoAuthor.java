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
 
public class ListCoAuthor extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
 
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
 
        String author = request.getParameter("author");
 
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/article", Config.USER, Config.PASS);
            String query = "select distinct authorB from coauthor_table where authorA= '" + author + "';";
            Statement stmt = con.createStatement();
            ResultSet res = stmt.executeQuery(query);
            out.println("Co-authors of " + author + ":<br>");
            while(res.next())
            	out.println(res.getString("authorB") + "<br>");
 
        } catch (Exception e2) {
            System.out.println(e2);
        }
        out.close();
    }
 
}