/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.thomasasel.jsf.inspector.components;

import java.io.IOException;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import static de.thomasasel.jsf.inspector.components.HTML.*;
import javax.faces.application.Resource;
import javax.faces.application.ResourceDependency;
import javax.faces.component.FacesComponent;
import javax.faces.component.UIOutput;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.ComponentSystemEventListener;
import javax.faces.event.ListenerFor;
import javax.faces.event.PostAddToViewEvent;

/**
 *
 * @author tasel
 */
@FacesComponent(JSFInspector.TYPE)
@ListenerFor(systemEventClass = PostAddToViewEvent.class)
public class JSFInspector extends UIComponentBase implements ComponentSystemEventListener {

    	/** Component Type */
	public static final String TYPE = "JSFInspector";
	/** Component Family */
	public static final String FAMILY = "de.thomasasel.JSFInspector";
        
        public static final String SUPRESS_JQUERY_CONTEXT_PARAM = "de.thomasasel.jsfinspector.SUPPRESS_JQUERY";
        public static final String SUPRESS_CSS_CONTEXT_PARAM = "de.thomasasel.jsfinspector.SUPPRESS_CSS";
    
    @Override
    public String getFamily() {
        return FAMILY;
    }

    @Override
    public void encodeEnd(FacesContext context) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        
        writer.startElement(TAG.DIV, this);
        writer.writeAttribute(ATTRIBUTE.CLASS, "jsfinspect-inspector", null);
        writer.write("Inspector");
        writer.endElement(TAG.DIV);

    }

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
    
}
