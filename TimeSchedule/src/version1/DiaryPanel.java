package version1;

import java.awt.Color;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JPanel;

/**
 * Grid Layout���� ��¥�� ���
 * 
 * @author Sungho
 *
 */
public class DiaryPanel extends JPanel{

	Connection connection = null;
	Statement st = null;

	Calendar cal = Calendar.getInstance();
	int standardYear;
	int standardMonth;
	Schedule schedule[][];
	int solar[][];
	String moon[][] ;
	/**
	 * Create the panel.
	 */
	public DiaryPanel(int standardYear, int standardMonth) {
		
		this.standardYear = standardYear;
		this.standardMonth = standardMonth;
		connection = MainFrame.connection;
		schedule = new Schedule[6][7];
		 solar = new int[6][7];
		 moon = new String[6][7];

		boolean isNext = false;
		setLayout(new GridLayout(6, 7));
		// ���� �⵵, ��, ��
		int currentYear = standardYear;
		int currentMonth = standardMonth;
		// �޷� ����
		// ���� �� ���� ����
		cal.set(currentYear, currentMonth, 1);
		int startOfMonthWeek = cal.get(Calendar.DAY_OF_WEEK) - 1;
		// ���� �� �ִ� ��¥
		int daysOfMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		// ���� �� �ִ� ��¥
		cal.add(Calendar.MONTH, -1);
		int preDaysOfMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		try {
			// ù���� �ٿ��� ���� �� ���� ��¥ ���
			for (int i = startOfMonthWeek - 1; i >= 0; i--) {
				String sql;
				// connection���� statement�� �����´�.
				st = connection.createStatement();
				// ��� ��¥ ���� ����
				String solaDate = Integer.toString(currentYear) + "-" + currentMonth + "-" + preDaysOfMonth;
				String memo = "";
				solar[0][i] = preDaysOfMonth--;
				sql = "select * FROM g4_lunartosolar WHERE solar_date ='" + solaDate + "';";
				ResultSet rs = st.executeQuery(sql);
				while (rs.next()) {
					moon[0][i] = rs.getString("lunar_date").substring(5);
					memo = rs.getString("memo");
					
				}
				schedule[0][i] = new Schedule(solar[0][i], moon[0][i]);
				if(getDateDay(solaDate) == 1 || memo.length() != 0) {
					schedule[0][i].setSolarColor(new Color(255, 0, 0, 100));
				} else if(getDateDay(solaDate) == 7){
					schedule[0][i].setSolarColor(new Color(0, 0, 255, 100));	
				} else {
					schedule[0][i].setSolarColor(new Color(0, 0, 0, 100));
				} 
				
				if(memo.length() != 0) {
					schedule[0][i].insertTodo(memo);
				}
				
				sql = "select memo FROM schedule WHERE solar ='" + solaDate +"';";
				st = connection.createStatement();
				rs = st.executeQuery(sql);
				while (rs.next()) {
					schedule[0][i].insertTodo(rs.getString("memo"));
					schedule[0][i].updateTodo();
				}
				
			}
			// ù��° �ٿ��� ����� ��¥�� ���
			int day = 1;
			for (int i = startOfMonthWeek; i < 7; i++) {
				String sql;
				st = connection.createStatement();
				String solaDate = Integer.toString(currentYear) + "-" + (currentMonth + 1) + "-"
						+ (day < 10 ? "0" + day : day);
				String memo = "";
				solar[0][i] = day++;
				sql = "select * FROM g4_lunartosolar WHERE solar_date ='" + solaDate + "';";
				ResultSet rs = st.executeQuery(sql);
				while (rs.next()) {
					moon[0][i] = rs.getString("lunar_date").substring(5);
					memo = rs.getString("memo");
				}
				schedule[0][i] = new Schedule(solar[0][i], moon[0][i]);
				if(getDateDay(solaDate) == 1 || memo.length() != 0) {
					schedule[0][i].setSolarColor(new Color(255, 0, 0));
				} else if(getDateDay(solaDate) == 7){
					schedule[0][i].setSolarColor(new Color(0, 0, 255));	
				} else {
					schedule[0][i].setSolarColor(new Color(0, 0, 0));
				}
				
				if(memo.length() != 0) {
					schedule[0][i].insertTodo(memo); 
				}
				
				sql = "select memo FROM schedule WHERE solar ='" + solaDate +"';";
				st = connection.createStatement();
				rs = st.executeQuery(sql);
				while (rs.next()) {
					schedule[0][i].insertTodo(rs.getString("memo"));
					schedule[0][i].updateTodo();
				}
			}
			// ������ ���� ��¥�� �Է��Ѵ�.
			for (int i = 1; i < 6; i++) {
				for (int j = 0; j < 7; j++) {
					// ���� ��¥�� ������� �ִ븦 �Ѿ�ٸ� 1�� �ʱ�ȭ �����ش�.
					if (day > daysOfMonth) {
						currentMonth++;
						day = 1;
						isNext = true;
						if (currentMonth >= 12) {
							currentYear++;
							currentMonth = 0;
						}
					}
					String sql;
					st = connection.createStatement();
					String solaDate = Integer.toString(currentYear) + "-" + (currentMonth + 1) + "-"
							+ (day < 10 ? "0" + day : day);
					String memo = "";
					solar[i][j] = day++;
					sql = "select * FROM g4_lunartosolar WHERE solar_date ='" + solaDate + "';";
					ResultSet rs = st.executeQuery(sql);
					while (rs.next()) {
						moon[i][j] = rs.getString("lunar_date").substring(5);
						memo = rs.getString("memo");
					}
					schedule[i][j] = new Schedule(solar[i][j], moon[i][j]);

					if (isNext) {
						if(getDateDay(solaDate) == 1 || memo.length() != 0) {
							schedule[i][j].setSolarColor(new Color(255, 0, 0,100));
						} else if(getDateDay(solaDate) == 7){
							schedule[i][j].setSolarColor(new Color(0, 0, 255,100));	
						} else {
							schedule[i][j].setSolarColor(new Color(0, 0, 0,100));
						}
					} else {
						if(getDateDay(solaDate) == 1 || memo.length() != 0) {
							schedule[i][j].setSolarColor(new Color(255, 0, 0));
						} else if(getDateDay(solaDate) == 7){
							schedule[i][j].setSolarColor(new Color(0, 0, 255));	
						} else {
							schedule[i][j].setSolarColor(new Color(0, 0, 0));
						}
					}
					
					System.out.println("length= " + memo.length() + "," + memo);
					if(memo.length() != 0) {
						System.out.println("solaDate = " + solaDate);
						System.out.println("schedul[i][j].insertTodo(memo); = " + memo);
						schedule[i][j].insertTodo(memo);
					}
					
					sql = "select memo FROM schedule WHERE solar ='" + solaDate +"';";
					st = connection.createStatement();
					rs = st.executeQuery(sql);
					while (rs.next()) {
						schedule[i][j].insertTodo(rs.getString("memo"));
						schedule[i][j].updateTodo();
					}
				}
				
			}

			for(int i = 0 ; i < 6 ; i++) {
				for(int j = 0 ; j < 7 ; j++) {
					add(schedule[i][j]);
				}
			}
			setOpaque(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		setFocusable(true);
		this.requestFocus();
		

	}


	/**
	 * Ư�� ��¥�� ���Ͽ� ������ ����(�� ~ ��)
	 * 
	 * @param date
	 * @param dateType
	 * @return
	 * @throws Exception
	 */
	public int getDateDay(String date) throws Exception {

		String day = "";
		String dateType = "yyyy-MM-dd";
		SimpleDateFormat dateFormat = new SimpleDateFormat(dateType);
		Date nDate = dateFormat.parse(date);

		Calendar cal = Calendar.getInstance();
		cal.setTime(nDate);

		int dayNum = cal.get(Calendar.DAY_OF_WEEK);
		return dayNum;
	}

}
