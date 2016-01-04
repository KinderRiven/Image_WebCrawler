package com.kinderriven.ui.window;

import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.SpringLayout;
import com.kinderriven.webspider.zero.GetZeroPageData;
import com.kinderriven.webspider.zero.ZeroManager;
import java.awt.Font;
import java.util.Vector;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class lingyuJPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JComboBox<String> comboBox;

	/**
	 * Create the panel.
	 */
	public void loadData(){

		Vector<String>vecTag = new GetZeroPageData().getTag();
		
		comboBox.removeAll();
		for(int i = 0; i < vecTag.size(); i++){
			
			comboBox.addItem(vecTag.get(i));
		
		}
		
	}
	public lingyuJPanel() {
		setToolTipText("PleaseSelect");
		setBackground(Color.BLACK);
		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);
		
		JLabel label = new JLabel("");
		springLayout.putConstraint(SpringLayout.NORTH, label, -90, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.WEST, label, 482, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, label, -10, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, label, -10, SpringLayout.EAST, this);
		label.setOpaque(true);
		label.setForeground(Color.WHITE);
		label.setIcon(new ImageIcon(lingyuJPanel.class.getResource("/image/logo.png")));
		add(label);
		
		JLabel lblHttpwwwlingyume = new JLabel("http://www.lingyu.me/");
		springLayout.putConstraint(SpringLayout.NORTH, lblHttpwwwlingyume, 113, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, lblHttpwwwlingyume, 125, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, lblHttpwwwlingyume, -329, SpringLayout.EAST, this);
		lblHttpwwwlingyume.setFont(new Font("SAO UI", Font.BOLD, 36));
		lblHttpwwwlingyume.setForeground(Color.WHITE);
		add(lblHttpwwwlingyume);
		
		comboBox = new JComboBox<String>();
		springLayout.putConstraint(SpringLayout.NORTH, comboBox, 2, SpringLayout.NORTH, lblHttpwwwlingyume);
		springLayout.putConstraint(SpringLayout.WEST, comboBox, 6, SpringLayout.EAST, lblHttpwwwlingyume);
		springLayout.putConstraint(SpringLayout.SOUTH, comboBox, 43, SpringLayout.NORTH, lblHttpwwwlingyume);
		springLayout.putConstraint(SpringLayout.EAST, comboBox, -159, SpringLayout.EAST, this);
		comboBox.setFont(new Font("SAO UI", Font.PLAIN, 31));
		comboBox.setToolTipText("PleaseSelect");
		comboBox.setBorder(null);
		add(comboBox);
		
		JButton btnStart = new JButton("START");
		btnStart.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				new Thread(){
					
					public void run(){
						String tag = comboBox.getSelectedItem().toString();
						
						System.out.println(tag);
						
						ZeroManager.getZM().startRun(tag);
					}
				}.start();
				
				
			}
		});
		springLayout.putConstraint(SpringLayout.SOUTH, lblHttpwwwlingyume, -81, SpringLayout.NORTH, btnStart);
		btnStart.setFocusable(false);
		btnStart.setForeground(Color.WHITE);
		btnStart.setContentAreaFilled(false);
		btnStart.setBackground(Color.WHITE);
		btnStart.setFont(new Font("SAO UI", Font.BOLD, 22));
		btnStart.setIcon(new ImageIcon(lingyuJPanel.class.getResource("/image/start.png")));
		add(btnStart);
		
		JButton btnFresh = new JButton("FRESH");
		btnFresh.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new Thread(){
				
					public void run(){
						loadData();
					}
				}.start();
				
			}
		});
		springLayout.putConstraint(SpringLayout.NORTH, btnFresh, 235, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, btnFresh, 412, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.NORTH, btnStart, 0, SpringLayout.NORTH, btnFresh);
		springLayout.putConstraint(SpringLayout.EAST, btnStart, -85, SpringLayout.WEST, btnFresh);
		btnFresh.setIcon(new ImageIcon(lingyuJPanel.class.getResource("/image/reload_normal.png")));
		btnFresh.setForeground(Color.WHITE);
		btnFresh.setFont(new Font("SAO UI", Font.BOLD, 22));
		btnFresh.setFocusable(false);
		btnFresh.setContentAreaFilled(false);
		btnFresh.setBackground(Color.WHITE);
		add(btnFresh);
		
		JLabel label_1 = new JLabel("");
		springLayout.putConstraint(SpringLayout.WEST, label_1, 0, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, label_1, 0, SpringLayout.SOUTH, this);
		label_1.setIcon(new ImageIcon(lingyuJPanel.class.getResource("/image/lovelive.png")));
		add(label_1);

	}
}
