/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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

    private int compositeCounter;
    private int noncompositeCounter ;
    
    private Map<ComponentType, List<UIComponent>> components = new HashMap<ComponentType, List<UIComponent>>();
    
    @Override
    public VisitResult visit(VisitContext context, UIComponent target) {
        
        ComponentType componentType = ComponentType.build(target); 
        
        if (getComponents().get(componentType) == null) {
            getComponents().put(componentType, new ArrayList<UIComponent>());
        }
        
        getComponents().get(componentType).add(target);
        
        if (componentType.isComposite()) {
            setCompositeCounter(getCompositeCounter() + 1);
        } else setNoncompositeCounter(getNoncompositeCounter() + 1);
        
        return VisitResult.ACCEPT;
        
    }

    /**
     * @return the compositeCounter
     */
    public int getCompositeCounter() {
        return compositeCounter;
    }

    /**
     * @param compositeCounter the compositeCounter to set
     */
    public void setCompositeCounter(int compositeCounter) {
        this.compositeCounter = compositeCounter;
    }

    /**
     * @return the noncompositeCounter
     */
    public int getNoncompositeCounter() {
        return noncompositeCounter;
    }

    /**
     * @param noncompositeCounter the noncompositeCounter to set
     */
    public void setNoncompositeCounter(int noncompositeCounter) {
        this.noncompositeCounter = noncompositeCounter;
    }

    /**
     * @return the components
     */
    public Map<ComponentType, List<UIComponent>> getComponents() {
        return components;
    }

    /**
     * @param components the components to set
     */
    public void setComponents(Map<ComponentType, List<UIComponent>> components) {
        this.components = components;
    }


    
}
