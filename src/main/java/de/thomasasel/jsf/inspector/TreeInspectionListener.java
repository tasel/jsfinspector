/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.thomasasel.jsf.inspector;

import java.util.List;
import java.util.Map.Entry;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.component.visit.VisitContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

/**
 *
 * @author tasel
 */
class TreeInspectionListener implements PhaseListener {

    @Override
    public void afterPhase(PhaseEvent event) {
        FacesContext ctx = event.getFacesContext();
        UIViewRoot viewRoot = ctx.getViewRoot();
        
        VisitContext vc = VisitContext.createVisitContext(ctx);
        TreeInspectionVisitor tiv = new TreeInspectionVisitor();
        viewRoot.visitTree(vc, tiv);

        outputStatistics(tiv);
    }

    @Override
    public void beforePhase(PhaseEvent event) {
        // nothing to do
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RENDER_RESPONSE;
    }

    private void outputStatistics(TreeInspectionVisitor tiv) {
        
        System.out.println("=================================");
        System.out.println("Total number of     components: " + (tiv.getCompositeCounter() + tiv.getNoncompositeCounter()));
        System.out.println("Total number of     composites: " + tiv.getCompositeCounter());
        System.out.println("Total number of non-composites: " + tiv.getNoncompositeCounter());
        System.out.println("=================================");
        
        for(Entry<ComponentType, List<UIComponent>> entry :tiv.getComponents().entrySet()) {
            ComponentType componentType = entry.getKey();
            List<UIComponent> components = entry.getValue();
            
            System.out.println("");
            System.out.println(componentType.getComponentTypeIdentifier() + " [" + componentType.getType() + "] ("+components.size()+")");

            for(UIComponent component : components) {
                System.out.println("\t" + component.getClientId());
            }
            
        }
        
        
    }

    
}
