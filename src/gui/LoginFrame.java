package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import dao.ManagementDAO;

public class LoginFrame extends JFrame {

    private JPanel lPanel;
    private JLabel lLogin, lId, lPasswd;
    private JTextField tId;
    private JPasswordField tPasswd;
    private JButton login, register, exit;

    public LoginFrame() {
        super("로그인");
        super.setResizable(true);
        setSize(550, 400);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(2, 7, 21));  // 배경색을 설정


        lPanel = new JPanel();
        lPanel.setLayout(new BorderLayout());
        setContentPane(lPanel);

        lLogin = new JLabel("DAILY HEALTH");
        lLogin.setFont(new Font("Alfa Slab One", Font.BOLD, 50));
        lLogin.setHorizontalAlignment(SwingConstants.CENTER);
        lLogin.setPreferredSize(new Dimension(120, 120));
        lPanel.add(lLogin, BorderLayout.NORTH);
        lPanel.setBackground(new Color(2, 7, 21));  // 배경색을  설정


        JPanel Main = new JPanel(new GridLayout(2, 2, 15, 15));
        Main.setBorder(BorderFactory.createEmptyBorder(0, 70, 0, 0)); // 여백 추가


        lId = new JLabel("ID");
        lId.setFont(new Font("S-Core Dream 8", Font.BOLD, 15));
        lId.setForeground(Color.BLACK);  // lId 텍스트 색을 검은색으로 변경
        lLogin.setForeground(new Color(30, 240, 240));  // lLogin 텍스트 색을 변경
        lId.setHorizontalAlignment(SwingConstants.CENTER);
        Main.add(lId);


        tId = new JTextField();
        tId.setPreferredSize(new Dimension(150, 30)); // 150px 너비, 30px 높이로 설정
        tId.setFont(new Font("S-Core Dream 4", Font.BOLD, 12));//글시채 설정
        lId.setForeground(Color.white);  // lPasswd 텍스트 색을 흰색으로 변경
        tId.setColumns(15);
        Main.add(tId);

        lPasswd = new JLabel("Password");
        lPasswd.setFont(new Font("S-Core Dream 8", Font.BOLD, 15));
        lPasswd.setForeground(Color.white);  // lPasswd 텍스트 색을 검은색으로 변경
        lPasswd.setHorizontalAlignment(SwingConstants.CENTER);
        Main.add(lPasswd);

        tPasswd = new JPasswordField();
        tPasswd.setColumns(10);
        tPasswd.setFont(new Font("S-Core Dream 4", Font.BOLD, 12));//글시채 설정
        Main.add(tPasswd);
        Main.setBackground(new Color(2, 7, 21));  // 입력창 배경 색상 설정

        lPanel.add(Main, BorderLayout.WEST);

        JPanel btnMain = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 60));

        login = new JButton("로그인");
        login.setFont(new Font("S-Core Dream 8", Font.BOLD, 12));//글시채 설정
        btnMain.add(login);
        login.setForeground(new Color(2, 7, 21));  // 로그인 버튼의 텍스트 색을 변경
        login.setBackground(Color.white);  // 로그인 버튼 배경색


        register = new JButton("회원등록");
        register.setFont(new Font("S-Core Dream 8", Font.BOLD, 12));//글시채 설정
        btnMain.add(register);
        register.setForeground(new Color(2, 7, 21));  // 회원등록 버튼의 텍스트 색을  변경
        register.setBackground(Color.white);  // 회원등록 버튼 배경색

        exit = new JButton("닫기");
        exit.setFont(new Font("S-Core Dream 8", Font.BOLD, 12));//글시채 설정
        btnMain.add(exit);
        exit.setForeground(new Color(2, 7, 21));  // 닫기 버튼의 텍스트 색을 검은색 변경
        exit.setBackground(Color.white);  // 닫기 버튼 배경색


        lPanel.add(btnMain, BorderLayout.SOUTH);
        btnMain.setBackground(new Color(2, 7, 21));  // 버튼 패널 배경 색상 설정

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        login.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                String id = tId.getText();
                String passwd = tPasswd.getText();

                ManagementDAO mdao = ManagementDAO.getInstance();

                int result = mdao.idPassword(id, passwd);
                if (result == 1) {
                    JOptionPane.showMessageDialog(null, "로그인 완료");
                    ListView lf = new ListView();
                    dispose();

                } else {
                    JOptionPane.showMessageDialog(null, "로그인 실패");
                }

            }
        });

        register.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                RegisterFrame rf = new RegisterFrame();

            }
        });

        exit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                dispose();

            }
        });
    }


    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    LoginFrame lif = new LoginFrame();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }
}