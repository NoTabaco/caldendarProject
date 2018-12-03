package version1;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class ScheduleInfoPanel extends JDialog {
	ArrayList<String> todoList;
	JLabel solarLabel;
	JLabel todoLabel[];
	JCheckBox checkbox[];
	JPanel todoPanel;
	JButton deleteButton;
	ImageIcon deleteButtonImage;
	ImageIcon deleteButtonEnterImage;

	Connection connection = null;
	Statement st = null;
	
	public ScheduleInfoPanel(JFrame owner, boolean modal) {
		super(owner, modal);
		deleteButtonImage = new ImageIcon("images/deleteButtonImage.png");
		deleteButtonEnterImage = new ImageIcon("images/deleteEnterButtonImage.png");
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(
					"jdbc:mysql://168.131.150.80:3306/bank?serverTimezone=UTC&useSSL=false", "root", "34290118");
		} catch (Exception e) {
			e.printStackTrace();
		}
		setLayout(new BorderLayout());
		setSize(270, 300);
		setLocationRelativeTo(null);

	}

	public void drawGUI(int year, int month, int day) {
		updatePanel();
		String solarDate = year + "년" + month +  "월" + day + "일";
		setTitle(solarDate+ " 일정" );
		solarLabel = new JLabel("  "+ solarDate+ " 일정" );
		solarLabel.setHorizontalAlignment(JLabel.CENTER);
		solarLabel.setFont(MainFrame.basicFont);
		add(solarLabel, BorderLayout.NORTH);
		
		todoPanel = new TodoPanel();
		add(todoPanel,BorderLayout.CENTER);
		
		deleteButton = new JButton(deleteButtonImage);
		setButtonUI(deleteButton);
		deleteButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				deleteButton.setIcon(deleteButtonEnterImage);
				deleteButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			// 마우스가 버튼에 나갔을때 이벤트 처리
			@Override
			public void mouseExited(MouseEvent e) {
				deleteButton.setIcon(deleteButtonImage);
				deleteButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			// 버튼을 클릭했을때 이벤트 처리
			@Override
			public void mousePressed(MouseEvent e) {
				String solarDate = year + "-" + month +  "-" + day + "-";
				for(int i = checkbox.length - 1 ; i >= 0 ; i--) {
					if(checkbox[i].isSelected()) {
						try {
						String sql = "DELETE FROM schedule WHERE solar='" + solarDate + "' AND memo='" + todoList.get(i) + "';";
						st = connection.createStatement();
						st.executeUpdate(sql);
						todoList.remove(i);
						isVisible(false);
						} catch(Exception err) {
							err.printStackTrace();
						}
					}
					
				}
			}
		});
		add(deleteButton,BorderLayout.SOUTH);
		
		
	}

	public void updatePanel() {
		if (solarLabel != null) {
			remove(solarLabel);
		}
		if (todoPanel != null) {
			remove(todoPanel);
		}
		revalidate();
		repaint();
	}

	public void isVisible(boolean isVisible) {
		setVisible(isVisible);
	}

	public void setTodoList(ArrayList todoList) {
		this.todoList = todoList;
	}
	
	public void setButtonUI(JButton button) {
		// 외곽선 제거
		button.setBorderPainted(false);
		// 내용 체우기 제거
		button.setContentAreaFilled(false);
		// 포커스 되었을시 테두리 제거
		button.setFocusPainted(false);
	}

	class TodoPanel extends JPanel {
		public TodoPanel() {
			
			setLayout(new GridLayout(0, 2));
			JLabel info = new JLabel("      일정");
			JLabel delete = new JLabel("삭제체크");
			info.setFont(MainFrame.basicFont);
			delete.setFont(MainFrame.basicFont);
			add(info); add(delete);
			todoLabel = new JLabel[todoList.size()];
			checkbox = new JCheckBox[todoList.size()];
			for (int i = 0; i < todoList.size(); i++) {
				checkbox[i] = new JCheckBox();
				todoLabel[i] = new JLabel("   - " + todoList.get(i));
				todoLabel[i].setFont(MainFrame.basicFont);
				todoLabel[i].setAlignmentX(Component.CENTER_ALIGNMENT);
				add(todoLabel[i]);
				add(checkbox[i]);
			}
		}
	}

}
