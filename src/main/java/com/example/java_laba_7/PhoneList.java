package com.example.java_laba_7;

import java.time.LocalTime;
import java.util.*;


public class PhoneList {
    public static ArrayList<Phone> phones;
    public static Map<String, ArrayList<Phone>> phonesByCities;

    public PhoneList() {
        phones = new ArrayList<>();
        phonesByCities = new HashMap<>();
    }

    void remove(int index) {
        phones.remove(index);
    }

    Phone get(int index) {
        return phones.get(index);
    }

    int size() {
        return phones.size();
    }

    ArrayList<Phone> timeLimitAll(LocalTime limit) {
        ArrayList<Phone> result = new ArrayList<>(phones);
        result.removeIf(phone -> phone.timeLimit(limit));
        return result;
    }

    ArrayList<Phone> userIntercityTalkAll() {
        ArrayList<Phone> result = new ArrayList<>(phones);
        result.removeIf(Phone::usesIntercityTalk);
        result.sort(Comparator.comparing(Phone::getSurname).thenComparing(Phone::getName).thenComparing(Phone::getSecond_name));
        return result;
    }

    ArrayList<Phone> rangeAccountAll(int start, int end) {
        ArrayList<Phone> result = new ArrayList<>(phones);
        result.removeIf(phone -> !phone.rangeAccount(start, end));
        return result;
    }

    void totalTalkTimeSort() {
        phones.sort(Comparator.comparing(phone -> - (phone.getCity().getSecond() + phone.getIntercity().getSecond())));
    }
}