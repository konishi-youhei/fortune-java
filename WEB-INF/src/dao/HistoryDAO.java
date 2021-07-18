package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class HistoryDAO extends BaseDAO {
    /**
     *おみくじ結果を履歴としてDBに保存
     * @param fortuneId
     * @return インサートできればtrueを返す
     * @throws Exception e DB関連の処理を実行する際に投げられたエラー
     */
     public boolean insertHistory(int fortuneId) throws Exception {

         Connection con = null;
         PreparedStatement pstmt = null;
         ResultSet rs = null;
         Timestamp timestamp = new Timestamp(System.currentTimeMillis());

         try {
             con = openDbConnection();

             // トランザクション処理
             con.setAutoCommit(false);

             pstmt = con.prepareStatement("INSERT INTO fortune_history(fortune_id,history_time) VALUES(?,?);");
             pstmt.setInt(1, fortuneId);
             pstmt.setTimestamp(2, timestamp);
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
     *DBから履歴を取得する
     * @return list おみくじ履歴
     * @throws Exception e DB関連の処理を実行する際に投げられたエラー
     */
     public List<String> getHistory() throws Exception {

         Connection con = null;
         PreparedStatement pstmt = null;
         ResultSet rs = null;

         try {
             List<String> list = new ArrayList<String>();
             con = openDbConnection();
             pstmt = con.prepareStatement("SELECT result From fortune_history INNER JOIN fortune ON fortune_history.fortune_id = fortune.id ORDER BY fortune_history.id DESC OFFSET 0 LIMIT 10;");
             rs = pstmt.executeQuery();

             while(rs.next()) {
                 list.add(rs.getString(1));
             }
             return list;
         } catch(Exception e) {
             System.out.println("error");
             e.printStackTrace();
             throw e;
         } finally {
             closeDbConnection(con, pstmt, rs);
         }
     }

}
