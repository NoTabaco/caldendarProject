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
		// 안티엘리어싱 설정
		System.setProperty("awt.useSystemAAFontSettings","on");
		System.setProperty("swing.aatext", "true");
		MainFrame frame = new MainFrame();
	}

	// create Frame
	public MainFrame() {
		taskPanel = new TaskPanel(this,true);
		connection = MainFrame.connection;
		// 데이터 베이스 연결
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(
					"jdbc:mysql://168.131.150.80:3306/bank?serverTimezone=UTC&useSSL=false", "root", "34290118");
		} catch (Exception e) {
			e.printStackTrace();
		}
		// local file 에서 폰트를 불러와서 설정
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
		// 화면 크기를 임의로 변경하지 못하게 설정
		setResizable(false);		
		// 배경화면을 검정으로 설정해준다.
		setBackground(Color.BLACK);
		// 화면 크기 지정
		setSize(450,800);
		// 메뉴바가 보이지 않게끔 설정
		setUndecorated(true);
		// 레이아웃 설정
		setLayout(new BorderLayout());
		// 배경화면 색 선정
		setBackground(Color.WHITE);
		setLocationRelativeTo(null);
		
		JPanel menubar = new MenubarPanel();
		menubar.addMouseListener(new MouseAdapter() {
			// 마우스를 입력했을때 컴포넌트내의 마우스의 x좌표와 y좌표를 가져온다
			@Override
			public void mousePressed(MouseEvent e) {
				mouseX = e.getX();
				mouseY = e.getY();
			}
		});
		// menuBar를 드래그 했을때 이벤트 처리를 해준다.
		menubar.addMouseMotionListener(new MouseMotionAdapter() {
			// 마우스를 입력했을때 스크린(모니터)내의 마우스의 x좌표와 y좌표를 가져온다
			@Override
			public void mouseDragged(MouseEvent e) {
				int x = e.getXOnScreen();
				int y = e.getYOnScreen();
				// 스크린내의 마우스의 좌표와 컴포넌트내의 마우스의 좌표의 차가 게임창의 위치이다.
				setLocation(x - mouseX, y - mouseY);
			}
		});
		// menubar를 North에 추가한다.
		add(menubar,BorderLayout.NORTH);
		mainPanel = new MainPanel();
		add(mainPanel,BorderLayout.CENTER);
		
		setVisible(true);
	}

}
