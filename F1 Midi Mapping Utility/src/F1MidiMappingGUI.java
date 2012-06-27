import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;


public class F1MidiMappingGUI extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6763481544625098200L;

	private JFrame frame;
	
	//UI elements
	private Control selectedControl = null;
	private JComboBox comboBoxMidiIn = null;
	private JComboBox comboBoxMidiOut = null;
	private JRadioButton radioButtonBaseLayer  = null;
	private JRadioButton radioButtonShiftLayer  = null;
	private Boolean previousBaseLayer = null;

	//data model and states
	private final F1MidiMapModel model = new F1MidiMapModel();
	private Boolean baseLayer = true;
	
	static JFileChooser fileChooser = new JFileChooser();

	private TransparentJButton selectedJButton = null;
	
	private final int ITEM_SPACING = 10;
	private final int IMAGE_HEIGHT = 600;
	private final int IMAGE_WIDTH = 246;
	
	private final String[] midiControlMsgs = {"-",
			"0","1","2","3","4","5","6","7","8","9",
			"10","11","12","13","14","15","16","17","18","19",
			"20","21","22","23","24","25","26","27","28","29",
			"30","31","32","33","34","35","36","37","38","39",
			"40","41","42","43","44","45","46","47","48","49",
			"50","51","52","53","54","55","56","57","58","59",
			"60","61","62","63","64","65","66","67","68","69",
			"70","71","72","73","74","75","76","77","78","79",
			"80","81","82","83","84","85","86","87","88","89",
			"90","91","92","93","94","95","96","97","98","99",
			"100","101","102","103","104","105","106","107","108","109",
			"110","111","112","113","114","115","116","117","118","119",
			"120","121","122","123","124","125","126","127",
			"CC0","CC1","CC2","CC3","CC4","CC5","CC6","CC7","CC8","CC9",
			"CC10","CC11","CC12","CC13","CC14","CC15","CC16","CC17","CC18","CC19",
			"CC20","CC21","CC22","CC23","CC24","CC25","CC26","CC27","CC28","CC29",
			"CC30","CC31","CC32","CC33","CC34","CC35","CC36","CC37","CC38","CC39",
			"CC40","CC41","CC42","CC43","CC44","CC45","CC46","CC47","CC48","CC49",
			"CC50","CC51","CC52","CC53","CC54","CC55","CC56","CC57","CC58","CC59",
			"CC60","CC61","CC62","CC63","CC64","CC65","CC66","CC67","CC68","CC69",
			"CC70","CC71","CC72","CC73","CC74","CC75","CC76","CC77","CC78","CC79",
			"CC80","CC81","CC82","CC83","CC84","CC85","CC86","CC87","CC88","CC89",
			"CC90","CC91","CC92","CC93","CC94","CC95","CC96","CC97","CC98","CC99",
			"CC100","CC101","CC102","CC103","CC104","CC105","CC106","CC107","CC108","CC109",
			"CC110","CC111","CC112","CC113","CC114","CC115","CC116","CC117","CC118","CC119",
			"CC120","CC121","CC122","CC123","CC124","CC125","CC126","CC127"};
	
	private final static ArrayList<ButtonRegion> buttonRegions = new ArrayList<ButtonRegion>(
			Arrays.asList(
				new ButtonRegion(Control.AFilter, 11, 45, 46, 55, false, false),
				new ButtonRegion(Control.BFilter, 71, 45, 46, 55, false, false),
				new ButtonRegion(Control.CFilter, 131, 45, 46, 55, false, false),
				new ButtonRegion(Control.DFilter, 191, 45, 46, 55, false, false),
				
				new ButtonRegion(Control.AFader, 6, 120, 58, 105, false, false),
				new ButtonRegion(Control.BFader, 65, 120, 58, 105, false, false),
				new ButtonRegion(Control.CFader, 124, 120, 58, 105, false, false),
				new ButtonRegion(Control.DFader, 183, 120, 58, 105, false, false),
				
				new ButtonRegion(Control.Sync, 17, 257, 35, 20, true, true),
				new ButtonRegion(Control.Quantize, 61, 257, 35, 20, true, true),
				new ButtonRegion(Control.Capture, 105, 257, 35, 20, true, true),
				
				new ButtonRegion(Control.Shift, 16, 300, 35, 20, true, true),
				new ButtonRegion(Control.Reverse, 61, 300, 35, 20, true, true),
				new ButtonRegion(Control.Type, 106, 300, 35, 20, true, true),
				new ButtonRegion(Control.Size, 150, 300, 35, 20, true, true),
				new ButtonRegion(Control.Browse, 194, 300, 35, 20, true, true),
				
				new ButtonRegion(Control.AKeylock, 18, 341, 45, 41, true, false),
				new ButtonRegion(Control.BKeylock, 73, 341, 45, 41, true, false),
				new ButtonRegion(Control.CKeylock, 129, 341, 45, 41, true, false),
				new ButtonRegion(Control.DKeylock, 184, 341, 45, 41, true, false),
				
				new ButtonRegion(Control.AFX, 18, 388, 45, 41, true, false),
				new ButtonRegion(Control.BFX, 73, 388, 45, 41, true, false),
				new ButtonRegion(Control.CFX, 129, 388, 45, 41, true, false),
				new ButtonRegion(Control.DFX, 184, 388, 45, 41, true, false),
				
				new ButtonRegion(Control.AMonitor, 18, 434, 45, 41, true, false),
				new ButtonRegion(Control.BMonitor, 73, 434, 45, 41, true, false),
				new ButtonRegion(Control.CMonitor, 129, 434, 45, 41, true, false),
				new ButtonRegion(Control.DMonitor, 184, 434, 45, 41, true, false),
				
				new ButtonRegion(Control.APunch, 18, 480, 45, 41, true, false),
				new ButtonRegion(Control.BPunch, 73, 480, 45, 41, true, false),
				new ButtonRegion(Control.CPunch, 129, 480, 45, 41, true, false),
				new ButtonRegion(Control.DPunch, 184, 480, 45, 41, true, false),
				
				new ButtonRegion(Control.AStop, 18, 532, 45, 18, true, true),
				new ButtonRegion(Control.BStop, 73, 532, 45, 18, true, true),
				new ButtonRegion(Control.CStop, 129, 532, 45, 18, true, true),
				new ButtonRegion(Control.DStop, 184, 532, 45, 18, true, true),
	
				new ButtonRegion(Control.Encoder, 196, 260, 42, 33, false, true)
			)
		);
	
	private static HashMap<Control, TransparentJButton> control2button = new HashMap<Control, TransparentJButton>();
	private static HashMap<TransparentJButton, Control> button2control = new HashMap<TransparentJButton, Control>();
	
	private ArrayList<Control> noMidiOutControls = new ArrayList<Control>(
			Arrays.asList( Control.AFilter, Control.BFilter, Control.CFilter, Control.DFilter,
						   Control.AFader, Control.BFader, Control.CFader, Control.DFader, Control.Encoder ));

	
	public void display() {

		/******************************************
		 * Menu
		 ******************************************/
		//MenuBar
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);//TODO fix this
		
		//File
		JMenu file = new JMenu("File");
		menuBar.add(file);
        file.setMnemonic(KeyEvent.VK_F);
        
        //Open
        JMenuItem open = new JMenuItem("Open");
        open.setMnemonic(KeyEvent.VK_O);
        file.add(open);
        open.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int openFileDialogReturnValue = fileChooser.showOpenDialog(frame);
				if (openFileDialogReturnValue == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					if (model.isDirty()) {
	            		int confirmContinueWithoutSavingReturnValue = JOptionPane.showConfirmDialog(
	            			    frame,
	            			    "Your unsaved changes will be lost.\nContinue without saving?",
	            			    "Warning!",
	            			    JOptionPane.YES_NO_OPTION,
	            			    JOptionPane.QUESTION_MESSAGE);
	            		if (confirmContinueWithoutSavingReturnValue == JFileChooser.APPROVE_OPTION) {
	            			model.populateFromFile(file);
	            			radioButtonBaseLayer.setSelected(true);
	            			baseLayer = true;
	            			updateComboBoxSelections();
	            			updateButtonMappingIcons();
	    				}
	    			} else {
	    				model.populateFromFile(file);
	    				radioButtonBaseLayer.setSelected(true);
            			baseLayer = true;
            			updateComboBoxSelections();
            			updateButtonMappingIcons();
	    			}
				}
			}
		});
        
        //Save
        JMenuItem save = new JMenuItem("Save");
        save.setMnemonic(KeyEvent.VK_S);
        file.add(save);
        save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
		        int saveFileDialogReturnValue = fileChooser.showSaveDialog(frame);
		        if (saveFileDialogReturnValue == JFileChooser.APPROVE_OPTION) {
		            File file = fileChooser.getSelectedFile();
		            try {
		            System.out.println(file.getCanonicalPath());
		            String newPath = file.getCanonicalPath() + ".f1map";
		            model.exportToFile(new File(newPath));
		            } catch (Exception excp) {
		            	excp.printStackTrace();
		            }
		        }
			}
		});
        
        //Separator
        file.addSeparator();
        
        //Exit
        JMenuItem quit = new JMenuItem("Quit");
        quit.setMnemonic(KeyEvent.VK_Q);
		file.add(quit);
        quit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
            	if (model.isDirty()) {
            		int returnVal = JOptionPane.showConfirmDialog(
            			    frame,
            			    "Your unsaved changes will be lost.\nAre you sure you want to quit?",
            			    "Warning!",
            			    JOptionPane.YES_NO_OPTION,
            			    JOptionPane.QUESTION_MESSAGE);
            		if (returnVal == JFileChooser.APPROVE_OPTION) {
    					System.exit(0);
    				}
            		return;
    			}
                System.exit(0);
            }
        });

		/******************************************
		 * Layout Panels
		 ******************************************/
		//parent panel for entire UI
		JPanel mainPanel = new JPanel();
		this.add(mainPanel);
		mainPanel.setLayout(new BorderLayout());
		
		//subpanels for each major UI area
		JPanel imagePanel = new JPanel();
		JPanel sidePanel = new JPanel();
		JPanel outersidePanel = new JPanel();
		outersidePanel.add(sidePanel);
		
		sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.PAGE_AXIS));
		
		JPanel mappingPanel = new JPanel();
		JPanel radioButtonLayerSelectPanel = new JPanel();
		
		mainPanel.add(imagePanel, BorderLayout.CENTER);
		mainPanel.add(outersidePanel, BorderLayout.EAST);
		
		sidePanel.add(radioButtonLayerSelectPanel);
		sidePanel.add(mappingPanel);
		
		JPanel midiPanel = new JPanel();
		midiPanel.setLayout(new GridLayout(2,3));

		/******************************************
		 * F1 Image
		 ******************************************/
		JLabel F1image = new JLabel(new ImageIcon(getClass().getResource("F1.jpg")));
		F1image.setLocation(ITEM_SPACING, ITEM_SPACING);
		imagePanel.setSize(IMAGE_WIDTH, IMAGE_HEIGHT);
		imagePanel.add(F1image);

		/******************************************
		 * Radio buttons
		 ******************************************/
		//Base layer radio button
	    radioButtonBaseLayer = new JRadioButton("Base Layer", true); //default selected
	    radioButtonBaseLayer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				baseLayer = true;
				updateComboBoxSelections();
				updateButtonMappingIcons();
			}
		});

	    //Shift layer radio button
	    radioButtonShiftLayer = new JRadioButton("Shift Layer", false);
	    radioButtonShiftLayer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				baseLayer = false;
				updateComboBoxSelections();
				updateButtonMappingIcons();
			}
		});

	    //Radio button group
	    ButtonGroup buttonGroupLayers = new ButtonGroup();
	    buttonGroupLayers.add(radioButtonBaseLayer);
	    buttonGroupLayers.add(radioButtonShiftLayer);

		//Radio button panel
		radioButtonLayerSelectPanel.setLayout(new GridLayout(1, 2));
		radioButtonLayerSelectPanel.add(radioButtonBaseLayer);
		radioButtonLayerSelectPanel.add(radioButtonShiftLayer);
		radioButtonLayerSelectPanel.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(), "Mapping Layer"));
		
		/******************************************
		 * MIDI in Combo Box and Learn controls
		 ******************************************/
		JLabel labelMidiIn = new JLabel("Midi In");
		comboBoxMidiIn = new JComboBox(midiControlMsgs);
		comboBoxMidiIn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
		        if (selectedControl != null)
		        {
			        JComboBox comboBox = (JComboBox)e.getSource();
			        String midiMsg = (String)comboBox.getSelectedItem();
			        if (baseLayer) {
			        	model.updateMidiIn(selectedControl, midiMsg);
			        } else {
			        	model.updateAltMidiIn(selectedControl, midiMsg);
			        }
			        control2button.get(selectedControl).updateMidiInValue(midiMsg);
		        }
			}
		});
		JButton buttonMidiInLearn = new JButton("Learn");
		buttonMidiInLearn.addActionListener(new MidiLearnButtonListener());	
		buttonMidiInLearn.setEnabled(false);
		
		/******************************************
		 * MIDI out Combo Box and Learn controls
		 ******************************************/
		JLabel labelMidiOut = new JLabel("Midi Out");
		comboBoxMidiOut = new JComboBox(midiControlMsgs);
		comboBoxMidiOut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
		        if (selectedControl != null)
		        {
			        JComboBox comboBox = (JComboBox)e.getSource();
			        String midiMsg = (String)comboBox.getSelectedItem();
			        if (baseLayer) {
			        	model.updateMidiOut(selectedControl, midiMsg);
			        } else {
			        	model.updateAltMidiOut(selectedControl, midiMsg);
	
			        }
			        	control2button.get(selectedControl).updateMidiOutValue(midiMsg);
		        }
			}
		});
		JButton buttonMidiOutLearn = new JButton("Learn");
		buttonMidiOutLearn.addActionListener(new MidiLearnButtonListener());
		buttonMidiOutLearn.setEnabled(false);
		
		/******************************************
		 * MIDI in/out panel
		 ******************************************/
		//Must be in order for GridLayout to work! 
		midiPanel.add(labelMidiIn);
		midiPanel.add(comboBoxMidiIn);
		midiPanel.add(buttonMidiInLearn);
		midiPanel.add(labelMidiOut);
		midiPanel.add(comboBoxMidiOut);
		midiPanel.add(buttonMidiOutLearn);
		mappingPanel.add(midiPanel);
		
		/******************************************
		 * Image button overlays
		 ******************************************/
		for(TransparentJButton button : button2control.keySet()) {
			button.addActionListener(new ButtonListener());
			button.showCheckmark(false);
			button.setVerticalAlignment(SwingConstants.BOTTOM);
			button.setHorizontalAlignment(SwingConstants.RIGHT);
			F1image.add(button);
		}
		
		updateButtonMappingIcons();
	}
	
	/******************************************
	 * Button callback
	 ******************************************/
	private class ButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			//determine which button was clicked
			TransparentJButton clicked = (TransparentJButton)arg0.getSource();
			
			//if the previous command being mapped was shift,
			//and the command before shift was in alt layer,
			//return back to the alt layer
			if (selectedControl == Control.Shift && !previousBaseLayer) {
				baseLayer = previousBaseLayer;
				radioButtonShiftLayer.setSelected(true);
			}
			
			//Look up and store the newly selected control
			selectedControl = button2control.get(clicked);
			
			//if the control is shift,
			//store the current layer, switch to the base layer, and disable the shift layer
			if (selectedControl == Control.Shift) {
				previousBaseLayer = baseLayer;
				radioButtonBaseLayer.setSelected(true);
				radioButtonShiftLayer.setEnabled(false);
				baseLayer = true;
			} else {
				//otherwise enable the shift layer
				radioButtonShiftLayer.setEnabled(true);
			}
			
			updateComboBoxSelections();
			updateButtonMappingIcons();
			
			//if the selected control does not have a midi out
			//disable the midi out combo box
			if (noMidiOutControls.contains(selectedControl)) {
				comboBoxMidiOut.setEnabled(false);
			} else {
				comboBoxMidiOut.setEnabled(true);
			}
			
			//unselect the previously selected UI button
			if (selectedJButton != null) {
				selectedJButton.setSelected(false);
			}
			//and select the new UI button
			clicked.setSelected(true);
			selectedJButton = clicked;
		}
	}

	
	/******************************************
	 * Update Combo Box Selections
	 ******************************************/
	//When a user changes layers or selects a new control,
	//this method populates midi in/out combo boxes with
	//any saved values
	private void updateComboBoxSelections() {
		if (selectedControl != null){
			if (baseLayer) {
				if (model.hasMidiIn(selectedControl)) {
					comboBoxMidiIn.setSelectedItem(model.getMidiIn(selectedControl));
				} else {
					comboBoxMidiIn.setSelectedIndex(0);
				}
				if (model.hasMidiOut(selectedControl)) {
					comboBoxMidiOut.setSelectedItem(model.getMidiOut(selectedControl));
				} else {
					comboBoxMidiOut.setSelectedIndex(0);
				}
			} else {
				if (model.hasAltMidiIn(selectedControl)) {
					comboBoxMidiIn.setSelectedItem(model.getAltMidiIn(selectedControl));
				} else {
					comboBoxMidiIn.setSelectedIndex(0);
				}
				if (model.hasAltMidiOut(selectedControl)) {
					comboBoxMidiOut.setSelectedItem(model.getAltMidiOut(selectedControl));
				} else {
					comboBoxMidiOut.setSelectedIndex(0);
				}
			}
		}
	}
	
	/******************************************
	 * Update Button Mapped/UnMapped Icons
	 ******************************************/
	private void updateButtonMappingIcons() {
		for (TransparentJButton button : button2control.keySet()) {
			if (baseLayer) {
				String midiIn = model.getMidiIn(button2control.get(button));
				String midiOut = model.getMidiOut(button2control.get(button));
				if (midiIn == null) {
					midiIn = "-";
				}
				if (midiOut == null) {
					midiOut = "-";
				}
				button.updateMidiValues(midiIn, midiOut);
			} else {
				String midiIn = model.getAltMidiIn(button2control.get(button));
				String midiOut = model.getAltMidiOut(button2control.get(button));
				if (midiIn == null) {
					midiIn = "-";
				}
				if (midiOut == null) {
					midiOut = "-";
				}
				button.updateMidiValues(midiIn, midiOut);	
			}
		}
	}
	
	
	/******************************************
	 * Learn button callback
	 ******************************************/
	private class MidiLearnButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			//basically check to see which button was clicked
			JButton clicked = (JButton)arg0.getSource();
			//Then, using the one that was clicked, do the action we want.
			String msg = "";
			if (!baseLayer) {
				msg = "Alt";
			}
			JOptionPane.showMessageDialog(frame, msg + clicked.getText());
		}
	}
	
	public F1MidiMappingGUI(JFrame frame) {
		this.frame = frame;
		display();
	}
	
	/******************************************
	 * Main
	 ******************************************/
	public static void main(String args[]){

		try {
			// Set default system look and feel
			UIManager.setLookAndFeel(
					UIManager.getSystemLookAndFeelClassName());
		} 
		catch(Exception e) {
			e.printStackTrace();
			//TODO handle gracefully
		}

		for (ButtonRegion buttonRegion : buttonRegions) {
			control2button.put(buttonRegion.getName(), buttonRegion.getButton());
			button2control.put(buttonRegion.getButton(), buttonRegion.getName());
		}
		
		fileChooser.setFileFilter(new FileNameExtensionFilter("F1Mapping File", "f1map"));
		
		/******************************************
		 *GUI Window
		 ******************************************/
		JFrame frame = new JFrame("F1 Midi Mapper");
		frame.setSize(566,651); //XXX for now
		frame.setLocation(0, 0);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		F1MidiMappingGUI gui = new F1MidiMappingGUI(frame); 
		frame.setContentPane(gui);
		frame.setVisible(true);
	}
}

