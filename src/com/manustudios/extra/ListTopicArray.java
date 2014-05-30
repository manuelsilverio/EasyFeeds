package com.manustudios.extra;

import java.util.ArrayList;

public class ListTopicArray {

	String name;
	private int topicPosition;
	private ArrayList<Feed> items;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<Feed> getItems() {
		return items;
	}
	public void setItems(ArrayList<Feed> items) {
		this.items = items;
	}
	public int getTopicPosition() {
		return topicPosition;
	}
	public void setTopicPosition(int topicPosition) {
		this.topicPosition = topicPosition;
	}
	
	
}
