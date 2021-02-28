package com.Astar.game.dataStructures;

import java.util.ArrayList;

public class Heap<T extends IHeapItem<T>> {
	private ArrayList<T> items;
	private int currentItemSize;
	
	public Heap() {
		items = new ArrayList<T>();
	}
	
	public void add(T newItem) {
		newItem.heapIndex = currentItemSize;
		items.add(currentItemSize,newItem);
		sortUp(newItem);
		currentItemSize++;
	}
	
	public T removeFirst() {
		T firstItem = items.get(0);
		currentItemSize--;
		items.set(0, items.get(currentItemSize));
		items.get(0).heapIndex = 0;
		sortDown(items.get(0));
		return firstItem;
	}
	
	private void sortUp(T newItem) {
		int parentIndex = (newItem.heapIndex-1)/2;
		
		while (true) {
			T parentItem = items.get(parentIndex);
			if (newItem.compareTo(parentItem) > 0)
				swap(newItem,parentItem);
			else
				break;
			parentIndex = (newItem.heapIndex-1)/2;
		}
	}
	
	private void sortDown(T newItem) {
		while (true) {
			int childIndexLeft = newItem.heapIndex*2+1;
			int childIndexRight = newItem.heapIndex*2+2;
			int swapIndex = 0;
			
			if (childIndexLeft < currentItemSize) {
				swapIndex = childIndexLeft;
				if (childIndexRight < currentItemSize) {
					if (items.get(childIndexLeft).compareTo(items.get(childIndexRight)) < 0)
						swapIndex = childIndexRight;
				}
				
				if (newItem.compareTo(items.get(swapIndex)) < 0)
					swap(newItem,items.get(swapIndex));
				else
					return;
			}
			else
				return;
		}
	}
	
	public boolean contains(T item) {
		return items.contains(item);
	}
	
	public int size() {
		return currentItemSize;
	}
	
	public void updateItem(T item) {
		sortUp(item);
	}
	
	private void swap(T itemA, T itemB) {
		items.set(itemA.heapIndex,itemB);
		items.set(itemB.heapIndex, itemA);
		
		int tempIndex = itemA.heapIndex;
		itemA.heapIndex = itemB.heapIndex;
		itemB.heapIndex = tempIndex;
	}
}
abstract class IHeapItem<T> implements Comparable<T> {
	public int heapIndex;
}