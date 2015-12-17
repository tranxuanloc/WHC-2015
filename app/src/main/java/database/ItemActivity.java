package database;

public class ItemActivity {
	private int id;
	private String Name;
	private int KEY_Status;
	
	public ItemActivity(String Name, int Status) {
		super();
		this.Name = Name;
		this.KEY_Status = Status;
	}

	public String getName() {
		return Name;
	}

	public void setName(String Name) {
		this.Name = Name;
	}

	public int getStatus() {
		return KEY_Status;
	}

	public void setStatus(int Status) {
		this.KEY_Status = Status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}