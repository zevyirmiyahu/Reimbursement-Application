package com.zevyirmiyahu.service;

public class Main {
	
	public static void main(String[] args) {
		Service svc = new Service();
		svc.register(1, true, "Zev", "Yirmiyahu", "5506 Ascher Rd", "856-231-1576", "cool@email", "abc123");
		if(Service.users.isEmpty()) System.out.println("Empty");
		else {
			System.out.println(Service.users.get(0).getFirstName());
		}
	}
}
