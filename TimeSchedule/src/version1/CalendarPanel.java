package version1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.File;
import java.io.FileInputStream;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class CalendarPanel extends JPanel {

	String weekend[] = { "�Ͽ���", "������", "ȭ����", "������", "�����", "�ݿ���", "�����" };
	JPanel diaryPanel;
	Font font;
	JPanel weekPanel;
	int year;
	int month;
	/**
	 * Create the panel.
	 */
	public CalendarPanel(int year, int month) {
		this.year = year;
		this.month = month;
		diaryPanel = new DiaryPanel(year,month);
		font = MainFrame.basicFont;

		weekPanel = new JPanel();
		weekPanel.setLayout(new GridLayout(1, 7));
		for (int i = 0; i < 7; i++) {
			JLabel weekendLabel = new JLabel(weekend[i]);
			if (i == 0) {
				weekendLabel.setForeground(Color.RED);
			} else if (i == 6) {
				weekendLabel.setForeground(Color.BLUE);
			}
			weekendLabel.setFont(font);
			weekendLabel.setHorizontalAlignment(JLabel.CENTER);
			weekendLabel.setOpaque(false);
			weekPanel.add(weekendLabel);
		}

		weekPanel.setOpaque(false);
		setOpaque(false);
		setLayout(new BorderLayout());
		// weekPanel�� NORTH�� �߰�
		add(weekPanel, BorderLayout.NORTH);
		add(diaryPanel, BorderLayout.CENTER);
	}
	
	public void updatePanel() {
		remove(diaryPanel);
		diaryPanel = new DiaryPanel(year,month);
		add(diaryPanel, BorderLayout.CENTER);
		revalidate();
		repaint();
	}

}
