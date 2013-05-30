/*
 * Copyright 2013 Thomas Asel.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
 * This servlet services async request issued by the {@link JSFInpsector} component.
 * Extracts and removes the component tree analysis results from the session map. 
 * The reuests query string is expected to contain the key for the reults to retrieve.
 * Sends a JSON-encoded response to be used on the client side.
 * 
 * @author Thomas Asel
 */
@WebServlet(urlPatterns = "/jsfinspector")
public class TreeResultServlet extends HttpServlet {
    
    @Override
     public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        
        String resultKey = request.getQueryString();
        
        if (resultKey != null) {
            
            InspectionResults result = (InspectionResults) request.getSession(false).getAttribute(resultKey);
            request.getSession(false).removeAttribute(resultKey);
            
            PrintWriter out = response.getWriter();

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(result);
            
            out.write(json);
        }
    }
}
