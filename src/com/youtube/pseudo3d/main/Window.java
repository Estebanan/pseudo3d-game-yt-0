package com.youtube.pseudo3d.main;

import java.awt.Toolkit;

import javax.swing.JFrame;

import com.youtube.pseudo3d.util.Constants;

public class Window extends JFrame{

	private static final long serialVersionUID = 1L;

	public Window() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(true);
		
		int winX = Toolkit.getDefaultToolkit().getScreenSize().width / 2 - Constants.WINDOW_WIDTH / 2;
		int winY = Toolkit.getDefaultToolkit().getScreenSize().height / 2 - Constants.WINDOW_HEIGHT / 2;
		setLocation(winX, winY);
		
		setTitle(Constants.TITLE);
		setVisible(true);
	}
}
