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
		JPanel todoListPanel = new JPanel();
		todoListPanel.setLayout(new GridLayout(0,1));
		todoListPanel.setOpaque(false);
		String todos = "";
		if(layout.getLayoutComponent(BorderLayout.CENTER) != null) {
		remove(layout.getLayoutComponent(BorderLayout.CENTER));
		}
		JLabel todo;
		for (int i = 0 ; i < todoList.size() ; i++) {
			todo = new JLabel(todoList.get(i));
			todo.setHorizontalAlignment(JLabel.CENTER);
			todo.setForeground(new Color(78,223,206));
			todo.setFont(moonFont);
			todoListPanel.add(todo);
		}
		System.out.println(todos);
		add(todoListPanel, BorderLayout.CENTER);
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
			if(e.getClickCount()%2 == 0) {
				UIManager.put("OptionPane.messageFont", MainFrame.basicFont);
				String todos = MainPanel.setYear + "년" + MainPanel.setMonth + "월" + MainPanel.setDay + "일 일정\n";
				for (int i = 0 ; i < todoList.size() ; i++) {
					todos = todos +"  - " + todoList.get(i) +"\n";
				}
				JOptionPane.showMessageDialog(null, todos);
			}
		}
	}
}
