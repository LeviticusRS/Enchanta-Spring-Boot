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
                        <img src="/assets/img/breadcrumbs.png"  alt=""/><h3>Home</h3>
                    </div>
                    <div class="social">
                        <a href="#"><img src="/assets/img/fb.png"  alt=""/></a>
                        <a href="#"><img src="/assets/img/twitter.png"  alt=""/></a>
                        <a href="#"><img src="/assets/img/youtube.png"  alt=""/></a>
                    </div>
                </div>
            </div>
            <div class="row">
                <!--LEFT CONTENT-->
                <div class="main-content col-lg-8 col-xl-9">
                    <div id="intro">
                        <h3>Welcome to <span class="purple">Enchanta</span></h3>
                        <div class="seperate"></div>
                        <P>Welcome to Enchanta! the highest quality custom RSPS out there!
                            Enchanta has a team that is highly dedicated to do big things and won't settle for anything less.
                            Join one of the most unique custom RSPS today!</P>
                    </div>
                    <div id="news-content">
                            <#list news as article>
                                <#assign index = article?index>
                                <#if index lt 2>
                                    <#if article?is_first>
                                        <div class="row">
                                    </#if>
                                    <div class="col">
                                        <div class="news-post">
                                            <div class="featured-img" style="background:url('/assets/img/featured-img1.png') no-repeat center center;">
                                                <a href="${article.url}"><h4>${article.title} by ${article.by}</h4></a>
                                                <div class="date"><img src="/assets/img/date.png"  alt=""/>${article.time}</div>
                                            </div>
                                            <p>${article.body}</p>
                                        </div>
                                        <a href="${article.url}"><div class="read-more"></div></a>
                                    </div>
                                    <#if article?size == 1 || index == 1>
                                        </div>
                                    </#if>
                                <#else>
                                    <#if index == 2>
                                        <div class="row">
                                    </#if>
                                    <div class="col">
                                        <div class="news-post">
                                            <div class="featured-img" style="background:url('/assets/img/featured-img1.png') no-repeat center center;">
                                                <a href="${article.url}"><h4>${article.title} by ${article.by}</h4></a>
                                                <div class="date"><img src="/assets/img/date.png"  alt=""/>${article.time}</div>
                                            </div>
                                            <p>${article.body}</p>
                                        </div>
                                        <a href="${article.url}"><div class="read-more"></div></a>
                                    </div>
                                    <#if article?is_last>
                                        </div>
                                    </#if>
                                </#if>
                            </#list>
                        </div>
                    </div>


                <div class="col-lg-4 col-xl-3">

                    <div id="players-widget">
                        <div class="server-status">
                            Server Status<br>
                            <span class="online"><img src="/assets/img/online.png"  alt=""/>Online</span>
                        </div>
                        <div class="players">
                            <h2>#{playerCount}</h2>
                            Players Online
                        </div>
                    </div>

                   <div id="discord-widget">
                        <center><img src="/assets/img/discord-header.png" class="discord-header img-fluid"  alt=""/></center>
                        <div class="discord-body">
                            <div id="members-count"></div>
                            <div class="scroll-placeholder"></div>
                            <div class="discord-list-botshadow"></div>
                            <div class="discord-list">
                                <div class="discord-list-status">
                                    <div class="discord-list-label"></div>
                                </div>
                                <table id="members-list"></table>
                            </div>
                            <a onclick="window.open('https://discord.gg/4auGNkCTSU'); return false;"><button class="discord-cta"></button></a>
                        </div>
                    </div>

                </div>

            </div>
        </div>
        <div id="container-bottom"></div>
    </div>
</div>
</body>
<script src="/assets/js/scripts.js?v=2.0"></script>
<#include "includes/footer.ftlh">