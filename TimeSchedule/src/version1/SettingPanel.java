package version1;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileInputStream;

import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;

public class SettingPanel extends JDialog {

	Font font;

	JCheckBox holibox;
	JCheckBox moonbox;
	JCheckBox backbox;

	public SettingPanel(JFrame owner, boolean modal) {
		super(owner, modal);
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("font/Maplestory_Bold.ttf")))
					.deriveFont(Font.BOLD, 20);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error");
		}
		setLayout(new GridLayout(3, 1));
		
		drawGUI();
		setTitle("UI ȯ�漳��");
		setSize(170, 190);
		setLocationRelativeTo(null);
	}
	public void drawGUI() {
		holibox = new JCheckBox("������ ǥ��");
		moonbox = new JCheckBox("���� ǥ��");
		backbox = new JCheckBox("���ȭ�� ǥ��");
		
		holibox.setSelected(MainFrame.isHoliday);
		moonbox.setSelected(MainFrame.isMoon);
		backbox.setSelected(MainFrame.isBackground);
		// local file ���� ��Ʈ�� �ҷ��ͼ� ����

		holibox.setFont(font);
		moonbox.setFont(font);
		backbox.setFont(font);
		
		holibox.addItemListener(new CheckEvent());
		moonbox.addItemListener(new CheckEvent());		
		backbox.addItemListener(new CheckEvent());
		
		add(holibox);
		add(moonbox);
		add(backbox);
	}

	public void updatePanel() {
		remove(holibox);
		remove(moonbox);
		remove(backbox);
		drawGUI();
		revalidate();
		repaint();
	}

	public void visible(boolean isView) {
		setVisible(isView);
		System.out.println(holibox.getFont());
	}
	
	class CheckEvent implements ItemListener{

		@Override
		public void itemStateChanged(ItemEvent e) {
			if(e.getSource() == backbox) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					MainFrame.isBackground = true;
					
				} else {
					MainFrame.isBackground = false;
				}
			} else if(e.getSource() == moonbox) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					MainFrame.isMoon = true;
					
				} else {
					MainFrame.isMoon = false;
				}
			} else if(e.getSource() == holibox) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					MainFrame.isHoliday = true;
					
				} else {
					MainFrame.isHoliday = false;
				}
			}
			
		}
		
	}

}
