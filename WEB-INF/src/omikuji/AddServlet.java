package omikuji;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.FortuneDAO;

@WebServlet("/add")
public class AddServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String DEFAULT_FORWARD = "add.jsp";

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

        String fortune = req.getParameter("fortune");
        String word = req.getParameter("word");
        String img = req.getParameter("img");
        int time = Integer.parseInt(req.getParameter("timezone"));
        int prob = Integer.parseInt(req.getParameter("prob"));

        try {
            FortuneDAO fortuneDAO = new FortuneDAO();
            fortuneDAO.insertFortune(fortune, word, img, time, prob);
            System.out.println("おみくじ追加成功");
        } catch(Exception e) {
            System.out.println("エラー");
        }

        req.getRequestDispatcher(DEFAULT_FORWARD).forward(req, res);
    }
}