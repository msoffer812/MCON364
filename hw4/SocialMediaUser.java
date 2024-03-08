package hw4;
import java.util.*;

public class SocialMediaUser implements Person{
	private String email;
	private String fname;
	private String lname;
	private int age;
	private List<String> interests;
	
	public SocialMediaUser(String email) {
		this.email = email;
	}
	
	public SocialMediaUser(String email, String fname, String lname, int age) {
		this.email = email;
		this.fname = fname;
		this.lname = lname;
		this.age = age;
		this.interests = new ArrayList<>();
	}
	
	public SocialMediaUser(String email, String fname, String lname, int age, List<String> interests) {
		this.email = email;
		this.fname = fname;
		this.lname = lname;
		this.age = age;
		this.interests = interests;
	}
	
	/**
	 * The unique identifier for a user would be their email,
	 * which would be why that's how you compare two users, and that's 
	 * what the hashcode is based off of
	 * @param p
	 * @return
	 */
	@Override
	public boolean equals(Object o) {
		SocialMediaUser p = (SocialMediaUser)(o);
		return this.email.equals(p.getID());
	}
	/**
	 * Setters - set different fields
	 * @param name
	 */
	@Override
	public void setFirstName(String name) {
		this.fname = name;
	}
	@Override
	public void setLastName(String name) {
		this.lname = name;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public void addInterest(String interest){
		this.interests.add(interest);
	}
	
	/**
	 * @return string rep of the person
	 */
	@Override
	public String toString() {
		return "Name: " + getName() 
			+ ", Age: " + getAge() + ", Email: " 
				+ getID();
	}
	
	/**
	 * Overrides hashcode so it recognizes that any user with the same email is the same
	 */
	@Override
    public int hashCode() {
        return Objects.hash(email);
    }
	
	/**
	 * Getters
	 * @return the fields
	 */
	@Override
	public String getID() {
		return this.email;
	}
	@Override
	public String getName() {
		return this.fname + " " + this.lname;
	}
	@Override
	public int getAge() {
		return this.age;
	}
	public List<String> getInterests(){
		return this.interests;
	}
}
