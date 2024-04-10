package com.booking_manager.booking.models.dtos;

import com.booking_manager.booking.models.enums.EDocumentTypes;
import com.booking_manager.booking.models.enums.EMaritalStatus;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GuestRequestDto {
    @Size(min = 3, max = 100)
    @NotNull(message = "Name is required.")
    private String name;

    @Size(min = 3, max = 100)
    @NotNull(message = "Lastname is required.")
    private String lastName;

    @NotNull(message = "Document type is required")
    @Column(name = "document_type")
    @Enumerated(EnumType.STRING)
    private EDocumentTypes documentType;

    @NotNull(message = "Document number is required")
    @Column(name = "document_number")
    private String documentNumber;

    @Pattern(regexp = "^[1-9]{2,3}$", message = "Format: (+) + Area code. Without spaces and special characters")
    @NotNull(message = "Area code is required")
    @Column(name = "area_code")
    private String areaCode;

    @Pattern(regexp = "^[1-9]\\d{8,9}$", message = "Format: (+) + Area code + phone number. Without spaces and special characters")
    @Column(name = "phone_number")
    private String phoneNumber;

    @Email
    @NotEmpty(message = "Email is required")
    @Column(unique = true)
    private String email;

    @Min(18)
    @Max(99)
    private int age;

    @NotNull(message = "Date of birth is required (YYYY-MM-dd).")
    @Column(name = "day_of_birth")
    @Past
    private LocalDate dateOfBirth;

    @Size(min = 3, max = 100)
    @NotNull(message = "Address is required.")
    private String address;

    @Size(min = 3, max = 100)
    @NotNull(message = "City is required.")
    private String city;

    @Size(min = 3, max = 100)
    @NotNull(message = "Country is required.")
    private String country;

    private String origin;

    @NotNull(message = "Marital status is required.")
    @Enumerated(EnumType.STRING)
    @Column(name = "marital_status")
    private EMaritalStatus maritalStatus;
}
