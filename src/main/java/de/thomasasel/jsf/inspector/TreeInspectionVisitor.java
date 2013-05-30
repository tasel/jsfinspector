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
import javax.faces.application.Resource;
import javax.faces.component.UIComponent;
import javax.faces.component.visit.VisitCallback;
import javax.faces.component.visit.VisitContext;
import javax.faces.component.visit.VisitResult;

/**
 * Traverses the component tree and creates a record of the found components.
 * 
 * @see javax.faces.component.visit.VisitCallback
 * 
 * @author Thomas Asel
 */
class TreeInspectionVisitor implements VisitCallback {
    
    private final TreeInspectionResult result = new TreeInspectionResult();
    
    @Override
    public VisitResult visit(VisitContext context, UIComponent target) {
        
        ComponentType componentType = build(target); 
        
        
        if (componentType.isComposite()) {
            if (result.getComposites().get(componentType) == null) {
                result.getComposites().put(componentType, new ArrayList<String>());
            }
            result.getComposites().get(componentType).add(target.getClientId());
        } else {
            if (result.getComponents().get(componentType) == null) {
                result.getComponents().put(componentType, new ArrayList<String>());
            }
            result.getComponents().get(componentType).add(target.getClientId());
        }
        
        return VisitResult.ACCEPT;
        
    }

    /**
     * Returns the recorded component information.
     * @return 
     */
    public TreeInspectionResult getResult() {
        return result;
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
}
