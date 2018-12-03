package version1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

/**
 * ������ �ϳ��ϳ��� ǥ���ϴ� �г�
 * 
 * @author Sungho
 *
 */
public class Schedule extends JPanel {
	int solar;
	String moon;
	ArrayList<String> todoList;
	ArrayList<String> HolidayList;
	JLabel solarDate;
	JLabel moonDate;
	Font solaFont;
	Font moonFont;
	// ��Ʈ ����
	Color solarColor;
	BorderLayout layout;
	boolean isPrevious = false;
	boolean isNext = false;
	JPanel todoListPanel;

	public Schedule(int solar, String moon) {
		todoList = new ArrayList<String>();
		HolidayList = new ArrayList<String>();
		// font ����
		solaFont = MainFrame.basicFont;
		moonFont = MainFrame.moonFont;
		// ���̾ƿ� ����
		setLayout(new BorderLayout());
		layout = (BorderLayout) getLayout();

		// �����ڷ� ���� ������ �ʵ忡 ����
		this.solar = solar;
		this.moon = moon;
		// ���̺� �ʵ尪 ����
		solarDate = new JLabel(Integer.toString(solar));
		moonDate = new JLabel(moon);
		// ���̺� ����
		solarDate.setHorizontalAlignment(JLabel.CENTER);
		moonDate.setHorizontalAlignment(JLabel.CENTER);
		// ���̺� ��Ʈ ����
		solarDate.setFont(solaFont);
		moonDate.setFont(moonFont);
		// ���̺� ���̾ƿ��� �߰�
		add(solarDate, BorderLayout.NORTH);
		if (MainFrame.isMoon) {
			add(moonDate, BorderLayout.SOUTH);
		}
		// ��� �⺻�� ����
		solarColor = new Color(0, 0, 0);
		// ��� �⺻������
		solarDate.setForeground(solarColor);
		// ���� �⺻�� ����
		moonDate.setForeground(new Color(0, 0, 0, 100));
		// �׵θ� ����
		setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0, 20)));
		// ������ ����
		setOpaque(false);
		// ��Ŀ�� ����
		setFocusable(true);
		// �̺�Ʈ �߰�
		addFocusListener(new FocusEvent());
		addMouseListener(new MouseEvent());
	}

	public void getFocus() {
		requestFocus();
	}

	public void updateTodo() {
		if(todoListPanel != null) {
			remove(todoListPanel);
		}
		todoListPanel = new JPanel();
		todoListPanel.setLayout(new GridLayout(0, 1));
		todoListPanel.setOpaque(false);
		if (layout.getLayoutComponent(BorderLayout.CENTER) != null) {
			remove(layout.getLayoutComponent(BorderLayout.CENTER));
		}
		for (int i = 0; i < todoList.size(); i++) {
			JLabel todo = new JLabel(todoList.get(i));
			todo.setHorizontalAlignment(JLabel.CENTER);
			if (HolidayList.get(i).equals("Holiday")) {
				todo.setForeground(Color.RED);
			} else {
				todo.setForeground(new Color(38, 38, 38));
			}

			todo.setFont(moonFont);
			todoListPanel.add(todo);
		}
		add(todoListPanel, BorderLayout.CENTER);
		revalidate();
		repaint();
	}

	public void insertTodo(String todo, boolean isHoliday) {
		todoList.add(todo);
		if (isHoliday) {
			HolidayList.add("Holiday");
		} else {
			HolidayList.add("None");
		}
	}

	public int getSolar() {
		return solar;
	}

	public void setSolar(int solar) {
		this.solar = solar;
	}

	public String getMoon() {
		return moon;
	}

	public void setMoon(String moon) {
		this.moon = moon;
	}

	public ArrayList<String> getTodoList() {
		return todoList;
	}

	public void setTodoList(ArrayList<String> todoList) {
		this.todoList = todoList;
	}

	public void setSolarColor(Color color) {
		this.solarColor = color;
		solarDate.setForeground(solarColor);
	}

	public void setIsPrevious(boolean isPrevious) {
		this.isPrevious = isPrevious;
	}

	public void setIsNext(boolean isNext) {
		this.isNext = isNext;
	}
	public void updateUI() {
		
	}

	class FocusEvent implements FocusListener {

		@Override
		public void focusGained(java.awt.event.FocusEvent e) {
			setBorder(BorderFactory.createLineBorder(Color.RED));

		}

		@Override
		public void focusLost(java.awt.event.FocusEvent e) {
			setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0, 20)));
		}
	}

	class MouseEvent extends MouseAdapter {
		@Override
		public void mouseClicked(java.awt.event.MouseEvent e) {
			MainPanel.setDay = solar;
			if (!isPrevious && !isNext) {
				getFocus();
				if (e.getClickCount() % 2 == 0) {
					MainFrame.scheduleInfoPanel.setTodoList(todoList);
					MainFrame.scheduleInfoPanel.drawGUI(MainPanel.setYear, MainPanel.setMonth, MainPanel.setDay);
					MainFrame.scheduleInfoPanel.isVisible(true);
					updateTodo();
				}
			}
		}
	}
}
