<div class="row justify-content-center p-1">
    <#list products as product>
        <#if product.type == 1>
            <div class="col-sm-6 col-md-4 col-xl-3 my-2">
                <div class="card grow" style="width: 18rem;" data-toggle="tooltip" data-placement="bottom" title="${product.description}">
                     <#setting number_format="0" />
                    <img src="/assets/img/store/items/${product.image}" class="card-img-top" alt="${product.name}" />
                    <#setting number_format="" />
                    <div class="card-body">
                        <h5 class="card-title">${product.name}</h5>
                        <#if product.discount_price gt 0>
                            <p class="card-sub" style="color:greenyellow;">${product.price - product.discount_price} Credits</p>
                        <#else>
                            <p class="card-sub">${product.price} Credits</p>
                        </#if>
                        <form method="POST" action="/store/cart/add">
                            <input type="hidden" name="product" value="#{product.id}">
                            <div class="amount">
                                <input type="text" value="1" name="amount">
                            </div>
                            <button type="submit" class="enchanta-btn">Add to Cart</button>
                        </form>
                    </div>
                </div>
            </div>
        </#if>
    </#list>
</div>