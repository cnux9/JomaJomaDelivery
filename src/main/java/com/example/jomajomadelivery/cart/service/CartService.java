package com.example.jomajomadelivery.cart.service;

import com.example.jomajomadelivery.account.exception.LoginErrorCode;
import com.example.jomajomadelivery.cart.dto.response.CartResponseDto;
import com.example.jomajomadelivery.cart.entity.Cart;
import com.example.jomajomadelivery.cart.entity.CartStatus;
import com.example.jomajomadelivery.cart.exception.CartErrorCode;
import com.example.jomajomadelivery.cart.repository.CartRepository;
import com.example.jomajomadelivery.exception.CustomException;
import com.example.jomajomadelivery.user.entity.User;
import com.example.jomajomadelivery.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;

    @Transactional
    public CartResponseDto getUserCart(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()->new CustomException(LoginErrorCode.NEED_LOGIN));
        Optional<Cart> foundOptionalCart = cartRepository.findCartByUserAndStatus(user, CartStatus.ORDERING);
        Cart foundCart = foundOptionalCart.orElseGet(() ->
                cartRepository.save(Cart.newCart(user))
        );
        return CartResponseDto.toDto(foundCart);
    }

    @Transactional(readOnly = true)
    public CartResponseDto find(Long cartId) {
        Cart foundCart = getByCartId(cartId);
        return CartResponseDto.toDto(foundCart);
    }

    public Cart getByCartId(Long cartId) {
        return cartRepository.findCartByCartIdAndStatus(cartId, CartStatus.ORDERING).orElseThrow(() -> new CustomException(CartErrorCode.CART_NOT_FOUND));
    }

    @Transactional
    public void delete(Long cartId) {
        Cart cart = getByCartId(cartId);
        // TODO: Item이 DB에서 삭제 되는지 확인
        cart.getItems().clear();
    }

}
