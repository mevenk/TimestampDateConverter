package mevenk.timestampdateconverter.utility;

import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.Window.Type;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.border.EtchedBorder;

/**
 * The Class TimestampDateConverter.
 */
public class TimestampDateConverter {

	/** The timestamp date converter frame. */
	private JFrame timestampDateConverterFrame;

	/** The timestamp text field. */
	private JTextField timestampTextField;

	/** The simple date format text field. */
	private JTextField simpleDateFormatTextField;

	/** The to timestamp button. */
	private Button toTimestampButton;

	/** The date text field. */
	private JTextField dateTextField;

	/** The copy date to clipboard button. */
	private JButton copyDateToClipboardButton;

	private RunningClock runningClock = new RunningClock();

	private JPopupMenu contextMenu;
	private JPopupMenu contextMenu2;

	private JLabel lblRunningclock;

	private JLabel labelRunningClockTimestamp;

	/** The Constant lineSeparator. */
	private final static String lineSeparator = System.lineSeparator();

	private static final Date DATE_INITIAL = new Date();

	private static final SimpleDateFormat SIMPLE_DATE_FORMAT_DEFAULT = new SimpleDateFormat(
			"E, dd/MM/yyyy HH:mm:ss,SSS");

	/**
	 * Launch the application.
	 *
	 * @param args
	 *            the arguments
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
		runningClock.execute();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		timestampDateConverterFrame = new JFrame();
		timestampDateConverterFrame.setTitle("Timestamp/Date Converter");
		timestampDateConverterFrame.getContentPane().setEnabled(false);
		timestampDateConverterFrame.setResizable(false);
		timestampDateConverterFrame.setIconImage(Toolkit.getDefaultToolkit()
				.getImage(TimestampDateConverter.class.getResource("/mevenk/image/mevenkGitHubLogo.png")));
		timestampDateConverterFrame.setType(Type.UTILITY);
		timestampDateConverterFrame.setAlwaysOnTop(true);
		timestampDateConverterFrame.setBounds(100, 100, 565, 300);
		timestampDateConverterFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		timestampDateConverterFrame.getContentPane().setLayout(null);

		Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
		timestampDateConverterFrame.setLocation(
				screenDimension.width / 2 - timestampDateConverterFrame.getSize().width / 2,
				screenDimension.height / 2 - timestampDateConverterFrame.getSize().height / 2);

		timestampTextField = new JTextField(String.valueOf(DATE_INITIAL.getTime()));
		timestampTextField.setHorizontalAlignment(SwingConstants.CENTER);
		timestampTextField.setToolTipText("Timestamp in millis");
		timestampTextField.setBounds(10, 135, 175, 40);
		timestampDateConverterFrame.getContentPane().add(timestampTextField);
		timestampTextField.setColumns(10);

		simpleDateFormatTextField = new JTextField(SIMPLE_DATE_FORMAT_DEFAULT.toPattern());
		simpleDateFormatTextField.setHorizontalAlignment(SwingConstants.CENTER);
		simpleDateFormatTextField.setToolTipText("Date + Time  Recommended");
		simpleDateFormatTextField.setColumns(10);
		simpleDateFormatTextField.setBounds(189, 42, 175, 40);
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
						JOptionPane.showMessageDialog(timestampDateConverterFrame, "Invalid Inputs !!", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				} catch (Exception exception) {
					JOptionPane.showMessageDialog(timestampDateConverterFrame, "Error!!" + lineSeparator, "Error",
							JOptionPane.ERROR_MESSAGE);
				}

			}

		});
		toDateButton.setBounds(211, 100, 125, 50);
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
						JOptionPane.showMessageDialog(timestampDateConverterFrame, "Invalid Inputs !!", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				} catch (Exception exception) {
					JOptionPane.showMessageDialog(timestampDateConverterFrame, "Error!!" + lineSeparator, "Error",
							JOptionPane.ERROR_MESSAGE);
				}

			}
		});
		toTimestampButton.setBounds(211, 156, 125, 50);
		timestampDateConverterFrame.getContentPane().add(toTimestampButton);

		dateTextField = new JTextField(SIMPLE_DATE_FORMAT_DEFAULT.format(DATE_INITIAL));
		dateTextField.setHorizontalAlignment(SwingConstants.CENTER);
		dateTextField.setToolTipText("Date");
		dateTextField.setColumns(10);
		dateTextField.setBounds(374, 135, 175, 40);
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
		copyTimestampToClipboardButton.setBounds(25, 200, 150, 30);
		timestampDateConverterFrame.getContentPane().add(copyTimestampToClipboardButton);

		copyDateToClipboardButton = new JButton("Copy to Clipboard");
		copyDateToClipboardButton.setToolTipText("Copy to Clipboard");
		copyDateToClipboardButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				StringSelection dateTextFieldTextStringSelection = new StringSelection(dateTextField.getText());
				Toolkit.getDefaultToolkit().getSystemClipboard().setContents(dateTextFieldTextStringSelection, null);

			}
		});
		copyDateToClipboardButton.setBounds(387, 200, 150, 30);
		timestampDateConverterFrame.getContentPane().add(copyDateToClipboardButton);

		JLabel labelDateFormat = new JLabel("Date Format");
		labelDateFormat.setHorizontalAlignment(SwingConstants.CENTER);
		labelDateFormat.setBounds(230, 11, 75, 20);
		timestampDateConverterFrame.getContentPane().add(labelDateFormat);

		JLabel lblTimestamp = new JLabel("Timestamp(millis)");
		lblTimestamp.setHorizontalAlignment(SwingConstants.CENTER);
		lblTimestamp.setBounds(35, 100, 110, 20);
		timestampDateConverterFrame.getContentPane().add(lblTimestamp);

		JLabel lblDate = new JLabel("DATE");
		lblDate.setHorizontalAlignment(SwingConstants.CENTER);
		lblDate.setBounds(425, 100, 75, 20);
		timestampDateConverterFrame.getContentPane().add(lblDate);

		lblRunningclock = new JLabel("");
		lblRunningclock.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent e) {
				stopRunningClock(lblRunningclock);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				if (contextMenu.isShowing()) {
					return;
				}
				resumeRunningClock(lblRunningclock);
			}
		});
		lblRunningclock.setBounds(370, 25, 175, 40);
		lblRunningclock.setHorizontalAlignment(SwingConstants.CENTER);
		lblRunningclock.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
		lblRunningclock.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		timestampDateConverterFrame.getContentPane().add(lblRunningclock);

		contextMenu = new JPopupMenu();
		addPopup(lblRunningclock, contextMenu);

		JButton copyButton = new JButton("Copy");
		copyButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				String lblRunningclockText = lblRunningclock.getText();
				StringSelection packageNameTextFieldTextStringSelection = new StringSelection(lblRunningclockText);
				Toolkit.getDefaultToolkit().getSystemClipboard().setContents(packageNameTextFieldTextStringSelection,
						null);

				if (contextMenu.isShowing()) {
					runningClock = new RunningClock();
					runningClock.execute();
					lblRunningclock.setBackground(null);
					contextMenu.setVisible(false);
				}
			}
		});
		contextMenu.add(copyButton);

		labelRunningClockTimestamp = new JLabel("");
		labelRunningClockTimestamp.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent e) {
				stopRunningClock(labelRunningClockTimestamp);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				if (contextMenu2.isShowing()) {
					return;
				}
				resumeRunningClock(labelRunningClockTimestamp);
			}
		});
		labelRunningClockTimestamp.setHorizontalAlignment(SwingConstants.CENTER);
		labelRunningClockTimestamp.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
		labelRunningClockTimestamp.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		labelRunningClockTimestamp.setBounds(10, 25, 175, 40);
		timestampDateConverterFrame.getContentPane().add(labelRunningClockTimestamp);

		contextMenu2 = new JPopupMenu();
		addPopup(labelRunningClockTimestamp, contextMenu2);

		JButton copyButton2 = new JButton("Copy");
		copyButton2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				String lblRunningclockText = labelRunningClockTimestamp.getText();
				StringSelection labelRunningClockTimestampTextStringSelection = new StringSelection(
						lblRunningclockText);
				Toolkit.getDefaultToolkit().getSystemClipboard()
						.setContents(labelRunningClockTimestampTextStringSelection, null);

				if (contextMenu2.isShowing()) {
					runningClock = new RunningClock();
					runningClock.execute();
					labelRunningClockTimestamp.setBackground(null);
					contextMenu2.setVisible(false);
				}
			}
		});
		contextMenu2.add(copyButton2);

	}

	private void stopRunningClock(JLabel label) {
		label.setBackground(Color.GRAY);
		runningClock.cancel(true);
	}

	private void resumeRunningClock(JLabel label) {
		runningClock = new RunningClock();
		runningClock.execute();
		label.setBackground(null);
	}

	/**
	 * Valid inputs.
	 *
	 * @param strings
	 *            the strings
	 * @return true, if successful
	 */
	private boolean validInputs(String... strings) {
		for (String currentString : strings) {
			if (currentString.isEmpty())
				return false;
		}
		return true;
	}

	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}

	class RunningClock extends SwingWorker<Void, Void> {

		private final SimpleDateFormat simpleDateFormatRunningClock = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss,SSS");

		boolean keepClockRunning = true;

		@Override
		protected Void doInBackground() throws Exception {
			Date date = new Date();

			while (keepClockRunning) {
				date = new Date();
				lblRunningclock.setText(simpleDateFormatRunningClock.format(date));
				labelRunningClockTimestamp.setText(String.valueOf(date.getTime()));
			}
			return null;
		}

		@Override
		public void done() {
			keepClockRunning = false;
		}

	}
}
