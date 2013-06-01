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

import de.thomasasel.jsf.inspector.ComponentType.CompositeType;
import de.thomasasel.jsf.inspector.ComponentType.NonCompositeType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.application.Resource;
import javax.faces.component.UIComponent;
import javax.faces.component.visit.VisitCallback;
import javax.faces.component.visit.VisitContext;
import javax.faces.component.visit.VisitResult;

/**
 * Traverses the component tree and creates a record of the found components.
 * The results are stored in the {@link TreeInspectionVisitContext} passed to {@link #visit(javax.faces.component.visit.VisitContext, javax.faces.component.UIComponent)}
 * 
 * @see javax.faces.component.visit.VisitCallback
 * 
 * @author Thomas Asel
 */
class TreeInspectionVisitor implements VisitCallback {
        
    private Map<ComponentType, List<String>> components = new HashMap<ComponentType, List<String>>();
    private Map<ComponentType, List<String>> composites = new HashMap<ComponentType, List<String>>();
    
    /**
     * 
     * @param ctx Instance of {@link TreeInspectionVisitContext}
     * @param target
     * @return 
     */
    @Override
    public VisitResult visit(VisitContext context, UIComponent target) {

        ComponentType componentType = build(target); 
        
        if (componentType.isComposite()) {
            if (composites.get(componentType) == null) {
                composites.put(componentType, new ArrayList<String>());
            }
            composites.get(componentType).add(target.getClientId());
        } else {
            if (components.get(componentType) == null) {
                components.put(componentType, new ArrayList<String>());
            }
            components.get(componentType).add(target.getClientId());
        }
        
        return VisitResult.ACCEPT;
        
    }

     /**
     * Builder method, used to create the appropriate sub-type of {@link ComponentType} depending on the nature of target.
     * 
     * @param target
     * @return 
     */
    static ComponentType build(UIComponent target) {

        String identifier;
        
        if (UIComponent.isCompositeComponent(target)) {
            Resource resource = (Resource) target.getAttributes().get(Resource.COMPONENT_RESOURCE_KEY);
            identifier = resource.getResourceName();
            return new CompositeType(identifier);
        } else {
            identifier = target.getClass().getName();
            return new NonCompositeType(identifier);
        }
    }

    public Map<ComponentType, List<String>> getComponents() {
        return components;
    }

    public Map<ComponentType, List<String>> getComposites() {
        return composites;
    }
    
}
