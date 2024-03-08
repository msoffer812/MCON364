package hw4;

/**
 * Person object, defines a person
 * SocialMediaUser, which goes in the graph, implements it
 * @author mbrso
 *
 */
public interface Person {
	String getID();						/*Get a unique string identifier, usually an email or whatever*/
	String getName();					/*Get person's name*/
	int getAge();						/*Get Person's age*/
	void setFirstName(String fname);	/*Set Person's first name*/
	void setLastName(String lname);		/*Set Person's last name*/
}
