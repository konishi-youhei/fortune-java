<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>おみくじ課題</title>
  <link rel="stylesheet" href="style.css">
</head>
<body style="background-image:${backgroundImg};">
  <div class="container">
    <section class="omikuji">
      <h1>今日の運勢を占ってみよう！</h1>
      <h3 id='timeZone'>${seasonMsg}</h3>
      <button class="change" onclick="pushChangeBtn()">時間帯を変える</button>
      <img src="images/omikuji-torii.png">
      <form method="post" action="omikuji" id="pushBtn">
        <input type="submit" value="引く">
        <input type="hidden" name="mode" value="draw">
      </form>
      <form method="post" action="omikuji" id="pushBtn">
        <input type="submit" value="確率">
        <input type="hidden" name="mode" value="prob">
      </form>
      <button onclick="pushHistoryBtn()">履歴</button>
    </section>
    <form method="post" action="edit" id="pushBtn">
      <input type="submit" value="おみくじ管理画面">
      <input type="hidden" name="mode" value="link">
    </form>
    <section id="result">
      <div id='result_container'>
        <h1 id="fortune"></h1>
        <h2 id="word"></h2>
        <div><img src="" id= "result_img"></div>
        <button class="backOne" onclick="pushBackBtn()">戻る</button>
      </div>
    </section>
    <section id="history">
     <div id="history_container">
      <h2>過去10回の履歴(上から最新結果)</h2>
      <ul id='historyList'></ul>
      <button class="backTwo" onclick="pushBackBtn()">戻る</button>
     </div>
    </section>
    <section id="probability" class="hidden">
     <div id="probability_container">
        <h2>おみくじの種類と時間帯別の確率一覧</h2>
        <table border="1">
          <tr>
            <th></th><th>大吉</th><th>吉</th><th>凶</th><th>大凶</th>
          </tr>
          <tr id="probTable">
            <th></th><td></td><td></td><td></td><td></td>
          </tr>
        </table>
        <button class="backThree" onclick="pushBackBtn()">戻る</button>
     </div>
    </section>
    <section id="clock">
      <div id="clock_container">
        <h3>時間帯を変更する</h3>
        <ul class="kujiHenkou">
          <li>
            <form method="post" action="omikuji">
              <input type="submit" value="朝">
              <input type="hidden" name="hour" value="7" class="opa">
              <input type="hidden" name="mode" value="time">
            </form>
          </li>
          <li>
            <form method="post" action="omikuji">
              <input  type="submit" value="昼">
              <input type="hidden" name="hour" value="12" class="opa">
              <input type="hidden" name="mode" value="time">
            </form>
          </li>
          <li>
            <form method="post" action="omikuji">
              <input type="submit" value="夜">
              <input type="hidden" name="hour" value="18" class="opa">
              <input type="hidden" name="mode" value="time">
            </form>
          </li>
          <li>
            <form method="post" action="omikuji">
              <input type="submit" value="夕方">
              <input type="hidden" name="hour" value="20" class="opa">
              <input type="hidden" name="mode" value="time">
            </form>
          </li>
        </ul>
        <button class="backfour" onclick="pushBackBtn()">戻る</button>
      </div>
    </section>
  </div>
  <div id="overlay"></div>
  <script>
    const fortuneResult = "${result}";
    const fortuneText = "${text}";
    const fortuneImg = "${img}";

    const probs0 = "${probs0}";
    const probs1 = "${probs1}" + "%";
    const probs2 = "${probs2}" + "%";
    const probs3 = "${probs3}" + "%";
    const probs4 = "${probs4}" + "%";

    const historyStr = "${historyList}";
    const historyArray = historyStr.slice(1).slice(0, -1).split(',');

  </script>
  <script src="main.js"></script>
</body>

</html>