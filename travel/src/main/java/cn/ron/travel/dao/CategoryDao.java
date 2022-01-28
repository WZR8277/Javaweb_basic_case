package cn.ron.travel.dao;

import cn.ron.travel.domain.Category;

import java.util.List;

public interface CategoryDao {

    public List<Category> findAll();
}
