package omikuji;
import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Fortune;
import dao.FortuneDAO;

@WebServlet("/edit")
public class EditServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String DEFAULT_FORWARD = "mst.jsp";
    private static final String MODE = "mode";
    private static final String LINK = "link";
    private static final String DELETE = "delete";
    private static final String EDIT = "edit";
    private static final String UPDATE = "update";

    /**
     * GET送信があった場合
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException {

        req.getRequestDispatcher(DEFAULT_FORWARD).forward(req, res);
    }

    /**
     * POST送信があった場合
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");

        String mode = req.getParameter(MODE);
        if (mode == null || mode.equals("")) {
            mode = LINK;
        }

        FortuneDAO fortuneDAO = new FortuneDAO();
        // modeパラメータの値がdeleteの場合
        if (mode.equals(DELETE)) {

            int delete = Integer.parseInt(req.getParameter("delete"));
            System.out.println(delete);

            try {
                fortuneDAO.deleteFortune(delete);
                List<Fortune> fortuneList = fortuneDAO.getFortunes();
                req.setAttribute("length", fortuneList.size());
                for(int i = 0; i < fortuneList.size(); i++) {
                    req.setAttribute("result" + i, fortuneList.get(i).getResult());
                    req.setAttribute("word" + i, fortuneList.get(i).getText());
                    req.setAttribute("img" + i, fortuneList.get(i).getImg());
                    req.setAttribute("prob" + i, fortuneList.get(i).getProbability());
                    req.setAttribute("time" + i, fortuneList.get(i).getTimeZoneName());
                    req.setAttribute("id" + i, fortuneList.get(i).getId());
                }
            } catch(Exception e) {
                System.out.println("削除に失敗しました");
            }

        // modeパラメータの値がeditの場合
        } else if (mode.equals(EDIT)) {

            int edit = Integer.parseInt(req.getParameter("edit"));
            String time = req.getParameter("timeName");

            try {
                List<Fortune> fortuneList = fortuneDAO.getFortune(edit, time);
                req.setAttribute("result", fortuneList.get(0).getResult());
                req.setAttribute("word", fortuneList.get(0).getText());
                req.setAttribute("img", fortuneList.get(0).getImg());
                req.setAttribute("prob", fortuneList.get(0).getProbability());
                req.setAttribute("time", fortuneList.get(0).getTimeZoneName());
                req.setAttribute("id", fortuneList.get(0).getId());
            } catch (Exception e) {
                System.out.println("データ取得に失敗しました");
            }

            RequestDispatcher disp = req.getRequestDispatcher("edit.jsp");
            disp.forward(req, res);

        // modeパラメータの値がupdateの場合
        } else if (mode.equals(UPDATE)) {

            String fortune = req.getParameter("fortune");
            String word = req.getParameter("word");
            String img = req.getParameter("img");
            int time = Integer.parseInt(req.getParameter("timezone"));
            int prob = Integer.parseInt(req.getParameter("prob"));
            int id = Integer.parseInt(req.getParameter("id"));

            try {
                fortuneDAO.updateFortune(fortune, word, img, time, prob, id);
                System.out.println("アップデート成功");
            } catch (Exception e) {
                System.out.println("更新に失敗しました");
            }

            RequestDispatcher disp = req.getRequestDispatcher("add.jsp");
            disp.forward(req, res);

        // modeパラメータの値がlinkの場合
        } else if (mode.equals(LINK)) {

            try {
                List<Fortune> fortuneList = fortuneDAO.getFortunes();
                req.setAttribute("length", fortuneList.size());

                for(int i = 0; i < fortuneList.size(); i++) {
                    req.setAttribute("result" + i, fortuneList.get(i).getResult());
                    req.setAttribute("word" + i, fortuneList.get(i).getText());
                    req.setAttribute("img" + i, fortuneList.get(i).getImg());
                    req.setAttribute("prob" + i, fortuneList.get(i).getProbability());
                    req.setAttribute("time" + i, fortuneList.get(i).getTimeZoneName());
                    req.setAttribute("id" + i, fortuneList.get(i).getId());
                }
            } catch(Exception e) {
                System.out.println("エラー");
            }
        }
        req.getRequestDispatcher(DEFAULT_FORWARD).forward(req, res);
    }
}