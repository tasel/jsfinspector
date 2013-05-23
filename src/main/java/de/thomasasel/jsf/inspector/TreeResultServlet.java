/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.thomasasel.jsf.inspector;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Thomas Asel
 */
@WebServlet(urlPatterns = "/jsfinspector")
public class TreeResultServlet extends HttpServlet {

    
    
    @Override
     public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        
        String resultKey = request.getQueryString();
        
        if (resultKey != null) {
            
            TreeInspectionResult result = (TreeInspectionResult) request.getSession(false).getAttribute(resultKey);
            PrintWriter out = response.getWriter();

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(result);
            
            out.write(json);
        }
        
    }
    
    
}