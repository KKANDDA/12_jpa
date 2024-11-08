package com.ohgiraffers.chap05springdata.category.controller;

import com.ohgiraffers.chap05springdata.category.entity.Category;
import com.ohgiraffers.chap05springdata.category.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/category") //  '/*'을 생략하는 편이 좋다..
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/select")
    public List<Category> selectAllCategory(){
        List<Category> categoryList = categoryService.selectAllCategory();

        return categoryList;
    }

    /*
    * 이 방법은 Spring이 자동으로 HTTP 응답을 생성한다.
    * List<Category>를 반환하면 Spring은 이를 JSON 배열로 반환하여 클라이언트로 전송한다.
    *
    * Spring은 기본적으로 200 OK를 사용하여 응답을 처리한다.
    * 헤더: 기본적을 application/json, UTF-8이 자동 설정된다.
    * 하지만 응답 상태 코드나 헤더를 세밀하게 조정할 수 없다.
    * 예를 들어 특정 조건에서 다른 HTTP 상태 코드를 반환하고 싶거나, 헤더를 추가하고 싶다면 이 방식은 적합하지 않다.
    * */

    @GetMapping("/select/{categoryCode}")
    public ResponseEntity<Object> selectCategoryByCode(@PathVariable int categoryCode){
        try{
            Category category = categoryService.selectCategoryByCode(categoryCode);

            if(category != null){
                return ResponseEntity.ok(category); // 200 ok
            }else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("존재하지 않는 카테고리 코드");
            }

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버에서 오류가 발생했습니다.");
        }
    }

    @PostMapping("/insert")
    public ResponseEntity<Object> insertCategory(@RequestBody HashMap<String,String> categoryName){

        // 1. 유효성 검사
        if(Objects.isNull(categoryName)){
            return ResponseEntity.status(404).body("이름은 필수입니다.");
        }

        // 카테고리 저장 서비스 호출
        Category result = categoryService.insertCategory(categoryName.get("categoryName"));

        // 3. 카테고리 저장 실패 처리
        if(Objects.isNull(result)){
            return ResponseEntity.status(500).body("서버측에서 오류 발생");
        }
        // 성공시 처리
        return ResponseEntity.ok(result);
    }

    @PostMapping("/update")
    public ResponseEntity<Object> updateCategory(@RequestBody HashMap<String,Object> category){

        // 유효성 검사
        if(Objects.isNull(category.get("categoryCode"))){
            return ResponseEntity.status(404).body("변경할 카테고리의 코드 번호를 입력해 주세요.");
        } else if (Objects.isNull(category.get("categoryName"))) {
            return ResponseEntity.status(404).body("카테고리의 이름을 입력해 주세요.");
        }
        Integer categoryCode = (Integer) category.get("categoryCode");
        String categoryName = (String) category.get("categoryName");

        // 카테고리 코드와 바꿀 이름을 서비스로 보내서 해당 코드와 바뀐 이름을 받는다.
        Category result = categoryService.updateCategory(categoryCode, categoryName);

        // 업데이트 실패시 처리
        if(Objects.isNull(result)){
            return ResponseEntity.status(500).body("서버측에서 오류 발생");
        }

        // 업데이트 성공시 처리
        return ResponseEntity.ok(result);
    }

    // 카테고리 삭제
    @PostMapping("/delete")
    public ResponseEntity deleteCategory(@RequestBody HashMap<String,Integer> category){
        Integer categoryCode = category.get("categoryCode");

        if(categoryCode == null){
            return ResponseEntity.status(404).body("카테고리 고드를 입력하세요.");
        }
        boolean isDeleted = categoryService.deleteCategory(categoryCode);

        if(!isDeleted){
            return ResponseEntity.status(500).body("해당카테고리를 찾을 수 없습니다.");
        }
        return ResponseEntity.ok("성공적으로 삭제되었습니다.");
    }
}
