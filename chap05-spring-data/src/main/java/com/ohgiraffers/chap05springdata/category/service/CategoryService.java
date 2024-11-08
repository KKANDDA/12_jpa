package com.ohgiraffers.chap05springdata.category.service;

import com.ohgiraffers.chap05springdata.category.entity.Category;
import com.ohgiraffers.chap05springdata.category.repository.CategoryResponsitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class CategoryService {
    @Autowired
    private CategoryResponsitory categoryResponsitory;

    public List<Category> selectAllCategory() {

        List<Category> categoryList = categoryResponsitory.findAll();
        if (categoryList.isEmpty()) {
            return null;
        }
        return categoryList;
    }

    public Category selectCategoryByCode(int categoryCode) {
        Category category = categoryResponsitory.findByCategoryCode(categoryCode);

        if(Objects.isNull(category)){
            return null;
        }
        return category;
    }

    public Category insertCategory(String categoryName) {

        // 이름 중복 체크
        Category foundCategory = categoryResponsitory.findByCategoryName(categoryName);

        if(!Objects.isNull(foundCategory)){
            return null;
        }

        Category insertCategory = new Category();
        insertCategory.setCategoryName(categoryName);

        Category result = categoryResponsitory.save(insertCategory); // 바로 db에 저장..
        /*
        save()는 jpq에서 EntityManager를 통해 데이터를 저장하는 메소드
        기본적으로 , jpq는 트렌젝션 내에서 자동으로 커밋을 처리한다.
        새로운 엔티티의 경우:           db에 저장하고 저장한 객체 반환
        기존에 존재하는 엔티티의 경우:   해당 엔티티의 전보를 업데이트하고 업데이트한 객체 반환
        * */
        return result;
    }

    public Category updateCategory(Integer categoryCode, String categoryName) {

        // 이름이 수정되었는지 체크
        Category foundCategory = categoryResponsitory.findByCategoryCode(categoryCode);
        foundCategory.setCategoryName(categoryName);
        Category result = categoryResponsitory.save(foundCategory);
        return result;

    }

    public boolean deleteCategory(Integer categoryCode) {

        Category category = categoryResponsitory.findById(categoryCode).orElse(null);
        // 변수에 카테고리 코드를 조회해서 쿼리의 결과값을 category에 넣겠다. 혹 결과가 null이면 findById는 Optional이기에 category에 null을 넣겠다.

        if(category == null){
            return false;
        }
        categoryResponsitory.delete(category); // 딜리트는 리턴값이 void이다. 불린이 아니다. 그래서 위에서 체크하고 return을 펄스로 때렸다.
        return true;

    }
}
