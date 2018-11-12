package nl.rp.loglib.java;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

import nl.rp.loglib.Key;

@SuppressWarnings("serial")
public class LogBufferPanel extends JPanel {

	public static final int MARGIN_X = 20;
	public static final int MARGIN_Y = 30;
	public static final int PACKET_KEY_RECT_WIDTH = 25;
	public static final int PACKET_KEY_RECT_HEIGHT = 25;
	public static final Color PACKET_KEY_RECT_BORDER_COLOR = new Color(150, 150, 150);
	public static final Color PACKET_KEY_RECT_ALTERNATE_BG_COLOR = new Color(245, 245, 245);
	public static final Color PACKET_KEY_TEXT_COLOR = new Color(50, 50, 50);
	public static final int BUFFER_RECT_Y = 120;
	public static final int BUFFER_RECT_MARGIN_X = 85;
	public static final int BUFFER_RECT_HEIGHT = 15;
	public static final Color BUFFER_RECT_BORDER_COLOR = new Color(150, 150, 150);
	public static final int PREFERRED_HEIGHT = 200;

	private final LogBuffer logBuffer;
	
	private Key[] packetKeys = null;

	private int x;
	private boolean alternateBackground = false;
	private String s;
	private final Rectangle2D rect = new Rectangle2D.Double();
	private final Line2D line = new Line2D.Double();
	private final GeneralPath gp = new GeneralPath();
	private FontMetrics fontMetrics;
	private Rectangle2D fontRect;


	public LogBufferPanel(LogBuffer logBuffer) {
		this.logBuffer = logBuffer;
	}

	public Key[] getPacketKeys() {
		return packetKeys;
	}

	public void setPacketKeys(Key[] packetKeys) {
		this.packetKeys = packetKeys;
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		final Graphics2D g2d = (Graphics2D)g;
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		fontMetrics = g2d.getFontMetrics(g2d.getFont());

		drawPacketKeys(g2d);
		drawBuffer(g2d);

	}

	private void drawPacketKeys(Graphics2D g2d) {

		if (packetKeys != null) {

			alternateBackground = false;

			x = MARGIN_X;
			drawKey(g2d, "SF", x);

			x += PACKET_KEY_RECT_WIDTH;
			drawKey(g2d, "MB", x);

			x += PACKET_KEY_RECT_WIDTH;
			drawKey(g2d, "TP", x);

			for (Key key : packetKeys) {

				switch (key) {

				case GR8:
					drawMultipleKeys(g2d, "GR", 1);
					break;

				case GR16:
					drawMultipleKeys(g2d, "GR", 2);
					break;

				case ID8:
					drawMultipleKeys(g2d, "ID", 1);
					break;

				case ID16:
					drawMultipleKeys(g2d, "ID", 2);
					break;

				case CH8:
					drawMultipleKeys(g2d, "CH", 1);
					break;

				case CH16:
					drawMultipleKeys(g2d, "CH", 2);
					break;

				case TICK32:
					drawMultipleKeys(g2d, "TK", 4);
					break;

				case TICK64:
					drawMultipleKeys(g2d, "TK", 8);
					break;

				case BOOL8:
				case INT8:
				case UINT8:
					drawMultipleKeys(g2d, "VL", 1);
					break;

				case INT16:
				case UINT16:
					drawMultipleKeys(g2d, "VL", 2);
					break;

				case INT32:
				case UINT32:
				case REAL32:
					drawMultipleKeys(g2d, "VL", 4);
					break;

				case INT64:
				case UINT64:
				case REAL64:
					drawMultipleKeys(g2d, "VL", 8);
					break;

				default:
					break;
				}

			}

			x += PACKET_KEY_RECT_WIDTH;
			drawKey(g2d, "EF", x);

		}

	}

	private void drawMultipleKeys(Graphics2D g2d, String key, int repeat) {
		for (int i = 0; i < repeat; i++) {
			x += PACKET_KEY_RECT_WIDTH;
			drawKey(g2d, key, x);
		}
	}

	private void drawKey(Graphics2D g2d, String key, int x) {

		rect.setRect(x, MARGIN_Y, PACKET_KEY_RECT_WIDTH, PACKET_KEY_RECT_HEIGHT);

		if (alternateBackground) {
			g2d.setColor(PACKET_KEY_RECT_ALTERNATE_BG_COLOR);
		} else {
			g2d.setColor(Color.white);
		}
		g2d.fill(rect);

		g2d.setColor(PACKET_KEY_RECT_BORDER_COLOR);
		g2d.draw(rect);

		fontRect = fontMetrics.getStringBounds(key, g2d);
		g2d.setColor(PACKET_KEY_TEXT_COLOR);
		g2d.drawString(key, (int)(x + ((PACKET_KEY_RECT_WIDTH / 2) - (fontRect.getWidth() / 2))) + 1,
				(int)(MARGIN_Y + ((PACKET_KEY_RECT_HEIGHT / 2) - (fontRect.getHeight() / 2)) + fontRect.getHeight()) - 1);

		alternateBackground = !alternateBackground;

	}
	
