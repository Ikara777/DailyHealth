package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dto.ManagementDTO;

import javax.swing.*;

public class ManagementDAO {

    private Connection conn = null; // 데이터베이스 연결 객체를 초기화 (null로 설정)
    private PreparedStatement stmt = null; // 쿼리 연결을 위한 클래스 / 성능 향상도 시켜줌 있으면 꿀
    private ResultSet rs = null; // 데이터 베이스가 정보를 되받아 오는 값을 저장 하는 명령어

    private final String LOGIN = "select * from information where id = ? and passwd = ?";
    // select * from 으로 데이터 베이스에서 아이디와 페스워드를 검색하여 조회.
    private final String INSERT = "insert into information(id,passwd,name,tel,address,gender) "
            + "values(?,?,?,?,?,?)";
    // 회원 가입 쿼리 : 새로운 회원 정보를 information 테이블에 삽입 시키는  변수

    private ManagementDAO() {
    }

    private static ManagementDAO instance = new ManagementDAO(); // 싱글톤 패턴을 구현한 코드

    public static ManagementDAO getInstance() {
        return instance;
    }
    //  인스턴스가 여러 번 생성되는 것을 방지하고, 애플리케이션 전체에서 하나의 인스턴스만 사용되도록 보장할 수 있습니다.

    // ID 와 psswd를 받아들이는 창이다.
    public int idPassword(String id, String passwd) {

        conn = DataBase.getConnection(); // 메서드를 호출하여 데이터베이스 연결을 가져옴 이걸 통해 연결된 상태에서 쿼리문 실행

        try {
            stmt = conn.prepareStatement(LOGIN); // 데이터 베이스에서 검색할 LOGIN 쿼리문을 미리 준비함 준비

            stmt.setString(1, id);  // 첫 번째 ? 자리에는 외부에서 받은 id를 넣음
            stmt.setString(2, passwd); // 두 번째 ? 자리에는 외부에서 받은 passwd를 넣음

            rs = stmt.executeQuery();  // 쿼리 실행, 성공 여부를 ResultSet에 저장하여 true 와 false 반환

            if (rs.next()) {
                return 1;
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }



    //ListView에서  아이디와 비밀번호 등  가입정보를 받고 이상 없이 추가가 되면 1리턴 안되면 -1을 리턴
    public int insertMember(ManagementDTO mdto) {

        conn = DataBase.getConnection(); // 메서드를 호출하여 데이터베이스 연결을 가져옴 이걸 통해 연결된 상태에서 쿼리문 실행


        if (mdto.getId().trim().isEmpty()) {
            // ID가 빈 공백일 경우 JOptionPane.showMessageDialog 오류 메세지를 작동 시켜 다시 화면을 되돌림
            JOptionPane.showMessageDialog(null, "ID를 입력하지 않으셨습니다. \n다시 입력해주세요.", "ID 입력 오류", JOptionPane.ERROR_MESSAGE);
            return -1;  // 빈 공백이면 회원가입이 되지 않도록 하고 여기서 끝냄
        }


        try {
            PreparedStatement pstmtCheck = conn.prepareStatement("SELECT COUNT(*) FROM information WHERE id = ?"); // ?에 아이디를 넣고 조건문을 넣어 information 문에서 아이디를 검색함.
            pstmtCheck.setString(1, mdto.getId()); //mdto 생성자에서 get으로 받은 id를 전달
            ResultSet rs = pstmtCheck.executeQuery();  // 쿼리 실행 후 결과를 rs에 저장
            rs.next(); // 결과가 있다면 첫 번째 결과 행으로 이동
            int count = rs.getInt(1); // 검색된 아이디를 숫자로 저장
            // 중복된 아이디가 있을 경우
            if (count > 0) {
                // 아이디가 잘못되면 0값을 반환하도록 설정
                return 0;
            }


            String information = mdto.getTel();  // 전화번호를 입력받음
            try {
                // 전화번호가 숫자만 포함하는지 확인
                Integer.parseInt(information);  // 숫자로 변환 시도
            } catch (NumberFormatException ex) {
                // 전화번호가 숫자가 아니면 예외 발생
                return 2;  // 전화번호가 올바르지 않으면 진행하지 않음
            }


        } catch (SQLException e) { //그밖의 예외 사항 체크
            e.printStackTrace();
        }

        try {
            stmt = conn.prepareStatement(INSERT); //DB와 연결된 conn 객체를 사용해 정보를 추가시키는 INSERT 쿼리문을 준비

            stmt.setString(1, mdto.getId());
            stmt.setString(2, mdto.getPasswd());
            stmt.setString(3, mdto.getName());
            stmt.setString(4, mdto.getTel());
            stmt.setString(5, mdto.getAddress());
            stmt.setString(6, mdto.getGender());
            // 각각 ?에 들어가는 순번대로 정보를 넣음
            stmt.executeUpdate();  // 쿼리를 실행하고 데이터베이스에 변경을 반영
            return 1; //성공하면 1 리턴



        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1; // 실패하면 -1 리턴
    }


}
