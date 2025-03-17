package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import dao.ManagementDAO;
import dto.ManagementDTO;

public class RegisterFrame extends JFrame {

    private JPanel Panel;
    private JLabel Label;
    private JLabel Id, Passwd, Name,confirmPasswd, Tel, Address;
    private JTextField tId, tPasswd, tconfirmPasswd, tName, tTel, tAddress;
    private JRadioButton Man, Woman;
    private JButton Button;

    public RegisterFrame() {

        super("회원등록");
        super.setResizable(true);
        setSize(350, 400);
        setLocationRelativeTo(null);

        Panel = new JPanel();
        Panel.setLayout(new BorderLayout());
        setContentPane(Panel);

        Label = new JLabel("회원 등록");
        Label.setFont(new Font("Freesentation 1", Font.BOLD, 25));
        Label.setHorizontalAlignment(SwingConstants.CENTER);
        Panel.add(Label, BorderLayout.NORTH);
        Label.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 0)); // 여백 추가

        JPanel main = new JPanel(new GridLayout(8, 2, 10, 10)); //행렬 개수 수정
        Id = new JLabel("아이디");
        Id.setHorizontalAlignment(SwingConstants.CENTER);
        main.add(Id);

        tId = new JTextField();
        main.add(tId);

        Passwd = new JLabel("비밀 번호");
        Passwd.setHorizontalAlignment(SwingConstants.CENTER);
        main.add(Passwd);
        tPasswd = new JTextField();
        main.add(tPasswd);

        confirmPasswd = new JLabel("비밀 번호 확인");
        confirmPasswd .setHorizontalAlignment(SwingConstants.CENTER);
        main.add(confirmPasswd );
        tconfirmPasswd = new JTextField();
        main.add(tconfirmPasswd);


        Name = new JLabel("성함");
        Name.setHorizontalAlignment(SwingConstants.CENTER);
        main.add(Name);

        tName = new JTextField();
        main.add(tName);

        Tel = new JLabel("전화번호");
        Tel.setHorizontalAlignment(SwingConstants.CENTER);
        main.add(Tel);

        tTel = new JTextField();
        main.add(tTel);

        Address = new JLabel("주소");
        Address.setHorizontalAlignment(SwingConstants.CENTER);
        main.add(Address);

        tAddress = new JTextField();
        main.add(tAddress);


        ButtonGroup bg = new ButtonGroup();

        Man = new JRadioButton("남자");
        Man.setHorizontalAlignment(SwingConstants.CENTER);
        Man.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0)); // 여백 추가
        bg.add(Man);


        main.add(Man);
        Woman = new JRadioButton("여자");
        Woman.setHorizontalAlignment(SwingConstants.CENTER);
        Woman.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0)); // 여백 추가
        bg.add(Woman);
        main.add(Woman);

        Panel.add(main, BorderLayout.CENTER);

        JPanel sMain = new JPanel();
        Panel.add(sMain, BorderLayout.EAST);

        Button = new JButton("등록하기");
        Button.setFont(new Font("Freesentation 1", Font.BOLD, 25));

        Panel.add(Button, BorderLayout.SOUTH);
        Button.setForeground(Color.WHITE);
        Button.setBackground(new Color(2, 7, 21));

        setVisible(true);

        Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String password = tPasswd.getText();          // 비밀번호
                String confirmPassword = tconfirmPasswd.getText();  // 비밀번호 확인

                // 비밀번호와 비밀번호 확인이 일치하지 않는 경우
                if (!password.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(null, "비밀번호가 일치하지 않습니다.");
                    return;  // 일치하지 않으면 진행하지 않음
                }


                ManagementDTO mdto = new ManagementDTO();

                mdto.setId(tId.getText());
                mdto.setPasswd(tPasswd.getText());
                mdto.setName(tName.getText());
                mdto.setTel(tTel.getText());
                mdto.setAddress(tAddress.getText());

                if(Man.isSelected())
                    mdto.setGender(Man.getText());
                else
                    mdto.setGender(Woman.getText());


                ManagementDAO mdao = ManagementDAO.getInstance();
                int result = mdao.insertMember(mdto);


                mdto.setAddress(tAddress.getText());
                if (Man.isSelected())
                    mdto.setGender(Man.getText());
                else
                    mdto.setGender(Woman.getText());


                if (result == 1) {
                    JOptionPane.showMessageDialog(null, "회원등록 완료");
                    dispose();
                } else if (result==0) {
                    JOptionPane.showMessageDialog(null, "이미 존재하는 아이디입니다.");
                } else if (result==2) {
                    JOptionPane.showMessageDialog(null, "전화번호가 숫자가 아닙니다.");
                } else {
                    JOptionPane.showMessageDialog(null, "회원동록 실패");
                }
            }
        });

    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    RegisterFrame rf = new RegisterFrame();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }
}