package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JCalendar;


import dao.*;
import dto.ManagementDTO;
import dto.ManagementExer;

import java.awt.BorderLayout;


public class ListView extends JFrame {

    // 각각의 영역에 해당하는 영역을 변수로 지정
    private JPanel panel;
    private JTable table;
    private JLabel lilabel;
    private JButton logout, update, plus, del, search, refresh, info, calendarButton, trainers;
    private DefaultTableModel tModel;

    public ListView() {
        // ListView의 기본적인 프래임 크기와 보이는 방식 등 설정
        super("List");
        super.setResizable(true);
        setSize(1300, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel(new BorderLayout()); // 전체 레이아웃은 BorderLayout
        panel.setBorder(new EmptyBorder(8, 8, 8, 8));
        setContentPane(panel);
        panel.setBackground(new Color(27,31,44));  // 판낼 태두리 배경 색상 설정


        // 상단 버튼들을 하나의 패널로 묶어두기 (FlowLayout 사용)
        JPanel topPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon icon = new ImageIcon("C:\\Java4.Project\\LiveViewBack100-2.jpg"); // 이미지 경로 설정
                Image img = icon.getImage();
                g.drawImage(img, 0, 0, getWidth(), getHeight(), this); // 배경으로 이미지 그리기
            }
        };

        topPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 20, 20));  // 오른쪽 정렬, 버튼들 간 간격 20px

        refresh = new JButton("회원 운동 정보");
        refresh.setFont(new Font("Freesentation 8", Font.BOLD, 12));
        refresh.setForeground(new Color(2, 7, 21));
        refresh.setBackground(new Color(217,246,105));
        topPanel.add(refresh); // 버튼 탑 패널에 추가
        topPanel.setBackground(new Color(2, 7, 21));


        refresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RefreshTable();
            }
        });

        info = new JButton("회원 리스트");
        info.setFont(new Font("Freesentation 8", Font.BOLD, 12));
        info.setForeground(new Color(2, 7, 21));
        info.setBackground(new Color(217,246,105));

        topPanel.add(info); // 회원 리스트 버튼을 상단 패널에 추가
        info.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                RefreshTelTable();
            }
        });



        // 회원 운동 정보 추가 버튼
        plus = new JButton("회원 운동 정보 추가");
        plus.setFont(new Font("Freesentation 8", Font.BOLD, 12));
        plus.setForeground(new Color(2, 7, 21));  //  버튼의 텍스트 색을 변경
        plus.setBackground(new Color(217,246,105));  // 버튼 배경색
        topPanel.add(plus); //회원 운동 정보 추가를 상단 페널에 추가

        plus.addActionListener(new ActionListener() {
            // 추가 액션 버튼을 누르면 회원 운동 정보 추가 메소드가 작동 되도록 함

            @Override
            public void actionPerformed(ActionEvent e) { // 새로 생기는 창의 설정과 액션 값을 담은 매소드
                // 테이블 갱신
                RefreshTable();

                // 새로운 작은 창 (JDialog) 생성
                final JDialog dialog = new JDialog(); // 새로 출력 되는 작은 창
                dialog.setTitle("회원 운동 정보 추가"); // 타이틀
                dialog.setSize(400, 400); // 창 크기 설정
                dialog.setLocationRelativeTo(null); // 화면 중앙에 띄우기
                dialog.setModal(true); // 부모 창이 비활성화 되지 않도록 설정 - 부모창 ListView

                JPanel panel = new JPanel();
                panel.setLayout(new BorderLayout());
                dialog.setContentPane(panel);

                JLabel label = new JLabel("회원 운동 정보 추가");
                label.setFont(new Font("Freesentation 2", Font.BOLD, 20));
                label.setHorizontalAlignment(SwingConstants.CENTER);
                panel.add(label, BorderLayout.NORTH);
                panel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0)); // 상단 타이틀 여백 추가


                JPanel mainPanel = new JPanel(new GridLayout(8, 2, 10, 10)); // 입력 필드를 위한 중앙 레이아웃
                mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 30)); // 여백 추가

                // 각 항목 추가
                JLabel idLabel = new JLabel("아이디");
                idLabel.setHorizontalAlignment(SwingConstants.CENTER);
                mainPanel.add(idLabel);

                final JTextField tId = new JTextField();
                mainPanel.add(tId);

                JLabel nameLabel = new JLabel("성함");
                nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
                mainPanel.add(nameLabel);
                final JTextField tName = new JTextField();
                mainPanel.add(tName);

                JLabel dateLabel = new JLabel("날짜");
                dateLabel.setHorizontalAlignment(SwingConstants.CENTER);
                mainPanel.add(dateLabel);
                final JTextField tDate = new JTextField();
                mainPanel.add(tDate);

                JLabel partLabel = new JLabel("부위");
                partLabel.setHorizontalAlignment(SwingConstants.CENTER);
                mainPanel.add(partLabel);
                final JTextField tPart = new JTextField();
                mainPanel.add(tPart);

                JLabel startTimeLabel = new JLabel("시작 시간");
                startTimeLabel.setHorizontalAlignment(SwingConstants.CENTER);
                mainPanel.add(startTimeLabel);
                final JTextField tStartTime = new JTextField();
                mainPanel.add(tStartTime);

                JLabel endTimeLabel = new JLabel("끝난 시간");
                endTimeLabel.setHorizontalAlignment(SwingConstants.CENTER);
                mainPanel.add(endTimeLabel);
                final JTextField tEndTime = new JTextField();
                mainPanel.add(tEndTime);


                // 트레이너 목록을 가져와서 JComboBox에 추가
                List<String> trainers = ManagementDAOExer.getInstance().getTrainers(); // 트레이너 목록 가져오기

                JLabel trainerLabel = new JLabel("트레이너");
                trainerLabel.setHorizontalAlignment(SwingConstants.CENTER);
                mainPanel.add(trainerLabel);

                final JComboBox<String> trainerComboBox = new JComboBox<>();
                for (String trainer : trainers) {
                    trainerComboBox.addItem(trainer);
                } // 반복문으로 트레이너 수만큼 콤보박스 생성 및 출력
                mainPanel.add(trainerComboBox);

                panel.add(mainPanel, BorderLayout.CENTER); // 중앙 메인 페널을 눈에 보일 수 있도록 판넬에 추가


                // 버튼 패널 추가
                JPanel buttonPanel = new JPanel();
                panel.add(buttonPanel, BorderLayout.SOUTH);
                buttonPanel.setBackground(new Color(27, 31, 44)); // 버튼 배경색 수정

                JButton addButton = new JButton("추가하기");
                addButton.setFont(new Font("Freesentation 1", Font.BOLD, 12));
                addButton.setBackground(Color.white); //추가하기 버튼 색 변경
                buttonPanel.add(addButton);
                buttonPanel.setBackground(new Color(27, 31, 44)); // 버튼 배경색 수정

                // "추가하기" 버튼 클릭 시 동작
                addButton.addActionListener(new ActionListener() {
                    @Override


                    public void actionPerformed(ActionEvent e) {
                        String idToCreate = tId.getText();
                        String nameToCreate = tName.getText();
                        String dateToCreate = tDate.getText();
                        String partToCreate = tPart.getText();
                        String startTimeToCreate = tStartTime.getText();
                        String endTimeToCreate = tEndTime.getText();
                        String selectedTrainer = (String) trainerComboBox.getSelectedItem(); // 선택된 트레이너 가져오기

                        // 아이디와 이름, 등이 빈 영역인지, 비어있는지 등의 조건을 보고 오류창 리턴
                        if (idToCreate != null && !idToCreate.trim().isEmpty() && nameToCreate != null && !nameToCreate.trim().isEmpty()) {
                            // 회원 존재 여부 확인
                            if (!dateToCreate.matches("\\d{4}-\\d{2}-\\d{2}")) {
                                JOptionPane.showMessageDialog(null, "날짜 형식이 잘못되었습니다. yyyy-mm-dd 형식으로 입력해주세요.");
                                return;
                            }

                            // 시간 형식 검증 (HH:mm)
                            if (!startTimeToCreate.matches("\\d{2}:\\d{2}") || !endTimeToCreate.matches("\\d{2}:\\d{2}")) {
                                JOptionPane.showMessageDialog(null, "시간 형식이 잘못되었습니다. HH:mm 형식으로 입력해주세요.");
                                return;
                            }

                            int result = ManagementDAOTel.getInstance().idName(idToCreate, nameToCreate);
                            // daoTel에서 아이디와 이름 정보를 가지고옴

                            if (result == 1) { //리턴값이 1 즉 옳바르면 운동정보 생성자를 만들고 정보를 가지고옴
                                // 운동 정보 추가
                                ManagementExer mdto = new ManagementExer();
                                mdto.setId(idToCreate);
                                mdto.setName(nameToCreate);
                                mdto.setDate(dateToCreate);
                                mdto.setPart(partToCreate);
                                mdto.setStarttime(startTimeToCreate);
                                mdto.setEndtime(endTimeToCreate);
                                mdto.setTname(selectedTrainer); // 트레이너 이름을 tname으로 저장

                                int addResult = ManagementDAOExer.getInstance().insertMember(mdto);

                                if (addResult == 1) {
                                    JOptionPane.showMessageDialog(dialog, "회원 운동 정보가 추가되었습니다.");
                                    RefreshTable();  // 테이블 갱신
                                    dialog.dispose();  // 창 닫기
                                } else {
                                    JOptionPane.showMessageDialog(dialog, "운동 정보 추가 실패. 오류가 발생했습니다.");
                                }
                            } else {
                                JOptionPane.showMessageDialog(dialog, "아이디와 성함이 일치하는 회원이 없습니다.");
                            }
                        } else {
                            JOptionPane.showMessageDialog(dialog, "아이디와 성함을 모두 입력해야 합니다.");
                        }
                    }
                });


                //

                // 창 표시
                dialog.setVisible(true);
            }
        });
        // 눌렀을때 -> 회원 운동 정보 추가 시스템

        del = new JButton("회원 운동 정보 삭제");
        del.setFont(new Font("Freesentation 8", Font.BOLD, 12));
        del.setForeground(new Color(2, 7, 21));  // 버튼의 텍스트 색을 변경
        del.setBackground(new Color(217,246,105));  // 버튼 배경색
        topPanel.add(del);

        del.addActionListener(new ActionListener() { // 버튼을 누르면 새로운 창이 켜지도록 설정
            @Override
            public void actionPerformed(ActionEvent e) { // 새로 생기는 창의 설정과 액션 값을 담은 매소드
                // 테이블 갱신
                RefreshTable();

                // 새로운 작은 창 (JDialog) 생성
                final JDialog dialog = new JDialog(); // 새로 뜨는 작은 창
                dialog.setTitle("회원 운동 정보 삭제");
                dialog.setSize(400, 250); // 창 크기 설정
                dialog.setLocationRelativeTo(null); // 화면 중앙에 띄우기
                dialog.setModal(true); // 부모 창이 비활성화 되지 않도록 설정

                JPanel panel = new JPanel();
                panel.setLayout(new BorderLayout());
                dialog.setContentPane(panel);

                JLabel label = new JLabel("회원 운동 정보 삭제");
                label.setFont(new Font("Freesentation 1", Font.BOLD, 20));
                label.setHorizontalAlignment(SwingConstants.CENTER);
                panel.add(label, BorderLayout.NORTH);
                panel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0)); // 상단 타이틀 여백 추가


                JPanel mainPanel = new JPanel(new GridLayout(3, 2, 10, 10)); // 입력 필드를 위한 레이아웃
                mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 0, 30)); // 여백 추가

                // 각 항목 추가
                JLabel idLabel = new JLabel("아이디");
                idLabel.setHorizontalAlignment(SwingConstants.CENTER);
                mainPanel.add(idLabel);
                final JTextField tId = new JTextField();
                mainPanel.add(tId);

                JLabel dateLabel = new JLabel("날짜");
                dateLabel.setHorizontalAlignment(SwingConstants.CENTER);
                mainPanel.add(dateLabel);
                final JTextField tDate = new JTextField();
                mainPanel.add(tDate);

                panel.add(mainPanel, BorderLayout.CENTER);

                // 버튼 패널 추가
                JPanel buttonPanel = new JPanel();
                panel.add(buttonPanel, BorderLayout.SOUTH);

                JButton delButton = new JButton("삭제하기");
                delButton.setFont(new Font("Freesentation 1", Font.BOLD, 12));
                buttonPanel.add(delButton);
                delButton.setBackground(Color.white); // 삭제하기 버튼 색 변경
                buttonPanel.setBackground(new Color(27, 31, 44)); //삭제하기 버튼 배경색 수정

                // "삭제하기" 버튼 클릭 시 동작
                delButton.addActionListener(new ActionListener() { //액션 버튼을 누르면 작동하는 삭제 로직
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // 삭제할 아이디와 날짜를 입력받기
                        String idToDelete = tId.getText();
                        String dateToDelete = tDate.getText();

                        // 입력값이 비어있는지 확인
                        if (idToDelete != null && !idToDelete.trim().isEmpty() && dateToDelete != null && !dateToDelete.trim().isEmpty()) {
                            // 삭제 메서드 호출
                            int result = ManagementDAOExerDelete.getInstance().deleteMember(idToDelete, dateToDelete);

                            if (result == 1) {
                                JOptionPane.showMessageDialog(null, "회원 정보가 삭제되었습니다.");
                                dialog.dispose();  // 창 닫기
                                DeleteRefreshTable();  // 테이블 갱신
                            } else {
                                JOptionPane.showMessageDialog(null, "삭제 실패. 해당 아이디가 존재하지 않거나 오류가 발생했습니다.");
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "아이디와 날짜를 모두 입력해야 합니다.");
                        }
                    }
                });

                // 창 표시
                dialog.setVisible(true);
            }
        });



        // 회원 운동 정보 수정 버튼
        update = new JButton("회원 운동 정보 수정");
        update.setFont(new Font("Freesentation 8", Font.BOLD, 12));
        update.setForeground(new Color(2, 7, 21));  //  버튼의 텍스트 색을 변경
        update.setBackground(new Color(217,246,105));  //  버튼 배경색
        topPanel.add(update);
        // 눌렀을때 -> 회원 운동 정보 수정 시스템
        update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { // 수정하기 버튼을 눌럿을 때 나오는 입력창 메소드
                // 테이블 갱신
                RefreshTable();

                // 새로운 작은 창 (JDialog) 생성
                final JDialog dialog = new JDialog(); // 새로 뜨는 작은 창
                dialog.setTitle("회원 운동 정보 수정");
                dialog.setSize(400, 400); // 창 크기 설정
                dialog.setLocationRelativeTo(null); // 화면 중앙에 띄우기
                dialog.setModal(true); // 부모 창이 비활성화 되지 않도록 설정

                JPanel panel = new JPanel();
                panel.setLayout(new BorderLayout());
                panel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0)); // 상단 타이틀 여백 추가
                dialog.setContentPane(panel);

                JLabel label = new JLabel("회원 운동 정보 수정");
                label.setFont(new Font("Freesentation 1", Font.BOLD, 20));
                label.setHorizontalAlignment(SwingConstants.CENTER);
                panel.add(label, BorderLayout.NORTH);

                JPanel mainPanel = new JPanel(new GridLayout(8, 2, 10, 10)); // 입력 필드를 위한 레이아웃
                mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 0, 20)); // 여백 추가


                // 각 항목 추가
                JLabel idLabel = new JLabel("아이디");
                idLabel.setHorizontalAlignment(SwingConstants.CENTER);
                mainPanel.add(idLabel);

                final JTextField tId = new JTextField();
                mainPanel.add(tId);

                JLabel nameLabel = new JLabel("성함");
                nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
                mainPanel.add(nameLabel);
                final JTextField tName = new JTextField();
                mainPanel.add(tName);

                JLabel dateLabel = new JLabel("날짜");
                dateLabel.setHorizontalAlignment(SwingConstants.CENTER);
                mainPanel.add(dateLabel);
                final JTextField tDate = new JTextField();
                mainPanel.add(tDate);

                JLabel partLabel = new JLabel("부위");
                partLabel.setHorizontalAlignment(SwingConstants.CENTER);
                mainPanel.add(partLabel);
                final JTextField tPart = new JTextField();
                mainPanel.add(tPart);

                JLabel startTimeLabel = new JLabel("시작 시간");
                startTimeLabel.setHorizontalAlignment(SwingConstants.CENTER);
                mainPanel.add(startTimeLabel);
                final JTextField tStartTime = new JTextField();
                mainPanel.add(tStartTime);

                JLabel endTimeLabel = new JLabel("끝난 시간");
                endTimeLabel.setHorizontalAlignment(SwingConstants.CENTER);
                mainPanel.add(endTimeLabel);
                final JTextField tEndTime = new JTextField();
                mainPanel.add(tEndTime);

                panel.add(mainPanel, BorderLayout.CENTER);


                // 트레이너 목록을 가져와서 JComboBox에 추가
                List<String> trainers = ManagementDAOExer.getInstance().getTrainers(); // 트레이너 목록 가져오기

                JLabel trainerLabel = new JLabel("트레이너");
                trainerLabel.setHorizontalAlignment(SwingConstants.CENTER);
                mainPanel.add(trainerLabel);

                final JComboBox<String> trainerComboBox = new JComboBox<>();
                for (String trainer : trainers) {
                    trainerComboBox.addItem(trainer);
                }
                mainPanel.add(trainerComboBox);

                panel.add(mainPanel, BorderLayout.CENTER);


                // 버튼 패널 추가
                JPanel buttonPanel = new JPanel();
                panel.add(buttonPanel, BorderLayout.SOUTH);

                JButton updateButton = new JButton("수정하기");  // 버튼 이름 수정
                updateButton.setFont(new Font("Freesentation 1", Font.BOLD, 12));
                buttonPanel.add(updateButton);
                buttonPanel.setBackground(new Color(2, 7, 21));  // 버튼의 텍스트 색을 변경
                updateButton.setBackground(Color.white);  // 버튼 배경색

                // "수정하기" 버튼 클릭 시 동작하는 메소드
                updateButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // 입력받은 업데이트 정보를 변수로 선언함
                        String idToUpdate = tId.getText();
                        String dateToUpdate = tDate.getText();
                        String nameToUpdate = tName.getText();
                        String partToUpdate = tPart.getText();
                        String starttimeToUpdate = tStartTime.getText();
                        String endtimeToUpdate = tEndTime.getText();
                        String selectedTname = (String) trainerComboBox.getSelectedItem(); // 선택된 트레이너 가져오기

                        // 액션 버튼으로 받은 사용자 입력값이 유효한지 확인
                        if (idToUpdate != null && !idToUpdate.trim().isEmpty() &&
                                nameToUpdate != null && !nameToUpdate.trim().isEmpty() &&
                                dateToUpdate != null && !dateToUpdate.trim().isEmpty() &&
                                partToUpdate != null && !partToUpdate.trim().isEmpty() &&
                                starttimeToUpdate != null && !starttimeToUpdate.trim().isEmpty() &&
                                endtimeToUpdate != null && !endtimeToUpdate.trim().isEmpty() &&
                                selectedTname != null && !selectedTname.trim().isEmpty())
                        // 모든 조건이 옳바른지 판단하는 장문의 if 조건문
                        {
                            // 날짜 형식 검증 (yyyy-mm-dd)
                            if (!dateToUpdate.matches("\\d{4}-\\d{2}-\\d{2}")) {
                                JOptionPane.showMessageDialog(null, "날짜 형식이 잘못되었습니다. yyyy-mm-dd 형식으로 입력해주세요.");
                                return; // 데이터가 거짓이면 값을 return하고 if문을 종료
                            }

                            // 시간 형식 검증 (HH:mm)
                            if (!starttimeToUpdate.matches("\\d{2}:\\d{2}") || !endtimeToUpdate.matches("\\d{2}:\\d{2}")) {
                                JOptionPane.showMessageDialog(null, "시간 형식이 잘못되었습니다. HH:mm 형식으로 입력해주세요.");
                                return; //데이터가 거짓이면 값을 return하고 if문을 종료
                            }

                            // 입력받은 값으로 ManagementExer 객체 생성
                            // 데이터가 참이면 set 메소드를 통하여 new 생성자에 사용자 설정값을 보낸다.
                            ManagementExer mdto = new ManagementExer();
                            mdto.setId(idToUpdate);
                            mdto.setName(nameToUpdate);
                            mdto.setDate(dateToUpdate);  // 날짜
                            mdto.setPart(partToUpdate);
                            mdto.setStarttime(starttimeToUpdate);  // 시작 시간
                            mdto.setEndtime(endtimeToUpdate);  // 종료 시간
                            mdto.setTname(selectedTname); // 선택된 트레이너 추가

                            // 회원 운동 정보 업데이트를 보내고 데이터 베이스에 정보가 있는지 찾아 참 거짓 여부 확인
                            int result = ManagementDAOExerUpDate.getInstance().updateMember(mdto);

                            if (result == 1) { // result 값에 다라 내부에서 다시 연산을 시작함
                                JOptionPane.showMessageDialog(null, "회원 정보가 수정되었습니다.");
                                dialog.dispose();  // 창 닫기
                                UpdateRefreshTable();  // 테이블 갱신
                            } else {
                                JOptionPane.showMessageDialog(null, "수정 실패. 해당 아이디 또는 성함이 존재하지 않거나 오류가 발생했습니다.");
                            }

                        } else {
                            JOptionPane.showMessageDialog(null, "모든 필드를 입력해야 합니다.");
                        }




                    }
                });

                // 창 표시
                dialog.setVisible(true);
            }
        });



        // 회원 운동 정보 검색 버튼
        search = new JButton("회원 운동 정보 검색");
        search.setForeground(new Color(2, 7, 21));  // 회원 운동 정보 검색 버튼의 텍스트 색을 변경
        search.setBackground(new Color(217,246,105));  // 회원 운동 정보 검색 버튼 배경색
        topPanel.add(search);

        search.addActionListener(new ActionListener() { // 클릭하면 검색창을 활성화
            @Override
            public void actionPerformed(ActionEvent e) {
                // 검색할 아이디와 이름 입력 받기
                RefreshTable();
                String idToSearch = JOptionPane.showInputDialog("검색할 회원의 아이디를 입력하세요:");

                if (idToSearch != null && !idToSearch.trim().isEmpty()) {
                    // searchMember 메서드를 호출하여 검색된 리스트 받기
                    List<ManagementExer> searchResults = ManagementDAOExerSearch.getInstance().searchMember(idToSearch);

                    if (searchResults != null && !searchResults.isEmpty()) { // 리턴받은 값이 빈값인지 아닌지로 여부 판단
                        JOptionPane.showMessageDialog(null, "회원 정보가 검색되었습니다.");
                        SearchRefreshTable(idToSearch);  // 검색 조건을 전달하여 테이블 갱신
                    } else {
                        JOptionPane.showMessageDialog(null, "검색 실패. 해당 아이디 또는 이름이 존재하지 않거나 오류가 발생했습니다.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "아이디와 이름을 모두 입력해야 합니다.");
                }
            }
        });

        // 눌렀을때 -> 회원 운동 정보 검색 시스템

        // 상단 패널을 BorderLayout의 NORTH에 추가
        panel.add(topPanel, BorderLayout.NORTH);

        // 회원 리스트 제목
        lilabel = new JLabel("회원 운동 기록 리스트");
        lilabel.setFont(new Font("Serif", Font.PLAIN, 50));
        lilabel.setHorizontalAlignment(SwingConstants.CENTER);
        lilabel.setPreferredSize(new Dimension(400, 80));

        // 회원 리스트 제목을 BorderLayout의 CENTER에 추가
        panel.add(lilabel, BorderLayout.CENTER);

        // 데이터 테이블의 데이터를 가지고 있는 생성자와 이를 List 변수에 배열로 담음
        ManagementDAOExer mdao = ManagementDAOExer.getInstance();
        List<ManagementExer> list = mdao.managementListExer();

        String[] member = {"아이디", "성함", "날짜", "부위", "시작 시간", "끝난 시간", "트레이너 이름"};
        // String 배열을 통해 순서에 따라 타이틀을 자동으로 넣게 함
        tModel = new DefaultTableModel(member, 0);

        // 테이블 데이터 추가 - 조건문을 이용해 list 수 만큼 테이블 추가
        for (int i = 0; i < list.size(); i++) {
            // list에서 받아온 get 값을 차례대로 변수에 담음
            String id1 = list.get(i).getId();
            String name = list.get(i).getName();
            String date = list.get(i).getDate();
            String part = list.get(i).getPart();
            String starttime = list.get(i).getStarttime();
            String endtime = list.get(i).getEndtime();
            String Tname = list.get(i).getTname(); // 선택된 트레이너 가져오기

            Object[] data = {id1, name, date, part, starttime, endtime, Tname};
            // 변수에 담긴 get 값을 순차적으로 넣도록 함.
            tModel.addRow(data); // data 에저장된 변수를 테이블 명령어에 넣어 반복 생성함.
        }

        // 테이블 생성
        table = new JTable(tModel);
        table.setFont(new Font("돋움", Font.PLAIN, 20));
        table.setRowHeight(30);

        // JScrollPane을 테이블에 추가 (스크롤을 달기 위함)
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER); // 패널에 스크롤이 있는 테이블 추가

        // 로그아웃 버튼 (오른쪽 하단 배치)
        logout = new JButton("로그아웃");
        logout.setFont(new Font("Freesentation 8", Font.BOLD, 12));
        logout.setForeground(Color.WHITE);  // 로그아웃 버튼의 텍스트 색을 변경
        logout.setBackground(new Color(2, 7, 21));  // 로그아웃 버튼의 색상을 변경

        // 로그아웃 버튼의 액션 리스너
        logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "로그아웃 되었습니다.");
                dispose();
                new LoginFrame();
            }
        });


        // 트레이너 버튼 버튼
        trainers = new JButton("트레이너");
        trainers.setFont(new Font("Freesentation 8", Font.BOLD, 12));
        trainers.setForeground(Color.WHITE);  // 트레이너 버튼의 텍스트 색을 변경
        trainers.setBackground(new Color(2, 7, 21));  // 트레이너 버튼의 색상을 변경

        // 트레이너 버튼 액션 리스너
        trainers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // 새로운 작은 창 (JDialog) 생성
                final JDialog dialog = new JDialog(); // 새로 출력 되는 작은 창
                dialog.setTitle("트레이너 정보 갱신"); // 타이틀
                dialog.setSize(300 , 170); // 창 크기 설정
                dialog.setLocationRelativeTo(null); // 화면 중앙에 띄우기
                dialog.setModal(true); // 부모 창이 비활성화 되지 않도록 설정 - 부모창 ListView

                JPanel panel = new JPanel();
                panel.setLayout(new BorderLayout());
                dialog.setContentPane(panel);

                JPanel mainPanel = new JPanel(new GridLayout(1, 2, 10, 10)); // 입력 필드를 위한 중앙 레이아웃

                // 각 항목 추가
                JLabel TanmeLabel = new JLabel("트레이너 추가/삭제");
                TanmeLabel.setHorizontalAlignment(SwingConstants.CENTER);
                mainPanel.add(TanmeLabel);
                TanmeLabel.setFont(new Font("Freesentation 1", Font.BOLD, 12));
                final JTextField tTname = new JTextField();

                mainPanel.add(tTname);
                panel.add(mainPanel, BorderLayout.CENTER); // 중앙 메인 페널을 눈에 보일 수 있도록 판넬에 추가
                mainPanel.setBorder(BorderFactory.createEmptyBorder(25, 10, 25, 10)); // 여백 추가


                // 버튼 패널 추가
                JPanel buttonPanel = new JPanel();
                panel.add(buttonPanel, BorderLayout.SOUTH);
                buttonPanel.setBackground(new Color(27, 31, 44)); // 버튼 배경색 수정

                JButton addButton = new JButton("추가하기");
                addButton.setFont(new Font("Freesentation 1", Font.BOLD, 12));
                addButton.setBackground(Color.white); //추가하기 버튼 색 변경
                buttonPanel.add(addButton);
                buttonPanel.setBackground(new Color(27, 31, 44)); // 버튼 배경색 수정


                // "추가하기" 버튼 클릭 시 동작
                addButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String tTnameToCreate = tTname.getText();

                        // 아이디와 이름, 등이 빈 영역인지, 비어있는지 등의 조건을 보고 오류창 리턴
                        if (tTnameToCreate != null && !tTnameToCreate.trim().isEmpty()) {

                            ManagementDAOTrainers mdtoT = new ManagementDAOTrainers();
                            int result = mdtoT.checkDuplicate(tTnameToCreate);
                            // daoTel에서 아이디와 이름 정보를 가지고옴

                            if (result == 1) { //리턴값이 1 즉 옳바르면 운동정보 생성자를 만들고 정보를 가지고옴
                                // 운동 정보 추가

                                int addResult = mdtoT.insertMember(tTnameToCreate);//------------------

                                if (addResult == 1) {
                                    JOptionPane.showMessageDialog(dialog, "트레이너 정보가 추가되었습니다.");
                                    dialog.dispose();  // 창 닫기
                                } else {
                                    JOptionPane.showMessageDialog(dialog, "트레이너 정보 추가 실패. 오류가 발생했습니다.");
                                }
                            } else {
                                JOptionPane.showMessageDialog(dialog, "이미 추가되신 분입니다.");
                            }
                        } else {
                            JOptionPane.showMessageDialog(dialog, "이름을 공백 없이 정확히 입력하세요.");
                        }
                    }
                });


                JButton DelButton = new JButton("삭제하기");
                DelButton.setFont(new Font("Freesentation 1", Font.BOLD, 12));
                DelButton.setBackground(Color.white); //추가하기 버튼 색 변경
                buttonPanel.add(DelButton);
                buttonPanel.setBackground(new Color(27, 31, 44)); // 버튼 배경색 수정
                // "삭제하기" 버튼 클릭 시 동작
                DelButton.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String tTnameToCreate = tTname.getText();

                        // 아이디와 이름, 등이 빈 영역인지, 비어있는지 등의 조건을 보고 오류창 리턴
                        if (tTnameToCreate != null && !tTnameToCreate.trim().isEmpty()) {

                            ManagementDAOTrainers mdtoT = new ManagementDAOTrainers();
                            int result = mdtoT.DelcheckDuplicate(tTnameToCreate);
                            // daoTel에서 아이디와 이름 정보를 가지고옴

                            if (result == 1) { //리턴값이 1 즉 옳바르면 운동정보 생성자를 만들고 정보를 가지고옴
                                // 운동 정보 추가

                                int addResult = mdtoT.deleteMember(tTnameToCreate);//------------------

                                if (addResult == 1) {
                                    JOptionPane.showMessageDialog(dialog, "트레이너 정보가 삭제되었습니다.");
                                    dialog.dispose();  // 창 닫기
                                } else {
                                    JOptionPane.showMessageDialog(dialog, "트레이너 정보 삭제 실패. 오류가 발생했습니다.");
                                }
                            } else {
                                JOptionPane.showMessageDialog(dialog, "이름이 일치하는 분이 없습니다..");
                            }
                        } else {
                            JOptionPane.showMessageDialog(dialog, "이름을 공백 없이 정확히 입력하세요.");
                        }
                    }
                });


                // 창 표시
                dialog.setVisible(true);
            }
        });

        // 달력 버튼 (왼쪽 하단 배치)
        calendarButton = new JButton("달력");
        calendarButton.setFont(new Font("Freesentation 8", Font.BOLD, 12));
        calendarButton.setForeground(Color.WHITE);  // 달력 버튼의 텍스트 색을 변경
        calendarButton.setBackground(new Color(2, 7, 21));  // 달력 버튼의 색상을 변경

        // 달력 버튼 클릭 시 달력 창 띄우기
        calendarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // gui 패키지에 있는 CalendarFrame을 호출하도록 수정
                gui.ListView.CalendarFrame calendarFrame = new gui.ListView.CalendarFrame();
                calendarFrame.setVisible(true); // 새로운 달력 창을 보이도록 설정
            }
        });

        // -----왼쪽에 정렬될 판낼 영역
        JPanel EASTPanel = new JPanel(new BorderLayout());
        EASTPanel.add(trainers, BorderLayout.EAST);  // 트레이너 버튼 켈린더 왼쪽에 배치
        EASTPanel.add(calendarButton, BorderLayout.WEST);  // 켈린더 버튼 오른쪽 끝에 배치

        // -----바텀 패널 생성 및 위치 조정
        JPanel BottomPanel = new JPanel(new BorderLayout());
        BottomPanel.add(logout, BorderLayout.EAST);  // 로그아웃 버튼 바텀 판넬 오른쪽 배치
        BottomPanel.add(EASTPanel, BorderLayout.WEST);  // 켈린더와 트레이너가 담긴 페널 왼쪽 끝에 배치

        panel.add(BottomPanel, BorderLayout.SOUTH);// 정리된 바텀 패널 매인 판낼에 추가


        setVisible(true);
        // 셀 편집을 비활성화
        table.setDefaultEditor(Object.class, null);
    }

    public void RefreshTelTable() {
        // 데이터베이스에서 갱신된 리스트를 가져옴
        ManagementDAOTel mda = ManagementDAOTel.getInstance();
        List<ManagementDTO> list = mda.managementListTel();

        // 만약 리스트가 비어 있거나 null이면 메세지 출력 후 종료
        if (list == null || list.isEmpty()) {
            JOptionPane.showMessageDialog(null, "회원 정보가 없습니다.");
            return;
        }

        // 테이블 모델을 새로 갱신
        String[] columns = {"아이디", "비밀번호", "이름", "전화번호", "주소", "성별"};
        DefaultTableModel newModel = new DefaultTableModel(columns, 0);

        // 새로운 데이터로 테이블 모델 채우기
        for (ManagementDTO tel : list) {
            Object[] row = {tel.getId(), tel.getPasswd(), tel.getName(), tel.getTel(), tel.getAddress(), tel.getGender()};
            newModel.addRow(row);
        }

        // 테이블에 새 모델 적용
        table.setModel(newModel);

        // 셀 편집을 비활성화
        table.setDefaultEditor(Object.class, null);
    }


    public void RefreshTable() {
        // 데이터베이스에서 갱신된 리스트를 가져옴
        ManagementDAOExer mdao = ManagementDAOExer.getInstance();
        List<ManagementExer> list = mdao.managementListExer();

        // 테이블 모델을 새로 갱신
        String[] columns = {"아이디", "성함", "날짜", "부위", "시작 시간", "끝난 시간", "트레이너 이름"};
        DefaultTableModel newModel = new DefaultTableModel(columns, 0);

        // 새로운 데이터로 테이블 모델 채우기
        for (ManagementExer exer : list) {
            Object[] row = {exer.getId(), exer.getName(), exer.getDate(), exer.getPart(), exer.getStarttime(), exer.getEndtime(), exer.getTname()};
            newModel.addRow(row);
        }

        // 테이블에 새 모델 적용
        table.setModel(newModel);

        // 셀 편집을 비활성화
        table.setDefaultEditor(Object.class, null);
    }


    public void DeleteRefreshTable() {
        // 데이터베이스에서 갱신된 리스트를 가져옴
        ManagementDAOExerDelete mdao = ManagementDAOExerDelete.getInstance();
        List<ManagementExer> list = mdao.managementListExerDelete();

        // 테이블 모델을 새로 갱신
        String[] columns = {"아이디", "성함", "날짜", "부위", "시작 시간", "끝난 시간", "트레이너 이름"};
        DefaultTableModel newModel = new DefaultTableModel(columns, 0);

        // 새로운 데이터로 테이블 모델 채우기
        for (ManagementExer exer : list) {
            Object[] row = {exer.getId(), exer.getName(), exer.getDate(), exer.getPart(), exer.getStarttime(), exer.getEndtime(), exer.getTname()};
            newModel.addRow(row);
        }

        // 테이블에 새 모델 적용
        table.setModel(newModel);

        // 셀 편집을 비활성화
        table.setDefaultEditor(Object.class, null);
    }

    public void UpdateRefreshTable() {
        // 데이터베이스에서 갱신된 리스트를 가져옴
        ManagementDAOExerUpDate mdao = ManagementDAOExerUpDate.getInstance();
        List<ManagementExer> list = mdao.managementListExerUpdate();

        // 테이블 모델을 새로 갱신
        String[] columns = {"아이디", "성함", "날짜", "부위", "시작 시간", "끝난 시간", "트레이너 이름"};
        DefaultTableModel newModel = new DefaultTableModel(columns, 0);

        // 새로운 데이터로 테이블 모델 채우기
        for (ManagementExer exer : list) {
            Object[] row = {exer.getId(), exer.getName(), exer.getDate(), exer.getPart(), exer.getStarttime(), exer.getEndtime(), exer.getTname()};
            newModel.addRow(row);
        }

        // 테이블에 새 모델 적용
        table.setModel(newModel);

        // 셀 편집을 비활성화
        table.setDefaultEditor(Object.class, null);
    }

    public void SearchRefreshTable(String idToSearch) {
        // 데이터베이스에서 갱신된 리스트를 가져옴
        ManagementDAOExerSearch mdao = ManagementDAOExerSearch.getInstance();
        List<ManagementExer> list = mdao.searchMember(idToSearch);  // idToSearch와 nameToSearch는 검색 입력값

        if (list == null || list.isEmpty()) {
            JOptionPane.showMessageDialog(null, "검색된 결과가 없습니다.");
            return;
        }

        // 테이블 모델을 새로 갱신
        String[] columns = {"아이디", "성함", "날짜", "부위", "시작 시간", "끝난 시간", "트레이너 이름"};
        DefaultTableModel newModel = new DefaultTableModel(columns, 0);

        // 새로운 데이터로 테이블 모델 채우기
        for (ManagementExer exer : list) {
            Object[] row = {exer.getId(), exer.getName(), exer.getDate(), exer.getPart(), exer.getStarttime(), exer.getEndtime(), exer.getTname()};
            newModel.addRow(row);
        }

        // 테이블에 새 모델 적용
        table.setModel(newModel);

        // 셀 편집을 비활성화
        table.setDefaultEditor(Object.class, null);
    }


    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ListView lf = new ListView();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    // 달력 창을 생성하기 위한 JFrame 클래스
    public class CalendarFrame extends JFrame {
        private String selectedDateString;  // 선택된 날짜를 저장할 변수

        public CalendarFrame() {
            // JFrame 설정
            setTitle("달력");
            setSize(300, 300);
            //setLocation(25, 120);//달력이 출력되는 위치를 정하는 좌표 (왼쪽, 상단)
            setLocation(320, 550);//달력이 출력되는 위치를 정하는 좌표 (왼쪽, 하단)
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            // JCalendar 컴포넌트 생성
            JCalendar calendar = new JCalendar();

            // 선택된 날짜를 String으로 저장할 변수 초기화
            selectedDateString = "";

            // JCalendar에서 날짜 클릭 시 날짜가 바뀔 때마다 호출되는 리스너 설정
            calendar.getDayChooser().addPropertyChangeListener(evt -> {
                Date selectedDate = calendar.getDate();  // 선택된 날짜를 가져옴
                if (selectedDate != null) {
                    // 날짜를 String으로 변환
                    selectedDateString = new SimpleDateFormat("yyyy-MM-dd").format(selectedDate);
                }
            });

            // 날짜 선택 버튼 생성
            JButton selectButton = new JButton("날짜 선택");

            // 버튼 클릭 시 선택된 날짜를 출력하는 이벤트 리스너
            selectButton.addActionListener(e -> {
                // selectedDateString 값이 잘 설정되었는지 확인

                if (!selectedDateString.isEmpty()) {
                    // 선택된 날짜로 검색 처리 (예: memberListExer 메서드에 전달)
                    ManagementDAOCalendar mdao = ManagementDAOCalendar.getInstance();
                    List<ManagementExer> list = mdao.managementListExerByDate(selectedDateString);  // 날짜로 데이터 조회

                    if (list != null && !list.isEmpty()) {
                        // 데이터를 테이블에 갱신
                        JOptionPane.showMessageDialog(null, "선택된 날짜의 운동 기록을 찾았습니다.");
                    } else {
                        JOptionPane.showMessageDialog(null, "선택된 날짜에 운동 기록이 없습니다.");
                    }

                    // 테이블 모델을 새로 갱신
                    String[] columns = {"아이디", "성함", "날짜", "부위", "시작 시간", "끝난 시간", "트레이너 이름"};
                    DefaultTableModel newModel = new DefaultTableModel(columns, 0);


                    // 새로운 데이터로 테이블 모델 채우기
                    for (ManagementExer exer : list) {
                        Object[] row = {exer.getId(), exer.getName(), exer.getDate(), exer.getPart(), exer.getStarttime(), exer.getEndtime(), exer.getTname()};
                        newModel.addRow(row);
                    }

                    // 테이블에 새 모델 적용
                    table.setModel(newModel);

                    // 셀 편집을 비활성화
                    table.setDefaultEditor(Object.class, null);

                } else {
                    // 날짜를 선택하지 않았을 때 메시지 출력
                    JOptionPane.showMessageDialog(this, "날짜를 선택해주세요.");
                }
            });

            // 레이아웃 설정
            setLayout(new BorderLayout());
            add(calendar, BorderLayout.CENTER);  // JCalendar를 중앙에 배치
            add(selectButton, BorderLayout.SOUTH); // 버튼을 하단에 배치

            // JFrame 보이기
            setVisible(true);
        }

        public  void main(String[] args) {
            new CalendarFrame();
        }
    }

}