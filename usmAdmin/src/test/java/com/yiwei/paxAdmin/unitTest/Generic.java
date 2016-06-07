package com.yiwei.paxAdmin.unitTest;

import java.util.ArrayList;
import java.util.List;

public class Generic {
	static class Food{}
	static class Fruit extends Food{}
	static class Apple extends Fruit{}
	static class RedApple extends Apple{}
	
	public static void main(String[] args) {
		List<? extends Fruit> flist = new ArrayList<Apple>();
		//flist.add(new Apple());
		Fruit fruit = flist.get(0);
		Apple apple = (Apple) flist.get(0);
		
		flist.contains(new Fruit());
		flist.contains(new Apple());
		
		List<? super Fruit> flist0 = new ArrayList<Fruit>();
		flist0.add(new Fruit());
		flist0.add(new Apple());
		flist0.add(new RedApple());
		
		//List<? super Fruit> flist1 = new ArrayList<Apple>();
		//Fruit item = flist0.get(0);
	}

}
