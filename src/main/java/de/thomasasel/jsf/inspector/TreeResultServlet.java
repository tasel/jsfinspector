/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.thomasasel.jsf.inspector;

import java.io.IOException;
import java.net.URI;
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

    private static final String INSPECTOR_RESULT_PARAMETER = "jsfinspector_result";
    
    @Override
     public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        
        String resultKey = request.getParameter(INSPECTOR_RESULT_PARAMETER);
        
        if (resultKey != null) {
            
            TreeInspectionResult result = (TreeInspectionResult) request.getSession(false).getAttribute(resultKey);
            
        }
        
        
    }
    
    
}
