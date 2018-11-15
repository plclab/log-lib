package nl.rp.loglib.java;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import nl.rp.loglib.Constant;
import nl.rp.loglib.DataType;
import nl.rp.loglib.Key;
import nl.rp.loglib.java.TestServer.TestServerListener;

@SuppressWarnings("serial")
public class ServerPanel extends JPanel {

	public static final Iterable<Constant> AVAILABLE_CONSTANTS = Constant.CORE_EVENTS_DEFAULT;

	private final TestServer testServer;

	private final JSpinner portSpinner;
	private final JSpinner sendIntervalSpinner;
	private final JButton startButton;
	private final JButton stopButton;
	private final JLabel serverStatusLabel;
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
		portSpinner.setEditor(new JSpinner.NumberEditor(portSpinner, "#"));
		add(createLabel("Port"), new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 4, 0, 0), 0, 0));
		add(portSpinner, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 4, 0, 0), 0, 0));

		sendIntervalSpinner = new JSpinner(new SpinnerNumberModel(1000, 1, 60000, 1));
		sendIntervalSpinner.setEditor(new JSpinner.NumberEditor(sendIntervalSpinner, "#"));
		add(createLabel("Send interval (ms)"), new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
		add(sendIntervalSpinner, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
		sendIntervalSpinner.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				testServer.setBufferReadInterval((int)sendIntervalSpinner.getValue());
			}
		});

		startButton = new JButton("Start");
		add(startButton, new GridBagConstraints(5, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
		startButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				startButton.setEnabled(false);
				testServer.start();
			}
		});

		stopButton = new JButton("Stop");
		stopButton.setEnabled(false);
		add(stopButton, new GridBagConstraints(7, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
		stopButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				stopButton.setEnabled(false);
				testServer.stop();
			}
		});

		serverStatusLabel = new JLabel();
		add(serverStatusLabel, new GridBagConstraints(9, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 3, 0, 0), 0, 0));

		eventPanel = new EventPanel();
		eventPanel.setBorder(BorderFactory.createTitledBorder("Event"));
		add(eventPanel, new GridBagConstraints(1, 3, 11, 5, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

		logBufferPanel = new LogBufferPanel(testServer.getLogBuffer());
		logBufferPanel.setBorder(BorderFactory.createTitledBorder("Buffer"));
		add(logBufferPanel, new GridBagConstraints(1, 9, 11, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

		testServer.addServerListener(new TestServerListener() {

			@Override
			public void started() {
				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						stopButton.setEnabled(true);
						serverStatusLabel.setText("Started");
					}
				});
			}

			@Override
			public void clientConnected() {
				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						serverStatusLabel.setText("Connected");
					}
				});
			}

			@Override
			public void clientDisconnected() {
				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						serverStatusLabel.setText("Disconnected");
					}
				});
			}

			@Override
			public void stopped() {
				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						startButton.setEnabled(true);
						stopButton.setEnabled(false);
						serverStatusLabel.setText("Stopped");
					}
				});
			}
		});

		eventPanel.updateValueComponents();
		eventPanel.updateSelectedEvent();

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

		if (eventPanel.isAutoUpdateTickEnabled()) {
			eventPanel.autoUpdateTick();
		}

		if (eventPanel.isRandomValueEnabled()) {
			eventPanel.updateRandomValue();
		}

	}

	private class EventPanel extends JPanel {

		private final JCheckBox groupCheckBox;
		private final JSpinner groupSpinner;
		private final JCheckBox idCheckBox;
		private final JSpinner idSpinner;
		private final JCheckBox channelCheckBox;
		private final JSpinner channelSpinner;
		private final JCheckBox tickCheckBox;
		private final JComboBox<DataType> tickDataTypeComboBox;
		private final JSpinner tickSpinner;
		private final JCheckBox autoUpdateTickCheckBox;
		private final JCheckBox valueCheckBox;
		private final JComboBox<DataType> valueDataTypeComboBox;
		private final JComboBox<Boolean> booleanValuesComboBox;
		private final JSpinner integerValuesSpinner;
		private final JSpinner floatValuesSpinner;
		private final JTextField stringValuesTextField;
		private final JCheckBox randomValuesCheckBox;
		private final JComboBox<String> eventListComboBox;
		private final JComboBox<String> eventComboBox;
		private final JButton addEventButton;

		private long autoUpdateTickStart = System.currentTimeMillis();

		private final List<JComponent> activeValueComponents = new ArrayList<>();

		private final Random random = new Random();


		public EventPanel() {

			final GridBagLayout layout = new GridBagLayout();
			layout.rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.1, 0.0};
			layout.rowHeights = new int[] {8, 22, 5, 22, 5, 22, 8};
			layout.columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.1, 0.0, 0.0};
			layout.columnWidths = new int[] {8, 75, 5, 115, 5, 85, 5, 115, 5, 250, 5, 0, 100, 8};
			setLayout(layout);

			groupCheckBox = createLabelCheckBox("Group");
			add(groupCheckBox, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
			groupCheckBox.addItemListener(new ItemListener() {

				@Override
				public void itemStateChanged(ItemEvent e) {
					groupSpinner.setEnabled(groupCheckBox.isSelected());
					updateSelectedEvent();
				}
			});

			groupSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 65535, 1));
			groupSpinner.setEditor(new JSpinner.NumberEditor(groupSpinner, "#"));
			add(groupSpinner, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
			groupSpinner.addChangeListener(new ChangeListener() {

				@Override
				public void stateChanged(ChangeEvent e) {
					updateSelectedEvent();
				}
			});

			idCheckBox = createLabelCheckBox("Id");
			add(idCheckBox, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
			idCheckBox.addItemListener(new ItemListener() {

				@Override
				public void itemStateChanged(ItemEvent e) {
					idSpinner.setEnabled(idCheckBox.isSelected());
					if (!idCheckBox.isSelected()) {
						groupCheckBox.setSelected(false);
					}
					updateSelectedEvent();
				}
			});

			idSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 65535, 1));
			idSpinner.setEditor(new JSpinner.NumberEditor(idSpinner, "#"));
			add(idSpinner, new GridBagConstraints(3, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
			idSpinner.addChangeListener(new ChangeListener() {

				@Override
				public void stateChanged(ChangeEvent e) {
					updateSelectedEvent();
				}
			});

			channelCheckBox = createLabelCheckBox("Channel");
			add(channelCheckBox, new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
			channelCheckBox.addItemListener(new ItemListener() {

				@Override
				public void itemStateChanged(ItemEvent e) {
					channelSpinner.setEnabled(channelCheckBox.isSelected());
					if (!channelCheckBox.isSelected()) {
						groupCheckBox.setSelected(false);
						idCheckBox.setSelected(false);
						valueCheckBox.setSelected(false);
					}
					updateSelectedEvent();
				}
			});

			channelSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 65535, 1));
			channelSpinner.setEditor(new JSpinner.NumberEditor(channelSpinner, "#"));
			add(channelSpinner, new GridBagConstraints(3, 5, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
			channelSpinner.addChangeListener(new ChangeListener() {

				@Override
				public void stateChanged(ChangeEvent e) {
					updateSelectedEvent();
				}
			});

			tickCheckBox = createLabelCheckBox("Tick");
			add(tickCheckBox, new GridBagConstraints(5, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
			tickCheckBox.addItemListener(new ItemListener() {

				@Override
				public void itemStateChanged(ItemEvent e) {
					tickDataTypeComboBox.setEnabled(tickCheckBox.isSelected());
					tickSpinner.setEnabled(tickCheckBox.isSelected());
					autoUpdateTickCheckBox.setEnabled(tickCheckBox.isSelected());
					updateSelectedEvent();
				}
			});

			tickDataTypeComboBox = new JComboBox<>();
			add(tickDataTypeComboBox, new GridBagConstraints(7, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
			tickDataTypeComboBox.addItem(DataType.INT32);  //TODO? (Protocol doesn't know if it's signed/unsigned)
			tickDataTypeComboBox.addItem(DataType.UINT32); //TODO? (Protocol doesn't know if it's signed/unsigned)
			tickDataTypeComboBox.addItem(DataType.INT64);  //TODO? (Protocol doesn't know if it's signed/unsigned)
			tickDataTypeComboBox.addItem(DataType.UINT64); //TODO? (Protocol doesn't know if it's signed/unsigned)
			tickDataTypeComboBox.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					updateSelectedEvent();	
				}
			});

			tickSpinner = new JSpinner();
			tickSpinner.setEditor(new JSpinner.NumberEditor(tickSpinner, "#"));
			add(tickSpinner, new GridBagConstraints(9, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

			autoUpdateTickCheckBox = new JCheckBox("Auto update");
			autoUpdateTickCheckBox.setSelected(true);
			add(autoUpdateTickCheckBox, new GridBagConstraints(11, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
			autoUpdateTickCheckBox.addItemListener(new ItemListener() {

				@Override
				public void itemStateChanged(ItemEvent e) {
					if (autoUpdateTickCheckBox.isSelected()) {
						autoUpdateTickStart = System.currentTimeMillis();
					}
				}
			});

			valueCheckBox = createLabelCheckBox("Value");
			add(valueCheckBox, new GridBagConstraints(5, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
			valueCheckBox.addItemListener(new ItemListener() {

				@Override
				public void itemStateChanged(ItemEvent e) {
					valueDataTypeComboBox.setEnabled(valueCheckBox.isSelected());
					booleanValuesComboBox.setEnabled(valueCheckBox.isSelected());
					integerValuesSpinner.setEnabled(valueCheckBox.isSelected());
					floatValuesSpinner.setEnabled(valueCheckBox.isSelected());
					stringValuesTextField.setEnabled(valueCheckBox.isSelected());
					randomValuesCheckBox.setEnabled(valueCheckBox.isSelected());
					if (!valueCheckBox.isSelected()) {
						groupCheckBox.setSelected(false);
						idCheckBox.setSelected(false);
						channelCheckBox.setSelected(false);
					}
					updateSelectedEvent();
				}
			});

			valueDataTypeComboBox = new JComboBox<>();
			add(valueDataTypeComboBox, new GridBagConstraints(7, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
			valueDataTypeComboBox.addItem(DataType.BOOL8);
			valueDataTypeComboBox.addItem(DataType.INT8);
			valueDataTypeComboBox.addItem(DataType.UINT8);
			valueDataTypeComboBox.addItem(DataType.INT16);
			valueDataTypeComboBox.addItem(DataType.UINT16);
			valueDataTypeComboBox.addItem(DataType.INT32);
			valueDataTypeComboBox.addItem(DataType.UINT32);
			valueDataTypeComboBox.addItem(DataType.REAL32);
			valueDataTypeComboBox.addItem(DataType.INT64);
			valueDataTypeComboBox.addItem(DataType.UINT64);
			valueDataTypeComboBox.addItem(DataType.REAL64);
			valueDataTypeComboBox.addItem(DataType.STRING);
			valueDataTypeComboBox.setSelectedItem(DataType.INT32);
			valueDataTypeComboBox.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					updateValueComponents();
					updateSelectedEvent();	
				}
			});

			booleanValuesComboBox = new JComboBox<>();
			booleanValuesComboBox.addItem(Boolean.FALSE);
			booleanValuesComboBox.addItem(Boolean.TRUE);

			integerValuesSpinner = new JSpinner();
			integerValuesSpinner.setEditor(new JSpinner.NumberEditor(integerValuesSpinner, "#"));

			floatValuesSpinner = new JSpinner(new SpinnerNumberModel(0.0, null, null, 0.001));
			final JSpinner.NumberEditor floatValuesSpinnerEditor = new JSpinner.NumberEditor(floatValuesSpinner);
			floatValuesSpinnerEditor.getFormat().setGroupingUsed(false);
			floatValuesSpinnerEditor.getFormat().setMinimumFractionDigits(3);
			floatValuesSpinner.setEditor(floatValuesSpinnerEditor);

			stringValuesTextField = new JTextField();

			randomValuesCheckBox = new JCheckBox("Random values");
			randomValuesCheckBox.setSelected(true);

			add(new JLabel("Lib, Event"), new GridBagConstraints(5, 5, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.VERTICAL, new Insets(0, 0, 0, 3), 0, 0));

			//TODO
			eventListComboBox = new JComboBox<>();
			add(eventListComboBox, new GridBagConstraints(7, 5, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 0, 1, 0), 0, 0));
			eventListComboBox.addItem("core-min");
			eventListComboBox.addItem("core");
			eventListComboBox.addItem("core-ext");
			eventListComboBox.addItem("core-ext-notick");
			eventListComboBox.addItem("core-ext-tick32");
			eventListComboBox.setSelectedIndex(1);

			eventComboBox = new JComboBox<>();
			add(eventComboBox, new GridBagConstraints(9, 5, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 0, 1, 0), 0, 0));
			eventComboBox.addItem("Unknown event..");
			for (Constant constant : AVAILABLE_CONSTANTS) {
				if (constant.name().startsWith(Key.EVT.name())) {
					eventComboBox.addItem(constant.name());
				}
			}
			eventComboBox.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					final Constant selectedConstant = getSelectedEvent();
					if (selectedConstant != null) {
						logBufferPanel.setPacketKeys(Key.stringToKeys(selectedConstant.name()));
						refreshUI();
					}

				}
			});

			addEventButton = new JButton("Add event");
			add(addEventButton, new GridBagConstraints(12, 5, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
			addEventButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					final Constant selectedConstant = getSelectedEvent();
					if (selectedConstant != null) {

						testServer.createEvent(selectedConstant,
								getSelectedGroup(),
								getSelectedId(),
								getSelectedChannel(),
								getSelectedTick(),
								getSelectedValue());

						refreshUI();

					}

				}
			});

		}

		private JCheckBox createLabelCheckBox(String text) {

			final JCheckBox checkBox = new JCheckBox(text);
			//checkBox.setFont(checkBox.getFont().deriveFont(10.0f));
			//checkBox.setForeground(Color.darkGray);
			//checkBox.setBorder(BorderFactory.createEmptyBorder(12, 0, 2, 0));
			checkBox.setSelected(true);
			checkBox.setHorizontalTextPosition(SwingConstants.LEFT);
			checkBox.setHorizontalAlignment(SwingConstants.RIGHT);

			return checkBox;

		}

		private boolean isAutoUpdateTickEnabled() {
			return autoUpdateTickCheckBox.isEnabled() && autoUpdateTickCheckBox.isSelected();
		}

		private void autoUpdateTick() {
			tickSpinner.setValue(System.currentTimeMillis() - autoUpdateTickStart);
		}

		private boolean isRandomValueEnabled() {
			return randomValuesCheckBox.isEnabled() && randomValuesCheckBox.isSelected();
		}

		private void updateRandomValue() {

			final DataType valueDataType = getSelectedValueDataType();
			if (valueDataType != null) {

				switch (valueDataType) {

				case BOOL8:
					booleanValuesComboBox.setSelectedItem(random.nextBoolean());
					break;

				case INT8:
					integerValuesSpinner.setValue(Byte.MIN_VALUE + random.nextInt((int)Math.pow(2, 8)));
					break;

				case UINT8:
					integerValuesSpinner.setValue(random.nextInt((int)Math.pow(2, 8)));
					break;

				case INT16:
					integerValuesSpinner.setValue(Short.MIN_VALUE + random.nextInt((int)Math.pow(2, 16)));
					break;

				case UINT16:
					integerValuesSpinner.setValue(random.nextInt((int)Math.pow(2, 16)));
					break;

				case INT32:
					integerValuesSpinner.setValue(Integer.MIN_VALUE + (2 * random.nextInt((int)Math.pow(2, 31))));
					break;

				case UINT32:
					integerValuesSpinner.setValue(2 * (long)random.nextInt((int)Math.pow(2, 31)));
					break;

				case INT64:
					integerValuesSpinner.setValue(random.nextLong());
					break;

				case UINT64:
					integerValuesSpinner.setValue(0); //TODO
					break;

				case REAL32:
				case REAL64:
					floatValuesSpinner.setValue((random.nextDouble() * random.nextInt(Integer.MAX_VALUE)) + random.nextDouble()); //TODO: Handle DataType bounds
					break;

				case STRING:
					stringValuesTextField.setText(UUID.randomUUID().toString());
					break;

				default:
					break;

				}

			}

		}

		private void updateValueComponents() {

			for (JComponent component : activeValueComponents) {
				remove(component);
			}
			activeValueComponents.clear();

			final DataType valueDataType = getSelectedValueDataType();
			if (valueDataType != null) {

				switch (valueDataType) {

				case BOOL8:
					add(booleanValuesComboBox, new GridBagConstraints(9, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
					add(randomValuesCheckBox, new GridBagConstraints(11, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
					activeValueComponents.add(booleanValuesComboBox);
					activeValueComponents.add(randomValuesCheckBox);
					break;

				case INT8:
				case UINT8:
				case INT16:
				case UINT16:
				case INT32:
				case UINT32:
				case INT64:
				case UINT64:
					add(integerValuesSpinner, new GridBagConstraints(9, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
					add(randomValuesCheckBox, new GridBagConstraints(11, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
					activeValueComponents.add(integerValuesSpinner);
					activeValueComponents.add(randomValuesCheckBox);
					break;

				case REAL32:
				case REAL64:
					add(floatValuesSpinner, new GridBagConstraints(9, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
					add(randomValuesCheckBox, new GridBagConstraints(11, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
					activeValueComponents.add(floatValuesSpinner);
					activeValueComponents.add(randomValuesCheckBox);
					break;

				case STRING:
					add(stringValuesTextField, new GridBagConstraints(9, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
					add(randomValuesCheckBox, new GridBagConstraints(11, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
					activeValueComponents.add(stringValuesTextField);
					activeValueComponents.add(randomValuesCheckBox);
					break;

				default:
					break;

				}

			}

			revalidate();
			repaint();

		}

		private DataType getSelectedGroupDataType() {
			if (groupSpinner.isEnabled()) {
				if ((int)groupSpinner.getValue() < 256) {
					return DataType.UINT8;
				} else if ((int)groupSpinner.getValue() < 65536) {
					return DataType.UINT16;
				}
			}
			return null;
		}

		private Integer getSelectedGroup() {
			if (groupSpinner.isEnabled()) {
				return ((Number)groupSpinner.getValue()).intValue();
			}
			return null;
		}

		private DataType getSelectedIdDataType() {
			if (idSpinner.isEnabled()) {
				if ((int)idSpinner.getValue() < 256) {
					return DataType.UINT8;
				} else if ((int)idSpinner.getValue() < 65536) {
					return DataType.UINT16;
				}
			}
			return null;
		}

		private Integer getSelectedId() {
			if (idSpinner.isEnabled()) {
				return ((Number)idSpinner.getValue()).intValue();
			}
			return null;
		}

		private DataType getSelectedChannelDataType() {
			if (channelSpinner.isEnabled()) {
				if ((int)channelSpinner.getValue() < 256) {
					return DataType.UINT8;
				} else if ((int)channelSpinner.getValue() < 65536) {
					return DataType.UINT16;
				}
			}
			return null;
		}

		private Integer getSelectedChannel() {
			if (channelSpinner.isEnabled()) {
				return ((Number)channelSpinner.getValue()).intValue();
			}
			return null;
		}

		private DataType getSelectedTickDataType() {
			if (tickDataTypeComboBox.isEnabled()) {
				final Object selectedTickDataType = tickDataTypeComboBox.getSelectedItem();
				if (selectedTickDataType != null && selectedTickDataType instanceof DataType) {
					return (DataType)selectedTickDataType;
				}
			}
			return null;
		}

		private Long getSelectedTick() {
			if (tickSpinner.isEnabled()) {
				return ((Number)tickSpinner.getValue()).longValue();
			}
			return null;
		}

		private DataType getSelectedValueDataType() {
			if (valueDataTypeComboBox.isEnabled()) {
				final Object selectedValueDataType = valueDataTypeComboBox.getSelectedItem();
				if (selectedValueDataType != null && selectedValueDataType instanceof DataType) {
					return (DataType)selectedValueDataType;
				}
			}
			return null;
		}

		private Object getSelectedValue() {

			if (valueCheckBox.isSelected()) {

				final DataType valueDataType = getSelectedValueDataType();
				if (valueDataType != null) {

					switch (valueDataType) {

					case BOOL8:
						return booleanValuesComboBox.getSelectedItem();

					case INT8:
					case UINT8:
					case INT16:
					case UINT16:
					case INT32:
					case UINT32:
					case INT64:
					case UINT64:
						return integerValuesSpinner.getValue();

					case REAL32:
					case REAL64:
						return floatValuesSpinner.getValue();

					case STRING:
						return stringValuesTextField.getText();

					default:
						break;

					}

				}

			}

			return null;

		}

		private Constant getSelectedEvent() {
			final Object selectedConstant = eventComboBox.getSelectedItem();
			if (selectedConstant != null && selectedConstant instanceof String) {
				return Constant.getConstantForName((String)selectedConstant);
			} else {
				return null;
			}
		}

		private void updateSelectedEvent() {

			final Constant eventConstant = getEventConstant(
					getSelectedGroupDataType(),
					getSelectedIdDataType(),
					getSelectedChannelDataType(),
					getSelectedTickDataType(),
					getSelectedValueDataType());

			if (eventConstant != null) {
				eventComboBox.setSelectedItem(eventConstant.name());
			} else {
				eventComboBox.setSelectedIndex(0);
			}

			addEventButton.setEnabled(eventComboBox.getSelectedIndex() > 0);

		}

		private Constant getEventConstant(DataType groupDataType, DataType idDataType, DataType channelDataType, DataType tickDataType, DataType valueDataType) {

			Key groupKey = null;
			if (groupDataType != null) {
				if (groupDataType == DataType.UINT8) {
					groupKey = Key.GR8;
				} else if (groupDataType == DataType.UINT16) {
					groupKey = Key.GR16;
				}
			}

			Key idKey = null;
			if (idDataType != null) {
				if (idDataType == DataType.UINT8) {
					idKey = Key.ID8;
				} else if (idDataType == DataType.UINT16) {
					idKey = Key.ID16;
				}
			}

			Key channelKey = null;
			if (channelDataType != null) {
				if (channelDataType == DataType.UINT8) {
					channelKey = Key.CH8;
				} else if (channelDataType == DataType.UINT16) {
					channelKey = Key.CH16;
				}
			}

			Key tickKey = null;
			if (tickDataType != null) {
				if (tickDataType == DataType.UINT32 || tickDataType == DataType.INT32) {
					tickKey = Key.TICK32;
				} else if (tickDataType == DataType.UINT64 || tickDataType == DataType.INT64) {
					tickKey = Key.TICK64;
				}
			}

			Key valueKey = null;
			if (valueDataType != null) {
				valueKey = Key.getKeyForName(valueDataType.name());
			}

			Constant constant = getEventConstant(groupKey, idKey, channelKey, tickKey, valueKey);

			if (constant == null && channelKey == Key.CH8) {
				channelKey = Key.CH16;
				constant = getEventConstant(groupKey, idKey, channelKey, tickKey, valueKey);
			}

			if (constant == null && idKey == Key.ID8) {
				idKey = Key.ID16;
				constant = getEventConstant(groupKey, idKey, channelKey, tickKey, valueKey);
			}

			if (constant == null && groupKey == Key.GR8) {
				groupKey = Key.GR16;
				constant = getEventConstant(groupKey, idKey, channelKey, tickKey, valueKey);
			}

			return constant;

		}

		private Constant getEventConstant(Key groupKey, Key idKey, Key channelKey, Key tickKey, Key valueKey) {

			String constantName = "EVT";

			if (groupKey != null) constantName += "_" + groupKey.name();
			if (idKey != null) constantName += "_" + idKey.name();
			if (channelKey != null) constantName += "_" + channelKey.name();
			if (tickKey != null) constantName += "_" + tickKey.name();
			if (valueKey != null) constantName += "_" + valueKey.name();

			return findAvailableConstantForName(constantName);

		}

		private Constant findAvailableConstantForName(String name) {

			if (name == null || name.length() == 0) {
				return null;
			}

			for (Constant constant : AVAILABLE_CONSTANTS) {
				if (name.equals(constant.name())) {
					return constant;
				}
			}

			return null;

		}

	}

}
