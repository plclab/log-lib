package nl.rp.loglib.java;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import nl.rp.loglib.Constant;
import nl.rp.loglib.Key;

public class LogLibJavaTesterApplication {

	public static final int UI_REFRESH_INTERVAL = 250;
	public static final int PORT = 5000;

	private final JFrame frame;
	private final LogLibTestServer testServer;
	private final LogLibTestClient testClient;


	public LogLibJavaTesterApplication() {

		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setTitle("log-lib-Java Test Application");
		frame.setSize(1000, 800);

		testServer = new LogLibTestServer(PORT, 100);
		testClient = new LogLibTestClient(PORT);

		final JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		final JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);

		final JMenuItem exitMenuItem = new JMenuItem("Exit");
		fileMenu.add(exitMenuItem);
		exitMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				exit();
			}
		});

		final JPanel contentPanel = new JPanel();
		frame.getContentPane().add(contentPanel);

		final GridBagLayout layout = new GridBagLayout();
		layout.rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.1};
		layout.rowHeights = new int[] {8, 25, 5, 25, 5, 25, 5, 25, 8};
		layout.columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.1};
		layout.columnWidths = new int[] {8, 150, 5, 250, 5, 75, 8};
		contentPanel.setLayout(layout);

		final JComboBox<Constant> evtComboBox = new JComboBox<>();
		contentPanel.add(evtComboBox, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 0, 1, 0), 0, 0));
		for (Constant constant : Constant.values()) {
			if (constant.name().startsWith(Key.EVT.name())) {
				evtComboBox.addItem(constant);
			}
		}

		final JButton addEvtButton = new JButton("Add event");
		contentPanel.add(addEvtButton, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
		addEvtButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				final Object selectedConstant = evtComboBox.getSelectedItem();
				if (selectedConstant != null && selectedConstant instanceof Constant) {
					testServer.createEvent((Constant)selectedConstant);
				}

			}
		});

		final JLabel bufferWritePointerLabel = new JLabel();
		bufferWritePointerLabel.setBorder(BorderFactory.createLineBorder(Color.lightGray));
		contentPanel.add(bufferWritePointerLabel, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

		final JLabel bufferReadPointerLabel = new JLabel();
		bufferReadPointerLabel.setBorder(BorderFactory.createLineBorder(Color.lightGray));
		contentPanel.add(bufferReadPointerLabel, new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

		final JLabel bufferOverflowLabel = new JLabel();
		bufferOverflowLabel.setBorder(BorderFactory.createLineBorder(Color.lightGray));
		contentPanel.add(bufferOverflowLabel, new GridBagConstraints(1, 7, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

		new Timer(UI_REFRESH_INTERVAL, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				bufferWritePointerLabel.setText("  Buffer write pointer: " + testServer.getLogBuffer().getBufferWritePointer());
				bufferReadPointerLabel.setText("  Buffer read pointer: " + testServer.getLogBuffer().getBufferReadPointer());
				bufferOverflowLabel.setText("  Buffer overflow: " + testServer.getLogBuffer().getBufferOverflow());

			}
		}).start();

		frame.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				exit();
			}
		});

		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

	}

	private void exit() {

		testClient.exit();
		testServer.exit();

		System.exit(0);

	}

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {

				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (UnsupportedLookAndFeelException e) {
					e.printStackTrace();
				}

				new LogLibJavaTesterApplication();

			}
		});

	}

}
