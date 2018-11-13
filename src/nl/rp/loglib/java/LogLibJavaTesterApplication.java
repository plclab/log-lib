package nl.rp.loglib.java;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.AbstractBorder;

public class LogLibJavaTesterApplication {

	public static final int UI_REFRESH_INTERVAL = 250;
	public static final int PORT = 5000;

	private final TestServer testServer;
	private final TestClient testClient;

	private final JFrame frame;
	private final ServerPanel serverPanel; 
	private final ClientPanel clientPanel; 


	public LogLibJavaTesterApplication() {

		testServer = new TestServer(PORT, 100);
		testClient = new TestClient(PORT);

		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setTitle("log-lib-Java Test Application");
		frame.setSize(1000, 800);

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
		layout.rowWeights = new double[] {0.0, 0.1, 0.0, 0.1, 0.0};
		layout.rowHeights = new int[] {8, 400, 5, 340, 10};
		layout.columnWeights = new double[] {0.0, 0.1, 0.0};
		layout.columnWidths = new int[] {12, 150, 12};
		contentPanel.setLayout(layout);

		final JTabbedPane topTabbedPane = new JTabbedPane();
		topTabbedPane.setFocusable(false);
		contentPanel.add(topTabbedPane, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

		final JTabbedPane bottomTabbedPane = new JTabbedPane();
		bottomTabbedPane.setFocusable(false);
		contentPanel.add(bottomTabbedPane, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

		serverPanel = new ServerPanel(this);
		topTabbedPane.add("Server", serverPanel);
		//serverPanel.setBorder(BorderFactory.createTitledBorder("Server"));
		//contentPanel.add(serverPanel, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

		clientPanel = new ClientPanel(this);
		bottomTabbedPane.add("Client", clientPanel);
		//clientPanel.setBorder(BorderFactory.createTitledBorder("Client"));
		//contentPanel.add(clientPanel, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

		new Timer(UI_REFRESH_INTERVAL, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				refreshUI();
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

	public TestServer getTestServer() {
		return testServer;
	}

	public TestClient getTestClient() {
		return testClient;
	}

	private void refreshUI() {
		serverPanel.refreshUI();
	}

	private void exit() {

		testClient.stop();
		testServer.stop();

		System.exit(0);

	}

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {

			@SuppressWarnings("serial")
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

				UIManager.put("TitledBorder.border", new AbstractBorder() {

					private final Color lineColor = new Color(220, 220, 220);


					@Override
					public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
						if (g instanceof Graphics2D) {
							final Graphics2D g2d = (Graphics2D)g;
							g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
							g2d.setColor(lineColor);
							g2d.draw(new RoundRectangle2D.Float(x, y, width - 1, height - 1, 4, 4));
						}
					}

					@Override
					public Insets getBorderInsets(Component c) {
						return new Insets(0, 0, 0, 0);
					}

					@Override
					public Insets getBorderInsets(Component c, Insets insets) {
						insets.left = 0;
						insets.top = 0;
						insets.right = 0;
						insets.bottom = 0;
						return insets;
					}

					@Override
					public boolean isBorderOpaque() {
						return true;
					}
				});

				new LogLibJavaTesterApplication();

			}
		});

	}

}
