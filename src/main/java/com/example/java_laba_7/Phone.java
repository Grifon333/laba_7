package com.example.java_laba_7;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalTime;

@Data
@AllArgsConstructor
public class Phone {
    private long id;
    private String name;
    private String surname;
    private String second_name;
    private int account;
    private LocalTime city;
    private LocalTime intercity;
    private String sender;

    @Override
    public String toString() {
        return "Phone{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", second_name='" + second_name + '\'' +
                ", account=" + account +
                ", city=" + city +
                ", intercity=" + intercity +
                ", sender='" + sender + '\'' +
                '}';
    }

    boolean timeLimit(LocalTime limit) {
        return limit.isBefore(city);
    }

    boolean usesIntercityTalk() {
        return intercity.equals(LocalTime.MIN);
    }

    boolean rangeAccount(int start, int end) {
        return account >= start && account < end;
    }
}