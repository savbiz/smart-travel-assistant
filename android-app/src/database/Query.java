package database;

//classe Query
public class Query {

	// private variables
	int _id;
	String _type;
	int _radius;
	double _latitude;
	double _longitude;
	String _keyword;
	String _category;
	String _created_at;

	// Empty constructor
	public Query() {

	}

	// constructor
	public Query(String type, int radius, double latitude, double longitude,
			String keyword, String category) {
		this._type = type;
		this._radius = radius;
		this._latitude = latitude;
		this._longitude = longitude;
		this._keyword = keyword;
		this._category = category;
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
	public String getType() {
		return this._type;
	}

	// setting name
	public void setType(String type) {
		this._type = type;
	}

	// setting created_at
	public void setCreatedAt(String created_at) {
		this._created_at = created_at;
	}

	public int getRadius() {
		return _radius;
	}

	public void setRadius(int radius) {
		this._radius = radius;
	}

	public double getLatitude() {
		return _latitude;
	}

	public void setLatitude(double latitude) {
		this._latitude = latitude;
	}

	public double getLongitude() {
		return _longitude;
	}

	public void setLongitude(double longitude) {
		this._longitude = longitude;
	}

	public String getKeyword() {
		return _keyword;
	}

	public void setKeyword(String keyword) {
		this._keyword = keyword;
	}

	// setting created_at
	public void setCategory(String category) {
		this._category = category;
	}

	// getting created_at
	public String getCategory() {
		return this._category;
	}

	// getting created_at
	public String getCreatedAt() {
		return this._created_at;
	}

}
