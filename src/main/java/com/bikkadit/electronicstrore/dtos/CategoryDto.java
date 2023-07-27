package com.bikkadit.electronicstrore.dtos;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class CategoryDto {

    private String categoryId;

    @NotBlank(message="Description is required")
    private String description;

    @NotBlank
    @Min(value=4,message="title must be of 4 characters")
    private String title;

    private String coverImage;
}
