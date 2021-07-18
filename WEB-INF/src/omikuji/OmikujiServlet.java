package omikuji;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Fortune;
import dao.FortuneDAO;
import dao.HistoryDAO;
import dao.TimezoneDAO;
import util.Random;

@WebServlet("/omikuji")
public class OmikujiServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String DEFAULT_FORWARD = "index.jsp";
    private static final String MODE = "mode";
    private static final String TIME = "time";
    private static final String DRAW = "draw";
    private static final String PROB = "prob";

    /**
     * GET送信があった場合
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException {

        Calendar calendar = Calendar.getInstance();
        int status = getStatus(calendar.get(Calendar.HOUR));

        Map<String, String> seasonMap = new HashMap<>();

        // getTimeメソッドでデータベースから取得
        try {
            TimezoneDAO dao = new TimezoneDAO();
            seasonMap = dao.getTimeInfo(status);
        } catch(Exception e) {
            System.out.println("エラー");
        }

        HttpSession session = req.getSession();
        session.setAttribute("seasonMap", seasonMap);

        req.setAttribute("seasonMsg", seasonMap.get("seasonMsg"));
        req.setAttribute("backgroundImg", seasonMap.get("backgroundImg"));
        req.setAttribute("status", seasonMap.get("status"));
        req.getRequestDispatcher(DEFAULT_FORWARD).forward(req, res);
    }

    /**
     * POST送信があった場合
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException {

        String mode = req.getParameter(MODE);
        if (mode == null || mode.equals("")) {
            mode = TIME;
        }

        HttpSession session = req.getSession();
        @SuppressWarnings("unchecked")
        Map<String, String> seasonMap = (Map<String, String>) session.getAttribute("seasonMap");
        // ステータスを定義
        int status = Integer.parseInt(seasonMap.get("status"));

        FortuneDAO fortuneDAO = new FortuneDAO();
        HistoryDAO historyDAO = new HistoryDAO();
        TimezoneDAO timezoneDAO = new TimezoneDAO();

        // modeパラメータの値がtimeの場合
        if (mode.equals(TIME)) {

            status = getStatus(Integer.parseInt(req.getParameter("hour")));

            // getTimeメソッドでデータベースから取得
            try {
                seasonMap = timezoneDAO.getTimeInfo(status);
                System.out.println("timeメソッド");
            } catch(Exception e) {
                System.out.println("エラー");
            }
            session.setAttribute("seasonMap", seasonMap);

        // modeパラメータの値がdrawの場合
        } else if (mode.equals(DRAW)) {

            // 乱数生成
            Random random = new Random();
            int num = random.rand();

            // getOmikujiメソッドでデータベースから取得
            List<String> historyList = new ArrayList<String>();
            Fortune fortune = new Fortune();
            try {
                List<Fortune> FortuneList = new ArrayList<Fortune>();
                FortuneList = fortuneDAO.getOmikuji(status);
                fortune = pull(num, FortuneList);
                System.out.println("くじひき");
                // 履歴表示のためhistlist配列の先頭に追加
                historyDAO.insertHistory(fortune.getId());
                System.out.println("履歴完了");
                // DBから履歴取得
                historyList = historyDAO.getHistory();
            } catch(Exception e) {
                System.out.println("エラー");
            }

            req.setAttribute("result", fortune.getResult());
            req.setAttribute("text", fortune.getText());
            req.setAttribute("img", fortune.getImg());
            session.setAttribute("historyList", historyList);

        // modeパラメータの値がprobの場合
        } else if (mode.equals(PROB)) {

            // getProbメソッドでデータベースから取得
            List<String> probList = new ArrayList<String>();
            try {
                probList = fortuneDAO.getProb(status);
                probList.add(0, fortuneDAO.getTimeName(status));
                System.out.println("確率表示");
            } catch(Exception e) {
                System.out.println("エラー");
            }

            for(int i = 0; i < probList.size(); i++) {
                req.setAttribute("probs" + i, probList.get(i));
            }
        }
        req.setAttribute("seasonMsg", seasonMap.get("seasonMsg"));
        req.setAttribute("backgroundImg", seasonMap.get("backgroundImg"));
        req.setAttribute("status", seasonMap.get("status"));
        req.getRequestDispatcher(DEFAULT_FORWARD).forward(req, res);
    }

    /**
     * おみくじを引く
     * @param num ランダム値
     * @param fortuneList くじデータのリスト
     * @return Fortune fortune
     */
    private Fortune pull(int num, List<Fortune> fortuneList) {

        int len = fortuneList.size();
        int fortuneProb = 0;
        Fortune fortune = new Fortune();

        for (int i = 0; i < len; i++) {
            fortuneProb += fortuneList.get(i).getProbability();
            if(num < fortuneProb) {
                fortune = fortuneList.get(i);
                break;
            }
        }
        return fortune;
    }

    /**
     * hourパラメータと現在時間をステータスに変換する
     * @param hour
     * @return status ステータスを返す
     */
    private int getStatus(int hour) {
        int status;

        if (6 <= hour && hour <= 11) {
            status = 1;
          } else if(hour <= 15) {
            status = 2;
          } else if(hour <= 19) {
            status = 3;
          } else {
            status = 4;
          }
        return status;
    }
}