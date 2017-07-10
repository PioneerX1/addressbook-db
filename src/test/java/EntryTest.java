import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

public class EntryTest {
  @Before
  public void setUp() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/addressbook_test", null, null);
  }
  @After
  public void tearDown() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM entries *;";
      con.createQuery(sql).executeUpdate();
    }
  }

  @Test
  public void Entry_instantiatesCorrectly_true() {
    Entry testEntry = new Entry("Home", "301-542-4697", "123 Main Street", "myemail@outlook.com");
    assertEquals(true, testEntry instanceof Entry);
  }
  @Test
  public void getPhone_retrievesPhoneNumber_3015424697() {
    Entry testEntry = new Entry("Home", "301-542-4697", "123 Main Street", "myemail@outlook.com");
    assertEquals("301-542-4697", testEntry.getPhone());
  }
  @Test
  public void save_assignsIdToObject_true() {
    Entry testEntry = new Entry("Home", "301-542-4697", "123 Main Street", "myemail@outlook.com");
    testEntry.save();
    Entry savedEntry = Entry.all().get(0);
    assertEquals(testEntry.getId(), savedEntry.getId());
  }
  @Test
  public void getId_entriesInstantiateWithAnId_true() {
    Entry testEntry = new Entry("Home", "301-542-4697", "123 Main Street", "myemail@outlook.com");
    testEntry.save();
    assertTrue(testEntry.getId() > 0);
  }
  @Test
  public void all_returnsAllInstancesofEntry_true() {
    Entry testEntry = new Entry("Home", "301-542-4697", "123 Main Street", "myemail@outlook.com");
    testEntry.save();
    Entry testEntry2 = new Entry("Work", "443-542-1111", "99 Broadway", "mywork@gmail.com");
    testEntry2.save();
    assertEquals(true, Entry.all().get(0).equals(testEntry));
    assertEquals(true, Entry.all().get(1).equals(testEntry2));
  }
  @Test
  public void save_returnsTrueIfObjectsAreTheSame_true() {
    Entry testEntry = new Entry("Home", "301-542-4697", "123 Main Street", "myemail@outlook.com");
    testEntry.save();
    assertTrue(Entry.all().get(0).equals(testEntry));
  }
  @Test
  public void find_returnsEntryWithSameId_testEntry2() {
    Entry testEntry = new Entry("Home", "301-542-4697", "123 Main Street", "myemail@outlook.com");
    testEntry.save();
    Entry testEntry2 = new Entry("Work", "443-542-1111", "99 Broadway", "mywork@gmail.com");
    testEntry2.save();
    assertEquals(Entry.find(testEntry2.getId()), testEntry2);
  }


}
