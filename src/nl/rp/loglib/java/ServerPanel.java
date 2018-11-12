package nl.rp.loglib.java;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

import nl.rp.loglib.Constant;
import nl.rp.loglib.Key;

@SuppressWarnings("serial")
public class ServerPanel extends JPanel {

	private final TestServer testServer;

	private final JSpinner portSpinner;
	private final JSpinner sendIntervalSpinner;
	private final JButton startButton;
	private final JButton stopButton;
	private final EventPanel eventPanel;
	private final LogBufferPanel logBufferPanel;



	public ServerPanel(LogLibJavaTesterApplication context) {

		testServer = context.getTestServer();

		final GridBagLayout layout = new GridBagLayout();
		layout.rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.1, 0.0};
		layout.rowHeights = new int[] {8, 22, 5, 22, 5, 22, 5, 22, 5, 22, 8};
		layout.columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.1, 0.0};
		layout.columnWidths = new int[] {8, 114, 5, 110, 5, 50, 5, 50, 5, 100, 5, 100, 8};
		setLayout(layout);

		portSpinner = new JSpinner(new SpinnerNumberModel(5000, 0, 65535, 1));
		add(createLabel("Port"), new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 4, 0, 0), 0, 0));
		add(portSpinner, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 4, 0, 0), 0, 0));

		sendIntervalSpinner = new JSpinner(new SpinnerNumberModel(1000, 1, 60000, 1));
		add(createLabel("Send interval (ms)"), new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
		add(sendIntervalSpinner, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

		startButton = new JButton("Start");
		add(startButton, new GridBagConstraints(5, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

		stopButton = new JButton("Stop");
		add(stopButton, new GridBagConstraints(7, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

		eventPanel = new EventPanel();
		eventPanel.setBorder(BorderFactory.createTitledBorder("Event"));
		add(eventPanel, new GridBagConstraints(1, 3, 11, 5, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

		logBufferPanel = new LogBufferPanel(testServer.getLogBuffer());
		logBufferPanel.setBorder(BorderFactory.createTitledBorder("Buffer"));
		add(logBufferPanel, new GridBagConstraints(1, 9, 11, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

		eventPanel.evtComboBox.setSelectedIndex(0);

	}

	private JLabel createLabel(String text) {
		final JLabel label = new JLabel(text);
		label.setFont(label.getFont().deriveFont(10.0f));
		//label.setForeground(Color.darkGray);
		//label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setBorder(BorderFactory.createEmptyBorder(6, 1, 2, 0));
		//label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 3));
		return label;
	}

	protected void refreshUI() {

		logBufferPanel.repaint();

	}

	private class EventPanel extends JPanel {

		private final JSpinner groupSpinner;
		private final JSpinner idSpinner;
		private final JSpinner channelSpinner;

		private final JComboBox<Constant> evtComboBox;


		public EventPanel() {

			final GridBagLayout layout = new GridBagLayout();
			layout.rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.1, 0.0};
			layout.rowHeights = new int[] {8, 22, 5, 22, 5, 22, 8};
			layout.columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.1, 0.0};
			layout.columnWidths = new int[] {8, 75, 5, 100, 5, 100, 5, 100, 5, 100, 5, 100, 8};
			setLayout(layout);

			groupSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 65535, 1));
			add(createLabelCheckBox("Group", groupSpinner), new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
			add(groupSpinner, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

			idSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 65535, 1));
			add(createLabelCheckBox("Id", idSpinner), new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
			add(idSpinner, new GridBagConstraints(3, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

			channelSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 65535, 1));
			add(createLabelCheckBox("Channel", channelSpinner), new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
			add(channelSpinner, new GridBagConstraints(3, 5, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

			evtComboBox = new JComboBox<>();
			add(evtComboBox, new GridBagConstraints(9, 5, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 0, 1, 0), 0, 0));
			for (Constant constant : Constant.values()) {
				if (constant.name().startsWith(Key.EVT.name())) {
					evtComboBox.addItem(constant);
				}
			}
			evtComboBox.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					final Constant selectedConstant = getSelectedConstant();
					if (selectedConstant != null) {
						logBufferPanel.setPacketKeys(Key.stringToKeys(selectedConstant.name()));
						refreshUI();
					}

				}
			});

			final JButton addEvtButton = new JButton("Add event");
			add(addEvtButton, new GridBagConstraints(7, 5, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
			addEvtButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					final Constant selectedConstant = getSelectedConstant();
					if (selectedConstant != null) {
						testServer.createEvent(selectedConstant);
						refreshUI();
					}

				}
			});

		}

		private JCheckBox createLabelCheckBox(String text, JComponent component) {

			final JCheckBox checkBox = new JCheckBox(text);
			//checkBox.setFont(checkBox.getFont().deriveFont(10.0f));
			//checkBox.setForeground(Color.darkGray);
			//checkBox.setBorder(BorderFactory.createEmptyBorder(12, 0, 2, 0));
			checkBox.setSelected(true);
			checkBox.setHorizontalTextPosition(SwingConstants.LEFT);
			checkBox.setHorizontalAlignment(SwingConstants.RIGHT);
			checkBox.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					component.setEnabled(checkBox.isSelected());
				}
			});

			return checkBox;

		}

		private Constant getSelectedConstant() {

			final Object selectedConstant = evtComboBox.getSelectedItem();
			if (selectedConstant != null && selectedConstant instanceof Constant) {
				return (Constant)selectedConstant;
			} else {
				return null;
			}

		}

	}

}
