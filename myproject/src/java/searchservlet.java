/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonElement;
import java.io.*;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileReader;
import static java.net.HttpCookie.parse;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.sql.*;
import org.json.simple.parser.JSONParser;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import org.json.simple.parser.ParseException;

    public class searchservlet extends HttpServlet {
        
        private static HttpURLConnection conn;
        static final String USER="root";
        static final String PASS="";
        
        private static String encodeValue(String value) {
        try {
            return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex.getCause());
        }
    }   
        
  
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ParseException, ScriptException, NoSuchMethodException {
        //Connection conn=DriverManager.getConnection();
       response.setContentType("text/html;charset=UTF-8");
       String text=request.getParameter("textfield");
       String encodedText=encodeValue(text);
       String url=" http://www.omdbapi.com/?apikey=9a7eb5ce&t="+encodedText;
       
       
           URL myurl=new URL(url);
           conn=(HttpURLConnection) myurl.openConnection();
           conn.setRequestMethod("GET");
           conn.connect();
           int responseCode=conn.getResponseCode();
           Scanner sc = new Scanner(myurl.openStream());
           String inline="";
           
           try
           {
               if(responseCode==200)
             {
               while(sc.hasNext())
               {
                   inline+=sc.nextLine();
               }
               System.out.println(text);
               System.out.println("\nJson Object in String format");
               System.out.println(inline);
             }
               else if(responseCode!=200)
               {
                   try(PrintWriter out=response.getWriter())
                   {
                       out.println("<!DOCTYPE html>");
                       out.println("<html>");
                       out.println("<head>");
                       out.println("<script type=\"text/javascript\">");
                       out.println("if(alert(\"Enter valid movie\")) document.location = 'C:\\Users\\USER\\Documents\\NetBeansProjects\\myproject\\web\\index.html/'");
                       out.println("</script>");
                       out.println("</head>");
                       out.println("</html>");
                       
                   
                   }
                  response.sendRedirect("index.html");
               }
               
               JSONParser parser=new JSONParser();
               JSONObject jobj=(JSONObject)parser.parse(inline);
               String title=(String)jobj.get("Title");
               String year=(String)jobj.get("Year");
               String rated=(String)jobj.get("Rated");
               String rel=(String)jobj.get("Released");
               String run=(String)jobj.get("Runtime");
               String gen=(String)jobj.get("Genre");
               String actors=(String)jobj.get("Actors");
               String lan=(String)jobj.get("Language");
               String plot=(String)jobj.get("Plot");
          if(title!=null)
          {
          try(PrintWriter out = response.getWriter())
          {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<style> .bg-2 { \n" +"    background-color: #1abc9c;\n" +"    color: #ffffff;\n" +"  }\n" +"  </style>");
            out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css\">");
            out.println("<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js\"></script>");           
            out.println("<script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js\"></script>");
            out.println("</head>");
            out.println("<body class=\"bg-2\">");
            out.println("<center>");
            out.println("<div class=\"jumbotron text-center\">");
            out.println("<h2 style=\"color:#006666; font-weight:bold; font-family: arial\">MOVIE-DETAILS</style></h2>");
            out.println("</div>");
           
            //out.println("<div class=\"container bg-2\"");
            out.println("<h4>Title: "+title + "</h4>");
            out.println("<h4>Year Released: "+year + "</h4>");
            out.println("<h4>Rated: "+rated + "</h4>");
            out.println("<h4>Release Date: "+rel + "</h4>");
            out.println("<h4>Runtime: "+run + "</h4>");
            out.println("<h4>Genre: "+gen + "</h4>");
            out.println("<h4>Actors: "+actors+"</h4>");
            out.println("<h4>Languages Available: "+lan+"</h4>");
            out.println("<h4>Plot of the movie: "+plot+"</h4>");
            out.println("<center><button class=\"btn btn-default\" onclick=\"window.location.href='index.html'\">BACK TO HOME</button></center>");
            out.println("</center>");
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
          }
          }
          else
          {
              try(PrintWriter out=response.getWriter())
                   {
                       out.println("<!DOCTYPE html>");
                       out.println("<html>");
                       out.println("<head>");
                       out.println("<script type=\"text/javascript\">");
                       out.println("if(alert(\"Enter movie name\")) document.location = 'C:\\Users\\USER\\Documents\\NetBeansProjects\\myproject\\web\\index.html/'");
                       out.println("</script>");
                       out.println("</head>");
                       out.println("</html>");
                       
                   
                   }
                  response.sendRedirect("index.html");
              
          }
          
       }
           catch(IOException e)
            {
               throw new RuntimeException("HttpResponseCode:"+responseCode);
               
               
            }
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            try {
                processRequest(request, response);
            } catch (ParseException | ScriptException ex) {
                Logger.getLogger(searchservlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NoSuchMethodException ex) {
                Logger.getLogger(searchservlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
            try {
                processRequest(request, response);
            } catch (ParseException | ScriptException ex) {
                Logger.getLogger(searchservlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NoSuchMethodException ex) {
                Logger.getLogger(searchservlet.class.getName()).log(Level.SEVERE, null, ex);
            }
       
           
       }
 

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
