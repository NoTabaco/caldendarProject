package version1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class TaskPanel extends JDialog {
	JPanel nearPanel;
	JPanel currentPanel;
	JPanel previousPanel;

	CalendarPanel calendarPanel;
	Calendar cal;

	Connection connection = null;
	Statement st = null;

	int currentYear;
	int currentMonth;
	int currentDay;

	int nearYear;
	int nearMonth;
	int nearDay;

	int previousYear;
	int previousMonth;
	int previousDay;

	public TaskPanel(JFrame owner, boolean modal) {
		super(owner, modal);
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(
					"jdbc:mysql://168.131.150.80:3306/bank?serverTimezone=UTC&useSSL=false", "root", "34290118");
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 달력 생성
		cal = Calendar.getInstance();
		// 현재 년도, 월, 일
		currentYear = cal.get(cal.YEAR);
		currentMonth = cal.get(cal.MONTH) + 1;
		currentDay = cal.get(cal.DATE);

		cal.add(Calendar.DATE, 14);

		nearYear = cal.get(cal.YEAR);
		nearMonth = cal.get(cal.MONTH) + 1;
		nearDay = cal.get(cal.DATE);

		cal.add(Calendar.DATE, -28);

		previousYear = cal.get(cal.YEAR);
		previousMonth = cal.get(cal.MONTH) + 1;
		previousDay = cal.get(cal.DATE);

		setLayout(new BorderLayout());
		setTitle("프로젝트 관리");
		setSize(1000, 600);
		setVisible(false);
		setLocationRelativeTo(null);
		nearPanel = new NearPanel();
		currentPanel = new CurrentPanel();
		previousPanel = new PreviousPanel();
		add(nearPanel, BorderLayout.EAST);
		add(currentPanel, BorderLayout.CENTER);
		add(previousPanel, BorderLayout.WEST);
	}

	public void visible(boolean isView) {
		setVisible(isView);
	}

	class NearPanel extends JPanel {
		ImageIcon previousIcon = new ImageIcon("images/previousImage.png");
		JPanel contentPanel;

		public NearPanel() {
			setLayout(new BorderLayout());
			JLabel infoLabel = new JLabel(previousIcon);
			add(infoLabel, BorderLayout.NORTH);

			contentPanel = new JPanel();
			contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
			try {
				st = connection.createStatement();
				String currentSolaDate = Integer.toString(currentYear) + "-" + currentMonth + "-" + (currentDay-1)
						+ " 00:00:00";
				String previousSolaDate = Integer.toString(previousYear) + "-" + previousMonth + "-" + previousDay
						+ " 11:59:59";
				String sql = "SELECT * FROM schedule WHERE solar between '" + previousSolaDate + "' and '"
						+ currentSolaDate + "'ORDER BY solar ASC;;";
				ResultSet rs = st.executeQuery(sql);
				while (rs.next()) {
					JButton btn = new JButton(rs.getString("solar") + "\n " + rs.getString("memo"));
					setButtonUI(btn);
					contentPanel.add(btn);
					contentPanel.add(Box.createVerticalStrut(10) );
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			add(contentPanel, BorderLayout.CENTER);
		}
	}

	class CurrentPanel extends JPanel {
		ImageIcon nearIcon = new ImageIcon("images/currentImage.png");
		JPanel contentPanel;

		public CurrentPanel() {
			setLayout(new BorderLayout());
			JLabel infoLabel = new JLabel(nearIcon);
			add(infoLabel, BorderLayout.NORTH);

			contentPanel = new JPanel();
			contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
			try {
				st = connection.createStatement();
				String currentSolaDate = Integer.toString(currentYear) + "-" + currentMonth + "-" + currentDay
						+ " 00:00:00";
				String sql = "SELECT * FROM schedule WHERE solar = '" + currentSolaDate + "';";
				ResultSet rs = st.executeQuery(sql);
				while (rs.next()) {
					JButton btn = new JButton(rs.getString("solar") + "\n " + rs.getString("memo"));
					setButtonUI(btn);
					contentPanel.add(btn);
					contentPanel.add(Box.createVerticalStrut(10) );
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			add(contentPanel, BorderLayout.CENTER);
		}
	}

	class PreviousPanel extends JPanel {
		ImageIcon nearIcon = new ImageIcon("images/nearImage.png");
		JPanel contentPanel;

		public PreviousPanel() {
			setLayout(new BorderLayout());
			JLabel infoLabel = new JLabel(nearIcon);
			add(infoLabel, BorderLayout.NORTH);
			contentPanel = new JPanel();
			contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
			try {
				st = connection.createStatement();
				String currentSolaDate = Integer.toString(currentYear) + "-" + currentMonth + "-" + (currentDay+1)
						+ " 00:00:00";
				System.out.println(currentSolaDate);
				String nearSolaDate = Integer.toString(nearYear) + "-" + nearMonth + "-" + nearDay + " 11:59:59";
				String sql = "SELECT * FROM schedule WHERE solar between '" + currentSolaDate + "' and '" + nearSolaDate
						+ "'ORDER BY solar ASC;";
				ResultSet rs = st.executeQuery(sql);
				while (rs.next()) {
					JButton btn = new JButton(rs.getString("solar") + "\n " + rs.getString("memo"));
					setButtonUI(btn);
					contentPanel.add(btn);
					contentPanel.add(Box.createVerticalStrut(10) );
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			add(contentPanel, BorderLayout.CENTER);
		}
	}

	public void updatePanel() {
		remove(nearPanel);
		remove(currentPanel);
		remove(previousPanel);
		nearPanel = new NearPanel();
		currentPanel = new CurrentPanel();
		previousPanel = new PreviousPanel();
		add(nearPanel, BorderLayout.EAST);
		add(currentPanel, BorderLayout.CENTER);
		add(previousPanel, BorderLayout.WEST);
		revalidate();
		repaint();
	}

	public void setButtonUI(JButton button) {
		button.setFont(MainFrame.basicFont);
		// 외곽선 제거
		button.setBorderPainted(false);
		// 내용 체우기 제거
		button.setContentAreaFilled(false);
		// 포커스 되었을시 테두리 제거
		button.setFocusPainted(false);
		Border line = new LineBorder(Color.BLACK);
		Border margin = new EmptyBorder(5, 15, 5, 15);
		Border compound = new CompoundBorder(line, margin);
		button.setBorder(compound);
	}
}
