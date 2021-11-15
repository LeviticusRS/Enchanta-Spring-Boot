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
                        <h3>Home</h3>
                    </div>
                    <div class="social">
                        <a href="#"><img src="/assets/img/fb.png" alt=""/></a>
                        <a href="#"><img src="/assets/img/twitter.png" alt=""/></a>
                        <a href="#"><img src="/assets/img/youtube.png" alt=""/></a>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="left-panel col-md-6">
                    <form method="post">
                        <span class="realism">${error!}</span>
                        <div class="form-group">
                            <label for="registerUsername">Username</label>
                            <label>
                                <input type="text" name="username" class="form-control" placeholder="Your Username"
                                       required="required">
                            </label>
                        </div>
                        <div class="form-group">
                            <label for="exampleInputPassword1">Password</label>
                            <label>
                                <input type="password" name="password" class="form-control invalid"
                                       placeholder="Your Password" required="required">
                            </label>
                        </div>
                        <input type="hidden" name="server">
                        <button type="submit" name="login" class="enchanta-btn">LOGIN TO ENCHANTA</button>
                        <p><span class="text-muted">Please sign in with your in-game account.</span></p>
                    </form>
                </div>
                <div class="right-panel col-md-6">
                    <div id="download-btns">
                        <a href="#">
                            <div class="enchanta-btn">Download Enchanta</div>
                        </a>
                        <a href="#">
                            <div class="enchanta-btn">Download Java</div>
                        </a>
                    </div>
                    <hr>
                    <h3>Need Help?</h3>
                    <hr>
                    <p><a href="#">My username is already registered!</a></p>
                    <p><a href="#">I didn't receive a confirmation email.</a></p>
                    <p><a href="#">My password is insecure. What should I do?</a></p>
                </div>
            </div>
        </div>
        <div id="container-bottom"></div>
    </div>
</div>
</body>
<#include "includes/footer.ftlh">
