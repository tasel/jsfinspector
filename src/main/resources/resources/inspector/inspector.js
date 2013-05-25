$( document ).ready(function() {
    // Move inspector to top
    $("body").prepend($(".jsfinspect-inspector"));
    
    // Add Click-Handlers for inspector
    $(".jsfinspect-inspector").click(function(event){
        
        var container = $("#jsfinspector-inspector-content").slideToggle();
        
//        var container = $(".jsfinspect-inspector");
//        if ($(container).hasClass("jsfinspect-inspector-hidden")){
//            container.removeClass("jsfinspect-inspector-hidden");
//        } else {
//            container.addClass("jsfinspect-inspector-hidden");
//        }
    });
});


function handleResponse(data) {

    if (data) {
        
         var json = jQuery.parseJSON(data);
        
        if (json.components && ! jQuery.isEmptyObject(json.components)) {
            createList(json.components);
        }
        
        if (json.composites && ! jQuery.isEmptyObject(json.composites)){
            createList(json.composites);
        }
    }
}

function createList(components) {

    $("#jsfinspector-inspector-content").append("<table border=\"1\">");
    var container = $(".jsfinspect-inspector table");
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
    $(".jsfinspect-inspector table tr:even").addClass("evenRow");
    $(".jsfinspect-inspector table tr:odd").addClass("oddRow");
    
}

function highlight(jsfClientId) {
    var escapedClientId = escapeClientId(jsfClientId);
    $(escapedClientId).addClass("jsfinspector-highlight");
    console.log($(escapedClientId));
}

function flatten(jsfClientId) {
    var escapedClientId = escapeClientId(jsfClientId);
    $(escapedClientId).removeClass("jsfinspector-highlight");
}


function escapeClientId(clientId) {
    return "#" + clientId.replace(/:/g,"\\:");
}

