import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.Arrays;

public class ContactTest {

  @Before
  public void setUp() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/addressbook_test", null, null);
  }

  @After
  public void tearDown() {
    try(Connection con = DB.sql2o.open()) {
      String deleteEntriesQuery = "DELETE FROM entries *;";
      String deleteContactsQuery = "DELETE FROM contacts *;";
      con.createQuery(deleteEntriesQuery).executeUpdate();
      con.createQuery(deleteContactsQuery).executeUpdate();
    }
  }

  @Test
  public void Contact_instantiatesCorrectly_true() {
    Contact testContact = new Contact("Witty Chang", "January 14, 1983");
    assertEquals(true, testContact instanceof Contact);
  }
}
