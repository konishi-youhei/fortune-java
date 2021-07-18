package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class TimezoneDAO extends BaseDAO {
    /**
     * 時間帯の情報
     * @param status 現在時間もしくはhourパラメータのステータス
     * @return map 時間帯の情報
     * @throws Exception e DB関連の処理を実行する際に投げられたエラー
     */
     public Map<String, String> getTimeInfo(int status) throws Exception {

         Connection con = null;
         PreparedStatement pstmt = null;
         ResultSet rs = null;

         try {
             Map<String, String> map = new HashMap<>();
             con = openDbConnection();
             pstmt = con.prepareStatement("SELECT id, season_msg, background_img FROM timezone WHERE id = ?;");
             pstmt.setInt(1, status);
             rs = pstmt.executeQuery();
             rs.next();

             map.put("seasonMsg", rs.getString(2));
             map.put("backgroundImg", rs.getString(3));
             map.put("status", rs.getString(1));

             return map;
         } catch(Exception e) {
             System.out.println("error");
             e.printStackTrace();
             throw e;
         } finally {
             closeDbConnection(con, pstmt, rs);
         }
     }

}
