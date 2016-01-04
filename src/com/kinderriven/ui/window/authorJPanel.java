package com.kinderriven.ui.window;

import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

public class authorJPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public authorJPanel() {
		setBackground(Color.BLACK);
		setLayout(null);
		
		JLabel lblIot = new JLabel("IOT 13-1");
		lblIot.setHorizontalTextPosition(SwingConstants.CENTER);
		lblIot.setHorizontalAlignment(SwingConstants.CENTER);
		lblIot.setFont(new Font("SAO UI", Font.BOLD, 51));
		lblIot.setForeground(Color.WHITE);
		lblIot.setBounds(377, 61, 193, 102);
		add(lblIot);
		
		JLabel lblHanShukai = new JLabel(" Han Shukai");
		lblHanShukai.setIcon(new ImageIcon(authorJPanel.class.getResource("/image/skills_normal.png")));
		lblHanShukai.setForeground(Color.WHITE);
		lblHanShukai.setFont(new Font("SAO UI", Font.BOLD, 51));
		lblHanShukai.setBounds(345, 189, 250, 102);
		add(lblHanShukai);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(authorJPanel.class.getResource("/image/bniang.jpg")));
		label.setForeground(Color.WHITE);
		label.setFont(new Font("SAO UI", Font.BOLD, 51));
		label.setBounds(101, 104, 128, 266);
		add(label);
		
//		JLabel lblGongchen = new JLabel(" GongChen");
//		lblGongchen.setIcon(new ImageIcon(authorJPanel.class.getResource("/image/skills_normal.png")));
//		lblGongchen.setForeground(Color.WHITE);
//		lblGongchen.setFont(new Font("SAO UI", Font.BOLD, 51));
//		lblGongchen.setBounds(345, 300, 225, 102);
//		add(lblGongchen);

	}

}
