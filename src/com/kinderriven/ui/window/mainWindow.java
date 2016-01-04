package com.kinderriven.ui.window;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import java.awt.Font;
import java.awt.Point;

import javax.swing.JMenuItem;
import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JLabel;

public class mainWindow extends JFrame {

	
	/* 							*/
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	
	/* Launch the application. */
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mainWindow frame = new mainWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	Point mousePos;
	private JMenu startMenu;
	private JMenuItem tiebaItem;
	private JMenuItem zeroItem;
	private JMenu mnSetting;
	private JMenuItem mntmDatabase;
	private JLabel label;
	private JLabel label_1;
	private JMenuBar menuBar;
	private JMenuItem mntmIndex;
	private JMenuItem mntmExit;
	private JMenuItem mntmAbout;
	public mainWindow() {
		
		//去除标题栏
		setUndecorated(true);
		//
		setOpacity(0.9f);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 750, 550);
		
		menuBar = new JMenuBar();
		menuBar.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				
				mousePos = arg0.getPoint();
				
				System.out.println(mousePos);
			}
		});
		menuBar.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			
			public void mouseDragged(MouseEvent e) {
				
				Point newPos = e.getPoint();
				Point windowPos = getLocation(); 
				
				setLocation((int)(windowPos.getX() + newPos.x -  mousePos.x),
						(int)(windowPos.getY() + newPos.y - mousePos.y));
				
			}
		});
		menuBar.setOpaque(false);
		menuBar.setBackground(Color.BLACK);
		setJMenuBar(menuBar);
		
		startMenu = new JMenu("START");
		startMenu.setIcon(new ImageIcon(mainWindow.class.getResource("/image/start.png")));
		startMenu.setFont(new Font("SAO UI", Font.BOLD, 26));
		menuBar.add(startMenu);
		
		tiebaItem = new JMenuItem("Tieba");
		tiebaItem.setFocusPainted(true);
		tiebaItem.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				
				setContentPane(new tiebaJPanel());
				validate();
			}
		});
		
		mntmIndex = new JMenuItem("INDEX");
		mntmIndex.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				
				setContentPane(contentPane);
				validate();
			}
		});
		mntmIndex.setIcon(new ImageIcon(mainWindow.class.getResource("/image/reload_normal.png")));
		mntmIndex.setFont(new Font("SAO UI", Font.BOLD, 22));
		startMenu.add(mntmIndex);
		tiebaItem.setIcon(new ImageIcon(mainWindow.class.getResource("/image/net_normal.png")));
		tiebaItem.setFont(new Font("SAO UI", Font.BOLD, 22));
		startMenu.add(tiebaItem);
		
		zeroItem = new JMenuItem("Zero");
		zeroItem.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				
				setContentPane(new lingyuJPanel());
				validate();
				
			}
		});
		zeroItem.setIcon(new ImageIcon(mainWindow.class.getResource("/image/net_normal.png")));
		zeroItem.setFont(new Font("SAO UI", Font.BOLD, 22));
		startMenu.add(zeroItem);
		
		mnSetting = new JMenu("CONFIG");
		mnSetting.setIcon(new ImageIcon(mainWindow.class.getResource("/image/setting.png")));
		mnSetting.setFont(new Font("SAO UI", Font.BOLD, 26));
		menuBar.add(mnSetting);
		
		mntmDatabase = new JMenuItem("DATABASE");
		
		mntmDatabase.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
								
				setContentPane(new settingJPanel());
				validate();
				
			}
		});
		
		mntmDatabase.setIcon(new ImageIcon(mainWindow.class.getResource("/image/modify_normal.png")));
		mntmDatabase.setFont(new Font("SAO UI", Font.BOLD, 22));
		mnSetting.add(mntmDatabase);
		
		mntmExit = new JMenuItem("EXIT");
		mntmExit.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				
				System.exit(0);
			}
		});
		
		JMenuItem mntmTieba = new JMenuItem("Tieba");
		mntmTieba.setIcon(new ImageIcon(mainWindow.class.getResource("/image/setting.png")));
		mntmTieba.setFont(new Font("SAO UI", Font.BOLD, 22));
		mnSetting.add(mntmTieba);
		mntmExit.setIcon(new ImageIcon(mainWindow.class.getResource("/image/shut-down_normal.png")));
		mntmExit.setFont(new Font("SAO UI", Font.BOLD, 22));
		mnSetting.add(mntmExit);
		
		JMenu mnHelp = new JMenu("HELP");
		mnHelp.setIcon(new ImageIcon(mainWindow.class.getResource("/image/help_normal.png")));
		mnHelp.setFont(new Font("SAO UI", Font.BOLD, 26));
		menuBar.add(mnHelp);
		
		
		mntmAbout = new JMenuItem("ABOUT");
		mntmAbout.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				
				setContentPane(new authorJPanel());
				validate();
			}
		});
		mntmAbout.setIcon(new ImageIcon(mainWindow.class.getResource("/image/skills_normal.png")));
		mntmAbout.setFont(new Font("SAO UI", Font.BOLD, 22));
		mnHelp.add(mntmAbout);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		label = new JLabel("New label");
		label.setBounds(201, 184, 334, 247);
		label.setIcon(new ImageIcon(mainWindow.class.getResource("/image/preview.png")));
		contentPane.add(label);
		
		label_1 = new JLabel("");
		label_1.setIcon(new ImageIcon(mainWindow.class.getResource("/image/welcome.png")));
		label_1.setBounds(58, 80, 657, 88);
		contentPane.add(label_1);
		//setContentPane(new tiebaJPanel());
	}
}