	private void drawBuffer(Graphics2D g2d) {
		
		rect.setRect(BUFFER_RECT_MARGIN_X, BUFFER_RECT_Y, 
				getWidth() - (2 * BUFFER_RECT_MARGIN_X), BUFFER_RECT_HEIGHT);
		
		g2d.setColor(BUFFER_RECT_BORDER_COLOR);
		g2d.draw(rect);

		g2d.setColor(Color.black);
		
		x = getXForBufferIndex(logBuffer.getBufferWritePointer());
		line.setLine(x, BUFFER_RECT_Y - 32, x, BUFFER_RECT_Y - 9);
		g2d.draw(line);
		drawArrowHead(g2d, x, BUFFER_RECT_Y - 4, false);
		
		s = "Write pointer";
		fontRect = fontMetrics.getStringBounds(s, g2d);
		g2d.drawString(s, (int)(x - fontRect.getWidth() - 5), BUFFER_RECT_Y - 25);

		s = "" + logBuffer.getBufferWritePointer();
		fontRect = fontMetrics.getStringBounds(s, g2d);
		g2d.drawString(s, (int)(x - fontRect.getWidth() - 5), BUFFER_RECT_Y - 12);

		x = getXForBufferIndex(logBuffer.getBufferReadPointer());
		line.setLine(x, BUFFER_RECT_Y + BUFFER_RECT_HEIGHT + 9, x, BUFFER_RECT_Y + BUFFER_RECT_HEIGHT + 32);
		g2d.draw(line);
		drawArrowHead(g2d, x, BUFFER_RECT_Y + BUFFER_RECT_HEIGHT + 4, true);

		s = "Read pointer";
		fontRect = fontMetrics.getStringBounds(s, g2d);
		g2d.drawString(s, (int)(x - fontRect.getWidth() - 5), BUFFER_RECT_Y + BUFFER_RECT_HEIGHT + 32);

		s = "" + logBuffer.getBufferReadPointer();
		fontRect = fontMetrics.getStringBounds(s, g2d);
		g2d.drawString(s, (int)(x - fontRect.getWidth() - 5), BUFFER_RECT_Y + BUFFER_RECT_HEIGHT + 20);

		if (logBuffer.getBufferOverflow() > 0) {
			
			x = getXForBufferIndex(logBuffer.getBufferOverflow());
			line.setLine(x, BUFFER_RECT_Y - 62, x, BUFFER_RECT_Y - 9);
			g2d.draw(line);
			drawArrowHead(g2d, x, BUFFER_RECT_Y - 4, false);
			
			s = "Overflow";
			fontRect = fontMetrics.getStringBounds(s, g2d);
			g2d.drawString(s, (int)(x - fontRect.getWidth() - 5), BUFFER_RECT_Y - 55);

			s = "" + logBuffer.getBufferOverflow();
			fontRect = fontMetrics.getStringBounds(s, g2d);
			g2d.drawString(s, (int)(x - fontRect.getWidth() - 5), BUFFER_RECT_Y - 42);

		}

	}
	
	private int getXForBufferIndex(int bufferIndex) {
		if (logBuffer.getBufferLength() > 0) {
			return BUFFER_RECT_MARGIN_X + 
					(int)(bufferIndex * ((getWidth() - (2 * BUFFER_RECT_MARGIN_X)) / (double)logBuffer.getBufferLength()));
		} else {
			return -1;
		}
		
	}
	
	private void drawArrowHead(Graphics2D g2d, int x, int y, boolean up) {
		
		gp.reset();
		gp.moveTo(x, y);
		
		if (up) {
			gp.lineTo(x - 2, y + 6);
			gp.lineTo(x + 3, y + 6);
			gp.lineTo(x + 1, y);
			gp.closePath();
		} else {
			gp.lineTo(x - 2, y - 6);
			gp.lineTo(x + 3, y - 6);
			gp.lineTo(x + 1, y);
			gp.closePath();
		}
		
		g2d.fill(gp);
		
	}

	@Override
	public Dimension getPreferredSize() {
		final Dimension preferredSize = super.getPreferredSize();
		preferredSize.height = PREFERRED_HEIGHT;
		return preferredSize;
	}
	
}
