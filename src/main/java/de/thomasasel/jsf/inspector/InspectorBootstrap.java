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
