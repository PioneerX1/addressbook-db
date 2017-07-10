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

  public String getName() {
    return name;
  }

  public String getDob() {
    return dob;
  }

  public int getId() {
    return id;
  }
}
