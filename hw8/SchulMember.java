package hw8;

public class SchulMember implements Comparable<SchulMember>{
	private String lastNameOfMember;
	private String firstNameOfMember;
	private int birthDateOfMember;
	private String spouseFirstName;
	private String spouseLastName;
	private String[] childrenNames;
	private int yearsOfMembership;
	
	public SchulMember(String lastNameOfMember, String firstNameOfMember, 
			int birthDateOfMember, String spouseFirstName, String spouseLastName,
				String[] childrenNames, int yearsOfMembership) {
		this.lastNameOfMember = lastNameOfMember;
		this.firstNameOfMember = firstNameOfMember;
		this.birthDateOfMember = birthDateOfMember;
		this.spouseFirstName = spouseFirstName;
		this.spouseLastName = spouseLastName;
		this.childrenNames = childrenNames;
		this.yearsOfMembership = yearsOfMembership;
	}
	
	public String getLastNameOfMember() {
		return this.lastNameOfMember;
	}
	public String getFirstNameOfMember() {
		return this.firstNameOfMember;
	}
	public String getSpouseFirstName() {
		return this.spouseFirstName;
	}
	public String getspouseLastName() {
		return this.spouseLastName;
	}
	public String[] getChildrenNames() {
		return this.childrenNames;
	}
	public int getBirthDateOfMember() {
		return this.birthDateOfMember;
	}
	public int getYearsOfMembership() {
		return this.yearsOfMembership;
	}
	
	
	public void setLastNameOfMember(String s) {
		this.lastNameOfMember = s;
	}
	public void setFirstNameOfMember(String s) {
		this.firstNameOfMember = s;
	}
	public void setBirthDateOfMember(int s) {
		this.birthDateOfMember = s;
	}
	public void setSpouseFirstName(String s) {
		this.spouseFirstName = s;
	}
	public void setSpouseLastName(String s) {
		this.spouseLastName = s;
	}
	public void setChildrenNames(String[] s) {
		this.childrenNames = s;
	}
	public void setYearsOfMembership(int s) {
		this.yearsOfMembership = s;
	}
	
	@Override
	public int compareTo(SchulMember m) {
		return m.getBirthDateOfMember() - this.birthDateOfMember;
	}
}
