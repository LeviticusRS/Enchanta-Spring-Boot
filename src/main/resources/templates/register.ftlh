<#setting locale="en_US">
<#include "includes/constants.ftlh">
<body>
<#include "includes/navigation.ftlh">
<#include "includes/header.ftlh">
<script src='https://www.google.com/recaptcha/api.js'></script>
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
                    <#if success?? && success[0] == "true">
                        <p class="text-center" style="color: rgba(166, 211, 8, 0.8)">Congratulations! Your game account
                            has been created.<br/>You can now download the game.</p><br><br>
                            <#if creditsAdded??>
                                <p class="text-center" style="color: rgba(166, 211, 8, 0.8)">Your account has been pre-loaded
                                with ${creditsAdded[0]} credits as being a past Cyros donator!</p><br><br>
                            </#if>
                    <#elseif success?? && success[0] == "false">
                        <p class="text-center" style="color: rgb(230, 69, 59)">Oops! Something went wrong creating your
                            account. Please try that again.</p><br>

                        <#if errors??>
                            <ul style="margin-left: 65px; color: rgb(230, 69, 59)">
                                <#list errors as error>
                                    <li>${error}</li>
                                </#list>
                            </ul>
                        </#if>
                        <br>
                    <#else>
                        <p class="text-center">Create a game account to use on all our games, the store and our forums.
                            Registration is quick and simple.</p><br><br>
                    </#if>
                    <form method="post">
                        <div class="form-group">
                            <label for="registerUsername">Username</label>
                            <div data-error="" class="input-group__field" id="usernameerror"></div>
                            <input type="text" name="username" class="form-control" placeholder="Create Username"
                                   maxlength="12" id="username" autocomplete="off">
                            <small class="form-text text-muted">No special characters allowed. Only appropriate names
                                tolerated.</small>
                        </div>
                        <div class="form-group">
                            <label for="registerEmail">Email address</label>
                            <div data-error="Invalid e-mail!" class="input-group__field" id="emailerror"></div>
                            <input type="email" name="email" class="form-control" id="registerEmail"
                                   aria-describedby="emailHelp" placeholder="Enter email">
                            <small class="form-text text-muted">You need to have access to this email to confirm your
                                account.</small>
                        </div>
                        <div class="form-group">
                            <label for="exampleInputPassword1">Password</label>
                            <div data-error="Too weak!" class="input-group__field" id="passworderror"></div>
                            <input type="password" name="password" class="form-control" placeholder="Password"
                                   style="margin-bottom:5px;">
                            <div data-error="Passwords do not match!" class="input-group__field"
                                 id="repeatpasserror"></div>
                            <input type="password" name="repeat_password" class="form-control" placeholder="Confirm Password">
                            <small class="form-text text-muted">Minimum length of 8 characters.</small>
                        </div>
                        <div class="form-check">
                            <input type="checkbox" name="termsAndRules" class="form-check-input" id="terms_and_rules">
                            <label class="form-check-label" for="terms">I have read and accept the <a href="/tos">Terms
                                    & Rules</a> of Enchanta.</label>
                        </div>
                        <div class="form-group">
                            <label>
                                <div class="input-group__title">Human verification *</div>
                                <div class="input-group__field" style="margin-top: 5px">
                                    <div class="g-recaptcha" data-sitekey="6LfczMIZAAAAAP4DE83HiCM7ojJw0qTtHAYF1_HV"></div>
                                </div>

                                <#if captcha_error??>
                                    <span style="color:red">Invalid captcha!</span>
                                </#if>
                            </label>
                        </div>
                        <button type="submit" onclick="return validateAll()" class="enchanta-btn">REGISTER ACCOUNT
                        </button>
                    </form>
                </div>
                <div class=" col-md-6">
                    <div id="download-btns">
                        <a href="/download">
                            <div class="enchanta-btn">Download Enchanta</div>
                        </a>
                        <a href="https://www.java.com/en/download/">
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