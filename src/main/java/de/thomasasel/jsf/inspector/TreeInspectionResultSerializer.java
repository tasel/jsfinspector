/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.thomasasel.jsf.inspector;

import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

/**
 *
 * @author Thomas Asel
 */
public class TreeInspectionResultSerializer {
    
    public String getJSON(TreeInspectionResult result) {
        
        StringBuilder buf = new StringBuilder("{\n\r");
        
        if (result != null) {
            
            if (result.getComponents() != null && ! result.getComponents().isEmpty()) {
                buf.append("\t\"components\": [");
                buf.append(write(result.getComponents().entrySet()));
                buf.append("]");
            }  
            
            if (result.getComposites()!= null && ! result.getComposites().isEmpty()) {
                buf.append("\t\"composites\": [");
                buf.append(write(result.getComposites().entrySet()));
                buf.append("]");                
            }
        }

        buf.append("}");
        return buf.toString();
    }
    
    private StringBuilder write(Set<Entry<ComponentType, List<String>>> entrySet) {
        
        StringBuilder buf = new StringBuilder();
        
        for (Entry<ComponentType, List<String>> entry : entrySet) {
                    buf.append("\t\""+entry.getKey().getComponentTypeIdentifier()+"\": ");
                    buf.append("[");
                    for (int i = 0; i < entry.getValue().size(); i++) {
                        buf.append("\""+entry.getValue().get(i)+"\"");
                        if (i + 1 < entry.getValue().size()) {
                            buf.append(", ");
                        }
                    }
                    buf.append("]}");
                }    
    
        return buf;
    }
    
}
