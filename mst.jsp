<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="./css/main.css">
    <title>おみくじ作成課題ー設定</title>
    <style>
      body{
        width: 1000px;
        margin: 0 auto;
      }
      table{
        margin-top: 50px;
        width: 1000px;
      }
      #addform{
        margin-top: 50px;
        text-align: center;
      }
      #add{
        font-size: 20px;
        padding: 5px 10px;
      }
      #backform{
        margin-top: 100px;
        float: left;
        display: inline-block;
      }
      #back{
        font-size: 20px;
        padding: 5px 10px;
      }
    </style>
</head>
<body>
	<header>
	    <h1>おみくじ内容編集</h1>
	    <form method="get" action="add" id="addform">
	      <input type="submit" value="おみくじ内容を追加する" id="add">
	    </form>
	</header>

	<div class="contents">
		<table border=1>
			<tr>
			    <th>時間帯</th>
				<th>result</th>
				<th>word</th>
				<th>img</th>
				<th>確率</th>
				<th>edit</th>
				<th>delete</th>
			</tr>
			<% for (int i=0; i < (int) request.getAttribute("length"); i++) { %>
			<tr>
			    <td><%=request.getAttribute("time" + i) %></td>
				<td><%=request.getAttribute("result" + i) %></td>
				<td><%=request.getAttribute("word" + i) %></td>
				<td><%=request.getAttribute("img" + i) %></td>
				<td><%=request.getAttribute("prob" + i) %></td>
				<td>
				  <form action="edit" method="post">
				    <input type="submit" name="mode" value="edit">
				    <input type="hidden" name="edit" value="<%=request.getAttribute("id" + i) %>">
				    <input type="hidden" name="timeName" value="<%=request.getAttribute("time" + i) %>">
				  </form>
				</td>
				<td>
				  <form action="edit" method="post">
				    <input type="submit" name="mode" value="delete">
				    <input type="hidden" name="delete" value="<%=request.getAttribute("id" + i) %>">
				  </form>
				</td>
			</tr>
			<% } %>
		</table>
		<form method="get" action="omikuji" id="backform">
          <input type="submit" value="おみくじ画面に戻る" id="back">
        </form>
	</div>
    <footer></footer>
<script src="js/main.js"></script>
</body>
</html>