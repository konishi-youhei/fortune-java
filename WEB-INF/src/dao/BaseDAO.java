package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BaseDAO {

    private static final String dbUrl = "jdbc:postgresql://localhost:5432/name";

    /**
     * DBとの接続を開始するためのメソッド
     * @return Connection
     * @throws Exception DB関連のエラー
     */
    public static Connection openDbConnection() throws Exception {

        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection(dbUrl);
    }

    /**
     * DBとの接続を終了するためのメソッド
     * @param con
     * @param pstmt
     * @param rs
     */
    public static void closeDbConnection(Connection con,PreparedStatement pstmt,ResultSet rs) {

        if (con != null) {
            try {
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (rs != null) {
            try {
                rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
