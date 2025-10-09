package com.dat.backend_version_2.enums.tournament;

import lombok.Getter;

import java.time.LocalDate;
import java.time.Period;

@Getter
public enum AgeDivision {
    U6(6),
    U7(7),
    U8(8),
    U9(9),
    U10(10),
    U11(11),
    U12(12),
    U13(13),
    U14(14),
    U15(15),
    U16(16),
    U17(17),
    U18(18),
    U19(19),
    U20(20);

    private final int age;

    AgeDivision(int age) {
        this.age = age;
    }

    // Hàm tính AgeDivision từ ngày sinh
    public static AgeDivision fromBirthDate(LocalDate birthDate) {
        int years = Period.between(birthDate, LocalDate.now()).getYears();

        // Tìm enum phù hợp
        for (AgeDivision division : values()) {
            if (years <= division.getAge()) {
                return division;
            }
        }

        return null; // hoặc throw exception nếu ngoài phạm vi
    }
}
