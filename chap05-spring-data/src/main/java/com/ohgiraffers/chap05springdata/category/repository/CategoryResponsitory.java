package com.ohgiraffers.chap05springdata.category.repository;

import com.ohgiraffers.chap05springdata.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@Repository
public interface CategoryResponsitory extends JpaRepository<Category, Integer> {
    Category findByCategoryCode(int categoryCode); // findBy까지는 정해진 키워드.. 그뒤 내용을 WHERE 문으로 작성해 준다..

    Category findByCategoryName(String categoryName);

}
