import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.sql.Timestamp;
import java.util.Date;
import java.text.DateFormat;


public class WaterMonsterTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void monster_instantiatesCorrectly_true() {
    WaterMonster testWaterMonster = new WaterMonster("Bubbles", 1);
    assertEquals(true, testWaterMonster instanceof WaterMonster);
  }

  @Test
  public void WaterMonster_instantiatesWithName_Bubbles() {
    WaterMonster testWaterMonster = new WaterMonster("Bubbles", 1);
    assertEquals("Bubbles", testWaterMonster.getName());
  }

  @Test
  public void WaterMonster_instantiatesWithPersonId_int() {
    WaterMonster testWaterMonster = new WaterMonster("Bubbles", 1);
    assertEquals(1, testWaterMonster.getPersonId());
  }

  @Test
  public void equals_returnsTrueIfNameAndPersonIdAreSame_true() {
    WaterMonster testWaterMonster= new WaterMonster("Bubbles", 1);
    WaterMonster anotherWaterMonster = new WaterMonster("Bubbles", 1);
    assertTrue(testWaterMonster.equals(anotherWaterMonster));
  }

  @Test
  public void save_returnsTrueIfDescriptionsAretheSame() {
    WaterMonster testWaterMonster = new WaterMonster("Bubbles", 1);
    testWaterMonster.save();
    assertTrue(WaterMonster.all().get(0).equals(testWaterMonster));
  }

  @Test
  public void save_assignsIdToWaterMonster() {
    WaterMonster testWaterMonster = new WaterMonster("Bubbles", 1);
    testWaterMonster.save();
    WaterMonster savedWaterMonster = WaterMonster.all().get(0);
    assertEquals(testWaterMonster.getId(), savedWaterMonster.getId());
  }

  @Test
  public void all_returnsAllInstancesOfWaterMonster_true() {
    WaterMonster firstWaterMonster = new WaterMonster("Bubbles", 1);
    firstWaterMonster.save();
    WaterMonster secondWaterMonster = new WaterMonster("Spud", 1);
    secondWaterMonster.save();
    assertEquals(true, WaterMonster.all().get(0).equals(firstWaterMonster));
    assertEquals(true, WaterMonster.all().get(1).equals(secondWaterMonster));
  }

  @Test
  public void find_returnsWaterMonsterWithSameId_secondWaterMonster() {
    WaterMonster firstWaterMonster = new WaterMonster("Bubbles", 1);
    firstWaterMonster.save();
    WaterMonster secondWaterMonster = new WaterMonster("Spud", 3);
    secondWaterMonster.save();
    assertEquals(WaterMonster.find(secondWaterMonster.getId()), secondWaterMonster);
  }

  @Test
  public void monster_instantiatesWithHalfFullPlayLevel() {
    WaterMonster testWaterMonster = new WaterMonster("Bubbles", 1);
    assertEquals(testWaterMonster.getPlayLevel(), (WaterMonster.MAX_PLAY_LEVEL/2));
  }

  @Test
  public void monster_instantiatesWithHalfFullSleepLevel() {
    WaterMonster testWaterMonster = new WaterMonster("Bubbles", 1);
    assertEquals(testWaterMonster.getSleepLevel(), (WaterMonster.MAX_SLEEP_LEVEL/2));
  }

  @Test
  public void monster_instantiatesWithHalfFullFoodLevel() {
    WaterMonster testWaterMonster = new WaterMonster("Bubbles", 1);
    assertEquals(testWaterMonster.getFoodLevel(), (WaterMonster.MAX_FOOD_LEVEL/2));
  }

  @Test
  public void isAlive_confirmsWaterMonsterIsAliveIfAllLevelsAboveMinimum_true() {
    WaterMonster testWaterMonster = new WaterMonster("Bubbles", 1);
    assertEquals(true, testWaterMonster.isAlive());
  }

  @Test
  public void depleteLevels_reducesAllLevels(){
    WaterMonster testWaterMonster = new WaterMonster("Drippy", 1);
    testWaterMonster.depleteLevels();
    assertEquals(testWaterMonster.getFoodLevel(), (WaterMonster.MAX_FOOD_LEVEL / 2) - 1);
    assertEquals(testWaterMonster.getSleepLevel(), (WaterMonster.MAX_SLEEP_LEVEL / 2) - 1);
    assertEquals(testWaterMonster.getPlayLevel(), (WaterMonster.MAX_PLAY_LEVEL / 2) - 1);
    assertEquals(testWaterMonster.getWaterLevel(), (WaterMonster.MAX_WATER_LEVEL / 2) - 1);
  }

  @Test
  public void isAlive_recognizesWaterMonsterIsDeadWhenLevelsReachMinimum_false() {
    WaterMonster testWaterMonster = new WaterMonster("Bubbles", 1);
    for(int i = WaterMonster.MIN_ALL_LEVELS; i <= WaterMonster.MAX_FOOD_LEVEL; i++) {
      testWaterMonster.depleteLevels();
    }
    assertEquals(testWaterMonster.isAlive(), false);
  }

  @Test
  public void play_increasesWaterMonsterPlayLevel() {
    WaterMonster testWaterMonster = new WaterMonster("Bubbles", 1);
    testWaterMonster.play();
    assertTrue(testWaterMonster.getPlayLevel() > (WaterMonster.MAX_PLAY_LEVEL/2));
  }

  @Test
  public void sleep_increasesWaterMonsterSleepLevel() {
    WaterMonster testWaterMonster = new WaterMonster("Bubbles", 1);
    testWaterMonster.sleep();
    assertTrue(testWaterMonster.getSleepLevel() > (WaterMonster.MAX_SLEEP_LEVEL/2));
  }

  @Test
  public void feed_increasesWaterMonsterFoodLevel() {
    WaterMonster testWaterMonster = new WaterMonster("Bubbles", 1);
    testWaterMonster.feed();
    assertTrue(testWaterMonster.getFoodLevel() > (WaterMonster.MAX_FOOD_LEVEL/2));
  }

  @Test
  public void monster_foodLevelCannotGoBeyondMaxValue(){
    WaterMonster testWaterMonster = new WaterMonster("Bubbles", 1);
    for(int i = WaterMonster.MIN_ALL_LEVELS; i <= (WaterMonster.MAX_FOOD_LEVEL); i++){
      try {
        testWaterMonster.feed();
      } catch (UnsupportedOperationException exception) { }
    }
    assertTrue(testWaterMonster.getFoodLevel() <= WaterMonster.MAX_FOOD_LEVEL);
  }

  @Test(expected = UnsupportedOperationException.class)
  public void feed_throwsExceptionIfFoodLevelIsAtMaxValue() {
      WaterMonster testWaterMonster = new WaterMonster("Bubbles", 1);
      for(int i = WaterMonster.MIN_ALL_LEVELS; i <= (WaterMonster.MAX_FOOD_LEVEL); i++) {
        testWaterMonster.feed();
      }
  }

  @Test(expected = UnsupportedOperationException.class)
  public void play_throwsExceptionIfPlayLevelIsAtMaxValue() {
    WaterMonster testWaterMonster = new WaterMonster("Bubbles", 1);
    for(int i = WaterMonster.MIN_ALL_LEVELS; i<= (WaterMonster.MAX_PLAY_LEVEL); i++) {
      testWaterMonster.play();
    }
  }

  @Test
  public void monster_playLevelCannotGoBeyondMaxValue() {
    WaterMonster testWaterMonster = new WaterMonster("Bubbles", 1);
    for(int i = WaterMonster.MIN_ALL_LEVELS; i <= (WaterMonster.MAX_PLAY_LEVEL); i++) {
      try {
        testWaterMonster.play();
      } catch (UnsupportedOperationException exception) { }
    }
    assertTrue(testWaterMonster.getPlayLevel() <= WaterMonster.MAX_PLAY_LEVEL);
  }

  @Test(expected = UnsupportedOperationException.class)
  public void sleep_throwsExceptionIfSleepLevelIsAtMaxValue() {
    WaterMonster testWaterMonster = new WaterMonster("Bubbles", 1);
    for(int i = WaterMonster.MIN_ALL_LEVELS; i <= (WaterMonster.MAX_SLEEP_LEVEL); i++) {
      testWaterMonster.sleep();
    }
  }

  @Test
  public void monster_sleepLevelCannotGoBeyondMaxValue() {
    WaterMonster testWaterMonster = new WaterMonster("Bubbles", 1);
    for(int i = WaterMonster.MIN_ALL_LEVELS; i <= (WaterMonster.MAX_SLEEP_LEVEL); i++) {
      try {
        testWaterMonster.sleep();
      } catch (UnsupportedOperationException exception) { }
    }
    assertTrue(testWaterMonster.getSleepLevel() <= WaterMonster.MAX_SLEEP_LEVEL);
  }

