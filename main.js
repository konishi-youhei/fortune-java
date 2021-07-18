const result = document.getElementById('result');
const clock = document.getElementById('clock');
const overlay = document.getElementById('overlay');
const history = document.getElementById('history');
const historyList = document.getElementById('historyList');
const pushBtntwo = document.getElementById('pushBtntwo');
const pushBtnthree = document.getElementById('pushBtnthree');
const fortune = document.getElementById('fortune');
const word = document.getElementById('word');
const resultImg = document.getElementById('result_img');
const probTable = document.getElementById('probTable');
const probability = document.getElementById('probability');
const DAIKITI = '大吉';
const KITI = '吉';
const KYOU = '凶';
const DAIKYOU = '大凶';
const historyArr = [];

// おみくじが引く処理がされた時
if(fortuneResult && fortuneText && fortuneImg) {
    overlay.classList.add('fadein');
    result.classList.add('fadein');
    fortune.innerHTML = fortuneResult;
    word.innerHTML = fortuneText;
    resultImg.src = fortuneImg;
}

// 確率一覧ボタン表示
if(probs0 && probs1) {
	probability.classList.add('fadein');
    overlay.classList.add('fadein');
    probTable.children[0].textContent = probs0;
    probTable.children[1].textContent = probs1;
    probTable.children[2].textContent = probs2;
    probTable.children[3].textContent = probs3;
    probTable.children[4].textContent = probs4;
}

// 時間帯変更ボタンが押された時
function pushChangeBtn() {
  overlay.classList.add('fadein');
  clock.classList.add('fadein');
}

// 履歴ボタンが押された時
function pushHistoryBtn(){
  overlay.classList.add('fadein');
  history.classList.add('fadein');

  const len = historyArray.length;
  let stockList = [];
  //履歴に表示するための処理
  for(let i = 0; i < len; i++) {
    stockList.push('<li>' + historyArray[i] + '</li>');
  }
  if(len > 10){
    stockList = stockList.slice(0, 10);
  }
  historyList.innerHTML = stockList.join('');
}


// 戻るボタンが押された時
function pushBackBtn(){
  result.classList.remove('fadein');
  overlay.classList.remove('fadein');
  history.classList.remove('fadein');
  probability.classList.remove('fadein');
  clock.classList.remove('fadein');
}