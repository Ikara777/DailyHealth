package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.ManagementExer;

public class ManagementDAOCalendar {

    private Connection conn = null;
    private PreparedStatement stmt = null;
    private ResultSet rs = null;


    private ManagementDAOCalendar() {
    }

    private static ManagementDAOCalendar instance = new ManagementDAOCalendar();
    public static ManagementDAOCalendar getInstance() {
        return instance;
    }
    //  인스턴스가 여러 번 생성되는 것을 방지하고, 애플리케이션 전체에서 하나의 인스턴스만 사용되도록 보장할 수 있습니다.

    // 전체 회원 리스트 조회 메서드
    public List<ManagementExer> managementListExerByDate(String date) {
        List<ManagementExer> list = new ArrayList<>();

        // DB에서 해당 날짜에 맞는 운동 기록을 가져오는 쿼리문
        String query = "SELECT * FROM Record WHERE date = ?"; 

        try (Connection conn = DataBase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) { // 연결된 DB에서 쿼리문 실행
            stmt.setString(1, date); //외부에서 받아온 데이터를 기준으로 찾음
            ResultSet rs = stmt.executeQuery(); // 찾은 결과에 따라서 값을 리턴함.
            
            while (rs.next()) { //rs에서 값을 옳바르게 찾으면 true를 리턴하여 while문이 자동해 해당하는 데이터를 모두 출력함
                ManagementExer exer = new ManagementExer();
                exer.setId(rs.getString("id"));
                exer.setName(rs.getString("name"));
                exer.setDate(rs.getString("date"));
                exer.setPart(rs.getString("part"));
                exer.setStarttime(rs.getString("starttime"));
                exer.setEndtime(rs.getString("endtime"));
                exer.setTname(rs.getString("tname"));
                list.add(exer); //list에 검색된 모든 결과를 담음
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list; //메소드 형식이 LIST라 while문에서 받은 list 값을 모두 반환함
    }

}