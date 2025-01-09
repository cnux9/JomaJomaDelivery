package com.example.jomajomadelivery.item.service;

import com.example.jomajomadelivery.cart.dto.requestDto.AddCartRequestDto;
import com.example.jomajomadelivery.cart.entity.Cart;
import com.example.jomajomadelivery.item.dto.response.ItemResponseDto;
import com.example.jomajomadelivery.item.entity.Item;
import com.example.jomajomadelivery.item.repository.ItmeRepositoy;
import com.example.jomajomadelivery.menu.entity.Menu;
import com.example.jomajomadelivery.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItmeRepositoy itmeRepositoy;
    private final MenuRepository menuRepository;

    public List<ItemResponseDto> findAllItem(Long cartId) {
        itmeRepositoy.findAllByCart_CartId(cartId);
        return null;
    }

    public void addItem(AddCartRequestDto dto, Cart cart) {
        Menu menu = menuRepository.findById(dto.menu_id()).get();

        Item item = Item.selectItme(cart, menu, dto.quantity());
        itmeRepositoy.save(item);

    }
}
