package edu.armstrong.robotics;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;

public class Compiler extends JFrame {

	public static ArrayList<String> compile(String text) {
		String[] tokens = text.split("( )|(,)|[(]|[)]|(;)");

		// REMOVE ALL WHITESPACE
		ArrayList<String> temp = new ArrayList<>();
		for (int i = 0; i < tokens.length; i++) {
			if (!tokens[i].isEmpty()) {
				temp.add(tokens[i]);
				System.out.println(tokens[i]);
			}
		}
		tokens = new String[temp.size()];
		for (int i = 0; i < temp.size(); i++)
			tokens[i] = temp.get(i);

		ArrayList<String> result = new ArrayList<>();

		for (int i = 0; i < tokens.length; i++) {

			try {

				if (tokens[i].toLowerCase().contains("point")) {
					result.add(new String("gotoPoint("
							+ Integer.parseInt(tokens[i + 1]) + ", "
							+ Integer.parseInt(tokens[i + 2]) + ");"));
				} else if (tokens[i].toLowerCase().contains("edge")) {
					result.add(new String("gotoEdge("
							+ Integer.parseInt(tokens[i + 1]) + ");"));
				} else if (tokens[i].toLowerCase().contains("corner")) {
					result.add(new String("gotoCorner("
							+ Integer.parseInt(tokens[i + 1]) + ");"));
				} else if (tokens[i].toLowerCase().contains("move")) {
					if (tokens[i + 1].toLowerCase().startsWith("n")) {
						result.add(new String("move(NORTH,"
								+ Integer.parseInt(tokens[i + 2]) + ");"));
					} else if (tokens[i + 1].toLowerCase().startsWith("s")) {
						result.add(new String("move(SOUTH,"
								+ Integer.parseInt(tokens[i + 2]) + ");"));
					} else if (tokens[i + 1].toLowerCase().startsWith("e")) {
						result.add(new String("move(EAST,"
								+ Integer.parseInt(tokens[i + 2]) + ");"));
					} else if (tokens[i + 1].toLowerCase().startsWith("w")) {
						result.add(new String("move(WEST,"
								+ Integer.parseInt(tokens[i + 2]) + ");"));
					} else {
						result.add(new String("move(NORTH,"
								+ Integer.parseInt(tokens[i + 2]) + ");"));
					}
				} else if (tokens[i].toLowerCase().contains("face")) {
					if (tokens[i + 1].toLowerCase().startsWith("n")) {
						result.add(new String("changeDir(NORTH);"));
					} else if (tokens[i + 1].toLowerCase().startsWith("s")) {
						result.add(new String("changeDir(SOUTH);"));
					} else if (tokens[i + 1].toLowerCase().startsWith("e")) {
						result.add(new String("changeDir(EAST);"));
					} else if (tokens[i + 1].toLowerCase().startsWith("w")) {
						result.add(new String("changeDir(WEST);"));
					} else {
						result.add(new String("changeDir(NORTH);"));
					}
				} else {
					// skip
				}
			} catch (NumberFormatException e) {
			}
		}
		return result;
	}

	// TODO: check first to see if the user's syntax is correct
	// if it isn't warn them about it
	//
	// Also perhaps add something that tells them
	// whenever they're going out of bounds

	private String isCorrect(String text) {
		return null;
	}

	public Compiler() {

		super("Robotics Compiler");

		// syn is the user script
		TextArea syn = new TextArea();
		// fin is the compiled script
		TextArea fin = new TextArea();
		fin.setBackground(Color.LIGHT_GRAY);
		fin.setPreferredSize(new Dimension(250, 250));
		fin.setEditable(false);

		Button compile = new Button("Compile");
		compile.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ArrayList<String> a = compile(syn.getText());
				System.out.println(a);
				String result = "// start main code";
				for (String s : a) {
					result = result + "\n" + s;
				}
				fin.setText(result);
			}

		});

		add(BorderLayout.CENTER, syn);
		add(BorderLayout.SOUTH, compile);
		add(BorderLayout.EAST, fin);

	}

	public static void main(String[] args) {

		Compiler com = new Compiler();
		com.setSize(720, 480);
		com.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		com.setLocationRelativeTo(null);
		com.setVisible(true);

	}
}
