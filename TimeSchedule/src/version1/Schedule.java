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
 * 스케쥴 하나하나를 표시하는 패널
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
	// 폰트 선언
	Color solarColor;
	BorderLayout layout;
	boolean isPrevious = false;
	boolean isNext = false;
	JPanel todoListPanel;

	public Schedule(int solar, String moon) {
		todoList = new ArrayList<String>();
		HolidayList = new ArrayList<String>();
		// font 지정
		solaFont = MainFrame.basicFont;
		moonFont = MainFrame.moonFont;
		// 레이아웃 지정
		setLayout(new BorderLayout());
		layout = (BorderLayout) getLayout();

		// 생성자로 받은 데이터 필드에 대입
		this.solar = solar;
		this.moon = moon;
		// 레이블에 필드값 대입
		solarDate = new JLabel(Integer.toString(solar));
		moonDate = new JLabel(moon);
		// 레이블값 정렬
		solarDate.setHorizontalAlignment(JLabel.CENTER);
		moonDate.setHorizontalAlignment(JLabel.CENTER);
		// 레이블 폰트 설정
		solarDate.setFont(solaFont);
		moonDate.setFont(moonFont);
		// 레이블 레이아웃에 추가
		add(solarDate, BorderLayout.NORTH);
		if (MainFrame.isMoon) {
			add(moonDate, BorderLayout.SOUTH);
		}
		// 양력 기본색 지정
		solarColor = new Color(0, 0, 0);
		// 양력 기본색설정
		solarDate.setForeground(solarColor);
		// 음력 기본색 설정
		moonDate.setForeground(new Color(0, 0, 0, 100));
		// 테두리 설정
		setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0, 20)));
		// 불투명도 설정
		setOpaque(false);
		// 포커스 설정
		setFocusable(true);
		// 이벤트 추가
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
