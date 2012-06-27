import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

class TransparentJButton extends JButton {

	private static final long serialVersionUID = 1L;
	private boolean hover, selected;
	private String midiIn, midiOut;
	private boolean hasMidiOut;
	private boolean useSmallIcons;
	
	public TransparentJButton(boolean hasMidiOut, boolean useSmallIcons) {
		super();
		this.hasMidiOut = hasMidiOut;
		this.useSmallIcons = useSmallIcons;
		this.hover = false;
		setOpaque(false);

		addMouseListener(new MouseListener() {
			@Override
			public void mouseExited(MouseEvent arg0) {
				hover = false;
				repaint();
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				hover = true;
				repaint();
			}
			
			@Override
			public void mouseReleased(MouseEvent arg0) {}
			@Override
			public void mousePressed(MouseEvent arg0) {}
			@Override
			public void mouseClicked(MouseEvent arg0) {}
		});
		setContentAreaFilled(false);
		repaint();
	}
	
	
    public void paint(Graphics g) {
    	BufferedImage image = new BufferedImage(getWidth(), getHeight(),
    			BufferedImage.TYPE_INT_ARGB);
    	Graphics2D g2 = image.createGraphics();
    	g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    	g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    	
    	if(selected) {
    		// background
        	g2.setColor(new Color(255, 255, 255, 75));
        	g2.fillRect(0, 0, getWidth(), getHeight());
        	// outline
        	g2.setColor(new Color(255, 255, 255, 255));
        	g2.setStroke(new BasicStroke(3));
        	g2.drawRect(0, 0, getWidth()-1, getHeight()-1);
        }
        else if (hover) {
        	// background
        	g2.setColor(new Color(255, 255, 255, 150));
        	g2.fillRect(0, 0, getWidth(), getHeight());
        	
        	// outline
        	g2.setColor(new Color(255, 255, 255, 100));
        	g2.setStroke(new BasicStroke(3));
        	g2.drawRect(0, 0, getWidth()-1, getHeight()-1);
        	
        	// display midi numbers on hover
//        	g2.setColor(new Color(0, 0, 0));
//        	if (midiIn == null) {
//        		g2.drawString("1", 5, 10);
//        	}
//        	if (midiOut == null) {
//        		g2.drawString("2", 5, 25);
//        	}

        }
    	
    	if (hasMidiOut) {
        	if (midiIn != "-" && midiOut != "-") {
        	// if both are set, put a green dot in the corner
	        	ImageIcon dot;
	        	if (useSmallIcons) {
	        		dot = new ImageIcon(getClass().getResource("/resources/midiInOutArrowIcon20x20.png"));
	        	} else {
	        		dot = new ImageIcon(getClass().getResource("/resources/midiInOutArrowIcon32x32.png"));
	        	}
	        	g2.drawImage(dot.getImage(), getWidth() - dot.getIconWidth(), getHeight() - dot.getIconHeight(),
	        		dot.getIconWidth(), dot.getIconHeight(), null);
        	} else if (midiIn != "-") {
	        	ImageIcon dot;
	        	if (useSmallIcons) {
	        		dot = new ImageIcon(getClass().getResource("/resources/midiInArrowIcon20x20.png"));
	        	} else {
	        		dot = new ImageIcon(getClass().getResource("/resources/midiInArrowIcon32x32.png"));
	        	}
        		g2.drawImage(dot.getImage(), getWidth() - dot.getIconWidth(), getHeight() - dot.getIconHeight(),
        			dot.getIconWidth(), dot.getIconHeight(), null);
        	} else if (midiOut != "-") {
	        	ImageIcon dot;
	        	if (useSmallIcons) {
	        		dot = new ImageIcon(getClass().getResource("/resources/midiOutArrowIcon20x20.png"));
	        	} else {
	        		dot = new ImageIcon(getClass().getResource("/resources/midiOutArrowIcon32x32.png"));
	        	}
        		g2.drawImage(dot.getImage(), getWidth() - dot.getIconWidth(), getHeight() - dot.getIconHeight(),
        				dot.getIconWidth(), dot.getIconHeight(), null);	
        	}
    	} else {
    		if (midiIn != "-") {
	        	ImageIcon dot;
	        	if (useSmallIcons) {
	        		dot = new ImageIcon(getClass().getResource("/resources/midiInArrowIcon20x20.png"));
	        	} else {
	        		dot = new ImageIcon(getClass().getResource("/resources/midiInArrowIcon32x32.png"));
	        	}
        		g2.drawImage(dot.getImage(), getWidth() - dot.getIconWidth(), getHeight() - dot.getIconHeight(),
        				dot.getIconWidth(), dot.getIconHeight(), null);
    		}   
    	}
    	g2.dispose();
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
    }
    
    public void setSelected(boolean selected) {
    	this.selected = selected;
    	repaint();
    }
    
    public void updateMidiValues(String midiIn, String midiOut) {
    	updateMidiInValue(midiIn);
    	updateMidiOutValue(midiOut);
    }
    
    public void updateMidiInValue(String midiIn) {
    	this.midiIn = midiIn;
    	repaint();
    }
    
    public void updateMidiOutValue(String midiOut) {
    	this.midiOut = midiOut;
    	repaint();
    }
    
    public void showCheckmark(boolean visible) {
    	if (visible) {
    		add(new JLabel(new ImageIcon(getClass().getResource("check.png"))));
    	}
    	else {
    		removeAll();
    	}
    }
}
