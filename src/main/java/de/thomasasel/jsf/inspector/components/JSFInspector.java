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
import javax.faces.component.UIComponentBase;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.ComponentSystemEventListener;
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
@FacesComponent(JSFInspector.TYPE)
@ListenerFor(systemEventClass = PostAddToViewEvent.class)
@ResourceDependency(library = "inspector", name="inspector.js" , target = "body")
public class JSFInspector extends UIComponentBase implements ComponentSystemEventListener {

    	/** Component Type */
	public static final String TYPE = "JSFInspector";
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
        writer.writeAttribute(ATTRIBUTE.CLASS, "jsfinspector-inspector jsfinspector-inspector-hidden", null);
        writer.startElement(TAG.DIV, this);
        writer.writeAttribute(ATTRIBUTE.ID, "jsfinspector-inspector-heading", null);
        writer.write("Inspector");
        writer.endElement(TAG.DIV);
        writer.startElement(TAG.DIV, this);
        writer.writeAttribute(ATTRIBUTE.ID, "jsfinspector-inspector-content", null);
        writer.endElement(TAG.DIV);
        writer.endElement(TAG.DIV);
        
        // Render the script to issue an async request upon page load
        String resultKey = createResultKey(context); 
        writer.startElement(TAG.SCRIPT, this);
        writer.writeAttribute(ATTRIBUTE.LANGUAGE, "javascript", null);
        writer.write("$.get('jsfinspector', '"+ resultKey+ "',function(data) {handleResponse(data);});");
        writer.endElement(TAG.SCRIPT);
    }

    /**
     * Determine if the external resources should be included in the current view.
     * It is possible to suppress the resource dependency to the provided version of jQuery and the CSS file containing the basic stylesheets.
     * 
     * If jQuery is already used in the project, the context-param "de.thomasasel.jsfinspector.SUPPRESS_JQUERY" should be set to true.
     * 
     * Setting the context-param "de.thomasasel.jsfinspector.SUPPRESS_CSS" to true will suprress the provided stylesheet.
     * 
     * @param event
     * @throws AbortProcessingException 
     */
    @Override
    public void processEvent(ComponentSystemEvent event) throws AbortProcessingException {
            
        FacesContext context = FacesContext.getCurrentInstance();

        if (! suppressJquery(context)){
            UIOutput jquery = (UIOutput) context.getApplication().createComponent(context, "javax.faces.Output", "javax.faces.resource.Script");
            jquery.getAttributes().put("library", "inspector");
            jquery.getAttributes().put("name", "jquery-1.9.1.min.js");
            jquery.getAttributes().put("target", "head");
            jquery.setId("jsfinspector-resource-jquery");
            context.getViewRoot().addComponentResource(context, jquery, "head");        
        }

        if (! suppressCSS(context)){
            UIOutput css = (UIOutput) context.getApplication().createComponent(context, "javax.faces.Output", "javax.faces.resource.Stylesheet");
            css.getAttributes().put("library", "inspector");
            css.getAttributes().put("name", "inspector.css");
            css.getAttributes().put("target", "head");
            css.setId("jsfinspector-resource-css");
            context.getViewRoot().addComponentResource(context, css, "head");        
        }
        
    }
    
    private boolean suppressJquery(FacesContext context) {
        String param = context.getExternalContext().getInitParameter(SUPRESS_JQUERY_CONTEXT_PARAM);
        return param != null && param.trim().equalsIgnoreCase("true");
    }
    
    private boolean suppressCSS(FacesContext context) {
        String param = context.getExternalContext().getInitParameter(SUPRESS_CSS_CONTEXT_PARAM);
        return param != null && param.trim().equalsIgnoreCase("true");
    }

    private String createResultKey(FacesContext context) {
        String resultKey = new Long (System.currentTimeMillis()).toString();
        context.getExternalContext().getRequestMap().put(RESULT_KEY_REQUEST_ATTRIBUTE, resultKey);
        return resultKey;
    }
    
}
