package ru.yandex.practicum.gym;

public class CounterOfTrainings {
    private Coach coach;
    private int trainings;

    public CounterOfTrainings(Coach coach, int trainings) {
        this.coach = coach;
        this.trainings = trainings;
    }

    public int getTrainings() {
        return trainings;
    }

    public int getCount() {
        return trainings;
    }

    public Coach getCoach() {
        return coach;
    }
}