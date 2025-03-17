package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.ManagementExer;

public class ManagementDAOExerUpDate {

    private Connection conn = null;
    private PreparedStatement stmt = null;
    private ResultSet rs = null;

    // DB에서 명령어를 처리할 수 있게 하는 쿼리문 작성
    private final  String Update = "UPDATE record SET name = ?, part = ?, starttime = ?, endtime = ?, Tname = ? WHERE id = ? AND date = ?;";
    private final String LIST = "SELECT I.id, I.name, R.date, R.part, R.starttime,R. endtime, R.Tname FROM information I INNER JOIN Record R ON I.id = R.id ORDER BY R.date DESC;";

    // 싱글톤 패턴 적용
    private ManagementDAOExerUpDate() {}
    private static ManagementDAOExerUpDate instance = new ManagementDAOExerUpDate();
    public static ManagementDAOExerUpDate getInstance() {
        return instance;
    }


    public int updateMember(ManagementExer mdto) {

        conn = DataBase.getConnection(); // 디비와 연결하는 Connection 변수

        try {
            stmt = conn.prepareStatement(Update); // db에 업데이트 쿼리를 전달할 준비를 함.

            // stmt 에서 받은 db 정보를 ? 숫자의 순서대로 정리함.
            stmt.setString(1, mdto.getName());
            stmt.setString(2, mdto.getPart());
            stmt.setString(3, mdto.getStarttime());
            stmt.setString(4, mdto.getEndtime());
            stmt.setString(5, mdto.getTname());
            stmt.setString(6, mdto.getId());
            stmt.setString(7, mdto.getDate());


            // 쿼리 실행
            int rowsUpdated = stmt.executeUpdate();  // UPDATE 문 실행
            if (rowsUpdated > 0) { // 전달받은 데이터가 0이상이면 실행
                return 1; // 성공
            } else {
                return -1; // id, name, date에 해당하는 레코드가 없거나 업데이트 실패
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;  // 예외 발생시 실패 반환
    }

    public List<ManagementExer> managementListExerUpdate() {
        conn = DataBase.getConnection(); // 데이터베이스 연결 객체 가져오기
        List<ManagementExer> list = new ArrayList<ManagementExer>(); // 운동 정보 객체들을 저장할 리스트 생성

        try {
            stmt = conn.prepareStatement(LIST); // LIST 쿼리 준비하여 stmt에 저장
            rs = stmt.executeQuery(); // 쿼리 실행 후 결과를 rs(ResultSet)에 담음

            while (rs.next()) { // ResultSet(rs)에서 다음 데이터가 있을 때마다 실행됨
                ManagementExer mdto = new ManagementExer(); // ManagementExer 객체 생성

                // ResultSet에서 데이터를 가져와 mdto 객체에 세터를 통해 설정
                mdto.setId(rs.getString("id"));
                mdto.setName(rs.getString("name"));
                mdto.setDate(rs.getString("date"));
                mdto.setPart(rs.getString("part"));
                mdto.setStarttime(rs.getString("starttime"));
                mdto.setEndtime(rs.getString("endtime"));
                mdto.setTname(rs.getString("tname"));

                list.add(mdto); // 데이터를 모두 설정한 mdto 객체를 리스트에 추가
            }

            return list; // 데이터가 모두 추가된 리스트를 리턴

        } catch (Exception e) {
            e.printStackTrace(); // 예외 발생 시 스택 트레이스 출력
        }

        return null; // 예외가 발생한 경우 null을 리턴
    }



}