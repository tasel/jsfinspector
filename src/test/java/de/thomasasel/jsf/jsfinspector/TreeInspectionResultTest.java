/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.thomasasel.jsf.jsfinspector;

import de.thomasasel.jsf.inspector.ComponentType;
import org.junit.Assert;
import org.junit.Test;



/**
 *
 * @author Thomas Asel
 */
public class TreeInspectionResultTest {
    
    @Test
    public void shouldBeEqual() {
        
        // given
        ComponentType.NonCompositeType component1type = new ComponentType.NonCompositeType("javax.faces.component.html.HtmlInputText");
        ComponentType.NonCompositeType component2type = new ComponentType.NonCompositeType("javax.faces.component.html.HtmlInputText");
        
        // when
        Assert.assertEquals(component1type, component2type);
        
    }
    
}
