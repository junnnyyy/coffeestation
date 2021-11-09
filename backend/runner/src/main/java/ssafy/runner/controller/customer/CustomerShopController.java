package ssafy.runner.controller.customer;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.io.ParseException;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ssafy.runner.domain.dto.customer.ShopAndMenuResponseDto;
import ssafy.runner.domain.dto.menu.MenuNSizeNExtraResponseDto;
import ssafy.runner.domain.dto.shop.SearchShopResponseDto;
import ssafy.runner.domain.dto.shop.ShopBriefResponseDto;
import ssafy.runner.service.MenuService;
import ssafy.runner.service.ShopService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Api(tags = {"Customer 카페 상세 조회"})
@RequestMapping("/api/customer/shop")
public class CustomerShopController {

    private final ShopService shopService;
    private final MenuService menuService;

    @GetMapping("")
    @ApiOperation(value = "근처 카페 조회")
    public ResponseEntity<List<ShopBriefResponseDto>> nearShopList(@RequestParam("x") double x,
                                                                   @RequestParam("y") double y,
                                                                   @RequestParam("radius") double radius) throws ParseException {
        List<ShopBriefResponseDto> shopList = shopService.findNearShopList(x, y, radius);
        if (shopList.isEmpty()) throw new NullPointerException("근처에 카페 없어요");
        return new ResponseEntity<>(shopList, HttpStatus.OK);
    }

    @GetMapping("/{shopId}")
    @ApiOperation(value = "가게 정보 및 메뉴리스트 조회")
    public ShopAndMenuResponseDto getShopAndMenu(@PathVariable("shopId") Long shopId) {

        return shopService.getShopAndMenu(shopId);
    }

    @GetMapping("/{shopId}/menu/{menuId}")
    @ApiOperation(value = "단일 메뉴 상세 조회")
    public MenuNSizeNExtraResponseDto getMenuDetail(@PathVariable("shopId") Long shopId, @PathVariable("menuId") Long menuId) {

        return menuService.getMenuDetail(shopId, menuId);
    }

    @GetMapping("/search")
    @ApiOperation(value = "가게 검색")
    public List<SearchShopResponseDto> searchShop(@RequestParam("q") String searchWord) {

        List<SearchShopResponseDto> searchShopResponseDto = shopService.searchShop(searchWord);
        return searchShopResponseDto;
    }
}
