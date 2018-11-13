package project.gui;

import java.util.LinkedList;

/**
 * 
 * Verkettete Liste zum Speichern aller Module.
 * @deprecated Wurde durch {@link LinkedList} ersetzt.
 *
 */
public class WindowModuleList {
	
	private WindowModule first;
	
	public WindowModuleList() {
		first = new WindowModule(0, 40);
	}
	
	public int size() {
		int size = 0;
		WindowModule wm = first;
		while(wm != null) {
			size++;
			wm = wm.getNext();
		}
		return size;
	}
	
	public void add(WindowModule wm) {
		if(first == null) {
			first = wm;
		} else {
			WindowModule wmh = first;
			while(wmh.getNext() != null) {
				wmh = wmh.getNext();
			}
			wmh.setNext(wm);
		}
	}
	
	public WindowModule get(final int pos) {
		if(size() == 0 || pos < 0 || pos > size()) return null;
		int nPos = 0;
		WindowModule wm = first;
		while(wm != null) {
			if(pos == nPos) return wm;
			else {
				if(wm.getNext() == null) return null; 
				else {
					wm = wm.getNext();
					nPos++;
				}
			}
		}
		return null;
	}
	
	public void removeLast() {
		if(first == null) return;
		else {
			WindowModule wm = first;
			WindowModule wm2 = first.getNext();
			while(wm2.getNext() != null) {
				wm = wm.getNext();
				wm2 = wm2.getNext();
			}
			wm.setNext(null);
		}
	}
	
}
