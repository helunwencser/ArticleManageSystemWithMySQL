import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
public class ListPublicationByKeywords extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
 
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
 
        String[] keywords = request.getParameter("keywords").split(" ");
 
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/article", Config.USER, Config.PASS);
            String query = "select distinct title from article_table;";
            Statement stmt = con.createStatement();
            ResultSet res = stmt.executeQuery(query);
            List<String> articles = new ArrayList<String>();
            while(res.next()){
            	String title = res.getString("title");
            	for(String keyword : keywords){
            		if(title.contains(keyword)){
            			articles.add(title);
            			break;
            		}
            	}
            }
            out.println("Details of all publications contain " + keywords + ":<br>");
            for(String title : articles){
            	query = "select * from article_table where title = '" + title + "';";
            	res = stmt.executeQuery(query);
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
                out.println("<br>");
            }
        } catch (Exception e2) {
            System.out.println(e2);
        }
        out.close();
    }
 
}