/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.thomasasel.jsf.inspector;

import java.util.Date;
import java.util.List;
import java.util.Map;
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

        String key = new Long(System.currentTimeMillis()).toString();
        
        Map<String, Object> sessionMap = ctx.getExternalContext().getSessionMap();
        sessionMap.put(key, tiv.getResult());
        System.out.println(key);
    }

    @Override
    public void beforePhase(PhaseEvent event) {
        // nothing to do
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RENDER_RESPONSE;
    }
}
