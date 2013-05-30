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
import java.text.DecimalFormat;
import java.util.Map;
import javax.faces.component.UIViewRoot;
import javax.faces.component.visit.VisitContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

/**
 * TODO
 *
 * @see javax.faces.event.PhaseListener
 * 
 * @author Thomas Asel
 */
class InspectorListener implements PhaseListener {
    
    private static DecimalFormat DF = new DecimalFormat("0.00");

    private long[]timestampsBefore = new long[6];
    private long[]timestampsAfter = new long[6];
    
    @Override
    public void beforePhase(PhaseEvent event) {
        FacesContext context = event.getFacesContext();
        
        timestampsBefore[event.getPhaseId().getOrdinal()-1] = System.nanoTime();

    }
        
    @Override
    public void afterPhase(PhaseEvent event) {
        FacesContext context = event.getFacesContext();
        
        timestampsAfter[event.getPhaseId().getOrdinal()-1] = System.nanoTime();
        
        if (event.getPhaseId() == PhaseId.RENDER_RESPONSE) {
            InspectionResults treeInspectionResult = inspectTree(context);
            Map<String, Object> sessionMap = context.getExternalContext().getSessionMap();

            // store results in Session
            String key = (String) context.getExternalContext().getRequestMap().get(JSFInspector.RESULT_KEY_REQUEST_ATTRIBUTE);
            sessionMap.put(key, treeInspectionResult);   
            
            for (int i = 0; i < 6; i++) {
                double duration = timestampsAfter[i] - timestampsBefore[i];
                String formattedDuration = DF.format(duration / 1000000);
                treeInspectionResult.getPhaseDurations()[i] = formattedDuration;
            }
        }
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.ANY_PHASE;
    }

    /**
     * Initiates analysis of the component tree and stores the result in the session map
     * using the key {@link JSFInspector.RESULT_KEY_REQUEST_ATTRIBUTE}.
     * @param context FacesContext
     */
    private InspectionResults inspectTree(FacesContext context) {
        UIViewRoot viewRoot = context.getViewRoot();

        VisitContext vc = VisitContext.createVisitContext(context);
        TreeInspectionVisitor tiv = new TreeInspectionVisitor();
        viewRoot.visitTree(vc, tiv);

        return tiv.getResult();
    }
}
