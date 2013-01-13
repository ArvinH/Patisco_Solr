<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>PatiscoSearchTest</title>
<link rel="stylesheet" type="text/css" href="css/ext-all.css">
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"
	type="text/javascript"></script>
<script type="text/javascript" src="js/ext-all.js"></script>

<style type="text/css">
.S {
	position: relative;
	top: 150px;
	text-align: center;
}

.A {
	position: relative;
	top: 200px;
	text-align: center;
}

.B {
	position: relative;
	top: 210px;
	text-align: center;
}

.C {
	position: relative;
	top: 230px;
	text-align: center;
}

.page {
	position: relative;
	top: 240px;	
	text-align: center;
}
</style>
</head>
<body>
	<div class="S">
		Sort with:
		<form id="sort">
			<input type="radio" name="sort" value="id" /> id <input type="radio"
				name="sort" value="ProductName" /> ProductName <input type="radio"
				name="sort" value="publishedDate" /> publishedDate <input
				type="radio" name="sort" value="Specification" /> Specification
		</form>
	</div>
	<form class="A" action="search.do" method="post">
		<table style="position: relative; left: 450px; text-align: center">
			<tr>
				<td>ProductName: <input id="f_ProductName" type="text"
					name="input" /></td>
			</tr>
			<tr>
				<td>publishDate: <input id="f_publishDate" type="text"
					name="input" />
				</td>
			</tr>
			<tr>
				<td>Specification: <input id="search" type="text" name="input" />
				</td>
				<td><input id="searchButton" type="button" value="Search" /></td>
			</tr>
		</table>

	</form>
	<form class="B" action="index.do" method="post">
		<input type="submit" value="Index" />
	</form>


	<div id="resultnum" class="C" style="color: red">

		<h3>Total found: </h3>
		<h3 id="resultTotal" style="color: red"></h3>

	
	</div>
	
	<div class="page">
		<input id="back" disabled="disabled" type="button" value="back" />
		<input id="next" disabled="disabled" type="button" value="next" />
	</div>
	<div  style="display:none">
		<input id="count" type="text" value="0" />
	</div>
	<div id="result"
		style="position: relative; top: 250px; text-align: center;">
		<table id="tableid" border=5 bordercolor=blue>
		</table>
	</div>
	<div id="data"></div>
</body>
<script type="text/javascript">
	$("#searchButton")
			.click(
					function() {
						$
								.get(
										"search.do",
										{
											query : encodeURI($("#search").val()),
											fq_PName : encodeURI($("#f_ProductName")
													.val()),
											fq_PubDate : $("#f_publishDate")
													.val(),
											sort : $(
													"#sort input[type='radio']:checked")
													.val(),
											start : 0,
											range : 20
										},
										function(Result) {
											if(Result.length != 0){
												document.getElementById('next').disabled = false;
											}
											$('#resultTotal')
													.append( Result[Result.length-1].TotalResultFound);
											$('#tableid')
													.append(
															'<tr><td>id</td><td>CmpnyID</td><td>publishedDate</td><td>ProductName</td><td>Specification</td></tr>');
											for ( var i = 0; i < Result.length; i++) {
												$('#tableid')
														.append(
																'<tr><td>'
																		+ Result[i].id
																		+ '</td><td>'
																		+ Result[i].CmpnyID
																		+ '</td><td>'
																		+ Result[i].publishedDate
																		+ '</td><td>'
																		+ Result[i].ProductName
																		+ '</td><td>'
																		+ Result[i].Specification
																		+ '</td></tr>');
											}	
										});
					});
	
	$("#next")
	.click(
			function() {
				var count = $("#count").val();
				var countInt = parseInt(count);
				countInt += 20;
				var MaxResult = $("#resultTotal").html();
					document.getElementById('back').disabled = false;
					document.getElementById('count').value = countInt;
					if((countInt + 20) > parseInt(MaxResult)){
						document.getElementById('next').disabled = true;
					}
				$('#tableid tbody > tr').remove();	
				$
						.get(
								"search.do",
								{
									query : encodeURI($("#search").val()),
									fq_PName : encodeURI($("#f_ProductName")
											.val()),
									fq_PubDate : $("#f_publishDate")
											.val(),
									sort : $(
											"#sort input[type='radio']:checked")
											.val(),
									start : $("#count").val(),
									range : 20
								},
								function(Result) {
									$('#tableid')
											.append(
													'<tr><td>id</td><td>CmpnyID</td><td>publishedDate</td><td>ProductName</td><td>Specification</td></tr>');
									for ( var i = 0; i < Result.length; i++) {
										$('#tableid')
												.append(
														'<tr><td>'
																+ Result[i].id
																+ '</td><td>'
																+ Result[i].CmpnyID
																+ '</td><td>'
																+ Result[i].publishedDate
																+ '</td><td>'
																+ Result[i].ProductName
																+ '</td><td>'
																+ Result[i].Specification
																+ '</td></tr>');
									}	
								});
			});
	$("#back")
	.click(
			function() {
				var count = $("#count").val();
				var countInt = parseInt(count);;
				countInt -= 20;
				if(countInt == 0){
					document.getElementById('next').disabled = false;
					document.getElementById('back').disabled = true;
					document.getElementById('count').value = countInt;
				}else {
					document.getElementById('count').value = countInt;
				}
				$('#tableid tbody > tr').remove();	
				$
						.get(
								"search.do",
								{
									query : encodeURI($("#search").val()),
									fq_PName : encodeURI($("#f_ProductName")
											.val()),
									fq_PubDate : $("#f_publishDate")
											.val(),
									sort : $(
											"#sort input[type='radio']:checked")
											.val(),
									start : $("#count").val(),
									range : 20
								},
								function(Result) {
									$('#tableid')
											.append(
													'<tr><td>id</td><td>CmpnyID</td><td>publishedDate</td><td>ProductName</td><td>Specification</td></tr>');
									for ( var i = 0; i < Result.length; i++) {
										$('#tableid')
												.append(
														'<tr><td>'
																+ Result[i].id
																+ '</td><td>'
																+ Result[i].CmpnyID
																+ '</td><td>'
																+ Result[i].publishedDate
																+ '</td><td>'
																+ Result[i].ProductName
																+ '</td><td>'
																+ Result[i].Specification
																+ '</td></tr>');
									}	
								});
			});
</script>
</html>