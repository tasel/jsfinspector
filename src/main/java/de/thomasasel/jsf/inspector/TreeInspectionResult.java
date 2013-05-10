/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.thomasasel.jsf.inspector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.component.UIComponent;

/**
 *
 * @author tasel
 */
public class TreeInspectionResult {
    
    private int noncompositeCounter;
    private Map<ComponentType, List<UIComponent>> components = new HashMap<ComponentType, List<UIComponent>>();
    private int compositeCounter;

    public int getNoncompositeCounter() {
        return noncompositeCounter;
    }

    public void setNoncompositeCounter(int noncompositeCounter) {
        this.noncompositeCounter = noncompositeCounter;
    }

    public Map<ComponentType, List<UIComponent>> getComponents() {
        return components;
    }

    public void setComponents(Map<ComponentType, List<UIComponent>> components) {
        this.components = components;
    }

    public int getCompositeCounter() {
        return compositeCounter;
    }

    public void setCompositeCounter(int compositeCounter) {
        this.compositeCounter = compositeCounter;
    }
    
    
    
}
