package com.bikkadit.electronicstrore.entities;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name="categories")
public class Category {

    @Id
    @Column(name="id")
    private String categoryId;

    @Column(name="category_desc", length=50)
    private String description;

    @Column(name="category_title", length=50)
    private String title;

    private String coverImage;



}
