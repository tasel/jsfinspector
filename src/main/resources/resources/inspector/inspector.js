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

    $(".jsfinspect-inspector").append("<table border=\"1\">");
    var container = $(".jsfinspect-inspector table");
    
    var counter = 0;
    for (var component in components) {
        
        var idList = "";
        for (var id in components[component]){
            counter++;
            idList += components[component][id] +"<br />";
        }
        
        container.append("<tr><td>" + component + "</td><td>"+idList+"</td></tr>");
        console.log(component);
    }
    
    // Add banding
    $(".jsfinspect-inspector table tr:even").addClass("evenRow");
    $(".jsfinspect-inspector table tr:odd").addClass("oddRow");
    
}

