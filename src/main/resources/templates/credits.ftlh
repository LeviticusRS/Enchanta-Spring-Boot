<#include "includes/constants.ftlh">
<body>
<#include "includes/navigation.ftlh">
<#include "includes/header.ftlh">
<head>
    <script src='https://js.stripe.com/v3/'></script>
</head>
<div id="main">
    <div id="container" class="container-fluid">
        <div id="container-top"></div>
        <div id="content" class="blog">
            <!--BREADCRUMBS-->
            <div id="breadcrumbs-outer">
                <div id="breadcrumbs-inner">
                    <div class="left">
                        <a href="#"><img src="/assets/img/breadcrumbs.png"/>Back to Home</a>
                    </div>
                    <div class="social">
                        <a href="#"><img src="/assets/img/fb.png"/></a>
                        <a href="#"><img src="/assets/img/twitter.png"/></a>
                        <a href="#"><img src="/assets/img/youtube.png"/></a>
                    </div>
                </div>
            </div>
            <!--breadcrumbs end -->
            <div class="credits-content">
                <div class="row">
                    <div class="card-group" style="width:100%;">
                        <script type="text/javascript">
                            // Create an instance of the Stripe object with your publishable API key
                            const stripe = Stripe('${stripePublicKey}');
                        </script>
                        <#list packages as package>
                            <#setting number_format="number">
                            <div class="card grow">
                                <div class="card-img-top">
                                    <img src="/assets/img/store/${package.image()}.png" class="img-fluid"/>
                                </div>
                                <div class="card-body">
                                    <h5 class="card-title"
                                        style="color:white; text-shadow: 1px 1px lightgrey;">${package.credits()}
                                        Credits</h5>
                                    <p class="card-sub" style="color:greenyellow; text-shadow: 1px 1px lawngreen;">
                                        +${package.bonus()} Bonus</p>
                                </div>
                                <input type="hidden" name="product" value="#{package.productId()}">
                                <input type="hidden" name="id" value="${account.id()?int?c}">
                                <button class="enchanta-btn" id="checkout-button-#{package.productId()}" type="Submit">
                                    <#setting number_format="currency">
                                    <span class="glyphicon glyphicon-shopping-cart"
                                          aria-hidden="true"></span> ${package.price()} USD
                                    <#setting number_format="number">
                                </button>
                                <script type="text/javascript">
                                    const checkoutButton#{package.productId()} = document.getElementById('checkout-button-#{package.productId()}');

                                    checkoutButton#{package.productId()}.addEventListener('click', function () {
                                        // Create a new Checkout Session using the server-side endpoint you
                                        // created in step 3.
                                        fetch('/api/checkout-session', {
                                            method: 'POST',
                                            headers: new Headers({
                                                'Content-Type': 'application/x-www-form-urlencoded',
                                            }),
                                            body: "productId=#{package.productId()}&userId=${account.id()?int?c}"
                                        })
                                            .then(function (response) {
                                                if (response === "INVALID_PACKAGE") {
                                                    alert("Invalid Package");
                                                } else if (response === "INVALID_ACCOUNT") {
                                                    alert("Invalid Account");
                                                } else {
                                                    return response.json();
                                                }
                                            })
                                            .then(function (session) {
                                                return stripe.redirectToCheckout({sessionId: session.id});
                                            })
                                            .then(function (result) {
                                                if (result.error) {
                                                    alert(result.error.message);
                                                }
                                            })
                                            .catch(function (error) {
                                                console.error('Error:', error);
                                            });
                                    });
                                </script>
                            </div>
                        </#list>
                    </div>
                    <div class="card-group" style="width:100%;">
                        <#list bondPackages as package>
                            <#setting number_format="number">
                            <div class="card grow">
                                <div class="card-img-top">
                                    <img src="/assets/img/store/${package.image()}.png" class="img-fluid"/>
                                </div>
                                <div class="card-body">
                                    <h5 class="card-title"
                                        style="color:white; text-shadow: 1px 1px lightgrey;">$${package.credits()} Bond</h5>
                                </div>
                                <input type="hidden" name="product" value="#{package.productId()}">
                                <input type="hidden" name="id" value="${account.id()?int?c}">
                                <button class="enchanta-btn" id="checkout-button-#{package.productId()}" type="Submit">
                                    <#setting number_format="currency">
                                    <span class="glyphicon glyphicon-shopping-cart"
                                          aria-hidden="true"></span> ${package.price()} USD
                                    <#setting number_format="number">
                                </button>
                                <script type="text/javascript">
                                    const checkoutButton#{package.productId()} = document.getElementById('checkout-button-#{package.productId()}');

                                    checkoutButton#{package.productId()}.addEventListener('click', function () {
                                        // Create a new Checkout Session using the server-side endpoint you
                                        // created in step 3.
                                        fetch('/api/checkout-session', {
                                            method: 'POST',
                                            headers: new Headers({
                                                'Content-Type': 'application/x-www-form-urlencoded',
                                            }),
                                            body: "productId=#{package.productId()}&userId=${account.id()?int?c}"
                                        })
                                            .then(function (response) {
                                                if (response === "INVALID_PACKAGE") {
                                                    alert("Invalid Package");
                                                } else if (response === "INVALID_ACCOUNT") {
                                                    alert("Invalid Account");
                                                } else {
                                                    return response.json();
                                                }
                                            })
                                            .then(function (session) {
                                                return stripe.redirectToCheckout({sessionId: session.id});
                                            })
                                            .then(function (result) {
                                                if (result.error) {
                                                    alert(result.error.message);
                                                }
                                            })
                                            .catch(function (error) {
                                                console.error('Error:', error);
                                            });
                                    });
                                </script>
                            </div>
                        </#list>
                    </div>
                </div>
            </div>
        </div>
        <div id="container-bottom"></div>
    </div>
</div>
</body>
<#include "includes/footer.ftlh">