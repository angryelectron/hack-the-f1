
public class ButtonRegion {
	private int x, y, w, h;
	private Control name;
	private TransparentJButton button;
	private boolean hasMidiOut;
	private boolean usesSmallIcons;
	
	public ButtonRegion(Control name, int x, int y, int w, int h, boolean hasMidiOut, boolean usesSmallIcons) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.hasMidiOut = hasMidiOut;
		this.usesSmallIcons = usesSmallIcons;
		createButton();
	}
	
	private void createButton() {
		button = new TransparentJButton(hasMidiOut, usesSmallIcons);
		button.setLocation(getX(),getY());
		button.setSize(getW(), getH());
		button.setBorderPainted(true);
	}
	
	public TransparentJButton getButton() {
		return button;
	}
		
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getW() {
		return w;
	}

	public void setW(int w) {
		this.w = w;
	}

	public int getH() {
		return h;
	}

	public void setH(int h) {
		this.h = h;
	}

	public Control getName() {
		return name;
	}

	public void setName(Control name) {
		this.name = name;
	}
}
