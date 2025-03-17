package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DataBase {


    public static Connection dbConn;


    public static Connection getConnection() {

        Connection conn = null;

        try {
            // 데이터베이스 연결 정보
            String id = "root";  // 데이터베이스 사용자명
            String pw = "1234";  // 데이터베이스 비밀번호
            String url = "jdbc:mysql://localhost:3306/projectdatabase";  // MySQL 서버의 URL (로컬호스트에서 projectdatabase 데이터베이스에 연결)



            Class.forName("com.mysql.cj.jdbc.Driver");  // 'com.mysql.cj.jdbc.Driver' 클래스를 로드하여 MySQL 드라이버를 사용



            conn = DriverManager.getConnection(url, id, pw);  // DriverManager가 MySQL에 연결을 요청하고, 연결된 Connection 객체를 conn 변수로 반환

            
        } catch (Exception e) {  // 예외가 발생할 시
            e.printStackTrace();  // 예외 정보를 출력
        }
        return conn;  // 데이터베이스 연결 객체(conn)를 반환
    }


    public static void close(PreparedStatement pstmt, Connection conn) {
        if (pstmt != null) {  // PreparedStatement가 null이 아니면 실행
            try {
                if (!pstmt.isClosed())  // pstmt가 닫히지 않았다면
                    pstmt.close();  // pstmt를 닫음
            } catch (Exception e) {  // 예외 발생 시
                e.printStackTrace();  // 예외 메시지 출력
            } finally {
                pstmt = null;  // pstmt를 null로 설정하여 자원 해제
            }
        }

        if (conn != null) {  // Connection이 null이 아니면
            try {
                if (!conn.isClosed())  // conn이 닫히지 않았다면
                    conn.close();  // conn을 닫음
            } catch (Exception e) {  // 예외 발생 시
                e.printStackTrace();  // 예외 메시지 출력
            } finally {
                conn = null;  // conn을 null로 설정하여 자원 해제
            }
        }
        // -- 데이터를 DB에서 받아오며 DB와 연결하는 자체가 자원을 소모하기 때문에 데이터를 연결할 때만 사용하고 안전하게 처리함.
    }


    public static void close(ResultSet rs, PreparedStatement stmt, Connection conn) {
        if (rs != null) {  // ResultSet이 null이 아니면
            try {
                if (!rs.isClosed())  // rs가 닫히지 않았다면
                    rs.close();  // rs를 닫음
            } catch (Exception e) {  // 예외 발생 시
                e.printStackTrace();  // 예외 메시지 출력
            } finally {
                rs = null;  // rs를 null로 설정하여 자원 해제
            }
        }

        if (stmt != null) {  // PreparedStatement가 null이 아니면
            try {
                if (!stmt.isClosed())  // stmt가 닫히지 않았다면
                    stmt.close();  // stmt를 닫음
            } catch (Exception e) {  // 예외 발생 시
                e.printStackTrace();  // 예외 메시지 출력
            } finally {
                stmt = null;  // stmt를 null로 설정하여 자원 해제
            }
        }

        if (conn != null) {  // Connection이 null이 아니면
            try {
                if (!conn.isClosed())  // conn이 닫히지 않았다면
                    conn.close();  // conn을 닫음
            } catch (Exception e) {  // 예외 발생 시
                e.printStackTrace();  // 예외 메시지 출력
            } finally {
                conn = null;  // conn을 null로 설정하여 자원 해제
            }
        }
        //--자바에서 데이터 변환이나 변동이 있으면 디비에 전송 하는 역할을 하며 정보 전달이 있을 때만 잠시만 열고 닫으며 이를 안전하게 처리해주는 명령어?
    }

    // main 메서드: 프로그램 실행의 진입점
    public static void main(String[] args) {
        getConnection();  // 데이터베이스에 연결을 시도하는 메서드 호출
    }
}
