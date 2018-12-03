package version1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * 스케쥴 하나하나를  표시하는 패널
 * @author Sungho
 *
 */
public class Schedule extends JPanel{
	int solar;
	String moon;
	ArrayList <String> todoList;
	JLabel solarDate;
	JLabel moonDate;
	Font solaFont;
	Font moonFont;
	// 폰트 선언
	Color solarColor;
	BorderLayout layout;
	
	public Schedule(int solar, String moon) {
		todoList = new ArrayList<String>();
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
		add(solarDate,BorderLayout.NORTH);
		add(moonDate,BorderLayout.SOUTH);
		// 양력 기본색 지정
		solarColor = new Color(0,0,0);
		// 양력 기본색설정
		solarDate.setForeground(solarColor);
		// 음력 기본색 설정
		moonDate.setForeground(new Color(0,0,0,100));
		// 테두리 설정
		setBorder(BorderFactory.createLineBorder(new Color(0,0,0,20)));
		// 불투명도 설정
		setOpaque(false);
		// 포커스 설정
		setFocusable(true);
		// 이벤트  추가
		addFocusListener(new FocusEvent());
		addMouseListener(new MouseEvent());
	}
	public void getFocus() {
		requestFocus();
	}
	
	public void updateTodo() {
		if(layout.getLayoutComponent(BorderLayout.CENTER) != null) {
		remove(layout.getLayoutComponent(BorderLayout.CENTER));
		}
		JLabel todo[] = new JLabel[todoList.size()];
		for (int i = 0 ; i < todo.length ; i++) {
			todo[i] = new JLabel(todoList.get(i));
			todo[i].setFont(moonFont);
			todo[i].setHorizontalAlignment(JLabel.CENTER);
			todo[i].setForeground(Color.PINK);
			add(todo[i], BorderLayout.CENTER);
		}
	}
	public void insertTodo(String todo) {
		todoList.add(todo);
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
	
	class FocusEvent implements FocusListener{

		@Override
		public void focusGained(java.awt.event.FocusEvent e) {
			setBorder(BorderFactory.createLineBorder(new Color(195,241,199,255)));
			
		}

		@Override
		public void focusLost(java.awt.event.FocusEvent e) {
			setBorder(BorderFactory.createLineBorder(new Color(0,0,0,20)));
		}	
	}
	
	class MouseEvent extends MouseAdapter{

		@Override
		public void mouseClicked(java.awt.event.MouseEvent e) {
			MainPanel.setDay = solar;
			getFocus();
		}
	}
}
