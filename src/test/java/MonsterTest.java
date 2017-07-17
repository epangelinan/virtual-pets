import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.sql.Timestamp;
import java.util.Date;
import java.text.DateFormat;


public class MonsterTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void monster_instantiatesCorrectly_true() {
    Monster testMonster = new Monster("Bubbles", 1);
    assertEquals(true, testMonster instanceof Monster);
  }

  @Test
  public void Monster_instantiatesWithName_Bubbles() {
    Monster testMonster = new Monster("Bubbles", 1);
    assertEquals("Bubbles", testMonster.getName());
  }

  @Test
  public void Monster_instantiatesWithPersonId_int() {
    Monster testMonster = new Monster("Bubbles", 1);
    assertEquals(1, testMonster.getPersonId());
  }

  @Test
  public void equals_returnsTrueIfNameAndPersonIdAreSame_true() {
    Monster testMonster= new Monster("Bubbles", 1);
    Monster anotherMonster = new Monster("Bubbles", 1);
    assertTrue(testMonster.equals(anotherMonster));
  }

  @Test
  public void save_returnsTrueIfDescriptionsAretheSame() {
    Monster testMonster = new Monster("Bubbles", 1);
    testMonster.save();
    assertTrue(Monster.all().get(0).equals(testMonster));
  }

  @Test
  public void save_assignsIdToMonster() {
    Monster testMonster = new Monster("Bubbles", 1);
    testMonster.save();
    Monster savedMonster = Monster.all().get(0);
    assertEquals(testMonster.getId(), savedMonster.getId());
  }

  @Test
  public void all_returnsAllInstancesOfMonster_true() {
    Monster firstMonster = new Monster("Bubbles", 1);
    firstMonster.save();
    Monster secondMonster = new Monster("Spud", 1);
    secondMonster.save();
    assertEquals(true, Monster.all().get(0).equals(firstMonster));
    assertEquals(true, Monster.all().get(1).equals(secondMonster));
  }

  @Test
  public void find_returnsMonsterWithSameId_secondMonster() {
    Monster firstMonster = new Monster("Bubbles", 1);
    firstMonster.save();
    Monster secondMonster = new Monster("Spud", 3);
    secondMonster.save();
    assertEquals(Monster.find(secondMonster.getId()), secondMonster);
  }

  @Test
  public void monster_instantiatesWithHalfFullPlayLevel() {
    Monster testMonster = new Monster("Bubbles", 1);
    assertEquals(testMonster.getPlayLevel(), (Monster.MAX_PLAY_LEVEL/2));
  }

  @Test
  public void monster_instantiatesWithHalfFullSleepLevel() {
    Monster testMonster = new Monster("Bubbles", 1);
    assertEquals(testMonster.getSleepLevel(), (Monster.MAX_SLEEP_LEVEL/2));
  }

  @Test
  public void monster_instantiatesWithHalfFullFoodLevel() {
    Monster testMonster = new Monster("Bubbles", 1);
    assertEquals(testMonster.getFoodLevel(), (Monster.MAX_FOOD_LEVEL/2));
  }

  @Test
  public void isAlive_confirmsMonsterIsAliveIfAllLevelsAboveMinimum_true() {
    Monster testMonster = new Monster("Bubbles", 1);
    assertEquals(true, testMonster.isAlive());
  }

  @Test
  public void depleteLevels_reducesAllLevels() {
    Monster testMonster = new Monster("Bubbles", 1);
    testMonster.depleteLevels();
    assertEquals(testMonster.getFoodLevel(), (Monster.MAX_FOOD_LEVEL/2 -1));
    assertEquals(testMonster.getSleepLevel(), (Monster.MAX_SLEEP_LEVEL/2 -1));
    assertEquals(testMonster.getPlayLevel(), (Monster.MAX_PLAY_LEVEL/2 -1));
  }

  @Test
  public void isAlive_recognizesMonsterIsDeadWhenLevelsReachMinimum_false() {
    Monster testMonster = new Monster("Bubbles", 1);
    for(int i = Monster.MIN_ALL_LEVELS; i <= Monster.MAX_FOOD_LEVEL; i++) {
      testMonster.depleteLevels();
    }
    assertEquals(testMonster.isAlive(), false);
  }

  @Test
  public void play_increasesMonsterPlayLevel() {
    Monster testMonster = new Monster("Bubbles", 1);
    testMonster.play();
    assertTrue(testMonster.getPlayLevel() > (Monster.MAX_PLAY_LEVEL/2));
  }

  @Test
  public void sleep_increasesMonsterSleepLevel() {
    Monster testMonster = new Monster("Bubbles", 1);
    testMonster.sleep();
    assertTrue(testMonster.getSleepLevel() > (Monster.MAX_SLEEP_LEVEL/2));
  }

  @Test
  public void feed_increasesMonsterFoodLevel() {
    Monster testMonster = new Monster("Bubbles", 1);
    testMonster.feed();
    assertTrue(testMonster.getFoodLevel() > (Monster.MAX_FOOD_LEVEL/2));
  }

  @Test
  public void monster_foodLevelCannotGoBeyondMaxValue(){
    Monster testMonster = new Monster("Bubbles", 1);
    for(int i = Monster.MIN_ALL_LEVELS; i <= (Monster.MAX_FOOD_LEVEL); i++){
      try {
        testMonster.feed();
      } catch (UnsupportedOperationException exception) { }
    }
    assertTrue(testMonster.getFoodLevel() <= Monster.MAX_FOOD_LEVEL);
  }

  @Test(expected = UnsupportedOperationException.class)
  public void feed_throwsExceptionIfFoodLevelIsAtMaxValue() {
      Monster testMonster = new Monster("Bubbles", 1);
      for(int i = Monster.MIN_ALL_LEVELS; i <= (Monster.MAX_FOOD_LEVEL); i++) {
        testMonster.feed();
      }
  }

  @Test(expected = UnsupportedOperationException.class)
  public void play_throwsExceptionIfPlayLevelIsAtMaxValue() {
    Monster testMonster = new Monster("Bubbles", 1);
    for(int i = Monster.MIN_ALL_LEVELS; i<= (Monster.MAX_PLAY_LEVEL); i++) {
      testMonster.play();
    }
  }

  @Test
  public void monster_playLevelCannotGoBeyondMaxValue() {
    Monster testMonster = new Monster("Bubbles", 1);
    for(int i = Monster.MIN_ALL_LEVELS; i <= (Monster.MAX_PLAY_LEVEL); i++) {
      try {
        testMonster.play();
      } catch (UnsupportedOperationException exception) { }
    }
    assertTrue(testMonster.getPlayLevel() <= Monster.MAX_PLAY_LEVEL);
  }

  @Test(expected = UnsupportedOperationException.class)
  public void sleep_throwsExceptionIfSleepLevelIsAtMaxValue() {
    Monster testMonster = new Monster("Bubbles", 1);
    for(int i = Monster.MIN_ALL_LEVELS; i <= (Monster.MAX_SLEEP_LEVEL); i++) {
      testMonster.sleep();
    }
  }

  @Test
  public void monster_sleepLevelCannotGoBeyondMaxValue() {
    Monster testMonster = new Monster("Bubbles", 1);
    for(int i = Monster.MIN_ALL_LEVELS; i <= (Monster.MAX_SLEEP_LEVEL); i++) {
      try {
        testMonster.sleep();
      } catch (UnsupportedOperationException exception) { }
    }
    assertTrue(testMonster.getSleepLevel() <= Monster.MAX_SLEEP_LEVEL);
  }

