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
package de.thomasasel.jsf.inspector.components;

import javax.faces.application.ResourceDependency;
import javax.faces.component.FacesComponent;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.ListenerFor;
import javax.faces.event.PostAddToViewEvent;

/**
 * TODO
 *
 * @author Thomas Asel
 */
@FacesComponent(InspectLifecycleComponent.TYPE)
@ListenerFor(systemEventClass = PostAddToViewEvent.class)
@ResourceDependency(library = "inspector", name = "inspector.js", target = "body")
public class InspectLifecycleComponent extends JSFInspectorComponent {

    /**
     * Component Type
     */
    public static final String TYPE = "InspectLifecycle";
    
    @Override
    public void processEvent(ComponentSystemEvent event) throws AbortProcessingException {
        addResourcesOnDemand();
    }

    @Override
    public String getHeading() {
        return "Inspect Lifecycle";
    }

    @Override
    public String getStyleClass() {
        return "jsfinspector-lifecycle";
    }
}
