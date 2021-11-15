typeof NeoJS !== "undefined" ? (function($) {

    var trigger_realms = $.select("trigger--store-realm", "class");

    var store_section = $.select(".store-section");

    var store_realm = $.select(".store-realm");

    var items = $.select("store-section__items__item", "class");

    var cart_items = $.select(".store-cart__items");

    var trigger_clear = $.select("trigger-clear-cart", "class");

    var total_prices = $.select("store-cart__total__price", "class");

    var total_items = $.select("store-cart__total__items", "class");

    var categories = $.select("store-section__category__item", "class");
    
    var section_items = $.select("store-section__items", "class");

    trigger_realms.call("bind", "click", function(e) {

        e.preventDefault();

        var button = e.node;

        var hash = button.origin.hash;

        var realm = hash.substring(1);

        store_section.removeClass([ "store-section--economy", "store-section--pvp" ]).addClass("store-section--"+realm);
        store_realm.removeClass([ "store-realm--economy", "store-realm--pvp" ]).addClass("store-realm--"+realm);
        window.history.pushState(null, null, hash);

    });

    function update_price() {

        var _items = cart_items.select("store-cart__items__item", "class");

        var total_price = 0.00;

        var total_quantity = 0;

        _items.each(function(item) {

            var price = parseFloat(item.data("price"));

            var quantity = parseInt(item.data("amount"));

            total_price += (price * quantity);
            total_quantity += quantity;

        });

        total_prices.call("inner", total_price.toFixed(2).toString().replace(".", ","));
        total_items.call("inner", total_quantity.toString());

    }

    items.each(function(item) {

        var add_cart = item.select(".store-section__items__item__add-cart .icon");

        var dropdown = item.select(".input-dropdown");

        add_cart.bind("click", function(e) {

            e.preventDefault();

            var amount = parseInt(dropdown.data("value"));

            var item_price = parseFloat(item.data("price"));

            var _item = $.create("div", { class: "store-cart__items__item", "data-price": item_price, "data-amount": amount });

            var container = $.create("div", { class: "store-cart__items__item__container" });

            var remove = $.create("div", { class: "store-cart__items__item__remove" }).bind("click", function(e) {

                e.preventDefault();

                _item.remove();
                update_price();

            });

            var remove_icon = $.create("i", { class: "icon icon--small-cross" });

            var icon = $.create("figure", { class: "store-cart__items__item__icon pull-left" });

            var image = $.create("img", { src: "img/store/present_icon.png" });

            var price = $.create("div", { class: "store-cart__items__item__price pull-right", inner: amount+" for <strong class=\"store-cart__items__item__price__amount\">"+(item_price * amount).toFixed(2).toString().replace(".", ",")+"</strong>$" });

            cart_items.append(_item.append(container.append(icon.append(image).append(remove.append(remove_icon))).append(price).append($.create("div", { class: "clear-fix" }))));

            update_price();

        });

    });

    trigger_clear.call("bind", "click", function(e) {

        e.preventDefault();

        cart_items.select("store-cart__items__item", "class").call("remove");
        update_price();

    });

    categories.call("bind", "click", function(e) {

        e.preventDefault();

        categories.call("removeClass", "store-section__category__item--selected");
        e.node.addClass("store-section__category__item--selected");
        section_items.call("removeClass", "store-section__items--selected").filter(function(section_item) {
            return section_item.data("category") == e.node.data("category");
        }).call("addClass", "store-section__items--selected");

    });

    cart_items.select(".store-cart__items__item .store-cart__items__item__remove", "query_all").call("bind", "click", function(e) {

        e.preventDefault();

        var item = e.node.parent(".store-cart__items__item");

        if($.isNode(item)) {
            item.remove();
            update_price();
        }

    });

})(NeoJS) : "";