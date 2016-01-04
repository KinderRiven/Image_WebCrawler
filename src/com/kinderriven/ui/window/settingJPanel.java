package com.kinderriven.ui.window;

import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import com.kinderriven.mysql.mysql.MySQLSetting;
import com.kinderriven.webspider.baidutieba.TieBaManager;
import com.kinderriven.webspider.zero.ZeroManager;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class settingJPanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textIP;
	private JTextField textUsr;
	private JButton btnOk;
	private JPasswordField textPWD;
	private JTextField textPath;

	/* Create the panel. */
	void loadData(){
		
		textIP.setText(MySQLSetting.getMySQLSetting().mysql_ip);
		textUsr.setText(MySQLSetting.getMySQLSetting().username);
		textPWD.setText(MySQLSetting.getMySQLSetting().password);
	}
	public settingJPanel() {
		setBackground(Color.BLACK);
		setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Database IP");
		lblNewLabel.setFont(new Font("SAO UI", Font.BOLD, 32));
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBounds(286, 59, 150, 41);
		add(lblNewLabel);
		
		JLabel lblUsername = new JLabel("UserName");
		lblUsername.setForeground(Color.WHITE);
		lblUsername.setFont(new Font("SAO UI", Font.BOLD, 32));
		lblUsername.setBounds(286, 151, 150, 41);
		add(lblUsername);
		
		JLabel lblPassword = new JLabel("PassWord");
		lblPassword.setForeground(Color.WHITE);
		lblPassword.setFont(new Font("SAO UI", Font.BOLD, 32));
		lblPassword.setBounds(286, 246, 150, 41);
		add(lblPassword);
		
		textIP = new JTextField();
		textIP.setFont(new Font("SAO UI", Font.BOLD, 32));
		textIP.setBounds(473, 67, 181, 40);
		add(textIP);
		textIP.setColumns(10);
		
		textUsr = new JTextField();
		textUsr.setFont(new Font("SAO UI", Font.BOLD, 32));
		textUsr.setColumns(10);
		textUsr.setBounds(473, 159, 181, 40);
		add(textUsr);
		
		btnOk = new JButton("OK");
		btnOk.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				MySQLSetting.getMySQLSetting().mysql_ip = textIP.getText();
				MySQLSetting.getMySQLSetting().username = textUsr.getText();
				MySQLSetting.getMySQLSetting().password = new String(textPWD.getPassword());
				
				String folder = textPath.getText();
				
				MySQLSetting.getMySQLSetting().password = folder;
				
				TieBaManager.getTBM().setFolder("Tieba/");
				ZeroManager.getZM().setFolder("Zero/");
				
			}
		});
		btnOk.setRequestFocusEnabled(false);
		btnOk.setForeground(Color.WHITE);
		btnOk.setContentAreaFilled(false);
		btnOk.setFont(new Font("SAO UI", Font.BOLD, 30));
		btnOk.setBounds(411, 402, 113, 41);
		add(btnOk);
		
		textPWD = new JPasswordField();
		textPWD.setFont(new Font("SAO UI", Font.BOLD, 32));
		textPWD.setBounds(473, 246, 181, 40);
		add(textPWD);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(settingJPanel.class.getResource("/image/info.png")));
		label.setBounds(35, 78, 208, 209);
		add(label);
		
		JLabel lblSavepath = new JLabel("SavePath");
		lblSavepath.setForeground(Color.WHITE);
		lblSavepath.setFont(new Font("SAO UI", Font.BOLD, 32));
		lblSavepath.setBounds(286, 326, 150, 41);
		add(lblSavepath);
		
		textPath = new JTextField();
		textPath.setFont(new Font("SAO UI", Font.BOLD, 32));
		textPath.setColumns(10);
		textPath.setBounds(473, 326, 181, 40);
		
		textPath.setText(MySQLSetting.getMySQLSetting().savePath);
		
		add(textPath);
		
		loadData();

	}
}
