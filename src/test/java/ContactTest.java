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

  @Test
  public void save_savesIntoDatabase_true() {
    Contact testContact = new Contact("Witty Chang", "January 14, 1983");
    testContact.save();
    assertTrue(Contact.all().get(0).equals(testContact));
  }

  @Test
  public void save_assignsIdToObject_true() {
    Contact testContact = new Contact("Witty Chang", "January 14, 1983");
    testContact.save();
    Contact savedContact = Contact.all().get(0);
    assertEquals(testContact.getId(), savedContact.getId());
  }

  @Test
  public void find_returnsContactWithSameId_testContact2() {
    Contact testContact1 = new Contact("Witty Chang", "January 14, 1983");
    testContact1.save();
    Contact testContact2 = new Contact("John Doe", "December 31, 1980");
    testContact2.save();
    assertEquals(Contact.find(testContact2.getId()), testContact2);
  }

  @Test
  public void getEntries_retrievesAllEntriesFromDatabase_entriesList() {
    Contact testContact = new Contact("Witty Chang", "January 14, 1983");
    testContact.save();
    Entry testEntry = new Entry("Home", "301-542-4697", "123 Main Street", "myemail@outlook.com", testContact.getId());
    testEntry.save();
    Entry testEntry2 = new Entry("Work", "443-542-1111", "99 Broadway", "mywork@gmail.com", testContact.getId());
    testEntry2.save();
    Entry[] entries = new Entry[] { testEntry, testEntry2 };
    assertTrue(testContact.getEntries().containsAll(Arrays.asList(entries)));
  }
}
