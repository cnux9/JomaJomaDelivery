package com.example.jomajomadelivery.item.service;

import com.example.jomajomadelivery.cart.dto.requestDto.AddCartRequestDto;
import com.example.jomajomadelivery.cart.entity.Cart;
import com.example.jomajomadelivery.cart.entity.CartStatus;
import com.example.jomajomadelivery.cart.repository.CartRepository;
import com.example.jomajomadelivery.item.dto.response.ItemResponseDto;
import com.example.jomajomadelivery.item.entity.Item;
import com.example.jomajomadelivery.item.repository.ItmeRepositoy;
import com.example.jomajomadelivery.menu.entity.Menu;
import com.example.jomajomadelivery.menu.repository.MenuRepository;
import com.example.jomajomadelivery.user.entity.User;
import com.example.jomajomadelivery.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItmeRepositoy itmeRepositoy;
    private final CartRepository cartRepository;
    private final MenuRepository menuRepository;
    private final UserRepository userRepository;


    public void addItem(AddCartRequestDto dto, Cart cart) {
        Menu menu = menuRepository.findById(dto.menu_id()).get();
        Item item = Item.selectItme(cart, menu, dto.quantity());
        itmeRepositoy.save(item);
    }

    @Transactional(readOnly = true)
    public List<ItemResponseDto> findAllItem() {
        User user = userRepository.findById(1L).get();
        Cart cart = cartRepository.findCartByUserAndStatus(user,CartStatus.ORDERING).orElseThrow(()-> new RuntimeException());
        List<Item> itemList = itmeRepositoy.findAllByCart(cart);
        return itemList.stream().map(ItemResponseDto::toDto).toList();
    }

    @Transactional
    public void updateQuantity(Long itemId, int quantity) {
        Item item = itmeRepositoy.findById(itemId).get();
        item.updateQuantity(quantity);
    }

    public void deleteItem(Long itemId) {
        Item item = itmeRepositoy.findById(itemId).get();
        itmeRepositoy.delete(item);
    }
}
