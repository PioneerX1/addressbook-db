import java.util.ArrayList;
import java.util.List;
import org.sql2o.*;

public class Entry {
  private String place;
  private String phone;
  private String address;
  private String email;
  private int id;
  private int contactId;

  public Entry(String place, String phone, String address, String email, int contactId) {
    this.place = place;
    this.phone = phone;
    this.address = address;
    this.email = email;
    this.contactId = contactId;
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO entries(place, phone, address, email, contactId) VALUES (:place, :phone, :address, :email, :contactId);";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("place", this.place)
        .addParameter("phone", this.phone)
        .addParameter("address", this.address)
        .addParameter("email", this.email)
        .addParameter("contactId", this.contactId)
        .executeUpdate()
        .getKey();
    }
  }

  public static List<Entry> all() {
    String sql = "SELECT id, place, phone, address, email, contactId FROM entries";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Entry.class);
    }
  }

  @Override
  public boolean equals(Object otherEntry) {
    if(!(otherEntry instanceof Entry)) {
      return false;
    } else {
      Entry newEntry = (Entry) otherEntry;
      return this.getPlace().equals(newEntry.getPlace()) &&
            this.getPhone().equals(newEntry.getPhone()) &&
            this.getAddress().equals(newEntry.getAddress()) &&
            this.getEmail().equals(newEntry.getEmail()) &&
            this.getId() == (newEntry.getId()) &&
            this.getContactId() == (newEntry.getContactId());
    }
  }

  public static Entry find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM entries where id=:id";
      Entry entry = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Entry.class);
      return entry;
    }
  }

  public String getPhone() {
    return phone;
  }
  public String getPlace() {
    return place;
  }
  public String getAddress() {
    return address;
  }
  public String getEmail() {
    return email;
  }
  public int getId() {
    return id;
  }
  public int getContactId() {
    return contactId;
  }
}
