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
				String todos = MainPanel.setYear + "��" + MainPanel.setMonth + "��" + MainPanel.setDay + "�� ����\n";
				for (int i = 0 ; i < todoList.size() ; i++) {
					todos = todos +"  - " + todoList.get(i) +"\n";
				}
				JOptionPane.showMessageDialog(null, todos);
			}
		}
	}
}
