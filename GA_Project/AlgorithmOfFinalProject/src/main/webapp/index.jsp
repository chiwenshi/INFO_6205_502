<%@page import="java.io.File"%>
<%@page import="solution.pack.Knapsack"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
//	File data = new File("src/main/java/solution/pack/data.txt");  
	File data = new File("D://学习事物//学校课程//Master//INFO6205//A1//AlgorithmOfFinalProject//src//main//java//solution//pack//data.txt");
	Knapsack gaKnapsack = new Knapsack(2000, 200, 200, 0.5f, 0.05f, 0.1f, data);  
	gaKnapsack.solve();

%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    <script type="text/javascript">
    google.charts.load('current', {packages: ['corechart', 'line']});
    google.charts.setOnLoadCallback(drawCurveTypes);

    function drawCurveTypes() {
          var data = new google.visualization.DataTable();
          data.addColumn('number', 'Generation');
          data.addColumn('number', 'Total Profit');
          data.addColumn('number', 'Total Weigth');

          	
    		var size =<%=gaKnapsack.list.size() %>;
    		var info = [];
    		<%	for(int k=0;k<gaKnapsack.list.size();k++){ %>
    	  		var newArray = new Array(<%= gaKnapsack.list.get(k).getI() %>,
    	  				<%= gaKnapsack.list.get(k).getProfit() %>,
    	    	  		<%= gaKnapsack.list.get(k).getWeight()%>,
    	    	  		);
    	  		info.push(newArray);

    	  	<% 
    	  	}
    	  	
    	  	%>
   
    	  	

         for (var i = 0; i < info.length; i++)
			{
        	 	/* console.log(info[i][0]);
        	 	console.log(info[i][1]);
        	 	console.log(info[i][2]); */
			    data.addRow(info[i]);
			}
         
          var options = {
            hAxis: {
              title: 'Generation'
            },
            vAxis: {
              title: 'Popularity'
            },
            series: {
              1: {curveType: 'function'}
            }
          };

          var chart = new google.visualization.LineChart(document.getElementById('chart_div'));
          chart.draw(data, options);
        }
      
      
      <%-- 
      var info = [[]];
  		var size =<%=gaKnapsack.list.size(); %>;
  	
	  <%	for(int k=0;k<gaKnapsack.list.size();k++){ %>
	  	for(var num=0; num<size;num++){
	  		info[num][0]=1<%= gaKnapsack.list.get(k).getI(); %>;
	  		info[num][1]=2<%= gaKnapsack.list.get(k).getProfit(); %>;
	  		info[num][2]=3<%= gaKnapsack.list.get(k).getWeight();%>;
	  	}
	  	<% 
	  	}
	  	
	  	%>
      
      
      function drawChart() {
    	
        
        var data = new google.visualization.DataTable();
        data.addColumn('number', 'generation');
        data.addColumn('number', 'Total Profit');
        data.addColumn('number', 'Total Weight');
        
        var rowSize = 5<%// gaKnapsack.list.size(); %>;
        data.addRows(
        		['2004',  1000,      400],
                ['2005',  1170,      460],
                ['2006',  660,       1120],
                ['2007',  1030,      540]
        	/* for(var n = 0; n<rowSize;n++){
        		[info[n][0],info[n][1],info[n][2]]
        	} */		
        );
        

        var options = {
        		hAxis: {
        	          title: 'Generation',
        	          logScale: true
        	        },
        	        vAxis: {
        	          title: 'Popularity',
        	          logScale: false
        	        },
        	        colors: ['#a52714', '#097138']
        };

        var chart = new google.visualization.LineChart(document.getElementById('curve_chart'));

        chart.draw(data, options);
      } --%>
    </script>
</head>
<body>
<div id="chart_div"></div>
<%
//	for(int k=0;k<gaKnapsack.list.size();k++){
//		out.println("=====");%><br><%
//		out.println("The best unit of the " + gaKnapsack.list.get(k).getI()+" generation "
//				+ " Total Profit: "+gaKnapsack.list.get(k).getProfit()+" Total Weight: " +gaKnapsack.list.get(k).getWeight());
//		%><br><%
//	}
	%>



</body>
</html>