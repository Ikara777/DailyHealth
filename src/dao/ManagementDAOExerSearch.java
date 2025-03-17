package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.ManagementExer;

public class ManagementDAOExerSearch {

    private Connection conn = null;
    private PreparedStatement stmt = null;
    private ResultSet rs = null;

    private final  String Search = "select * from record where id = ? order by date DESC;";
    // 값을 설정하는 쿼리문


    private ManagementDAOExerSearch() {}
    private static ManagementDAOExerSearch instance = new ManagementDAOExerSearch();
    public static ManagementDAOExerSearch getInstance() {
        return instance;
    }
    // 싱글톤 패턴을 생성하여 인스턴스가 더 호출되는걸 막음


    public List<ManagementExer> searchMember(String id) {
        // 외부에서 검색하기 위한 id를 받는 메소드

        conn = DataBase.getConnection();
        List<ManagementExer> searchResults = new ArrayList<>();

        try {
            stmt = conn.prepareStatement(Search);
            stmt.setString(1, id);  // 쿼리의 ? 자리에 ID 매개변수 설정
            rs = stmt.executeQuery();  // 쿼리 실행

            while (rs.next()) {
                ManagementExer mdto = new ManagementExer(); //운동정보 매소드를 호출하여 DB에서 호출받은 값을 rs에서 get으로 받음
                mdto.setId(rs.getString("id"));
                mdto.setName(rs.getString("name"));
                mdto.setDate(rs.getString("date"));
                mdto.setPart(rs.getString("part"));
                mdto.setStarttime(rs.getString("starttime"));
                mdto.setEndtime(rs.getString("endtime"));
                mdto.setTname(rs.getString("Tname"));
                searchResults.add(mdto);  // 검색 결과를 생성된 리스트 변수에 추가
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return searchResults;  // 검색된 결과 리스트 반환
    }

    // 전체 회원 리스트 조회 메서드
    public List<ManagementExer> managementListExerSearch() {
        conn = DataBase.getConnection();
        List<ManagementExer> list = new ArrayList<ManagementExer>();
        // 값을 리턴할 list 생성

        try {
            stmt = conn.prepareStatement(Search);
            rs = stmt.executeQuery();
            while (rs.next()) {
                ManagementExer mdto = new ManagementExer();
                mdto.setId(rs.getString("id"));
                mdto.setName(rs.getString("name"));
                mdto.setDate(rs.getString("date"));
                mdto.setPart(rs.getString("part"));
                mdto.setStarttime(rs.getString("starttime"));
                mdto.setEndtime(rs.getString("endtime"));
                mdto.setTname(rs.getString("tname"));
                list.add(mdto); // 값을 리턴하기 위해 list에 정보를 담음
            }

            return list; // 이상 없다면 정보를 전달

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null; // 값이 없거나 이상하면 null을 전달
    }
}