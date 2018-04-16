<%@page import="java.io.File"%>
<%@page import="solution.pack.Knapsack"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Genetic Algoritm in Knapsack Problem</title>

<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<script type="text/javascript">
    $(document).ready(function(){
        $('#save').click(function(){
            $.ajax({
                type: "post",
                url: "Result.jsp",
                data: {
                    capacity: $('#capacity').val(), 
                    scale: $('#scale').val(),
                    maxgen: $('#maxgen').val(),
                    irate: $('#irate').val(),
                    arate1: $('#arate1').val(),
                    arate2: $('#arate2').val()
                      },
                success: function(msg){      
                        $('#output').append(msg);
                }
            });
        });

    });
</script>
</head>
<body>
<h1>Test Result</h1>
<div>
Capacity of the knapsack: <input id="capacity" value="1000"/><br>
Size of the population: <input id="scale" value="200"/><br>
Maximum generation:	<input id="maxgen" value="100"/> <br>		
Rate of crossover:	<input id="irate" value="0.1"/><br>
Rate of mutation:	<input id="arate1" value="0.1"/><br>
Rate of crossover of every digit of gene: <input id="arate2" value="0.1"/><br>
<input type="button" value="Comfirm" name="save" id="save"/><br>
</div>
<div id="output"></div>

</body>
</html>