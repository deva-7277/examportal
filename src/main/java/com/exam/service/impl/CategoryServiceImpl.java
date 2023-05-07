package com.exam.service.impl;

import com.exam.model.exam.Category;
import com.exam.model.exam.Quiz;
import com.exam.repo.CategoryRepository;
import com.exam.repo.QuizRepository;
import com.exam.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private QuizRepository quizRepository;

    @Override
    public Category addCategory(Category category) {
        return this.categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Category category) {
        return this.categoryRepository.save(category);
    }

    @Override
    public Set<Category> getCategories() {
        return new LinkedHashSet<>(this.categoryRepository.findAll());
    }

    @Override
    public Category getCategory(Long categoryId) {
        return this.categoryRepository.findById(categoryId).get();
    }

    @Override
    public void deleteCategory(Long categoryId) {
        Category category = new Category();
        category.setCid(categoryId);
//        List<Quiz> quizList = quizRepository.findBycategory(category);
//        for(Quiz q: quizList){
//            quizRepository.deleteById(q.getqId());
//        }
        this.categoryRepository.deleteById(categoryId);
    }
}
