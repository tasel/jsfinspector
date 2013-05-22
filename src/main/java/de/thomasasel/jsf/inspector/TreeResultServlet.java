/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.thomasasel.jsf.inspector;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map.Entry;
import javax.faces.component.UIComponent;
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
            
            TreeInspectionResultSerializer serializer = new TreeInspectionResultSerializer();
            String json = serializer.getJSON(result);
            
            out.write(json);
            
//            out.println("<table border=\"1\">");
//            for (Entry<ComponentType, List<String>> entry : result.getComponents().entrySet()){
//                ComponentType type = entry.getKey();
//                List<String> components = entry.getValue();
//                
//                out.println("<tr>");
//                out.println("<td>"+type.getComponentTypeIdentifier()+"</td>");
//                out.println("<td>");
//                for(String component : components) {
//                    out.println(component + ",<br />");
//                }
//                out.println("</td>");
//                out.println("<tr>");
//            }
//            out.println("</table>");
        }
        
    }
    
    
}