//This test is asserting that when we create a new monster, the birthday value inserted into our database reflects the current time.   As you can see, we construct a sample Timestamp object for comparison, providing a new Date object as an argument to the Timestamp constructor.
  @Test
  public void save_recordsTimeOfCreationInDatabase() {
    WaterMonster testWaterMonster = new WaterMonster("Bubbles", 1);
    testWaterMonster.save();
    Timestamp savedWaterMonsterBirthday = WaterMonster.find(testWaterMonster.getId()).getBirthday();
    Timestamp rightNow = new Timestamp(new Date().getTime());
    assertEquals(rightNow.getDay(), savedWaterMonsterBirthday.getDay());
  }

  @Test
  public void sleep_recordsTimeLastSleptInDatabase() {
    WaterMonster testWaterMonster = new WaterMonster("Bubbles", 1);
    testWaterMonster.save();
    testWaterMonster.sleep();
    Timestamp savedWaterMonsterLastSlept = WaterMonster.find(testWaterMonster.getId()).getLastSlept();
    Timestamp rightNow = new Timestamp(new Date().getTime());
    assertEquals(DateFormat.getDateTimeInstance().format(rightNow), DateFormat.getDateTimeInstance().format(savedWaterMonsterLastSlept));
  }

  @Test
  public void feed_recordsTimeLastAteInDatabase() {
    WaterMonster testWaterMonster = new WaterMonster("Bubbles", 1);
    testWaterMonster.save();
    testWaterMonster.feed();
    Timestamp savedWaterMonsterLastAte = WaterMonster.find(testWaterMonster.getId()).getLastAte();
    Timestamp rightNow = new Timestamp(new Date().getTime());
    assertEquals(DateFormat.getDateTimeInstance().format(rightNow), DateFormat.getDateTimeInstance().format(savedWaterMonsterLastAte));
  }

  @Test
  public void play_recordsTimeLastPlayedInDatabase() {
    WaterMonster testWaterMonster = new WaterMonster("Bubbles", 1);
    testWaterMonster.save();
    testWaterMonster.play();
    Timestamp savedWaterMonsterLastPlayed = WaterMonster.find(testWaterMonster.getId()).getLastPlayed();
    Timestamp rightNow = new Timestamp(new Date().getTime());
    assertEquals(DateFormat.getDateTimeInstance().format(rightNow), DateFormat.getDateTimeInstance().format(savedWaterMonsterLastPlayed));
  }

