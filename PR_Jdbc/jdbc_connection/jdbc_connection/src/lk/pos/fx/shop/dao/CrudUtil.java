package lk.pos.fx.shop.dao;

import lk.pos.fx.shop.db.Connection.DBconnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CrudUtil{
        public static PreparedStatement getPreparedStatement(String sql, Object...srt) throws ClassNotFoundException, SQLException {
            Connection connection= DBconnection.getInstance().getConnection();
            PreparedStatement pstm=connection.prepareStatement(sql);
//        for(int i=0;i<srt.length;i++){
//            pstm.setObject(i+1,srt[i]);
//        }
            int i=1;
            for (Object object : srt) {
                pstm.setObject(i++,object);
            }
            return pstm;
        }

        public static int updateQuery(String sql,Object...srt) throws ClassNotFoundException, SQLException{
            return getPreparedStatement(sql, srt).executeUpdate();
        }

        public static ResultSet exequteQuery(String sql, Object...srt) throws ClassNotFoundException, SQLException{
            return getPreparedStatement(sql, srt).executeQuery();
        }
}
