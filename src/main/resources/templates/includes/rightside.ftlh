<div class="col-lg-4 col-xl-3">

    <div class="credits">
        <h1>${account.credits()}</h1>
        <h2>Credits</h2>
        <p>Need more? Click below to purchase more</p>
        <a href="/credits/stripe/checkout"><div class="enchanta-btn">Buy More<#-- - <b style="color:greenyellow;">10% Bonus</b>--></div></a>
        <#-- <a href="/credits/crypto/checkout"><div class="enchanta-btn">Crypto - <b style="color:greenyellow;">15% Bonus</b></div></a>
         <a href="/credits/paypal/checkout"><div class="enchanta-btn">Paypal</div></a>-->
    </div>

    <div class="cart my-4">
        <h1 class="store-side-title">Pending</h1>
        <ul class="list-group" id="cart-items">
            <#list pending>
                <p>To claim pending purchases, type ::claim in-game!</p>

                <ul>
                    <#items as order>
                        <li>${order.quantity}x of ${order.name} </li>
                    </#items>
                </ul>
            <#else>
                <p>There are no orders awaiting collection.</p>
            </#list>
        </ul>
    </div>

    <div class="cart my-4">
        <h1 class="store-side-title">Cart</h1>
        <ul class="list-group" id="cart-items">
            <#list cart.products()>
            <#items as cartItem>
                <li class="list-group-item">
                    <span class="pull-left">${cartItem.quantity() * cartItem.itemAmount()} x ${cartItem.name()}</span>
                    <a><span class="remove-item" data-cart-clear="${cartItem.id()}"><i class="fa fa-times-circle"></i></span></a>
                </li>
            </#items>
            <#else>
            <p>Your cart is currently empty.</p>
            </#list>
        </ul>
        <h3 class="total-price">Total: ${cart.totalPrice()} Credits</h3>
        <a onclick="clearCart()"><div class="enchanta-btn">Clear Cart</div></a>
        <a onclick="completeCheckout()"><div class="enchanta-btn">Checkout</div></a></br>
    </div>
</div>
<script>
    $('[data-cart-clear]').on('click', function(e) {
        $.get('/store/cart/removeall/' + $(this).data('cart-clear')).always(function() {
            window.location.reload();
        });
    });

    function clearCart() {
        if (confirm("Are you sure you want to clear your cart?")) {
            window.location = '/store/cart/clear'
        }
    }

    function completeCheckout() {
        if (confirm("Are you sure you want to complete your checkout?")) {
            window.location = 'store/cart/checkout'
        }
    }
</script>