package com.mevenk.timestampdateconverter.utility;

import java.awt.Button;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class TimestampDateConverter {

	private JFrame timestampDateConverterFrame;
	private JTextField timestampTextField;
	private JTextField simpleDateFormatTextField;
	private Button toTimestampButton;
	private JTextField dateTextField;
	private JButton copyDateToClipboardButton;

	private final static String lineSeparator = System.lineSeparator();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TimestampDateConverter timestampDateConverterWindow = new TimestampDateConverter();
					timestampDateConverterWindow.timestampDateConverterFrame.setVisible(true);
				} catch (Exception exception) {
					exception.printStackTrace();
					JOptionPane.showMessageDialog(null, exception, "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TimestampDateConverter() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		timestampDateConverterFrame = new JFrame();
		timestampDateConverterFrame.setTitle("Timestamp/Date Converter");
		timestampDateConverterFrame.getContentPane().setEnabled(false);
		timestampDateConverterFrame.setResizable(false);
		timestampDateConverterFrame.setBounds(100, 100, 500, 300);
		timestampDateConverterFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		timestampDateConverterFrame.getContentPane().setLayout(null);

		timestampTextField = new JTextField();
		timestampTextField.setToolTipText("Timestamp in millis");
		timestampTextField.setBounds(10, 135, 150, 40);
		timestampDateConverterFrame.getContentPane().add(timestampTextField);
		timestampTextField.setColumns(10);

		simpleDateFormatTextField = new JTextField();
		simpleDateFormatTextField.setToolTipText("Date + Time  Recommended");
		simpleDateFormatTextField.setColumns(10);
		simpleDateFormatTextField.setBounds(151, 40, 175, 40);
		timestampDateConverterFrame.getContentPane().add(simpleDateFormatTextField);

		Button toDateButton = new Button("To Date  >>");
		toDateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					String simpleDateFormatString = simpleDateFormatTextField.getText();
					String timestampString = timestampTextField.getText();
					if (validInputs(simpleDateFormatString, timestampString)) {

						dateTextField.setText(
								new SimpleDateFormat(simpleDateFormatString).format(Long.parseLong(timestampString)));

					} else {
						JOptionPane.showMessageDialog(timestampDateConverterFrame, "Invalid Inputs !!", "Error", JOptionPane.ERROR_MESSAGE);
					}
				} catch (Exception exception) {
					JOptionPane.showMessageDialog(timestampDateConverterFrame, "Error!!" + lineSeparator, "Error", JOptionPane.ERROR_MESSAGE);
				}

			}

		});
		toDateButton.setBounds(175, 100, 125, 50);
		timestampDateConverterFrame.getContentPane().add(toDateButton);

		toTimestampButton = new Button("<<  To timestamp");
		toTimestampButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					String simpleDateFormatString = simpleDateFormatTextField.getText();
					String dateString = dateTextField.getText();
					if (validInputs(simpleDateFormatString, dateString)) {

						timestampTextField.setText(Long
								.toString(new SimpleDateFormat(simpleDateFormatString).parse(dateString).getTime()));

					} else {
						JOptionPane.showMessageDialog(timestampDateConverterFrame, "Invalid Inputs !!", "Error", JOptionPane.ERROR_MESSAGE);
					}
				} catch (Exception exception) {
					JOptionPane.showMessageDialog(timestampDateConverterFrame, "Error!!" + lineSeparator, "Error", JOptionPane.ERROR_MESSAGE);
				}

			}
		});
		toTimestampButton.setBounds(175, 165, 125, 50);
		timestampDateConverterFrame.getContentPane().add(toTimestampButton);

		dateTextField = new JTextField();
		dateTextField.setToolTipText("Date");
		dateTextField.setColumns(10);
		dateTextField.setBounds(334, 135, 150, 40);
		timestampDateConverterFrame.getContentPane().add(dateTextField);

		JButton copyTimestampToClipboardButton = new JButton("Copy to Clipboard");
		copyTimestampToClipboardButton.setToolTipText("Copy to Clipboard");
		copyTimestampToClipboardButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				StringSelection timestampTextFieldTextStringSelection = new StringSelection(
						timestampTextField.getText());
				Toolkit.getDefaultToolkit().getSystemClipboard().setContents(timestampTextFieldTextStringSelection,
						null);

			}
		});
		copyTimestampToClipboardButton.setBounds(20, 200, 125, 30);
		timestampDateConverterFrame.getContentPane().add(copyTimestampToClipboardButton);

		copyDateToClipboardButton = new JButton("Copy to Clipboard");
		copyDateToClipboardButton.setToolTipText("Copy to Clipboard");
		copyDateToClipboardButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				StringSelection dateTextFieldTextStringSelection = new StringSelection(dateTextField.getText());
				Toolkit.getDefaultToolkit().getSystemClipboard().setContents(dateTextFieldTextStringSelection, null);

			}
		});
		copyDateToClipboardButton.setBounds(345, 200, 125, 30);
		timestampDateConverterFrame.getContentPane().add(copyDateToClipboardButton);

		JLabel labelDateFormat = new JLabel("Date Format");
		labelDateFormat.setHorizontalAlignment(SwingConstants.CENTER);
		labelDateFormat.setBounds(200, 11, 75, 20);
		timestampDateConverterFrame.getContentPane().add(labelDateFormat);

		JLabel lblTimestamp = new JLabel("Timestamp(millis)");
		lblTimestamp.setHorizontalAlignment(SwingConstants.CENTER);
		lblTimestamp.setBounds(25, 100, 110, 20);
		timestampDateConverterFrame.getContentPane().add(lblTimestamp);

		JLabel lblDate = new JLabel("DATE");
		lblDate.setHorizontalAlignment(SwingConstants.CENTER);
		lblDate.setBounds(370, 100, 75, 20);
		timestampDateConverterFrame.getContentPane().add(lblDate);

		Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
		timestampDateConverterFrame.setLocation(screenDimension.width / 2 - timestampDateConverterFrame.getSize().width / 2,
				screenDimension.height / 2 - timestampDateConverterFrame.getSize().height / 2);

	}

	private boolean validInputs(String... strings) {
		for (String currentString : strings) {
			if (currentString.isEmpty())
				return false;
		}
		return true;
	}

}
