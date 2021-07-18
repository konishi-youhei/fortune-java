package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import beans.Fortune;

public class FortuneDAO extends BaseDAO {
    /**
     * おみくじ結果取得
     * @param status 時間帯のステータス
     * @return FortuneList おみくじ結果一覧
     * @throws Exception e DB関連の処理を実行する際に投げられたエラー
     */
    public List<Fortune> getOmikuji(int status) throws Exception {

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = openDbConnection();
            pstmt = con.prepareStatement("SELECT result, word, img, probability, id FROM fortune WHERE timezone_id = ?");
            pstmt.setInt(1, status);
            rs = pstmt.executeQuery();
            List<Fortune> fortuneList = new ArrayList<Fortune>();

            while(rs.next()){
                fortuneList.add(new Fortune(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5)));
            }
            return fortuneList;
        } catch(Exception e) {
            System.out.println("error");
            e.printStackTrace();
            throw e;
        } finally {
            closeDbConnection(con, pstmt, rs);
        }
    }
    /**
    * 確率取得
    * @param status 時間帯のステータス
    * @return probList 時間帯毎の確率一覧
    * @throws Exception e DB関連の処理を実行する際に投げられたエラー
    */
    public List<String> getProb(int status) throws Exception {

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            List<String> probList = new ArrayList<String>();
            con = openDbConnection();
            pstmt = con.prepareStatement("SELECT probability FROM fortune INNER JOIN timezone ON fortune.timezone_id = ? AND fortune.timezone_id = timezone.id ORDER BY fortune.id ASC;");
            pstmt.setInt(1, status);
            rs = pstmt.executeQuery();

            while(rs.next()){
                probList.add(rs.getString(1));
            }
            return probList;
        } catch(Exception e) {
            System.out.println("error");
            e.printStackTrace();
            throw e;
        } finally {
            closeDbConnection(con, pstmt, rs);
        }
    }

    /**
     *
     * @param status 時間帯のステータス
     * @return timeName 時間帯の名前
     * @throws Exception e DB関連の処理を実行する際に投げられたエラー
     */
    public String getTimeName(int status) throws Exception {

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            String timeName;
            con = openDbConnection();
            pstmt = con.prepareStatement("SELECT name FROM timezone WHERE id = ?;");
            pstmt.setInt(1, status);
            rs = pstmt.executeQuery();

            rs.next();
            timeName = rs.getString(1);

            return timeName;
        } catch(Exception e) {
            System.out.println("error");
            e.printStackTrace();
            throw e;
        } finally {
            closeDbConnection(con, pstmt, rs);
        }
    }

    /**
     * おみくじ追加
     * @param result
     * @param word
     * @param img
     * @param timezone_id
     * @param probability
     * @return インサート成功すればtrueを返す
     * @throws Exception e DB関連の処理を実行する際に投げられたエラー
     */
    public boolean insertFortune(String result, String word, String img, int timezone_id, int probability) throws Exception {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = openDbConnection();

            // トランザクション処理
            con.setAutoCommit(false);

            pstmt = con.prepareStatement("INSERT INTO Fortune(result, word, img, timezone_id, probability) VALUES(?,?,?,?,?);");
            pstmt.setString(1, result);
            pstmt.setString(2, word);
            pstmt.setString(3, img);
            pstmt.setInt(4, timezone_id);
            pstmt.setInt(5, probability);
            pstmt.executeUpdate();

            // コミット実行
            con.commit();

            return pstmt.executeUpdate() > 0;
        } catch(Exception e) {
            con.rollback();
            System.out.println("error");
            e.printStackTrace();
            throw e;
        } finally {
            closeDbConnection(con, pstmt, rs);
        }
    }

    /**
     * おみくじ一覧を取得
     * @return fortuneList おみくじ内容一覧
     * @throws Exception e DB関連の処理を実行する際に投げられたエラー
     */
    public List<Fortune> getFortunes() throws Exception {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = openDbConnection();
            pstmt = con.prepareStatement("SELECT result, word, img, probability, name, fortune.id FROM fortune INNER JOIN timezone ON timezone.id = timezone_id ORDER BY fortune.id ASC;");
            rs = pstmt.executeQuery();
            List<Fortune> fortuneList = new ArrayList<Fortune>();

            while(rs.next()){
                fortuneList.add(new Fortune(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5), rs.getInt(6)));
            }
            return fortuneList;
        } catch(Exception e) {
            System.out.println("error");
            e.printStackTrace();
            throw e;
        } finally {
            closeDbConnection(con, pstmt, rs);
        }
    }

    /**
     * 編集するおみくじ情報を取得
     * @return fortuneList おみくじ情報
     * @throws Exception e DB関連の処理を実行する際に投げられたエラー
     */
    public List<Fortune> getFortune(int id, String name) throws Exception {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = openDbConnection();
            pstmt = con.prepareStatement("SELECT result, word, img, probability, name, fortune.id FROM fortune INNER JOIN timezone ON fortune.id = ? AND timezone.name = ?;");
            pstmt.setInt(1, id);
            pstmt.setString(2, name);
            rs = pstmt.executeQuery();
            List<Fortune> fortuneList = new ArrayList<Fortune>();

            rs.next();
            fortuneList.add(new Fortune(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5), rs.getInt(6)));

            return fortuneList;
        } catch(Exception e) {
            System.out.println("error");
            e.printStackTrace();
            throw e;
        } finally {
            closeDbConnection(con, pstmt, rs);
        }
    }

    /**
     * おみくじ削除
     * @param id
     * @return 削除に成功すればtrueを返す
     * @throws Exception e DB関連の処理を実行する際に投げられたエラー
     */
    public boolean deleteFortune(int id) throws Exception {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = openDbConnection();

            // トランザクション処理
            con.setAutoCommit(false);

            pstmt = con.prepareStatement("DELETE FROM fortune WHERE id = ?;");
            pstmt.setInt(1, id);
            System.out.println(pstmt);
            pstmt.executeUpdate();

            // コミット実行
            con.commit();

            return pstmt.executeUpdate() > 0;
        } catch(Exception e) {
            con.rollback();
            System.out.println("error");
            e.printStackTrace();
            throw e;
        } finally {
            closeDbConnection(con, pstmt, rs);
        }
    }

    /**
     * おみくじ内容更新
     * @param result
     * @param word
     * @param img
     * @param timezone_id
     * @param probability
     * @param id
     * @return 更新に成功すればtrueを返す
     * @throws Exception
     */
    public boolean updateFortune(String result, String word, String img, int timezone_id, int probability, int id) throws Exception {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = openDbConnection();

            // トランザクション処理
            con.setAutoCommit(false);

            pstmt = con.prepareStatement("UPDATE fortune SET result = ?, word = ?, img = ?, timezone_id = ?, probability = ?  WHERE id = ?;");
            pstmt.setString(1, result);
            pstmt.setString(2, word);
            pstmt.setString(3, img);
            pstmt.setInt(4, timezone_id);
            pstmt.setInt(5, probability);
            pstmt.setInt(6, id);
            pstmt.executeUpdate();

            // コミット実行
            con.commit();

            return pstmt.executeUpdate() > 0;
        } catch(Exception e) {
            con.rollback();
            System.out.println("error");
            e.printStackTrace();
            throw e;
        } finally {
            closeDbConnection(con, pstmt, rs);
        }
    }
}
