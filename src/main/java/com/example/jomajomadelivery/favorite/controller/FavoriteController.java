package com.example.jomajomadelivery.favorite.controller;

import com.example.jomajomadelivery.favorite.service.FavoriteService;
import com.example.jomajomadelivery.user.entity.RoleConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/favorites")
@RequiredArgsConstructor
@Secured(RoleConstants.ROLE_USER)
public class FavoriteController {
    private final FavoriteService favoriteService;

    @GetMapping
//    public List<StoreResponseDto> find(Model model) {
    public List<Long> find(Model model) {
//        List<StoreResponseDto> favoriteStores = favoriteService.find();
        List<Long> favoriteStoreIds = favoriteService.find();

        return favoriteStoreIds;

//        model.addAttribute("favoriteStores", favoriteStores);
//        return "Favorites";
    }

}
