package gui;

import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateChooserExample extends JFrame {

    private JLabel selectedDateLabel;  // 선택된 날짜를 표시할 JLabel

    public DateChooserExample() {

        setTitle("JDateChooser Example");  // 프레임의 제목 설정
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // 창을 닫을 때 프로그램 종료 설정
        setLocationRelativeTo(null);  // 화면 중앙에 창을 띄우기

        // JDateChooser 객체 생성
        JDateChooser dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("yyyy-MM-dd");  // 날짜 형식을 "년-월-일" 형식으로 설정

        // 선택된 날짜를 출력할 JLabel 생성
        selectedDateLabel = new JLabel("선택된 날짜: 없음");

        // 날짜 선택 시 이벤트 처리
        dateChooser.addPropertyChangeListener("date", evt -> {
            Date selectedDate = dateChooser.getDate();  // 선택된 날짜를 가져옴
            if (selectedDate != null) {
                // 날짜를 문자열로 변환하여 JLabel에 출력
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String formattedDate = dateFormat.format(selectedDate);
                selectedDateLabel.setText("선택된 날짜: " + formattedDate);  // JLabel에 출력
            }
        });

        // JCalendar 객체 생성 (기본 달력)
        JCalendar calendar = new JCalendar();
        calendar.setPreferredSize(new Dimension(300, 300));  // 달력의 크기를 300x300으로 설정

        // 컴포넌트를 프레임에 추가
        setLayout(new BorderLayout());
        add(dateChooser, BorderLayout.NORTH);  // `dateChooser`를 JFrame의 상단에 추가
        add(selectedDateLabel, BorderLayout.SOUTH);  // 선택된 날짜를 표시하는 `JLabel`을 하단에 추가
        add(calendar, BorderLayout.CENTER);  // 달력을 프레임의 중앙에 추가

        setVisible(true);  // JFrame을 화면에 보이도록 설정
    }

    public static void main(String[] args) {
        new DateChooserExample();  // DateChooserExample 클래스의 인스턴스를 생성하여 실행
    }
}
