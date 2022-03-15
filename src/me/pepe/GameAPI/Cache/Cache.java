package me.pepe.GameAPI.Cache;

import java.util.ArrayList;
import java.util.List;

import me.pepe.GameAPI.Utils.Callback;

public class Cache<T> {
	private String id;
	private List<String> subIDS = new ArrayList<String>();
	private T value;
	private long caduc;
	private Callback<Boolean> removeCallback;
	public Cache(String id, T value, long caduc) {
		this.id = id;
		this.value = value;
		this.caduc = caduc + System.currentTimeMillis();
	}
	public String getID() {
		return id;
	}
	public T getValue() {
		return value;
	}
	public long getCaduc() {
		return caduc;
	}
	public boolean isCaduqued() {
		return caduc - System.currentTimeMillis() <= 0;
	}
	public void updateCaduc(long newCaduc) {
		this.caduc = newCaduc + System.currentTimeMillis();
	}
	public Callback<Boolean> getRemoveCallback() {
		return removeCallback;
	}
	public void setRemoveAction(Callback<Boolean> action) {
		this.removeCallback = action;
	}
	public void addSubID(String id) {
		if (!subIDS.contains(id)) {
			subIDS.add(id);
		}
	}
	public boolean hasSubID(String id) {
		return this.id.equals(id) || subIDS.contains(id);
	}
}
