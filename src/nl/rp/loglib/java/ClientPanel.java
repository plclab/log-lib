package nl.rp.loglib.java;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;

import nl.rp.loglib.Constant;
import nl.rp.loglib.java.TestClient.TestClientListener;

@SuppressWarnings("serial")
public class ClientPanel extends JPanel {

	public static final int COL_TIME = 0;
	public static final int COL_TYPE = 1;
	public static final int COL_GROUP = 2;
	public static final int COL_ID = 3;
	public static final int COL_CHANNEL = 4;
	public static final int COL_TICK = 5;
	public static final int COL_VALUE = 6;

	private final List<Packet> packets = new ArrayList<>();

	private final JTextField ipTextField;
	private final JSpinner portSpinner;
	private final JSpinner readIntervalSpinner;
	private final JButton connectButton;
	private final JButton disconnectButton;
	private final JLabel clientStatusLabel;

	private final PacketsTableModel packetsTableModel;
	private final JTable packetsTable;
	private final JScrollPane packetsTableScrollPane;


	public ClientPanel(LogLibJavaTesterApplication context) {

		final TestClient testClient = context.getTestClient();

		final GridBagLayout layout = new GridBagLayout();
		layout.rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.1, 0.0};
		layout.rowHeights = new int[] {8, 22, 8, 25, 5, 25, 5, 25, 5, 25, 10};
		layout.columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.1, 0.0};
		layout.columnWidths = new int[] {12, 110, 5, 110, 5, 110, 5, 50, 5, 50, 5, 75, 12};
		setLayout(layout);

		ipTextField = new JTextField("127.0.0.1");
		ipTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		add(createLabel("Remote ip"), new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
		add(ipTextField, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

		portSpinner = new JSpinner(new SpinnerNumberModel(5000, 0, 65535, 1));
		portSpinner.setEditor(new JSpinner.NumberEditor(portSpinner, "#"));
		add(createLabel("Port"), new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
		add(portSpinner, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

		readIntervalSpinner = new JSpinner(new SpinnerNumberModel(1000, 1, 60000, 1));
		readIntervalSpinner.setEditor(new JSpinner.NumberEditor(readIntervalSpinner, "#"));
		add(createLabel("Read interval (ms)"), new GridBagConstraints(5, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
		add(readIntervalSpinner, new GridBagConstraints(5, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
		readIntervalSpinner.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				testClient.setSocketReadInterval((int)readIntervalSpinner.getValue());
			}
		});

		connectButton = new JButton("Connect");
		add(connectButton, new GridBagConstraints(7, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
		connectButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				connectButton.setEnabled(false);
				testClient.start();
			}
		});

		disconnectButton = new JButton("Disconnect");
		disconnectButton.setEnabled(false);
		add(disconnectButton, new GridBagConstraints(9, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
		disconnectButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				disconnectButton.setEnabled(false);
				testClient.stop();
			}
		});

		clientStatusLabel = new JLabel();
		add(clientStatusLabel, new GridBagConstraints(11, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 3, 0, 0), 0, 0));

		packetsTableModel = new PacketsTableModel();

		packetsTable = new JTable(packetsTableModel);
		packetsTable.setDefaultRenderer(Packet.class, new PacketsTableCellRenderer());
		packetsTable.setRowHeight(20);
		packetsTable.setShowGrid(false);
		packetsTable.setFillsViewportHeight(true);

		packetsTable.getColumnModel().getColumn(COL_TIME).setPreferredWidth(140);
		packetsTable.getColumnModel().getColumn(COL_TYPE).setPreferredWidth(150);
		packetsTable.getColumnModel().getColumn(COL_GROUP).setPreferredWidth(20);
		packetsTable.getColumnModel().getColumn(COL_ID).setPreferredWidth(20);
		packetsTable.getColumnModel().getColumn(COL_CHANNEL).setPreferredWidth(20);
		packetsTable.getColumnModel().getColumn(COL_TICK).setPreferredWidth(100);
		packetsTable.getColumnModel().getColumn(COL_VALUE).setPreferredWidth(150);

		packetsTable.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE && !packetsTable.isEditing()) {
					packetsTable.clearSelection();
				}
			}
		});

		packetsTableScrollPane = new JScrollPane(packetsTable);
		add(packetsTableScrollPane, new GridBagConstraints(1, 3, 11, 7, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

		testClient.addClientListener(new TestClientListener() {

			@Override
			public void started() {
				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						disconnectButton.setEnabled(true);
						clientStatusLabel.setText("Started");
					}
				});
			}

			@Override
			public void connected() {
				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						clientStatusLabel.setText("Connected");
					}
				});
			}

			@Override
			public void packetReceived(Packet packet) {
				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {

						if (packet != null) {

							final int oldSize = packets.size();
							packets.add(packet);
							packetsTableModel.fireTableRowsInserted(oldSize, packets.size() - 1);

							if (packetsTable.getSelectedRowCount() == 0) {							
								scrollPacketsTableToLastRow();
							}

						} else {
							System.err.println("Received packet is null");
						}

					}
				});
			}

			@Override
			public void disconnected() {
				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						clientStatusLabel.setText("Disconnected");
					}
				});
			}

			@Override
			public void stopped() {
				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						connectButton.setEnabled(true);
						disconnectButton.setEnabled(false);
						clientStatusLabel.setText("Stopped");
					}
				});
			}
		});

	}

	private JLabel createLabel(String text) {
		final JLabel label = new JLabel(text);
		label.setFont(label.getFont().deriveFont(10.0f));
		//label.setForeground(Color.darkGray);
		label.setBorder(BorderFactory.createEmptyBorder(6, 1, 2, 0));
		return label;
	}

	private void scrollPacketsTableToLastRow() {

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {

				final int lastRow = packetsTable.getRowCount() - 1;
				if (lastRow >= 0) {

					final Rectangle rect = packetsTable.getCellRect(lastRow, 0, false);
					if (rect != null) {
						packetsTableScrollPane.getViewport().setViewPosition(rect.getLocation());
					}

				}

			}
		});

	}


	private class PacketsTableModel extends AbstractTableModel {

		@Override
		public boolean isCellEditable(int row, int column) {
			return true;
		}

		@Override
		public int getColumnCount() {
			return 7;
		}

		@Override
		public String getColumnName(int column) {
			switch (column) {
			case COL_TIME: return "Time";
			case COL_TYPE: return "Type";
			case COL_GROUP: return "Group";
			case COL_ID: return "Id";
			case COL_CHANNEL: return "Channel";
			case COL_TICK: return "Tick";
			case COL_VALUE: return "Value";
			default: return super.getColumnName(column);
			}
		}

		@Override
		public int getRowCount() {
			return packets.size();
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			if (rowIndex < packets.size()) {
				return packets.get(rowIndex);			
			} else {
				return null;
			}
		}

		@Override
		public void setValueAt(Object value, int rowIndex, int columnIndex) {
			//Editing is not possible
		}

		@Override
		public Class<?> getColumnClass(int colIndex) {
			return Packet.class;
		}

	}


	private class PacketsTableCellRenderer extends JLabel implements TableCellRenderer {

		public PacketsTableCellRenderer() {
			setOpaque(true);
			setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
		}

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

			setBackground(isSelected? table.getSelectionBackground() : table.getBackground());
			setForeground(isSelected? table.getSelectionForeground() : table.getForeground());

			if (value != null && value instanceof Packet) {

				final Packet packet = (Packet)value;

				switch (column) {

				case COL_TIME:
					setText(new Date(packet.getTime()).toString());
					break;

				case COL_TYPE:
					final Constant constant = Constant.getEventConstantForType(packet.getType());
					if (constant != null) {						
						setText(constant.name());
					} else {
						setText("Unknown..");
					}
					break;

				case COL_GROUP:
					setText(packet.getGroup() == null? "":"" + packet.getGroup());
					break;

				case COL_ID:
					setText(packet.getId() == null? "":"" + packet.getId());
					break;

				case COL_CHANNEL:
					setText(packet.getChannel() == null? "":"" + packet.getChannel());
					break;

				case COL_TICK:
					setText(packet.getTick() == null? "":"" + packet.getTick());
					break;

				case COL_VALUE:
					setText(packet.getValue() == null? "":"" + packet.getValue());
					break;

				default: 
					setText("...");
					break;

				}

			} else {
				setText("...");
			}

			return this;

		}

	}

}
