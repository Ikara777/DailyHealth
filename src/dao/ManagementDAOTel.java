package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import dto.ManagementDTO;



// insert into 하는 부분
public class ManagementDAOTel {

    private Connection conn = null;
    private PreparedStatement stmt = null;
    private ResultSet rs = null;

    private final String LIST = "select * from information order by id;";
    private final String FIND = "select * from information where id = ? and name = ?;";


    private ManagementDAOTel() {}
    private static ManagementDAOTel instance = new ManagementDAOTel();

    public static ManagementDAOTel getInstance() {
        return instance;
    }
    //싱글톤 패턴 적용

    public List<ManagementDTO> managementListTel() {
        conn = DataBase.getConnection(); //데이터 베이스 연결 객체 가져 오기
        List<ManagementDTO> list = new ArrayList<ManagementDTO>(); // 회원 정보 객체들을 저장할 리스트 생성

        try {
            stmt = conn.prepareStatement(LIST); // LIST 쿼리 준비하여 stmt에 저장
            rs = stmt.executeQuery();// 쿼리 실행 후 결과를 rs(ResultSet)에 담음
            while (rs.next()) { // ResultSet(rs)에서 다음 데이터가 있을 때마다 실행됨
                ManagementDTO mdt = new ManagementDTO();
                mdt.setId(rs.getString("id"));
                mdt.setPasswd(rs.getString("passwd"));
                mdt.setName(rs.getString("name"));
                mdt.setTel(rs.getString("tel"));
                mdt.setAddress(rs.getString("address"));
                mdt.setGender(rs.getString("gender"));
                list.add(mdt); // 데이터를 모두 설정한 mdto 객체를 리스트에 추가

            }

            return list; // 데이터가 모두 추가된 리스트를 리턴

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public int idName(String id, String name) {

        conn = DataBase.getConnection(); //데이터 베이스 연결 객체 가져 오기

        try {
            stmt = conn.prepareStatement(FIND); // FIND 쿼리 준비하여 stmt에 저장

            stmt.setString(1, id);// 쿼리문의 1번째에 해당하는 값을 넣음
            stmt.setString(2, name); // 퀴리문의 2번째에 해당하는 값을 넣음

            rs = stmt.executeQuery(); // 쿼리 실행 후 결과를 ResultSet 객체(rs)에 저장

            if(rs.next()) { // 저장된 데이터가 있으면 true로 변환 후 실행
                return 1; // 값이 옳바르거나 있으면 1을 리턴함
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }
}