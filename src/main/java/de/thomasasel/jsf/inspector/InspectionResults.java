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

/**
 * Encapsulates the data gathered during a Lifecycle run.
 * 
 * @author Thomas Asel
 */
public class InspectionResults {
    
    // Collection of component data
    private Map<ComponentType, List<String>> components = new HashMap<ComponentType, List<String>>();

    // Collection of composite data
    private Map<ComponentType, List<String>> composites = new HashMap<ComponentType, List<String>>();
    
    // Duration of lifecycle phases
    private String[] phaseDurations = new String[6];

    /**
     * Returns mapping of {@link ComponentType} and a List of Client-Ids. 
     * @return Map<ComponentType, List<String>> collection describing composites found in component tree.
     */
    public Map<ComponentType, List<String>> getComposites() {
        return composites;
    }
    
    /**
     * Returns mapping of {@link ComponentType} and a List of Client-Ids.
     * @return Map<ComponentType, List<String>> collection describing composites found in component tree.
     */
    public Map<ComponentType, List<String>> getComponents() {
        return components;
    }

    public String[] getPhaseDurations() {
        return phaseDurations;
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
        final InspectionResults other = (InspectionResults) obj;
        if (this.components != other.components && (this.components == null || !this.components.equals(other.components))) {
            return false;
        }
        if (this.composites != other.composites && (this.composites == null || !this.composites.equals(other.composites))) {
            return false;
        }
        return true;
    }
}
