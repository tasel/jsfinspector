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

import javax.faces.component.UIComponentBase;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEventListener;

/**
 *  Abstract base class for all JSFInspector components.
 */
public abstract class JSFInspectorComponent extends UIComponentBase  implements ComponentSystemEventListener {
    /** Component Family */
    public static final String FAMILY = "de.thomasasel.JSFInspector";
    public static final String INSPECTOR_RESULT_PARAMETER = "jsfinspector_result";
    public static final String RESULT_KEY_REQUEST_ATTRIBUTE = "de.thomasasel.jsfinspector.RESULT_KEY";
    public static final String SUPRESS_CSS_CONTEXT_PARAM = "de.thomasasel.jsfinspector.SUPPRESS_CSS";
    public static final String SUPRESS_JQUERY_CONTEXT_PARAM = "de.thomasasel.jsfinspector.SUPPRESS_JQUERY";
    public static final String ACTIVATION_SCRIPT_RENDERED_CONTEXT_ATTRIBUTE = "de.thomasasel.jsfinspector.ACTIVATION_SCRIPT_RENDERED";
    
    
    /* Name of the context attribute that holds a flag which determines if the script issuing an async request has already been rendered */
    public static final String ASYNC_REQUEST_SCRIPT_RENDERED_CONTEXT_ATTRIBUTE_NAME = "de.thomasasel.jsfinspector.ASYNC_REQUEST_SCRIPT_RENDERED";

    @Override
    public String getFamily() {
        return FAMILY;
    }
    
    public String createResultKey(FacesContext context) {
        String resultKey = new Long(System.currentTimeMillis()).toString();
        context.getExternalContext().getRequestMap().put(InspectLifecycleComponent.RESULT_KEY_REQUEST_ATTRIBUTE, resultKey);
        return resultKey;
    }

    protected boolean suppressCSS(FacesContext context) {
        String param = context.getExternalContext().getInitParameter(InspectLifecycleComponent.SUPRESS_CSS_CONTEXT_PARAM);
        return param != null && param.trim().equalsIgnoreCase("true");
    }

    protected boolean suppressJquery(FacesContext context) {
        String param = context.getExternalContext().getInitParameter(InspectLifecycleComponent.SUPRESS_JQUERY_CONTEXT_PARAM);
        return param != null && param.trim().equalsIgnoreCase("true");
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
    protected void addResourcesOnDemand() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (!suppressJquery(context)) {
            UIOutput jquery = (UIOutput) context.getApplication().createComponent(context, "javax.faces.Output", "javax.faces.resource.Script");
            jquery.getAttributes().put("library", "inspector");
            jquery.getAttributes().put("name", "jquery-1.9.1.min.js");
            jquery.getAttributes().put("target", "head");
            jquery.setId("jsfinspector-resource-jquery");
            context.getViewRoot().addComponentResource(context, jquery, "head");
        }
        if (!suppressCSS(context)) {
            UIOutput css = (UIOutput) context.getApplication().createComponent(context, "javax.faces.Output", "javax.faces.resource.Stylesheet");
            css.getAttributes().put("library", "inspector");
            css.getAttributes().put("name", "inspector.css");
            css.getAttributes().put("target", "head");
            css.setId("jsfinspector-resource-css");
            context.getViewRoot().addComponentResource(context, css, "head");
        }
    }
    
    public abstract String getHeading();
    public abstract String getStyleClass();
}
