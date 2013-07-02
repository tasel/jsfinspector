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
 * TODO
 *
 * @author Thomas Asel
 */
@FacesComponent(InspectLifecycleComponent.TYPE)
@ListenerFor(systemEventClass = PostAddToViewEvent.class)
@ResourceDependency(library = "inspector", name = "inspector.js", target = "body")
public class InspectLifecycleComponent extends AbstractJSFInspectorComponent {

    /**
     * Component Type
     */
    public static final String TYPE = "InspectLifecycle";
    
    private boolean expanded; 

    @Override
    public void encodeEnd(FacesContext context) throws IOException {
        ResponseWriter writer = context.getResponseWriter();

        // render the stub, jQuery will fill it on the client side.
        writer.startElement(TAG.DIV, this);
        writer.writeAttribute(ATTRIBUTE.CLASS, expanded ? "jsfinspector-component jsfinspector-lifecycle" : "jsfinspector-component jsfinspector-lifecycle", null);
        writer.startElement(TAG.DIV, this);
        writer.writeAttribute(ATTRIBUTE.CLASS, "jsfinspector-inspector-heading", null);
        writer.write("Inspect Lifecycle");
        writer.endElement(TAG.DIV);
        writer.startElement(TAG.DIV, this);
        writer.writeAttribute(ATTRIBUTE.CLASS, "jsfinspector-inspector-content", null);
        writer.endElement(TAG.DIV);
        writer.endElement(TAG.DIV);

        // Render the script to issue an async request upon page load
        String resultKey = createResultKey(context);
        writer.startElement(TAG.SCRIPT, this);
        writer.writeAttribute(ATTRIBUTE.LANGUAGE, "javascript", null);
        writer.write("$.get('jsfinspector', '" + resultKey + "',function(data) {handleResponse(data);});");
        writer.endElement(TAG.SCRIPT);
    }

    @Override
    public void processEvent(ComponentSystemEvent event) throws AbortProcessingException {
        addResourcesOnDemand();
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    
}
