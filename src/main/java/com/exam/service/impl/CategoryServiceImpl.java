package com.exam.service.impl;

import com.exam.model.exam.Category;
import com.exam.model.exam.Quiz;
import com.exam.repo.CategoryRepository;
import com.exam.repo.QuizRepository;
import com.exam.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
@CacheConfig(cacheNames =  {"categoryCache"})
public class CategoryServiceImpl implements CategoryService {

    private static final Logger log = LoggerFactory.getLogger(CategoryServiceImpl.class);



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
    @Cacheable(cacheNames = "category", key = "'#setOfCategories'" )
    public Set<Category> getCategories() {
        log.info("starting of get categories service");
        return new LinkedHashSet<>(this.categoryRepository.findAll());
    }

    @Override
    @Cacheable(cacheNames = "category", key = "'#singleCategory'" )
    public Category getCategory(Long categoryId) {
        log.info("starting of get category service");
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
