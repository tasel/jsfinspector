/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.thomasasel.jsf.inspector;

import javax.faces.FactoryFinder;
import javax.faces.application.Application;
import javax.faces.application.ProjectStage;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.PostConstructApplicationEvent;
import javax.faces.event.SystemEvent;
import javax.faces.event.SystemEventListener;
import javax.faces.lifecycle.Lifecycle;
import javax.faces.lifecycle.LifecycleFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author tasel
 */
public class InspectorBootstrap implements SystemEventListener {
    private static final Logger LOG = LoggerFactory.getLogger(InspectorBootstrap.class.getName());
    
    @Override
    public void processEvent(SystemEvent rawEvent) throws AbortProcessingException {
        
        if (rawEvent instanceof PostConstructApplicationEvent) {
            
            PostConstructApplicationEvent event = (PostConstructApplicationEvent) rawEvent;
            
            Application application =event.getApplication();
            ProjectStage stage = application.getProjectStage();
            
            if (stage == ProjectStage.Development) {
                bootstrap(event);
            } else {
                LOG.warn("Attempt to install JSF-Inspector in non-Development stage! Bootstrapping canceled.");
            }
        }
        
    }

    @Override
    public boolean isListenerForSource(Object source) {
        return source instanceof Application;
    }

    private void bootstrap(PostConstructApplicationEvent event) {
        
        LifecycleFactory lcFactory = (LifecycleFactory) FactoryFinder.getFactory(FactoryFinder.LIFECYCLE_FACTORY);
        Lifecycle lc = lcFactory.getLifecycle(LifecycleFactory.DEFAULT_LIFECYCLE);
        lc.addPhaseListener(new TreeInspectionListener());
        
        
    }
    
    
}
