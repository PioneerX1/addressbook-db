import java.util.ArrayList;
import java.util.List;
import org.sql2o.*;

public class Entry {
  private String place;
  private String phone;
  private String address;
  private String email;
  private int id;

  public Entry(String place, String phone, String address, String email) {
    this.place = place;
    this.phone = phone;
    this.address = address;
    this.email = email;
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO entries(place, phone, address, email) VALUES (:place, :phone, :address, :email);";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("place", this.place)
        .addParameter("phone", this.phone)
        .addParameter("address", this.address)
        .addParameter("email", this.email)
        .executeUpdate()
        .getKey();
    }
  }

  public static List<Entry> all() {
    String sql = "SELECT id, place, phone, address, email FROM entries";
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
            this.getId() == (newEntry.getId());
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
}
