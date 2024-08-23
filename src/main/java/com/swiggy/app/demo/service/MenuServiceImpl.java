package com.swiggy.app.demo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swiggy.app.demo.Dto.MenuDto;
import com.swiggy.app.demo.Exception.ResourceNotFoundException;
import com.swiggy.app.demo.entity.Menu;
import com.swiggy.app.demo.repository.MenuRepo;
import com.swiggy.app.demo.repository.RestaurantRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author
 **/
@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuRepo menuRepository;
     @Autowired
    private RestaurantRepo restaurantRepo;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public MenuDto createMenu(MenuDto menuDto) {
        Menu menu = objectMapper.convertValue(menuDto, Menu.class);
        menu = menuRepository.save(menu);
        return objectMapper.convertValue(menu, MenuDto.class);
    }

    @Override
    public MenuDto getMenuById(Long id) {
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu not found"));
        return objectMapper.convertValue(menu, MenuDto.class);
    }

    @Override
    public List<MenuDto> getAllMenus() {
        List<Menu> menus = menuRepository.findAll();
        return menus.stream()
                .map(menu -> objectMapper.convertValue(menu, MenuDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public MenuDto updateMenu(Long id, MenuDto menuDto) {
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu not found"));
        menu.setName(menuDto.getName());
        menu.setCategory(menuDto.getCategory()); // Update category
        menu = menuRepository.save(menu);
        return objectMapper.convertValue(menu, MenuDto.class);
    }

    @Override
    public void deleteMenu(Long id) {
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu not found"));
        menuRepository.delete(menu);
    }
}