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
		// ������ �ε�
		for(int i = 0 ; i < 12 ; i++) {
			backGroundImageIcons[i] = new ImageIcon("images/" + (i+1)+ ".png");
			backGroundImages[i] = backGroundImageIcons[i].getImage();
		}
		// ��Ʈ ����
		font = MainFrame.basicFont;
		// ���̾ƿ� ����
		setLayout(new BorderLayout());
		layout = (BorderLayout) getLayout();
		// North�� ������Ʈ �߰��� Date�г� ����
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
		// datePanel�� BorderLayout NORTH�� �߰�
		add(datePanel, BorderLayout.NORTH);
		add(textField, BorderLayout.SOUTH);

		// calendarPanel�� CENTER�� �߰�
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
	 * ���� �� ǥ��� �� ��ȯ
	 */
	class DatePanel extends JPanel {

		// ���� ��ư �⺻ �̹���
		ImageIcon leftButtonBasicImage = new ImageIcon("images/leftButtonBasicImage.png");
		// ���� ��ư Enter �̹���
		ImageIcon leftButtonEnterImage = new ImageIcon("images/leftButtonEnterImage.png");
		// ������ ��ư �⺻ �̹���
		ImageIcon rightButtonBasicImage = new ImageIcon("images/rightButtonBasicImage.png");
		// ������ ��ư Enter �̹���
		ImageIcon rightButtonEnterImage = new ImageIcon("images/rightButtonEnterImage.png");
		Font font;
		JLabel date;
		Calendar cal;

		// ���� �⵵, ��, ��
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
			// Button UI ����
			setButtonUI(left);
			setButtonUI(right);
			// �����ͺ��̽� ����
			connection = MainFrame.connection;
			// Mouse �̺�Ʈ ó��
			left.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					// Entered�̹����� ���� �����ش�.
					left.setIcon(leftButtonEnterImage);
					// Ŀ���� ����� �ٲ��ش�
					left.setCursor(new Cursor(Cursor.HAND_CURSOR));
					// ȿ���� ���
				}

				// ���콺�� ��ư�� �������� �̺�Ʈ ó��
				@Override
				public void mouseExited(MouseEvent e) {
					left.setIcon(leftButtonBasicImage);
					// Ŀ���� ����� �ٲ��ش�
					left.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

				}

				// ��ư�� Ŭ�������� �̺�Ʈ ó��
				@Override
				public void mousePressed(MouseEvent e) {
					currentMonth--;
					if (currentMonth <= 0) {
						currentYear--;
						currentMonth = 12;
					}
					date.setText(currentYear + "��" + currentMonth + "��");
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
					// Entered�̹����� ���� �����ش�.
					right.setIcon(rightButtonEnterImage);
					// Ŀ���� ����� �ٲ��ش�
					right.setCursor(new Cursor(Cursor.HAND_CURSOR));
					// ȿ���� ���
				}

				// ���콺�� ��ư�� �������� �̺�Ʈ ó��
				@Override
				public void mouseExited(MouseEvent e) {
					right.setIcon(rightButtonBasicImage);
					// Ŀ���� ����� �ٲ��ش�
					right.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

				}

				// ��ư�� Ŭ�������� �̺�Ʈ ó��
				@Override
				public void mousePressed(MouseEvent e) {
					currentMonth++;
					if (currentMonth > 12) {
						currentYear++;
						currentMonth = 1;
					}
					date.setText(currentYear + "��" + currentMonth + "��");
					calendarPanel = new CalendarPanel(currentYear, currentMonth - 1);
					removeCenter();
					addCenter(calendarPanel);
					MainPanel.setYear = currentYear;
					MainPanel.setMonth = currentMonth;
				}
			});
			// ���̾ƿ� ����
			setLayout(new BorderLayout());

			// �޷� ����
			cal = Calendar.getInstance();

			// ���� �⵵, ��, ��
			currentYear = cal.get(cal.YEAR);
			currentMonth = cal.get(cal.MONTH) + 1;
			currentDay = cal.get(cal.DATE);

			MainPanel.setYear = currentYear;
			MainPanel.setMonth = currentMonth;

			// ��¥�� ǥ���� JLabel ����
			date = new JLabel(currentYear + "�� " + currentMonth + "��");
			// ���ڻ� ����
			date.setForeground(Color.RED);
			// ��Ʈ ����
			date.setFont(font);
			// ���� ����
			date.setHorizontalAlignment(JLabel.CENTER);
			// ���ȭ�� ����
			setOpaque(false);
			// ������Ʈ �߰�
			add(date, BorderLayout.CENTER);
			add(left, BorderLayout.WEST);
			add(right, BorderLayout.EAST);


		}

		public void setButtonUI(JButton button) {
			// �ܰ��� ����
			button.setBorderPainted(false);
			// ���� ü��� ����
			button.setContentAreaFilled(false);
			// ��Ŀ�� �Ǿ����� �׵θ� ����
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
