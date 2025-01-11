package com.example.jomajomadelivery.item.service;

import com.example.jomajomadelivery.cart.dto.request.AddCartRequestDto;
import com.example.jomajomadelivery.cart.entity.Cart;
import com.example.jomajomadelivery.cart.service.CartService;
import com.example.jomajomadelivery.exception.CustomException;
import com.example.jomajomadelivery.item.entity.Item;
import com.example.jomajomadelivery.item.exception.ItemErrorCode;
import com.example.jomajomadelivery.item.repository.ItmeRepositoy;
import com.example.jomajomadelivery.menu.entity.Menu;
import com.example.jomajomadelivery.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItmeRepositoy itmeRepositoy;
    private final MenuService menuService;
    private final CartService cartService;

    public void addItem(Long cartId, AddCartRequestDto dto) {
        Menu menu = menuService.getMenuEntity(dto.menuId());
        Cart cart = cartService.getByCartId(cartId);

        // 다른 가게일 때 장바구니 비우기
        checkSameStore(cart, menu);

        Item item = Item.selectItme(cart, menu, dto.quantity());
        itmeRepositoy.save(item);
    }


//    @Transactional(readOnly = true)
//    public List<ItemResponseDto> findAllItem() {
//        // TODO: 로그인된 유저
//        Long userId = 1L;
//        User user = userRepository.findById(userId).get();
//
//        Cart cart = cartRepository.findCartByUserAndStatus(user, CartStatus.ORDERING).orElseThrow(() -> new CustomException(ItemErrorCode));
//        List<Item> itemList = itmeRepositoy.findAllByCart(cart);
//        return itemList.stream().map(ItemResponseDto::toDto).toList();
//    }
    @Transactional
    public void updateQuantity(Long itemId, int quantity) {
        Item item = getItem(itemId);
        item.updateQuantity(quantity);
    }

    public void deleteItem(Long itemId) {
        itmeRepositoy.deleteById(itemId);
    }

    private Item getItem(Long itemId) {
        return itmeRepositoy.findById(itemId).orElseThrow(() -> new CustomException(ItemErrorCode.ITEM_NOT_FOUND));
    }

    private void checkSameStore(Cart cart, Menu menu) {
        Long cartStoreId = cart.getItems().get(0).getMenu().getStore().getStoreId();
        Long newItemStoreId = menu.getStore().getStoreId();
        if (!cartStoreId.equals(newItemStoreId)) {
            cartService.delete(cart.getCartId());
        }
    }
}
