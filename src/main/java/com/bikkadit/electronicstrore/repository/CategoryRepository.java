package com.bikkadit.electronicstrore.repository;

import com.bikkadit.electronicstrore.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository <Category,String> {
}
