package dao;

import dto.ManagementExer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ManagementDAOTrainers {

    private Connection conn = null;
    private PreparedStatement stmt = null;
    private ResultSet rs = null;

    // trainers 디비에 명령을 전달하는 쿼리
    private final String INSERT = "insert into trainers(tname) values(?)";
    private final String DELETE = "DELETE FROM trainers WHERE tname = ?";  // id를 사용해서 삭제
    private final String CHECK_DUPLICATE = "SELECT COUNT(*) FROM trainers WHERE tname = ?";  // 중복 체크 쿼리

    public ManagementDAOTrainers() {}
    private static ManagementDAOTrainers instance = new ManagementDAOTrainers();

    public static ManagementDAOTrainers getInstance() {
        return instance;
    }
    // 트레이너 DAO를 사용하기 위한 싱글톤 패턴 생성


    // 1. 중복된 tname이 있는지 확인하는 메서드
    public int checkDuplicate(String Tname) { // 외부에서 tname을 받아옴
        conn = DataBase.getConnection();

        try {
            stmt = conn.prepareStatement(CHECK_DUPLICATE); // 검색된 수의 tname 만큼 조회하는 쿼리문
            stmt.setString(1, Tname);
            rs = stmt.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                return -1;  // 중복된 tname이 존재하면 -1 반환
            }
            return 1;  // 중복된 tname이 없으면 1 반환

        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }

        return -1; // 조건이 실패해도 -1반환
    }


    // 2. tname이 중복되지 않으면 trainers 테이블에 추가하는 메서드
    public int insertMember(String Tname) {
        int check = checkDuplicate(Tname);  // checkDuplicate에 값을 대입하여 1혹은 -1의 값을 리턴받아 check에 저장
        if (check == -1) {
            return -1;  // 이미 존재하는 tname일 경우 -1 반환
        }

        conn = DataBase.getConnection(); // 데이터 배이스와 연동시키는 변수

        try {
            stmt = conn.prepareStatement(INSERT); //추가를 진행하는 쿼리문
            stmt.setString(1, Tname); // 1번 자리에 추가
            stmt.executeUpdate(); // 데이터 베이스에 업데이트
            return 1;  // 정상적으로 추가됨
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }

        return -1;
    }

    // 3. tname을 DB에서 삭제하는 메서드
    public int deleteMember(String Tname) {
        conn = DataBase.getConnection();

        try {
            stmt = conn.prepareStatement("SELECT * FROM trainers WHERE tname = ?");
            stmt.setString(1, Tname);
            rs = stmt.executeQuery();

            if (!rs.next()) {
                return -1;  // tname이 존재하지 않으면 -1 반환
            }

            // tname이 존재하면 삭제
            stmt = conn.prepareStatement(DELETE);
            stmt.setString(1, Tname);
            stmt.executeUpdate();
            return 1;  // 정상적으로 삭제됨

        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }

        return -1;
    }

    // DB 리소스 닫는 메서드
    private void closeResources() {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // 트레이너 삭제
    public int DelcheckDuplicate(String Tname) {
        conn = DataBase.getConnection();

        try {
            stmt = conn.prepareStatement(CHECK_DUPLICATE);
            stmt.setString(1, Tname);
            rs = stmt.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                return 1;  //  tname이 존재하면 +1 반환
            }
            return -1;  // 선택된 tname이 없으면 -1 반환

        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }

        return -1;
    }
}
