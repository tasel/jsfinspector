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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.component.UIComponent;
import javax.faces.component.visit.VisitCallback;
import javax.faces.component.visit.VisitContext;
import javax.faces.component.visit.VisitResult;

/**
 *
 * @author tasel
 */
class TreeInspectionVisitor implements VisitCallback {
    
    private final TreeInspectionResult result = new TreeInspectionResult();
    
    @Override
    public VisitResult visit(VisitContext context, UIComponent target) {
        
        ComponentType componentType = ComponentType.build(target); 
        
        if (result.getComponents().get(componentType) == null) {
            result.getComponents().put(componentType, new ArrayList<String>());
        }
        
        if (componentType.isComposite()) {
            result.getComposites().get(componentType).add(target.getClientId());
        } else {
            result.getComponents().get(componentType).add(target.getClientId());
        }
        
        return VisitResult.ACCEPT;
        
    }

    public TreeInspectionResult getResult() {
        return result;
    }
    
}
