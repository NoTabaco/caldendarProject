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
 * ������ �ϳ��ϳ���  ǥ���ϴ� �г�
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
	// ��Ʈ ����
	Color solarColor;
	BorderLayout layout;
	
	public Schedule(int solar, String moon) {
		todoList = new ArrayList<String>();
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
		add(solarDate,BorderLayout.NORTH);
		add(moonDate,BorderLayout.SOUTH);
		// ��� �⺻�� ����
		solarColor = new Color(0,0,0);
		// ��� �⺻������
		solarDate.setForeground(solarColor);
		// ���� �⺻�� ����
		moonDate.setForeground(new Color(0,0,0,100));
		// �׵θ� ����
		setBorder(BorderFactory.createLineBorder(new Color(0,0,0,20)));
		// ������ ����
		setOpaque(false);
		// ��Ŀ�� ����
		setFocusable(true);
		// �̺�Ʈ  �߰�
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
