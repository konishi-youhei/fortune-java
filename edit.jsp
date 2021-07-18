<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>

<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>おみくじ課題</title>
  <link rel="stylesheet" href="style.css">
  <style>
    body{
      width: 600px;
      margin: 0 auto;
    }
    h1{
    margin-top: 150px;
    margin-bottom: 50px;
    }
    .post{
      border: 1px solid black;
      border-radius: 10px;
      padding: 30px;
      overflow: hidden;
    }
    input[type='text'] {
      box-sizing: border-box;
      width: 100%;
      padding: 0.3em;
      letter-spacing: 1px;
      border: 1;
      margin: 10px 0;
      font-size: 20px;
    }
    select {
      box-sizing: border-box;
      width: 100%;
      height: 40px;
      padding: 10px;
      border: 1;
      margin: 10px 0;
      font-size: 20px;
    }
    input[type='submit'] {
      float: right;
      margin-top: 10px;
    }
  </style>
</head>
<body>
  <h1>おみくじ内容を編集する</h1>
  <form method="post" action="edit" class="post">
    <label>追加する時間帯
      <select name="timezone" id="timezone">
        <option value="1">朝</option>
        <option value="2">昼</option>
        <option value="3">夜</option>
        <option value="4">夕方</option>
      </select>
    </label><br>
    <label>
      おみくじ結果<input type="text" name="fortune" value="${result}">
    </label><br>
    <label>
      ひとこと<input type="text" name="word" value="${word}">
    </label><br>
    <label>画像
      <select name="img">
        <option value="images/恋大吉.png">恋みくじ　大吉</option>
        <option value="images/恋吉.png">恋みくじ　吉</option>
        <option value="images/恋凶.png">恋みくじ　凶</option>
        <option value="images/恋大凶.png">恋みくじ　大凶</option>
      </select>
    </label><br>
    <label>
      確率<input type="text" name="prob" value="${prob}">
    </label><br>
    <input type="submit" value="送信">
    <input type="hidden" name="mode" value="update">
    <input type="hidden" name="id" value="${id}">
  </form>
  <form method="post" action="edit" id="pushBtn">
    <input type="submit" value="戻る">
    <input type="hidden" name="mode" value="link">
  </form>
  <script>
    const time = "${time}";
    // select要素を取得
    var element = document.getElementById( "timezone" ) ;
    // option要素の集合を取得
    var elements = element.options ;
    if(time == "朝"){
      elements[0].selected = true ;
    } else if (time == "昼") {
      elements[1].selected = true ;
    } else if (time == "夜") {
      elements[2].selected = true ;
    } else if (time == "夕方") {
      elements[3].selected = true ;
    }
  </script>
</body>

</html>