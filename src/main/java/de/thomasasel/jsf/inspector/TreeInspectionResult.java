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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author tasel
 */
public class TreeInspectionResult {
    
    private Map<ComponentType, List<String>> components = new HashMap<ComponentType, List<String>>();

    private Map<ComponentType, List<String>> composites = new HashMap<ComponentType, List<String>>();

    public Map<ComponentType, List<String>> getComposites() {
        return composites;
    }

    public void setComposites(Map<ComponentType, List<String>> composites) {
        this.composites = composites;
    }

    public Map<ComponentType, List<String>> getComponents() {
        return components;
    }

    public void setComponents(Map<ComponentType, List<String>> components) {
        this.components = components;
    }
    
    @Override
    public String toString(){
        
        String out = "[Composites: ";
        for (Entry<ComponentType, List<String>> entry : composites.entrySet()) {
            out += "["+entry.getKey().getComponentTypeIdentifier()+": ";
            for (String id : entry.getValue()) {
                out += id + ", "; 
            }
            out += "] ";
        }
       out += "] [Non-composites: ";
 
       for (Entry<ComponentType, List<String>> entry : components.entrySet()) {
            out += "["+entry.getKey().getComponentTypeIdentifier()+": ";
            for (String id : entry.getValue()) {
                out += id + ", "; 
            }
            out += "] ";
        }
        
        out += "]";        
        
        return out;
    }
    
    
    @Override
    public int hashCode() {
        int hash = 3;
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
        final TreeInspectionResult other = (TreeInspectionResult) obj;
        if (this.components != other.components && (this.components == null || !this.components.equals(other.components))) {
            return false;
        }
        if (this.composites != other.composites && (this.composites == null || !this.composites.equals(other.composites))) {
            return false;
        }
        return true;
    }
    
}
