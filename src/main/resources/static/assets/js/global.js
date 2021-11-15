typeof NeoJS !== "undefined" ? (function($) {

    var trigger_header_dropdown = $.select("trigger--header-dropdown", "class");

    var header_dropdown = $.select(".header__dropdown");

    var body = $.select("body");

    var sliders = $.select("body-section__slider", "class");

    var dropdowns = $.select("input-dropdown", "class");

    trigger_header_dropdown.call("bind", "click", function(e) {

        e.preventDefault();

        header_dropdown.toggleClass("header__dropdown--show");

    });

    body.bind("click", function(e) {

        var target = $.select(e.target);

        if(!$.isNode(target.parent(".header__menu")) && !$.isNode(target.parent(".header__dropdown"))) {
            header_dropdown.removeClass("header__dropdown--show");
        }

        if(!$.isNode(target.parent(".input-dropdown"))) {
            dropdowns.call("removeClass", "input-dropdown--show");
        }

    });

    sliders.each(function(slider) {

        var item_container = slider.select(".body-section__slider__items");

        var items = item_container.select("body-section__slider__items__item", "class");

        var controls = slider.select(".body-section__slider__controls");

        var left_button = controls.select(".body-section__slider__controls__chevron--left");

        var right_button = controls.select(".body-section__slider__controls__chevron--right");

        function current_index() {

            var index = 0;

            items.each(function(item, i) {

                if(item.hasClass("body-section__slider__items__item--active")) {
                    index = i;
                    return true;
                }

                return false;

            });

            return index;

        }

        function slide(index) {

            if(index < 0) {
                index = items.size() - 1;
            } else if(index >= items.size()) {
                index = 0;
            }

            item_container.css("text-indent", -(index * "100")+"%");
            items.call("removeClass", "body-section__slider__items__item--active");
            items.get(index).addClass("body-section__slider__items__item--active");

        }

        left_button.bind("click", function(e) {

            e.preventDefault();

            slide(current_index() - 1);

        });

        right_button.bind("click", function(e) {

            e.preventDefault();

            slide(current_index() + 1);

        });

    });

    dropdowns.each(function(dropdown) {

        var container = dropdown.select(".input-dropdown__container");
        
        var options = dropdown.select("input-dropdown__options__option", "class");

        var selected = dropdown.select(".input-dropdown__selected");

        container.bind("click", function(e) {

            e.preventDefault();

            dropdowns.exclude(dropdown).call("removeClass", "input-dropdown--show");
            dropdown.toggleClass("input-dropdown--show");

        });
        
        options.call("bind", "click", function(e) {
           
            e.preventDefault();
            
            var option = e.node;
            
            dropdown.data("value", option.data("value"));
            selected.inner(option.inner());
            dropdowns.call("removeClass", "input-dropdown--show");
            
        });

    });

})(NeoJS) : "";