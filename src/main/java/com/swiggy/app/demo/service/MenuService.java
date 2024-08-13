package com.swiggy.app.demo.service;

import com.swiggy.app.demo.Dto.MenuDto;

import java.util.List;

/**
 * @author
 **/
public interface MenuService {

    MenuDto createMenu(MenuDto menuDto);

    MenuDto getMenuById(Long id);

    List<MenuDto> getAllMenus();

    MenuDto updateMenu(Long id, MenuDto menuDto);

    void deleteMenu(Long id);
}
