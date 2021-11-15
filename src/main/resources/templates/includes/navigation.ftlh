<div class="open-button" style="display:none;" xmlns="">
    <a href="#"><img src="/assets/img/menu.svg" /></a>
</div>
<div class="mobile-menu" style="display:none;">
    <div class="close-button"><i class="fas fa-times"></i></div>
    <ul class="mobile-menu-content">
        <li><a href="/">Home</a></li>
        <li><a href="/store">Store</a></li>
        <li><a href="/vote">Vote</a></li>
    </ul>
</div>
<div id="topbar">
    <div class="events">
        <img src="/assets/img/announcement.png" />
        <strong>Mega Corporeal Beast Mass Event</strong> happening now!
    </div>

    <div class="top-cta">
        <a href="download">Play Now</a>
    </div>
</div>

<nav>
    <ul id="site-links">
        <a href="/"><li>Home</li></a>
        <a href="/store"><li>Store</li></a>
        <a href="/vote"><li>Vote</li></a>
    </ul>

    <ul id="guest-panel">
        <#if account??>
            <ul id ="user-panel" class="dropdown show">
                <a id="user-identity" class="dropdown-toggle" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    ${account.displayName()}
                </a>
                <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                    <a class="dropdown-item" href="/logout">Log Out</a>
                </div>
            </ul>
        <#else>
            <a href="/register"><li><img src="/assets/img/register.png"  alt=""/>Sign Up</li></a>
            <a href="/login"><li><img src="/assets/img/login.png"  alt=""/>Login</li></a>
        </#if>

    </ul>
</nav>