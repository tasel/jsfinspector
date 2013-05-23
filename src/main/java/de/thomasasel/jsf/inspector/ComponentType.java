/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.thomasasel.jsf.inspector;

import javax.faces.application.Resource;
import javax.faces.component.UIComponent;

/**
 *
 * @author tasel
 */
public class ComponentType {

    private final Type componentType;
    private final String componentTypeIdentifier;

    private ComponentType(Type componentType, String componentTypeIdentifier) {
        this.componentType = componentType;
        this.componentTypeIdentifier = componentTypeIdentifier;
    }

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
    
    @Override
    public String toString() {
        return componentTypeIdentifier;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ComponentType other = (ComponentType) obj;
        if (this.componentType != other.componentType) {
            return false;
        }
        if ((this.componentTypeIdentifier == null) ? (other.componentTypeIdentifier != null) : !this.componentTypeIdentifier.equals(other.componentTypeIdentifier)) {
            return false;
        }
        return true;
    }

    /**
     * @return the componentType
     */
    public Type getType() {
        return componentType;
    }

    /**
     * @return the componentTypeIdentifier
     */
    public String getComponentTypeIdentifier() {
        return componentTypeIdentifier;
    }

    boolean isComposite() {
        return componentType == Type.COMPOSITE;
    }

    public static class NonCompositeType extends ComponentType {

        public NonCompositeType(String compositeTypeIdentifier) {
            super(Type.NONCOMPOSITE, compositeTypeIdentifier);
        }
    }

    public static class CompositeType extends ComponentType {

        public CompositeType(String componentTypeIdentifier) {
            super(Type.COMPOSITE, componentTypeIdentifier);
        }
    }

    enum Type {

        COMPOSITE, NONCOMPOSITE;
    }
    
}
