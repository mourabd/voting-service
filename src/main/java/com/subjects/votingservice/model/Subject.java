package com.subjects.votingservice.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;

/**
 * Subject entity.
 */
@Data
@Document
@EqualsAndHashCode(callSuper = true)
public class Subject extends BaseEntity {

    @NotBlank(message = "Title is required.")
    private String title;

    @NotBlank(message = "Description is required.")
    private String description;

    private String code;

    /**
     * Class constructor.
     *
     * @param code code
     */
    public Subject(String code) {
        this.code = code;
    }
}
