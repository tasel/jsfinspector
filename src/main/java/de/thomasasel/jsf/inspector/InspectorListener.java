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
 * Initiates the various analysis and stores the results as {@link InspectionResults}
 * in the session map using the key {@link JSFInspector.RESULT_KEY_REQUEST_ATTRIBUTE}.
 *
 * @see javax.faces.event.PhaseListener
 * 
 * @author Thomas Asel
 */
class InspectorListener implements PhaseListener {
    
    private static DecimalFormat DF = new DecimalFormat("0.00");
    private String TIMESTAMPS_BEFORE_REQUEST_ATTRIBUTE = "de.thomasasel.jsfinspector.beforePhaseTimestamp";
    private String TIMESTAMPS_AFTER_REQUEST_ATTRIBUTE = "de.thomasasel.jsfinspector.afterPhaseTimestamp";

    @Override
    public void beforePhase(PhaseEvent event) {
        
        FacesContext context = event.getFacesContext();
        
        if (event.getPhaseId() == PhaseId.RESTORE_VIEW) {
            // init timestamps
            context.getExternalContext().getRequestMap().put(TIMESTAMPS_BEFORE_REQUEST_ATTRIBUTE, new long[6]);
            context.getExternalContext().getRequestMap().put(TIMESTAMPS_AFTER_REQUEST_ATTRIBUTE, new long[6]);
        }

        setBeforeTimestamp(event);

    }
        
    @Override
    public void afterPhase(PhaseEvent event) {
        
        setAfterTimestamp(event);
        
        if (event.getPhaseId() == PhaseId.RENDER_RESPONSE) {
            FacesContext context = event.getFacesContext();
            
            // gather results
            InspectionResults inspectionResult = new InspectionResults();
            addTreeResults(context, inspectionResult);
            addPhaseResults(context, inspectionResult);
            
            // store results in Session
            Map<String, Object> sessionMap = context.getExternalContext().getSessionMap();
            String key = (String) context.getExternalContext().getRequestMap().get(JSFInspector.RESULT_KEY_REQUEST_ATTRIBUTE);
            
            if (key != null) {
                sessionMap.put(key, inspectionResult);   
            }
        }
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.ANY_PHASE;
    }

    /**
     * Trigger component tree analysis and store the results in the results parameter.
     * 
     * @param context
     * @param results 
     * @return 
     */
    private InspectionResults addTreeResults(FacesContext context, InspectionResults results) {
        UIViewRoot viewRoot = context.getViewRoot();

        VisitContext vc = VisitContext.createVisitContext(context);
        TreeInspectionVisitor tiv = new TreeInspectionVisitor();
        viewRoot.visitTree(vc, tiv);
        
        results.setComponents(tiv.getComponents());
        results.setComposites(tiv.getComposites());
        
        return results;
    }

    /**
     * Store results of the lifecycle phases in the results parameter.
     * 
     * @param results
     * @return 
     */
    private InspectionResults addPhaseResults(FacesContext context, InspectionResults results) {
        
        long[] timestampsBefore = getBeforeTimestamps(context);
        long[] timestampsAfter = getAfterTimestamps(context);
        
        PhaseResult[] phaseResults = new PhaseResult[6];
        
        for (int i = 0; i < 6; i++) {
            double duration = timestampsAfter[i] - timestampsBefore[i];
            String formattedDuration = DF.format(duration / 1000000);
            PhaseStatus phaseStatus = duration > 0 ? PhaseStatus.PASSED : PhaseStatus.SKIPPED;
            phaseResults[i] = new PhaseResult(formattedDuration, phaseStatus);
        }        
        
        results.setPhaseResults(phaseResults);
        return results;
    }

    private void setBeforeTimestamp(PhaseEvent event) {
        final long[] timestampsBefore = getBeforeTimestamps(event.getFacesContext());
        timestampsBefore[event.getPhaseId().getOrdinal()-1] = System.nanoTime();    
    }

    private void setAfterTimestamp(PhaseEvent event) {
        final long[] timestampsAfter = getAfterTimestamps(event.getFacesContext());
        timestampsAfter[event.getPhaseId().getOrdinal()-1] = System.nanoTime();    
    }

    private long[] getAfterTimestamps(FacesContext context) {
        return (long[]) context.getExternalContext().getRequestMap().get(TIMESTAMPS_AFTER_REQUEST_ATTRIBUTE);
    }

    private long[] getBeforeTimestamps(FacesContext context) {
        return (long[]) context.getExternalContext().getRequestMap().get(TIMESTAMPS_BEFORE_REQUEST_ATTRIBUTE);
    }

    
}
