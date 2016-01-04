package com.kinderriven.ui.window;

import javax.swing.JPanel;
import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.SpringLayout;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import com.kinderriven.mysql.mysql.MySQLServer;
import com.kinderriven.mysql.mysql.MySQLSetting;
import com.kinderriven.webspider.baidutieba.TieBaManager;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingConstants;
import javax.swing.JTextPane;


public class tiebaJPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable table;

	
	public final String mysql_ip = MySQLSetting.getMySQLSetting().mysql_ip;
	public final String mysql_database = MySQLSetting.getMySQLSetting().mysql_database;
	public final int mysql_port = MySQLSetting.getMySQLSetting().mysql_port;
	private JButton btnStart;
	private JButton btnRefresh;
	private JButton btnSelect;
	
	
	//
	private Vector<Integer>vecSelect = new Vector<>();
	private JLabel lblStatus;
	private JTextPane textPane;
	/**
	 * Create the panel.
	 */
	public void loadData(){
		
		textPane.setText("");
		
		ResultSet result = new MySQLServer(mysql_ip, mysql_database, mysql_port)
				.getResult("SELECT id,url,name FROM webspider.tieba;");
		
		try {
		
			while(result.next()){
				
				String id = result.getString(1);
				String url = result.getString(2);
				String name = result.getString(3);
				
				Object[] object = new Object[]{id, url, name};
				
				DefaultTableModel model = (DefaultTableModel)table.getModel();
				
				model.addRow(object);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	public tiebaJPanel() {
		
		//
		//
		setBackground(Color.BLACK);
		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);
		
		JLabel lblNewLabel = new JLabel("");
		springLayout.putConstraint(SpringLayout.NORTH, lblNewLabel, 388, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.SOUTH, lblNewLabel, -10, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, lblNewLabel, -10, SpringLayout.EAST, this);
		lblNewLabel.setIcon(new ImageIcon(tiebaJPanel.class.getResource("/image/tieba.jpg")));
		
		add(lblNewLabel);
		
		table = new JTable();
		table.setForeground(Color.WHITE);
		table.setBackground(Color.BLACK);
		springLayout.putConstraint(SpringLayout.WEST, table, 10, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, table, -6, SpringLayout.WEST, lblNewLabel);
		table.setFont(new Font("楷体", Font.BOLD, 20));
		
		table.setRowHeight(25);
		DefaultTableModel model = (DefaultTableModel)table.getModel();
		
		model.addColumn("id");
		model.addColumn("url");
		model.addColumn("name");
		
		TableColumn column1 = table.getColumnModel().getColumn(0);
		column1.setMaxWidth(60);
		column1.setMinWidth(60);
		
		TableColumn column2 = table.getColumnModel().getColumn(1);
		column2.setMaxWidth(380);
		column2.setMinWidth(380);
		
		TableColumn column3 = table.getColumnModel().getColumn(2);
		column3.setMaxWidth(85);
		column3.setMinWidth(85);
		
		//loadData();
		springLayout.putConstraint(SpringLayout.NORTH, table, 10, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.SOUTH, table, -10, SpringLayout.SOUTH, this);
		add(table);
		
		btnRefresh = new JButton("FRESH");
		btnRefresh.setContentAreaFilled(false);
		btnRefresh.setInheritsPopupMenu(true);
		springLayout.putConstraint(SpringLayout.WEST, btnRefresh, 0, SpringLayout.WEST, lblNewLabel);
		springLayout.putConstraint(SpringLayout.EAST, btnRefresh, 0, SpringLayout.EAST, lblNewLabel);
		btnRefresh.setFocusable(false);
		btnRefresh.setBackground(Color.BLACK);
		btnRefresh.setForeground(Color.WHITE);
		btnRefresh.setFont(new Font("SAO UI", Font.PLAIN, 30));
		btnRefresh.setIcon(new ImageIcon(tiebaJPanel.class.getResource("/image/reload_normal.png")));
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
					
				DefaultTableModel model = (DefaultTableModel)table.getModel();
				
				model.setRowCount(0);
				
				loadData();
				
			}
		});
		add(btnRefresh);
		
		btnStart = new JButton("START");
		btnStart.setContentAreaFilled(false);
		springLayout.putConstraint(SpringLayout.NORTH, btnStart, 269, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.SOUTH, btnRefresh, -7, SpringLayout.NORTH, btnStart);
		springLayout.putConstraint(SpringLayout.EAST, btnStart, 0, SpringLayout.EAST, lblNewLabel);
		springLayout.putConstraint(SpringLayout.WEST, btnStart, 0, SpringLayout.WEST, lblNewLabel);
		btnStart.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				new Thread(){
					
					public void run(){
						TieBaManager.getTBM().getDataFromUI(vecSelect);
					
						TieBaManager.getTBM().startRun();
					}
				}.start();
			}
		});
		btnStart.setIcon(new ImageIcon(tiebaJPanel.class.getResource("/image/start.png")));
		btnStart.setForeground(Color.WHITE);
		btnStart.setFont(new Font("SAO UI", Font.PLAIN, 30));
		btnStart.setFocusable(false);
		btnStart.setBackground(Color.WHITE);
		btnStart.setAutoscrolls(true);
		add(btnStart);
		
		btnSelect = new JButton("SELECT");
		btnSelect.setIcon(new ImageIcon(tiebaJPanel.class.getResource("/image/setting.png")));
		btnSelect.setContentAreaFilled(false);
		springLayout.putConstraint(SpringLayout.SOUTH, btnStart, -6, SpringLayout.NORTH, btnSelect);
		springLayout.putConstraint(SpringLayout.EAST, btnSelect, 0, SpringLayout.EAST, lblNewLabel);
		springLayout.putConstraint(SpringLayout.WEST, btnSelect, 0, SpringLayout.WEST, lblNewLabel);
		springLayout.putConstraint(SpringLayout.NORTH, btnSelect, 323, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.SOUTH, btnSelect, -17, SpringLayout.NORTH, lblNewLabel);
		btnSelect.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				vecSelect.clear();
				
				int []a = table.getSelectedRows();
				
				String str = "";
				
				for(int i = 0; i < a.length; i++){
					
					String sId = (String)table.getValueAt(a[i], 0);
					
					str += (String)table.getValueAt(a[i], 2);
					str += "\n";
					
					Integer id = Integer.parseInt(sId);
					
					vecSelect.add(id);

				}
				textPane.setText(str);
				
			}
		});
		btnSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		btnSelect.setForeground(Color.WHITE);
		btnSelect.setFont(new Font("SAO UI", Font.PLAIN, 30));
		btnSelect.setFocusable(false);
		btnSelect.setBackground(Color.WHITE);
		btnSelect.setAutoscrolls(true);
		add(btnSelect);
		
		lblStatus = new JLabel("STATUS");
		springLayout.putConstraint(SpringLayout.NORTH, btnRefresh, 169, SpringLayout.SOUTH, lblStatus);
		springLayout.putConstraint(SpringLayout.SOUTH, lblStatus, -451, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.NORTH, lblStatus, 10, SpringLayout.NORTH, this);
		lblStatus.setHorizontalAlignment(SwingConstants.CENTER);
		lblStatus.setFont(new Font("SAO UI", Font.BOLD, 31));
		springLayout.putConstraint(SpringLayout.WEST, lblStatus, 6, SpringLayout.EAST, table);
		springLayout.putConstraint(SpringLayout.EAST, lblStatus, 0, SpringLayout.EAST, lblNewLabel);
		lblStatus.setForeground(Color.WHITE);
		add(lblStatus);
		
		textPane = new JTextPane();
		textPane.setFont(new Font("华康少女文字W5(P)", Font.PLAIN, 18));
		textPane.setBackground(Color.BLACK);
		textPane.setForeground(Color.WHITE);
		springLayout.putConstraint(SpringLayout.NORTH, textPane, 6, SpringLayout.SOUTH, lblStatus);
		springLayout.putConstraint(SpringLayout.WEST, textPane, 6, SpringLayout.EAST, table);
		springLayout.putConstraint(SpringLayout.SOUTH, textPane, -6, SpringLayout.NORTH, btnRefresh);
		springLayout.putConstraint(SpringLayout.EAST, textPane, 0, SpringLayout.EAST, lblNewLabel);
		add(textPane);

		
	}
}
