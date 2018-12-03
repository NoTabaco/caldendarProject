package version1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3650402257121452162L;
	int mouseX;
	int mouseY;
	static JPanel mainPanel;
	static TaskPanel taskPanel;
	
	static Font moonFont;
	static Font basicFont;
	static Font smallFont;

	
	static Connection connection = null;
	static Statement st = null;

	// Launch the application.
	public static void main(String[] args) {
		// ��Ƽ������� ����
		System.setProperty("awt.useSystemAAFontSettings","on");
		System.setProperty("swing.aatext", "true");
		MainFrame frame = new MainFrame();
	}

	// create Frame
	public MainFrame() {
		taskPanel = new TaskPanel(this,true);
		connection = MainFrame.connection;
		// ������ ���̽� ����
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(
					"jdbc:mysql://168.131.150.80:3306/bank?serverTimezone=UTC&useSSL=false", "root", "34290118");
		} catch (Exception e) {
			e.printStackTrace();
		}
		// local file ���� ��Ʈ�� �ҷ��ͼ� ����
		try {
			basicFont = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("font/Maplestory_Bold.ttf"))).deriveFont(Font.BOLD, 20);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Error");
			}
		
		try {
			moonFont = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("font/Maplestory_Bold.ttf"))).deriveFont(Font.BOLD, 15);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Error");
			}
		
		try {
			smallFont = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("font/Maplestory_Bold.ttf"))).deriveFont(Font.BOLD, 20);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Error");
			}
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// ȭ�� ũ�⸦ ���Ƿ� �������� ���ϰ� ����
		setResizable(false);		
		// ���ȭ���� �������� �������ش�.
		setBackground(Color.BLACK);
		// ȭ�� ũ�� ����
		setSize(450,800);
		// �޴��ٰ� ������ �ʰԲ� ����
		setUndecorated(true);
		// ���̾ƿ� ����
		setLayout(new BorderLayout());
		// ���ȭ�� �� ����
		setBackground(Color.WHITE);
		setLocationRelativeTo(null);
		
		JPanel menubar = new MenubarPanel();
		menubar.addMouseListener(new MouseAdapter() {
			// ���콺�� �Է������� ������Ʈ���� ���콺�� x��ǥ�� y��ǥ�� �����´�
			@Override
			public void mousePressed(MouseEvent e) {
				mouseX = e.getX();
				mouseY = e.getY();
			}
		});
		// menuBar�� �巡�� ������ �̺�Ʈ ó���� ���ش�.
		menubar.addMouseMotionListener(new MouseMotionAdapter() {
			// ���콺�� �Է������� ��ũ��(�����)���� ���콺�� x��ǥ�� y��ǥ�� �����´�
			@Override
			public void mouseDragged(MouseEvent e) {
				int x = e.getXOnScreen();
				int y = e.getYOnScreen();
				// ��ũ������ ���콺�� ��ǥ�� ������Ʈ���� ���콺�� ��ǥ�� ���� ����â�� ��ġ�̴�.
				setLocation(x - mouseX, y - mouseY);
			}
		});
		// menubar�� North�� �߰��Ѵ�.
		add(menubar,BorderLayout.NORTH);
		mainPanel = new MainPanel();
		add(mainPanel,BorderLayout.CENTER);
		
		setVisible(true);
	}

}
