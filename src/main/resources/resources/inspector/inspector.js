$( document ).ready(function() {
    // Move inspector to top
    $("body").prepend($(".jsfinspect-inspector"));
    
    // Add Click-Handlers
    $(".jsfinspect-inspector").click(function(event){
        var container = $(".jsfinspect-inspector");
        if ($(container).hasClass("jsfinspect-inspector-hidden")){
            container.removeClass("jsfinspect-inspector-hidden");
        } else {
            container.addClass("jsfinspect-inspector-hidden");
        }
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

    $(".jsfinspect-inspector").append("<table border=\"1\">");
    var container = $(".jsfinspect-inspector table");
    var overallCounter = 0;
    
    for (var component in components) {
        
        var counter = 0;
        var idList = "";
        for (var id in components[component]){
            counter++;
            idList += components[component][id] +"<br />";
        }
        
        container.append("<tr><td>" + component + "("+counter+")" + "</td><td>"+idList+"</td></tr>");
        overallCounter += counter;
    }
    
    container.append("<tr><tfoot><td>Total number of components:</td><td>"+ overallCounter +"</td></tfoot></tr>");
    
    // Add banding
    $(".jsfinspect-inspector table tr:even").addClass("evenRow");
    $(".jsfinspect-inspector table tr:odd").addClass("oddRow");
    
}

