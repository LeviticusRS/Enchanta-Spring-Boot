<#setting locale="en_US">
<#include "includes/constants.ftlh">
<body>
<#include "includes/navigation.ftlh">
<#include "includes/header.ftlh">
<div id="main">
    <div id="container" class="container-fluid">
        <div id="container-top"></div>
        <div id="content">

            <div id="breadcrumbs-outer">
                <div id="breadcrumbs-inner">
                    <div class="left">
                        <img src="/assets/img/breadcrumbs.png" alt=""/>
                        <h3>Set Email</h3>
                    </div>
                    <div class="social">
                        <a href="#"><img src="/assets/img/fb.png" alt=""/></a>
                        <a href="#"><img src="/assets/img/twitter.png" alt=""/></a>
                        <a href="#"><img src="/assets/img/youtube.png" alt=""/></a>
                    </div>
                </div>
            </div>
            <div class="left-panel col-md-6">
                <form method="post">
                    <p>Before you can proceed with anything, you must update your email in your account.
                        Fill in the email form below and submit to continue.</p>
                    <p><b>WE CANNOT GUARANTEE THE SECURITY OF YOUR ACCOUNT IF THIS IS NOT A VALID EMAIL!</b></p>

                    <div class="form-group">
                        <label for="email">Username</label>
                        <label>
                            <input type="email" name="email" class="form-control" placeholder="your@email.com"
                                   required="required">
                        </label>
                    </div>
                    <input type="submit" class="enchanta-btn"><br/>
                </form>
            </div>
        </div>
        <div id="container-bottom"></div>
    </div>
</div>
</body>
<#include "includes/footer.ftlh">