//beginning the Timer will start lowering WaterMonster levels.   we tell the JUnit test to pause for 6 seconds with Thread.sleep(6000)
  @Test
  public void timer_executesDepleteLevelsMethod() {
    WaterMonster testWaterMonster = new WaterMonster("Bubbles", 1);
    int firstPlayLevel = testWaterMonster.getPlayLevel();
    testWaterMonster.startTimer();
    try {
      Thread.sleep(6000);
    } catch (InterruptedException exception){}
    int secondPlayLevel = testWaterMonster.getPlayLevel();
    assertTrue(firstPlayLevel > secondPlayLevel);
  }

  @Test
  public void timer_haltsAfterWaterMonsterDies() {
    WaterMonster testWaterMonster = new WaterMonster("Bubbles", 1);
    testWaterMonster.startTimer();
    try {
      Thread.sleep(6000);
    } catch (InterruptedException exception){}
    assertFalse(testWaterMonster.isAlive());
    assertTrue(testWaterMonster.getFoodLevel() >= 0);
  }

  @Test
  public void waterMonster_instantiatesWithHalfFullWaterLevel(){
    WaterMonster testWaterMonster = new WaterMonster("Drippy", 1);
    assertEquals(testWaterMonster.getWaterLevel(), (WaterMonster.MAX_WATER_LEVEL / 2));
  }

  @Test
  public void water_increasesWaterMonsterWaterLevel(){
    WaterMonster testWaterMonster = new WaterMonster("Drippy", 1);
    testWaterMonster.water();
    assertTrue(testWaterMonster.getWaterLevel() > (WaterMonster.MAX_WATER_LEVEL / 2));
  }

  @Test(expected = UnsupportedOperationException.class)
  public void water_throwsExceptionIfWaterLevelIsAtMaxValue(){
    WaterMonster testWaterMonster = new WaterMonster("Drippy", 1);
    for(int i = WaterMonster.MIN_ALL_LEVELS; i <= (WaterMonster.MAX_WATER_LEVEL); i++){
      testWaterMonster.water();
    }
  }
}
