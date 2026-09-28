package database;

//Classe Utenti
public class User {

	// private variables
	int _id;
	String _name;
	String _username;
	String _password;
	int _age;
	String _sex;
	String _created_at;

	// Empty constructor
	public User() {

	}

	// constructor
	public User(int id, String name, String username, String password, int age,
			String disability, String sex) {
		this._id = id;
		this._name = name;
		this._username = username;
		this._password = password;
		this._age = age;
		this._sex = sex;
	}

	// constructor
	public User(int id, String name, String username, String password) {
		this._id = id;
		this._name = name;
		this._username = username;
		this._password = password;
	}

	// constructor
	public User(String name, String username, String password) {
		this._name = name;
		this._username = username;
		this._password = password;
	}

	// getting ID
	public int getID() {
		return this._id;
	}

	// setting id
	public void setID(int id) {
		this._id = id;
	}

	// getting name
	public String getName() {
		return this._name;
	}

	// setting name
	public void setName(String name) {
		this._name = name;
	}

	// getting username
	public String getUsername() {
		return this._username;
	}

	// setting username
	public void setUsername(String username) {
		this._username = username;
	}

	// getting username
	public String getPassword() {
		return this._password;
	}

	// getting username
	public void setPassword(String password) {
		this._password = password;
	}

	// getting age
	public int getAge() {
		return this._age;
	}

	// getting age
	public void setAge(int age) {
		this._age = age;
	}

	// getting sex
	public String getSex() {
		return this._sex;
	}

	// getting sex
	public void setSex(String sex) {
		this._sex = sex;
	}

	// setting created_at
	public void setCreatedAt(String created_at) {
		this._created_at = created_at;
	}

}