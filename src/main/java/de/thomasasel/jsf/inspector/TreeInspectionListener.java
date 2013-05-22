/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.thomasasel.jsf.inspector;

import de.thomasasel.jsf.inspector.components.JSFInspector;
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
        FacesContext context = event.getFacesContext();
        UIViewRoot viewRoot = context.getViewRoot();

        VisitContext vc = VisitContext.createVisitContext(context);
        TreeInspectionVisitor tiv = new TreeInspectionVisitor();
        viewRoot.visitTree(vc, tiv);

        String key = (String) context.getExternalContext().getRequestMap().get(JSFInspector.RESULT_KEY_REQUEST_ATTRIBUTE);
        
        Map<String, Object> sessionMap = context.getExternalContext().getSessionMap();
        sessionMap.put(key, tiv.getResult());
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
