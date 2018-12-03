package version1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Calendar;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MainPanel extends JPanel {
	static int setYear;
	static int setMonth;
	static int setDay;

	CalendarPanel calendarPanel;
	JPanel datePanel;
	JPanel plusPanel;
	ImageIcon backGroundImageIcons[] = new ImageIcon[12];
	Image backGroundImages[] = new Image[12];
	BorderLayout layout;
	
	JTextField textField;
	Font font;
	
	Connection connection = null;
	Statement st = null;


	/**
	 * Create the panel.
	 */
	public MainPanel() {
		// 배경사진 로딩
		for(int i = 0 ; i < 12 ; i++) {
			backGroundImageIcons[i] = new ImageIcon("images/" + (i+1)+ ".png");
			backGroundImages[i] = backGroundImageIcons[i].getImage();
		}
		// 폰트 설정
		font = MainFrame.basicFont;
		// 레이아웃 설정
		setLayout(new BorderLayout());
		layout = (BorderLayout) getLayout();
		// North에 컴포넌트 추가할 Date패널 생성
		datePanel = new DatePanel();
		textField = new JTextField(10);
		textField.setFont(font);
		textField.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String solaDate = Integer.toString(setYear) + "-" + setMonth + "-" + setDay;
				String memo = textField.getText();
				String sql;
				sql = "INSERT INTO `schedule`(`solar`,`memo`) VALUES('"+ solaDate +"','" + memo +  "');";
				try {
					
					st = connection.createStatement();
				st.executeUpdate(sql);
				} catch(Exception err) {
					err.printStackTrace();
				}
				textField.setText("");
				calendarPanel.updatePanel();
			}
			
		});
		// datePanel을 BorderLayout NORTH에 추가
		add(datePanel, BorderLayout.NORTH);
		add(textField, BorderLayout.SOUTH);

		// calendarPanel을 CENTER에 추가
		calendarPanel = new CalendarPanel(setYear, setMonth - 1);
		addCenter(calendarPanel);
		
	}

	public void removeCenter() {
		remove(layout.getLayoutComponent(BorderLayout.CENTER));
	}

	public void addCenter(JPanel panel) {
		this.add(panel, BorderLayout.CENTER);
		repaint();
	}

	public void rePaint() {
		repaint();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(backGroundImages[setMonth-1], 0, 0, getWidth(), getHeight(), this);
	}

	/*
	 * 현재 월 표기와 월 전환
	 */
	class DatePanel extends JPanel {

		// 왼쪽 버튼 기본 이미지
		ImageIcon leftButtonBasicImage = new ImageIcon("images/leftButtonBasicImage.png");
		// 왼쪽 버튼 Enter 이미지
		ImageIcon leftButtonEnterImage = new ImageIcon("images/leftButtonEnterImage.png");
		// 오른쪽 버튼 기본 이미지
		ImageIcon rightButtonBasicImage = new ImageIcon("images/rightButtonBasicImage.png");
		// 오른쪽 버튼 Enter 이미지
		ImageIcon rightButtonEnterImage = new ImageIcon("images/rightButtonEnterImage.png");
		Font font;
		JLabel date;
		Calendar cal;

		// 현재 년도, 월, 일
		int currentYear;
		int currentMonth;
		int currentDay;

		/**
		 * Create the panel.
		 */
		public DatePanel() {
			font = MainFrame.basicFont;
			JButton left = new JButton(leftButtonBasicImage);
			JButton right = new JButton(rightButtonBasicImage);
			// Button UI 설정
			setButtonUI(left);
			setButtonUI(right);
			// 데이터베이스 연결
			connection = MainFrame.connection;
			// Mouse 이벤트 처리
			left.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					// Entered이미지로 변경 시켜준다.
					left.setIcon(leftButtonEnterImage);
					// 커서의 모양을 바꿔준다
					left.setCursor(new Cursor(Cursor.HAND_CURSOR));
					// 효과음 재생
				}

				// 마우스가 버튼에 나갔을때 이벤트 처리
				@Override
				public void mouseExited(MouseEvent e) {
					left.setIcon(leftButtonBasicImage);
					// 커서의 모양을 바꿔준다
					left.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

				}

				// 버튼을 클릭했을때 이벤트 처리
				@Override
				public void mousePressed(MouseEvent e) {
					currentMonth--;
					if (currentMonth <= 0) {
						currentYear--;
						currentMonth = 12;
					}
					date.setText(currentYear + "년" + currentMonth + "월");
					CalendarPanel calendarPanel = new CalendarPanel(currentYear, currentMonth - 1);
					removeCenter();
					addCenter(calendarPanel);
					MainPanel.setYear = currentYear;
					MainPanel.setMonth = currentMonth;
				}
			});
			right.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					// Entered이미지로 변경 시켜준다.
					right.setIcon(rightButtonEnterImage);
					// 커서의 모양을 바꿔준다
					right.setCursor(new Cursor(Cursor.HAND_CURSOR));
					// 효과음 재생
				}

				// 마우스가 버튼에 나갔을때 이벤트 처리
				@Override
				public void mouseExited(MouseEvent e) {
					right.setIcon(rightButtonBasicImage);
					// 커서의 모양을 바꿔준다
					right.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

				}

				// 버튼을 클릭했을때 이벤트 처리
				@Override
				public void mousePressed(MouseEvent e) {
					currentMonth++;
					if (currentMonth > 12) {
						currentYear++;
						currentMonth = 1;
					}
					date.setText(currentYear + "년" + currentMonth + "월");
					calendarPanel = new CalendarPanel(currentYear, currentMonth - 1);
					removeCenter();
					addCenter(calendarPanel);
					MainPanel.setYear = currentYear;
					MainPanel.setMonth = currentMonth;
				}
			});
			// 레이아웃 설정
			setLayout(new BorderLayout());

			// 달력 생성
			cal = Calendar.getInstance();

			// 현재 년도, 월, 일
			currentYear = cal.get(cal.YEAR);
			currentMonth = cal.get(cal.MONTH) + 1;
			currentDay = cal.get(cal.DATE);

			MainPanel.setYear = currentYear;
			MainPanel.setMonth = currentMonth;

			// 날짜를 표현할 JLabel 선언
			date = new JLabel(currentYear + "년 " + currentMonth + "월");
			// 글자색 지정
			date.setForeground(Color.RED);
			// 폰트 지정
			date.setFont(font);
			// 글자 정렬
			date.setHorizontalAlignment(JLabel.CENTER);
			// 배경화면 설정
			setOpaque(false);
			// 컴포넌트 추가
			add(date, BorderLayout.CENTER);
			add(left, BorderLayout.WEST);
			add(right, BorderLayout.EAST);


		}

		public void setButtonUI(JButton button) {
			// 외곽선 제거
			button.setBorderPainted(false);
			// 내용 체우기 제거
			button.setContentAreaFilled(false);
			// 포커스 되었을시 테두리 제거
			button.setFocusPainted(false);
		}
	}
	
	class MenuPanel extends JPanel {
		ImageIcon plusButtonImage = new ImageIcon("images/plusButton");
		JButton plusButton;
		public MenuPanel() {
			
		}
	}
	
	class TaskPanel extends JPanel{
		public TaskPanel() {
			
		}
	}
	
}
