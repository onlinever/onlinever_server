$(function($) {
	function getCityRegion(rank,rankNo){
		$.post("http://www.onlinever.com/gateway/getCityRegion.htm",
				"{'rank':"+rank+",'rankNo':"+rankNo+"}",
				   function(data){
				     if(data.statusCode==200){
				    	 var cityRegions = data.result;
				    	 var level = rank == 1 ? $("#province") :rank == 2 ? $("#city"): $("#cityregion");
				    	 for (var i = 0; i < cityRegions.length; i++) {
				    		 level.append("<option value="+cityRegions[i].id+" >"+cityRegions[i].name+"</option>");
						}
				     }
			 });
	}
	getCityRegion(1,1);
	$("#province").change(function(){
		getCityRegion(2,$("#province").val());
		$("#city option:gt(0)").remove();
		$("#cityregion option:gt(0)").remove();
	});
	$("#city").change(function(){
		getCityRegion(3,$("#city").val());
		$("#cityregion option:gt(0)").remove();
	});
});