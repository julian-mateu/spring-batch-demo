package com.pluralsight.batchprocessing.model;

import java.util.Objects;

public class OutputPerson {
  private String fullName;
  private int age;

  public OutputPerson() {
  }

  public OutputPerson(String fullName, int age) {
    this.fullName = fullName;
    this.age = age;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
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
    OutputPerson that = (OutputPerson) o;
    return age == that.age && Objects.equals(fullName, that.fullName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(fullName, age);
  }

  @Override
  public String toString() {
    return "OutputPerson{" +
        "fullName='" + fullName + '\'' +
        ", age=" + age +
        '}';
  }
}
