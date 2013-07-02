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
package de.thomasasel.jsf.inspector.components;

import de.thomasasel.jsf.inspector.TreeResultServlet;
import de.thomasasel.jsf.inspector.components.HTML.ATTRIBUTE;
import de.thomasasel.jsf.inspector.components.HTML.TAG;
import java.io.IOException;
import javax.faces.application.ResourceDependency;
import javax.faces.component.FacesComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.ListenerFor;
import javax.faces.event.PostAddToViewEvent;

/**
 * This component displays the output of a componen tree analysis.
 * The component will issue an async request to {@link TreeResultServlet}. This is neccessary since analysis of the component tree 
 * is not finished yet when this component is rendered.
 * 
 * The tedious part of rendering is shifted to the client side using jQuery, so this component renders just a stub and a script to execute
 * upon page load.
 * 
 * @author Thomas Asel
 */
@FacesComponent(InspectTreeComponent.TYPE)
@ListenerFor(systemEventClass = PostAddToViewEvent.class)
@ResourceDependency(library = "inspector", name="inspector.js" , target = "body")
public class InspectTreeComponent extends AbstractJSFInspectorComponent {

    	/** Component Type */
	public static final String TYPE = "InspectTree";
	/** Component Family */
	public static final String FAMILY = "de.thomasasel.JSFInspector";
        
        public static final String SUPRESS_JQUERY_CONTEXT_PARAM = "de.thomasasel.jsfinspector.SUPPRESS_JQUERY";
        public static final String SUPRESS_CSS_CONTEXT_PARAM = "de.thomasasel.jsfinspector.SUPPRESS_CSS";
        public static final String RESULT_KEY_REQUEST_ATTRIBUTE = "de.thomasasel.jsfinspector.RESULT_KEY";
        
        public  static final String INSPECTOR_RESULT_PARAMETER = "jsfinspector_result";
    
    @Override
    public String getFamily() {
        return FAMILY;
    }

    @Override
    public void encodeEnd(FacesContext context) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        
        // render the stub, jQuery will fill it on the client side.
        writer.startElement(TAG.DIV, this);
        writer.writeAttribute(ATTRIBUTE.CLASS, "jsfinspector-component jsfinspector-tree", null);
        writer.startElement(TAG.DIV, this);
        writer.writeAttribute(ATTRIBUTE.CLASS, "jsfinspector-tree-content", null);
        writer.endElement(TAG.DIV);
        writer.endElement(TAG.DIV);
        
        // Render the script to issue an async request upon page load
        String resultKey = createResultKey(context); 
        writer.startElement(TAG.SCRIPT, this);
        writer.writeAttribute(ATTRIBUTE.LANGUAGE, "javascript", null);
        writer.write("$.get('jsfinspector', '"+ resultKey+ "',function(data) {handleResponse(data);});");
        writer.endElement(TAG.SCRIPT);
    }

    @Override
    public void processEvent(ComponentSystemEvent event) throws AbortProcessingException {
        addResourcesOnDemand();
    }
    
}
