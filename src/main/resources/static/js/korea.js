window.onload = function() {
    drawMap('#container');
};

//지도 그리기
function drawMap(target) {
    var width = 500; //지도의 넓이
    var height = 750; //지도의 높이
    var initialScale = 5280; //확대시킬 값
    var initialX = -11520; //초기 위치값 X
    var initialY = 3950; //초기 위치값 Y
    var labels;

    var projection = d3.geo
        .mercator()
        .scale(initialScale)
        .translate([initialX, initialY]);
    var path = d3.geo.path().projection(projection);
    var zoom = d3.behavior
        .zoom()
        .translate(projection.translate())
        .scale(projection.scale())
        .scaleExtent([height, 500 * height])
        .on('zoom', zoom);

    var svg = d3
        .select(target)
        .append('svg')
        .attr('width', width + 'px')
        .attr('height', height + 'px')
        .attr('id', 'map')
        .attr('class', 'map');

    var states = svg
        .append('g')
        .attr('id', 'states')
        .call(zoom);

    states
        .append('rect')
        .attr('class', 'background')
        .attr('width', width + 'px')
        .attr('height', height + 'px');

    //geoJson데이터를 파싱하여 지도그리기
    d3.json('json/korea.json', function(json) {
        states
            .selectAll('path') //지역 설정
            .data(json.features)
            .enter()
            .append('path')
            .attr('d', path)
            .attr('id', function(d) {
                return 'path-' + d.properties.name_eng;
            })
           states
			     .selectAll('path')
			     .on('mouseover', function(d) {
					 d3.select(this).style('fill','skyblue')
					 document.getElementById("donm").innerHTML = d.properties.name;
				 })
        	     .on('mouseout', function() {
			    	 d3.select(this).style('fill','gainsboro');
			    	 document.getElementById("donm").innerHTML = "";
			     })
			     .on('click', function(d) {
						page = 1;
					    document.getElementById("donm").innerHTML = d.properties.name;
					    $("#npage").empty();
					    $("#npage").append(page);
					    $.ajax({
			             url :"search/do",
			             type : "get",
			             data :{
			            	 category : d.properties.name,
			             },
			         	 contentType: "application/x-www-form-urlencoded; charset=UTF-8;",
			             success:function(data){
			            	$(".camprow").empty();
			            	$("#totalcountsix").empty();
			            	$("#totalcount").empty();
			            	$('#doselect').val(d.properties.name).prop("selected", true);
			            	$('#sigunguselect').css("display","inline");
			            	document.getElementById("totalcount").innerHTML += ("전체 개수 : "+data.count[0].count);
			            	document.getElementById("totalcountsix").innerHTML += (parseInt((data.count[0].count)/9)+1);
							for(var i = 0; i< data.camplist.length; i++) {
								if(data.camplist[i].firstimageurl != "no data") {
	                     			$(".camprow")
	                      			.append("<div class='col-4 mb-4'>"+
	                     	          "<div class='card text-dark bg-white' onClick='location.href=`/place?contentid="+data.camplist[i].contentid+"`'>"+
	                     	            "<div class='card-header'>"+data.camplist[i].facltnm+"</div>"+
	                     	            "<div class='card-body'>"+
	                   	              	  "<img src="+data.camplist[i].firstimageurl+" style='width:250px; height:200px;'></img>"+
	                     	              "<p>"+data.camplist[i].addr1+"</p>"+
	                     	              "</div></div>")
	                     			}
	                      		else {
	                      			$(".camprow")
	                      			.append("<div class='col-4 mb-4'>"+
	                     	          "<div class='card text-dark bg-white' onClick='location.href=`/place?contentid="+data.camplist[i].contentid+"`'>"+
	                     	            "<div class='card-header'>"+data.camplist[i].facltnm+"</div>"+
	                     	            "<div class='card-body'>"+
	                   	              	  "<img src='/images/camp3.webp' style='width:250px; height:200px;'></img>"+
	                   	              	"<p>"+data.camplist[i].addr1+"</p>"+
	                     	              "</div></div></div>")
	                   	        }
                       		}
							var target = document.getElementById("sigunguselect");
					    	 $.ajax({
					             url :"search/sigungucategory",
					             type : "get",
					             async:false,
					             data :{
					            	 category : d.properties.name,
					             },
					         	 contentType: "application/x-www-form-urlencoded; charset=UTF-8;",
					             success:function(data){
					            	 $("#sigunguselect").empty();
					            	 var optAll = document.createElement("option");
					    			 optAll.value = "";
					    			 optAll.innerHTML = "전체";
					             	 $('#sigunguselect').append(optAll);
					            	 for(var i =0; i<data.length; i++){
										var option = $("<option>"+data[i].sigungunm+"</option>");
					            		 $('#sigunguselect').append(option)
					            	 }
					        	 }
					        })
							
					    }
         			})
				 })
    });
    //텍스트 위치 조절 - 하드코딩으로 위치 조절을 했습니다.
  /*  function translateTolabel(d) {
        var arr = path.centroid(d);
        if (d.properties.code == 31) {
            //서울 경기도 이름 겹쳐서 경기도 내리기
            arr[1] +=
                d3.event && d3.event.scale
                    ? d3.event.scale / height + 20
                    : initialScale / height + 20;
        } else if (d.properties.code == 34) {
            //충남은 조금 더 내리기
            arr[1] +=
                d3.event && d3.event.scale
                    ? d3.event.scale / height + 10
                    : initialScale / height + 10;
        }
        return 'translate(' + arr + ')';
    }*/
   /* function zoom() {
        projection.translate(d3.event.translate).scale(d3.event.scale);
        states.selectAll('path').attr('d', path);
        labels.attr('transform', translateTolabel);
    }*/
    // 확대, 지도 이동 기능
}