package ru.yandex.practicum.gym;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.*;

public class TimetableTest {

    @Test
    public void testGetTrainingSessionsForDaySingleSession() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        //Проверить, что за понедельник вернулось одно занятие
        Assertions.assertEquals(1, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size());
        //Проверить, что за вторник не вернулось занятий
        Assertions.assertTrue(timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY).isEmpty());
    }

    @Test
    public void testGetTrainingSessionsForDayMultipleSessions() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        // Проверить, что за понедельник вернулось одно занятие
        Assertions.assertEquals(1, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size());
        // Проверить, что за четверг вернулось два занятия в правильном порядке: сначала в 13:00, потом в 20:00
        NavigableSet<TimeOfDay> times = timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY).navigableKeySet();
        Assertions.assertEquals(new TimeOfDay(13, 0), times.first());
        Assertions.assertEquals(new TimeOfDay(20, 0), times.last());
        // Проверить, что за вторник не вернулось занятий
        Assertions.assertTrue(timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY).isEmpty());
    }

    @Test
    public void testGetTrainingSessionsForDayAndTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        //Проверить, что за понедельник в 13:00 вернулось одно занятие
        Assertions.assertEquals(1,
                timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(13, 0))
                        .size());
        //Проверить, что за понедельник в 14:00 не вернулось занятий
        Assertions.assertTrue(
                timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(14, 0))
                        .isEmpty());
    }
    //новые тесты
    //ну например две тренировки в один день в одно время
    @Test
    public void testAddSessionsAtSameTime() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        Group groupChild = new Group("Детская группа", Age.CHILD, 60);
        Group groupAdult = new Group("Взрослая группа", Age.ADULT, 90);

        TrainingSession session1 = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(18, 0));
        TrainingSession session2 = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(18, 0));

        timetable.addNewTrainingSession(session1);
        timetable.addNewTrainingSession(session2);

        List<TrainingSession> sessions = timetable.getTrainingSessionsForDayAndTime(
                DayOfWeek.THURSDAY, new TimeOfDay(18, 0));

        Assertions.assertEquals(2, sessions.size());
        Assertions.assertTrue(sessions.contains(session1));
        Assertions.assertTrue(sessions.contains(session2));
    }

    //проверим, что везде пусто
    @Test
    public void testEmptyTimetable() {
        Timetable timetable = new Timetable();

        Assertions.assertTrue(timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).isEmpty());
        Assertions.assertTrue(timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY).isEmpty());
        Assertions.assertTrue(timetable.getTrainingSessionsForDay(DayOfWeek.SUNDAY).isEmpty());
    }

    //тренировки в разных днях не перемешиваются
    @Test
    public void testDifferentDaysDontMix() {
        // Тест проверяет, что тренировки для разных дней не перемешиваются
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        timetable.addNewTrainingSession(new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(10, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach,
                DayOfWeek.TUESDAY, new TimeOfDay(10, 0)));

        Assertions.assertEquals(1, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size());
        Assertions.assertEquals(1, timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY).size());
        Assertions.assertTrue(timetable.getTrainingSessionsForDay(DayOfWeek.WEDNESDAY).isEmpty());
    }

    //тест метода на одном тренере
    @Test
    public void testGetCountByCoachesOneCoach() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Иванов", "Иван", "Иванович");
        Group group = new Group("Акробатика", Age.CHILD, 60);

        timetable.addNewTrainingSession(new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(10, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach,
                DayOfWeek.WEDNESDAY, new TimeOfDay(10, 0)));

        List<CounterOfTrainings> result = timetable.getCountByCoaches();

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(2, result.getFirst().getCount());
        Assertions.assertEquals(coach, result.getFirst().getCoach());
    }

    //пустое расписание
    @Test
    public void testGetCountByCoachesEmptyTimetable() {
        Timetable timetable = new Timetable();

        List<CounterOfTrainings> result = timetable.getCountByCoaches();

        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isEmpty());
    }

    //несколько тренеров с разным количеством занятий
    @Test
    public void testGetCountByCoaches_DifferentCounts() {
        Timetable timetable = new Timetable();

        Coach coach1 = new Coach("Иванов", "Иван", "Иванович");
        Coach coach2 = new Coach("Петров", "Петр", "Петрович");
        Coach coach3 = new Coach("Сидоров", "Сидор", "Сидорович");

        Group group = new Group("Акробатика", Age.CHILD, 60);

        //coach1- 1 тренировка
        timetable.addNewTrainingSession(new TrainingSession(group, coach1,
                DayOfWeek.MONDAY, new TimeOfDay(10, 0)));

        //coach2 - 3 тренировки
        timetable.addNewTrainingSession(new TrainingSession(group, coach2,
                DayOfWeek.MONDAY, new TimeOfDay(12, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach2,
                DayOfWeek.WEDNESDAY, new TimeOfDay(12, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach2,
                DayOfWeek.FRIDAY, new TimeOfDay(12, 0)));

        //coach3 - 2 тренировки
        timetable.addNewTrainingSession(new TrainingSession(group, coach3,
                DayOfWeek.TUESDAY, new TimeOfDay(15, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach3,
                DayOfWeek.THURSDAY, new TimeOfDay(15, 0)));

        List<CounterOfTrainings> result = timetable.getCountByCoaches();

        // Проверяем размер
        Assertions.assertEquals(3, result.size());

        // Проверяем порядок(по убыванию)
        Assertions.assertEquals(3, result.get(0).getCount());  // coach2 (3 тренировки)
        Assertions.assertEquals(2, result.get(1).getCount());  // coach3 (2 тренировки)
        Assertions.assertEquals(1, result.get(2).getCount());  // coach1 (1 тренировка)

        // Проверяем, что это правильные тренеры
        Assertions.assertEquals(coach2, result.get(0).getCoach());
        Assertions.assertEquals(coach3, result.get(1).getCoach());
        Assertions.assertEquals(coach1, result.get(2).getCoach());
    }
}