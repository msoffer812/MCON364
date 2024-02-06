package hw1;
import java.io.*;
public class Student implements Comparable<Student>{
	private String name;
	private int grade;
	
	public Student(String name, int grade) {
		this.name = name;
		this.grade = grade;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getGrade() {
		return this.grade;
	}
	
	@Override
	public int compareTo(Student student) {
		return this.grade - student.getGrade();
	}
	
	@Override
	public String toString() {
		return "Name: " + this.name + "  Grade: " + this.grade;
	}
}
