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

/**
 * Client-side bootstrapping
 */
$( document ).ready(function() {
    // Move inspector to top
    $("body").prepend($(".jsfinspector-inspector"));
    
    // Add Click-Handlers for inspector
    $(".jsfinspector-inspector").click(function(event){
        $("#jsfinspector-inspector-content").slideToggle();
    });
});

/**
 * Called upon incoming asynchronous response. 
 * Takes care of calling functions to create the output.
 * 
 * @param {JSON} data payload data containing information about the previous lifecycle run
 */
function handleResponse(data) {

    if (data) {
        
         var json = jQuery.parseJSON(data);
        
        if (json.components && ! jQuery.isEmptyObject(json.components)) {
            // Create the visual output for components
            createList(json.components, "Components");
        }
        
        if (json.composites && ! jQuery.isEmptyObject(json.composites)){
            // Create the visual output for composites
            createList(json.composites, "Composite Components");
        }
        
        if (json.phaseResults && ! jQuery.isEmptyObject(json.phaseResults)) {
            // Create visual output for phase results
            createPhaseResults(json.phaseResults);
        }
    }
}

/**
 * Creates the visual output for a set of components.
 * 
 * @param components
 */
function createList(components, title) {

    var table = $("<table border=\"1\">");
    $("#jsfinspector-inspector-content").append(table);
    
    
    if (title) {
       table.append("<thead><tr><td colspan=\"2\">"+title+"</td></tr></thead>")
    }
    
    var overallCounter = 0;
    
    for (var component in components) {
        
        var counter = 0;
        
        var idCell = $("<td></td>");
        
        for (var id in components[component]){
            counter++;
            
            var jsfClientId = components[component][id];
            var link = $("<a>"+jsfClientId+"</a>");
            $(link).mouseover({cid: jsfClientId}, function (event) {
                highlight(event.data.cid);
            });
            $(link).mouseout({cid: jsfClientId}, function (event) {
                flatten(event.data.cid);
            });

            idCell.append(link).append("<br />");
        }
        
        var componentCell = $("<td>"+component+ " ("+counter+")" +"</td>");
        var row = $("<tr></tr>");
        row.append(componentCell);
        row.append(idCell);
        table.append(row);
        overallCounter += counter;
    }
    
    table.append("<tfoot><tr><td>Total number of components:</td><td>"+ overallCounter +"</td></tr></tfoot>");
    
    // Add banding
    $(".jsfinspector-inspector table tr:even").addClass("evenRow");
    $(".jsfinspector-inspector table tr:odd").addClass("oddRow");
    
}

function createPhaseResults(phaseResults) {
    
    var table = $("<table border=\"1\">");
    table.addClass("jsfinspector-phaseresults");
    $("#jsfinspector-inspector-content").append(table);
    table.append("<thead><tr><td colspan=\"6\">Phase Results</td></tr></thead>");
    
    var row = $("<tr></tr>");
    for (var phase in phaseResults) {
        
        var cell = $("<td>"+phaseResults[phase].duration+" ms </td>");
        cell.addClass(phaseResults[phase].status.toLowerCase());
        row.append(cell);
    }
    table.append(row);
    
}

/**
 * Adds a CSS class to HTML elements with a given JSF ClientId.
 * 
 * @param jsfClientId
 */
function highlight(jsfClientId) {
    var escapedClientId = escapeClientId(jsfClientId);
    $(escapedClientId).addClass("jsfinspector-highlight");
    console.log($(escapedClientId));
}

/**
 * Inverse function to highlight(jsfClientId)
 * 
 * @param jsfClientId
 */
function flatten(jsfClientId) {
    var escapedClientId = escapeClientId(jsfClientId);
    $(escapedClientId).removeClass("jsfinspector-highlight");
}


/**
 * Transforms a given JSF ClientId to a format usable by jQuery.
 * E.g. "form:foo" -> "#form\\:foo"
 * 
 * @param clientId
 * @returns {String}
 */
function escapeClientId(clientId) {
    return "#" + clientId.replace(/:/g,"\\:");
}

