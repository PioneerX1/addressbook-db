import java.util.List;
import org.sql2o.*;

public class Contact {
  private String name;
  private String dob;
  private int id;


  public Contact(String name, String dob) {
    this.name = name;
    this.dob = dob;
  }

  public static List<Contact> all() {
    String sql = "SELECT id, name, dob FROM contacts";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Contact.class);
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO contacts(name, dob) VALUES (:name, :dob);";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .addParameter("dob", this.dob)
        .executeUpdate()
        .getKey();
    }
  }

  public static Contact find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM contacts WHERE id=:id;";
      Contact contact = con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetchFirst(Contact.class);
    return contact;
    }
  }

  public List<Entry> getEntries() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM entries WHERE contactId=:id";
      return con.createQuery(sql)
        .addParameter("id", this.id)
        .executeAndFetch(Entry.class);
    }
  }

  public String getName() {
    return name;
  }

  public String getDob() {
    return dob;
  }

  public int getId() {
    return id;
  }

  @Override
  public boolean equals(Object otherContact) {
    if(!(otherContact instanceof Contact)) {
      return false;
    } else {
      Contact newContact = (Contact) otherContact;
      return this.getName().equals(newContact.getName()) &&
              this.getDob().equals(newContact.getDob()) &&
              this.getId() == newContact.getId();
    }
  }
}
