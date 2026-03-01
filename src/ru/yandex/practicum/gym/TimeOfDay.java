package ru.yandex.practicum.gym;

import java.util.Objects;

public class TimeOfDay implements Comparable<TimeOfDay> {
    private int hours;
    private int minutes;

    public TimeOfDay(int hours, int minutes) {

        if (hours > 23) {
            this.hours = 23;
        } else if (hours < 0) {
            this.hours = 0;
        } else {
            this.hours = hours;
        }

        if (minutes > 59) {
            this.minutes = 59;
        } else if (minutes < 0) {
            this.minutes = 0;
        } else {
            this.minutes = minutes;
        }
    }

    @Override
    public int compareTo(TimeOfDay o) {
        if (hours != o.hours) return hours - o.hours;
        return minutes - o.minutes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeOfDay timeOfDay = (TimeOfDay) o;
        return hours == timeOfDay.hours && minutes == timeOfDay.minutes;
    }

    @Override
    public int hashCode() {
        return Objects.hash(hours, minutes);
    }

    public int getHours() {
        return hours;
    }

    public int getMinutes() {
        return minutes;
    }

    @Override
    public String toString() {
        return String.format("%02d:%02d", hours, minutes);
    }
}