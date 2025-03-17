package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dto.ManagementExer;


// insert into 하는 부분
public class ManagementDAOExer {

    private Connection conn = null;
    private PreparedStatement stmt = null;
    private ResultSet rs = null;

    private final String INSERT = "insert into record(id,name,date,part,starttime,endtime,tname) "
            + "values(?,?,?,?,?,?,?)";
    private final String LIST = "SELECT I.id, I.name, R.date, R.part, R.starttime,R.endtime,R.tname " +
            "FROM information I INNER JOIN Record R ON I.id = R.id ORDER BY R.date DESC;";


    private ManagementDAOExer() {}
    private static ManagementDAOExer instance = new ManagementDAOExer();

    public static ManagementDAOExer getInstance() {
        return instance;
    }


    public int insertMember(ManagementExer mdto) {

        conn = DataBase.getConnection();

        try {
            stmt = conn.prepareStatement(INSERT);

            stmt.setString(1, mdto.getId());
            stmt.setString(2, mdto.getName());
            stmt.setString(3, mdto.getDate());
            stmt.setString(4, mdto.getPart());
            stmt.setString(5, mdto.getStarttime());
            stmt.setString(6, mdto.getEndtime());
            stmt.setString(7, mdto.getTname());
            stmt.executeUpdate();
            return 1;

        } catch(Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public List<ManagementExer> managementListExer() {
        conn = DataBase.getConnection();
        List<ManagementExer> list = new ArrayList<ManagementExer>();

        try {
            stmt = conn.prepareStatement(LIST);
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
                list.add(mdto);

            }

            return list;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // 트레이너 Tname을 sql에서 긁어올 수 있게하는것
    public List<String> getTrainers() {
        conn = DataBase.getConnection();
        List<String> trainers = new ArrayList<>();
        String sql = "SELECT Tname FROM trainers"; // 트레이너 이름을 가져오는 SQL 예시
        try  {
            stmt=conn.prepareStatement(sql);
            rs=stmt.executeQuery();
            while (rs.next()) {
                trainers.add(rs.getString("Tname"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trainers;
    }

}