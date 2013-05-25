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
        System.out.println(tiv.getResult().toString());
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
