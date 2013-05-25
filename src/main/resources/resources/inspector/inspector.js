/**
 * Client-side bootstrap
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
            createList(json.components);
        }
        
        if (json.composites && ! jQuery.isEmptyObject(json.composites)){
            // Create the visual output for composites
            createList(json.composites);
        }
    }
}

/**
 * Creates the visual output for a set of components.
 * 
 * @param components
 */
function createList(components) {

    $("#jsfinspector-inspector-content").append("<table border=\"1\">");
    var container = $(".jsfinspector-inspector table");
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
        container.append(row);
        overallCounter += counter;
    }
    
    container.append("<tfoot><tr><td>Total number of components:</td><td>"+ overallCounter +"</td></tr></tfoot>");
    
    // Add banding
    $(".jsfinspector-inspector table tr:even").addClass("evenRow");
    $(".jsfinspector-inspector table tr:odd").addClass("oddRow");
    
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

