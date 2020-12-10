package com.subjects.votingservice.infrastructure.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

/**
 * Associate entity.
 */
@Data
@Document
@Validated
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Associate extends BaseEntity {

    @NotBlank(message = "First name is required.")
    private String firstName;

    @NotBlank(message = "Last name is required.")
    private String lastName;

    @NotBlank(message = "CPF is required.")
    private String cpf;
}
