import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Scanner;

public class F1MidiMapModel {
	private HashMap<Control, String> midiIn;
	private HashMap<Control, String> midiOut;
	private HashMap<Control, String> altMidiIn;
	private HashMap<Control, String> altMidiOut;
	private enum ControlType {in, out, altin, altout};
	private boolean isDirty;
	
	public F1MidiMapModel () {
		midiIn = new HashMap<Control, String>();
		altMidiIn = new HashMap<Control, String>();
		midiOut = new HashMap<Control, String>();
		altMidiOut = new HashMap<Control, String>();
		isDirty = false;
	}
	
	public void exportToFile(File file) {
		try {
			Writer outputWriter = new BufferedWriter(new FileWriter(file)); 
			for (Control control : midiIn.keySet()) {
				if (midiIn.get(control) != "-") {
					outputWriter.write(ControlType.in.toString() + " " + control.toString() +  " " + midiIn.get(control) + "\n");
				}
			}
			for (Control control : midiOut.keySet()) {
				if (midiOut.get(control) != "-") {
					outputWriter.write(ControlType.out.toString() + " " + control.toString() +  " " + midiOut.get(control) + "\n");
				}
			}
			for (Control control : altMidiIn.keySet()) {
				if (altMidiIn.get(control) != "-") {
					outputWriter.write(ControlType.altin.toString() + " " + control.toString() +  " " + altMidiIn.get(control) + "\n");
				}
			}
			for (Control control : altMidiOut.keySet()) {
				if (altMidiOut.get(control) != "-") {
					outputWriter.write(ControlType.altout.toString() + " " + control.toString() +  " " + altMidiOut.get(control) + "\n");
				}
			}
			outputWriter.close();
		} catch (Exception e) {
			e.printStackTrace();	
		}
		isDirty = false;
	}
	
	public void populateFromFile(File file) {
		try {
		    Scanner fileLineScanner = new Scanner(new FileReader(file));
		    Scanner entryLineScanner;
		    midiIn.clear();
		    midiOut.clear();
		    altMidiIn.clear();
		    altMidiOut.clear();
			while (fileLineScanner.hasNextLine()) {
				String entry = fileLineScanner.nextLine();
				entryLineScanner =  new Scanner(entry);
				entryLineScanner.useDelimiter(" ");
				if (entryLineScanner.hasNext()) {
					ControlType controlType = ControlType.valueOf(entryLineScanner.next());
					Control control = Control.valueOf(entryLineScanner.next());
					String note = entryLineScanner.next();
					switch (controlType) {
						case in:
							midiIn.put(control, note);
							break;
						case out:
							midiOut.put(control, note);
							break;
						case altin:
							altMidiIn.put(control, note);
							break;
						case altout:
							altMidiOut.put(control, note);
							break;	
					}
				}
			}
			fileLineScanner.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void updateMidiIn(Control control, String midiMsg) {
		midiIn.put(control, midiMsg);
		isDirty = true;
	}
	
	public void updateAltMidiIn(Control control, String midiMsg) {
		altMidiIn.put(control, midiMsg);
		isDirty = true;
	}
	
	public void updateMidiOut(Control control, String midiMsg) {
		midiOut.put(control, midiMsg);
		isDirty = true;
	}
	
	public void updateAltMidiOut(Control control, String midiMsg) {
		altMidiOut.put(control, midiMsg);
		isDirty = true;
	}
	
	public String getMidiIn(Control control) {
		return midiIn.get(control);
	}
	
	public String getAltMidiIn(Control control) {
		return altMidiIn.get(control);
	}
	
	public String getMidiOut(Control control) {
		return midiOut.get(control);			
	}
	
	public String getAltMidiOut(Control control) {
		return altMidiOut.get(control);
	}
	
	public Boolean hasMidiIn(Control control) {
		return midiIn.containsKey(control);
	}
	
	public Boolean hasAltMidiIn(Control control) {
		return altMidiIn.containsKey(control);
	}
	
	public Boolean hasMidiOut(Control control) {
		return midiOut.containsKey(control);
	}
	
	public Boolean hasAltMidiOut(Control control) {
		return altMidiOut.containsKey(control);
	}
	
	public Boolean isDirty() {
		return isDirty;
	}
}
