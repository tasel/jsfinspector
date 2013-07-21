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
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;
import javax.faces.render.Renderer;

/**
 *
 * @author Thomas Asel
 */
@FacesRenderer(componentFamily = JSFInspectorComponent.FAMILY, rendererType = "ClientStubRenderer")
public class ClientStubRenderer extends Renderer {
    
    @Override
    public void encodeEnd(FacesContext context, UIComponent comp) throws IOException {
        
        JSFInspectorComponent component = (JSFInspectorComponent) comp;
        
        ResponseWriter writer = context.getResponseWriter();
        
        // render the stub, jQuery will fill it on the client side.
        writer.startElement(TAG.DIV, component);
        writer.writeAttribute(ATTRIBUTE.CLASS, "jsfinspector-component " + component.getStyleClass(), null);
        writer.startElement(TAG.DIV, component);
        writer.writeAttribute(ATTRIBUTE.CLASS, "jsfinspector-component-heading ", null);
        writer.write(component.getHeading());
        writer.endElement(TAG.DIV);
        writer.startElement(TAG.DIV, component);
        writer.writeAttribute(ATTRIBUTE.CLASS, "jsfinspector-inspector-content", null);
        writer.endElement(TAG.DIV);
        writer.endElement(TAG.DIV);
        
        // Render the script to issue an async request upon page load
        String resultKey = component.createResultKey(context); 
        writer.startElement(HTML.TAG.SCRIPT, component);
        writer.writeAttribute(HTML.ATTRIBUTE.LANGUAGE, "javascript", null);
        writer.write("$.get('jsfinspector', '"+ resultKey+ "',function(data) {handleResponse(data);});");
        writer.endElement(HTML.TAG.SCRIPT);
    }
    
    
}