//This test is asserting that when we create a new monster, the birthday value inserted into our database reflects the current time.   As you can see, we construct a sample Timestamp object for comparison, providing a new Date object as an argument to the Timestamp constructor.
  @Test
  public void save_recordsTimeOfCreationInDatabase() {
    Monster testMonster = new Monster("Bubbles", 1);
    testMonster.save();
    Timestamp savedMonsterBirthday = Monster.find(testMonster.getId()).getBirthday();
    Timestamp rightNow = new Timestamp(new Date().getTime());
    assertEquals(rightNow.getDay(), savedMonsterBirthday.getDay());
  }

  @Test
  public void sleep_recordsTimeLastSleptInDatabase() {
    Monster testMonster = new Monster("Bubbles", 1);
    testMonster.save();
    testMonster.sleep();
    Timestamp savedMonsterLastSlept = Monster.find(testMonster.getId()).getLastSlept();
    Timestamp rightNow = new Timestamp(new Date().getTime());
    assertEquals(DateFormat.getDateTimeInstance().format(rightNow), DateFormat.getDateTimeInstance().format(savedMonsterLastSlept));
  }

  @Test
  public void feed_recordsTimeLastAteInDatabase() {
    Monster testMonster = new Monster("Bubbles", 1);
    testMonster.save();
    testMonster.feed();
    Timestamp savedMonsterLastAte = Monster.find(testMonster.getId()).getLastAte();
    Timestamp rightNow = new Timestamp(new Date().getTime());
    assertEquals(DateFormat.getDateTimeInstance().format(rightNow), DateFormat.getDateTimeInstance().format(savedMonsterLastAte));
  }

  @Test
  public void play_recordsTimeLastPlayedInDatabase() {
    Monster testMonster = new Monster("Bubbles", 1);
    testMonster.save();
    testMonster.play();
    Timestamp savedMonsterLastPlayed = Monster.find(testMonster.getId()).getLastPlayed();
    Timestamp rightNow = new Timestamp(new Date().getTime());
    assertEquals(DateFormat.getDateTimeInstance().format(rightNow), DateFormat.getDateTimeInstance().format(savedMonsterLastPlayed));
  }

//beginning the Timer will start lowering Monster levels.   we tell the JUnit test to pause for 6 seconds with Thread.sleep(6000)
  @Test
  public void timer_executesDepleteLevelsMethod() {
    Monster testMonster = new Monster("Bubbles", 1);
    int firstPlayLevel = testMonster.getPlayLevel();
    testMonster.startTimer();
    try {
      Thread.sleep(6000);
    } catch (InterruptedException exception){}
    int secondPlayLevel = testMonster.getPlayLevel();
    assertTrue(firstPlayLevel > secondPlayLevel);
  }

  @Test
  public void timer_haltsAfterMonsterDies() {
    Monster testMonster = new Monster("Bubbles", 1);
    testMonster.startTimer();
    try {
      Thread.sleep(6000);
    } catch (InterruptedException exception){}
    assertFalse(testMonster.isAlive());
    assertTrue(testMonster.getFoodLevel() >= 0);
  }

}
