package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private Map<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetable = new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {
        DayOfWeek day = trainingSession.getDayOfWeek();
        TimeOfDay time = trainingSession.getTimeOfDay();

        TreeMap<TimeOfDay, List<TrainingSession>> daySessions =
                timetable.computeIfAbsent(day, k -> new TreeMap<>());

        List<TrainingSession> daySessionList =
                daySessions.computeIfAbsent(time, k -> new ArrayList<>());

        daySessionList.add(trainingSession);
    }

    public TreeMap<TimeOfDay, List<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        if (!timetable.containsKey(dayOfWeek)) {
            System.out.printf("В этот день (%s) тренировок нет!\n", dayOfWeek);
            return new TreeMap<>();
        }
        return timetable.get(dayOfWeek);
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {

        if (!timetable.containsKey(dayOfWeek)) {
            System.out.printf("В этот день (%s) тренировок нет!\n", dayOfWeek);
            return Collections.emptyList();
        } else if (!timetable.get(dayOfWeek).containsKey(timeOfDay)) {
            System.out.printf("В этот день (%s) в %s тренировок нет!\n", dayOfWeek, timeOfDay.toString());
            return Collections.emptyList();
        }

        return timetable.get(dayOfWeek).get(timeOfDay);
    }

    public List<CounterOfTrainings> getCountByCoaches() {
        Map<Coach, Integer> coachCounts = new HashMap<>();

        for (DayOfWeek day : DayOfWeek.values()) {
            TreeMap<TimeOfDay, List<TrainingSession>> daySessions = timetable.get(day);

            if (daySessions != null) {

                for (List<TrainingSession> trainingSessionList : daySessions.values()) {

                    for (TrainingSession trainingSession : trainingSessionList) {
                        Coach coach = trainingSession.getCoach();
                        coachCounts.put(coach, coachCounts.getOrDefault(coach, 0) + 1);
                    }
                }
            }
        }

        List<CounterOfTrainings> result = new ArrayList<>();

        for (Map.Entry<Coach, Integer> entry : coachCounts.entrySet()) {
            result.add(new CounterOfTrainings(entry.getKey(), entry.getValue()));
        }

        Comparator<CounterOfTrainings> comparator = new Comparator<>() {
            @Override
            public int compare(CounterOfTrainings c1, CounterOfTrainings c2) {
                return c2.getTrainings() - c1.getTrainings();
            }
        };
        Collections.sort(result, comparator);
        return result;
    }
}