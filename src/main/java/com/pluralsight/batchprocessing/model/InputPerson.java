package com.pluralsight.batchprocessing.model;

import java.util.Objects;

public class InputPerson {
	private String lastName;
	private String firstName;
	private int age;

	public InputPerson() {
	}

	public InputPerson(String lastName, String firstName, int age) {
		this.lastName = lastName;
		this.firstName = firstName;
		this.age = age;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		InputPerson person = (InputPerson) o;
		return age == person.age && Objects.equals(lastName, person.lastName) && Objects.equals(firstName, person.firstName);
	}

	@Override
	public int hashCode() {
		return Objects.hash(lastName, firstName, age);
	}

	@Override
	public String toString() {
		return "Person{" +
				"lastName='" + lastName + '\'' +
				", firstName='" + firstName + '\'' +
				", age=" + age +
				'}';
	}
}
