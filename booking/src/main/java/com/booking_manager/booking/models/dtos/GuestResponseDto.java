package com.booking_manager.booking.models.dtos;

import com.booking_manager.booking.models.enums.EDocumentTypes;
import com.booking_manager.booking.models.enums.EMaritalStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GuestResponseDto {
    private Long id;
    private LocalDateTime creationDate;
    private LocalDateTime updateDate;
    private boolean deleted;
    private String name;
    private String lastName;
    private EDocumentTypes documentType;
    private String documentNumber;
    private String areaCode;
    private String phoneNumber;
    private String email;
    private int age;
    private LocalDate dateOfBirth;
    private String address;
    private String city;
    private String country;
    private String origin;
    private EMaritalStatus maritalStatus;
}
