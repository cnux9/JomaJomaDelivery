package com.example.jomajomadelivery.cart.service;

import com.example.jomajomadelivery.cart.dto.requestDto.AddCartRequestDto;
import com.example.jomajomadelivery.cart.entity.Cart;
import com.example.jomajomadelivery.cart.entity.CartStatus;
import com.example.jomajomadelivery.cart.repository.CartRepository;
import com.example.jomajomadelivery.item.entity.Item;
import com.example.jomajomadelivery.item.repository.ItmeRepositoy;
import com.example.jomajomadelivery.menu.entity.Menu;
import com.example.jomajomadelivery.menu.repository.MenuRepository;
import com.example.jomajomadelivery.user.entity.User;
import com.example.jomajomadelivery.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final MenuRepository menuRepository;
    private final ItmeRepositoy itmeRepositoy;

    public void addCart(AddCartRequestDto dto) {
        User user = userRepository.findById(1L).get();
        Cart cart = cartRepository.findCartByUserAndStatus(user, CartStatus.ORDERING).orElseGet(() ->
                cartRepository.save(Cart.newCart(user))
        );
        Menu menu = menuRepository.findById(dto.menu_id()).get();

        Item item = Item.selectItme(cart,menu,dto.quantity());
        itmeRepositoy.save(item);

    }
}
