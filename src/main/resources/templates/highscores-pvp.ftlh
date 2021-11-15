<!DOCTYPE html>
<#setting locale="en_US">
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="description"
          content="OS-Scape is the largest Old-School private server with an economy world and a heavy-action PvP world. Free to play.">
    <meta name="keywords"
          content="best, osrs, rsps, real, rs, runescape private, private server, old school, old, school, server, no member, free">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>OS-Scape Highscores</title>
    <link href="favicon.ico" rel="icon" type="image/x-icon">
    <link href="favicon.ico" rel="shortcut icon">
    <link href="/assets/css/global-highscores.css" rel="stylesheet" type="text/css">
</head>
<body class="hiscores">
<div class="page-container">
    <div class="container container--header">
        <header role="banner" class="header">
            <div class="wrapper">
                <div class="header__logo">
                    <div class="logo header__logo__image"></div>
                </div>
                <a href="${baseurl}" class="header__logo-link"></a>
                <div class="header__items">
                    <div class="header__items__left pull-left"><a href="/store"
                                                                  class="header__items__button header__items__button--red">
                        <div class="header__items__button__hover"></div>
                        <div class="header__items__button__border"><span>Store</span><i
                                class="icon icon--cart pull-right"></i>
                            <div class="clear-fix"></div>
                        </div>
                    </a></div>
                    <div class="header__items__right pull-right">
                    <#--<div class="user-info header__items__user-info">
                            <div class="user-info__left pull-left"><a href="#" class="user-info__name">Username</a>
                                <div class="user-info__menu"><span>Menu <i class="icon icon--chevron-down"></i></span></div>
                            </div>
                            <div class="user-info__right pull-right"><a href="#" class="user-info__avatar">
                                <div class="user-info__avatar__border">
                                    <div class="user-info__avatar__image">
                                        <picture>
                                            <source srcset="/assets/img/avatar.jpg 1x, /assets/img/avatar@2x.jpg 2x"/><img src="img/avatar.jpg" alt="" width="60" height="60"/>
                                        </picture>
                                    </div>
                                </div></a></div>
                            <div class="clear-fix"></div>
                        </div>-->
                    </div>
                    <div class="clear-fix"></div>
                </div>
                <div class="header__dropdown">
                    <div class="header__dropdown__buttons"><a href="#"
                                                              class="header__dropdown__buttons__button">Forums</a><a
                            href="#" class="header__dropdown__buttons__button">Highscores</a></div>
                </div>
                <div class="header__menu trigger--header-dropdown">
                    <div class="header__menu__title">Community</div>
                </div>
            </div>
        </header>
    </div>
    <div class="container container--body">
        <div class="wrapper">
            <section role="main" class="body-section"><a href="#" class="play-button body-section__play-button">
                <div class="play-button__hover"></div>
                <div class="play-button__text">
                    <picture>
                        <source srcset="/assets/img/play_button_text.png 1x, /assets/img/play_button_text@2x.png 2x"/>
                        <img src="/assets/img/play_button_text.png" alt="" width="115" height="43"/>
                    </picture>
                </div>
            </a>
                <div class="body-section__glow body-section__glow--red"></div>
                <div class="body-section__glow body-section__glow--green"></div>
                <header class="body-section__header">
                    <h1 class="body-section__header__title pull-left">Highscores</h1><a href="${baseurl}"
                                                                                      class="return-btn body-section__header__return-btn pull-right"><i
                        class="icon icon--arrow-left"></i></a>
                    <div class="clear-fix"></div>
                </header>
                <div class="body-section__container">
                    <section class="hiscores hiscores--economy">
                        <div class="hiscores__filters">
                            <div class="hiscores__filters__field hiscores__filters__field--dropdown">
                                <div data-value="1" class="input-dropdown input-dropdown--large">
                                    <div class="input-dropdown__container"><span
                                            class="input-dropdown__selected pull-left">
                                    <#if server == 1>Realism (W1)</#if>
                                    <#if server == 2>PvP (W2)</#if>
                                    <#if server == 3>Economy (W3)</#if>
                                    </span><i class="input-dropdown__chevron pull-right"></i>
                                        <div class="clear-fix"></div>
                                    </div>
                                    <div class="input-dropdown__options">
                                        <div data-value="1" class="input-dropdown__options__option" onclick="window.location='/highscores/1/${gamemode}/${skill}'">Realism (W1)</div>
                                        <div data-value="2" class="input-dropdown__options__option" onclick="window.location='/highscores/2/${gamemode}/${skill}'">PvP (W2)</div>
                                        <div data-value="3" class="input-dropdown__options__option" onclick="window.location='/highscores/3/${gamemode}/${skill}'">Economy (W3)</div>
                                    </div>
                                </div>
                            </div>
                            <div class="hiscores__filters__field hiscores__filters__field--dropdown">
                                <div data-value="3" class="input-dropdown input-dropdown--large">
                                    <div class="input-dropdown__container"><span
                                            class="input-dropdown__selected pull-left">
                                    <#if server == 3 && gamemode == 10>Iron Man
                                    <#elseif server == 3 && gamemode != 10>Regular
                                    <#elseif gamemode == 1>Laid-Back
                                    <#elseif gamemode == 2>Classic
                                    <#elseif gamemode == 4>Realism
                                    <#elseif gamemode == 10>Iron Man
                                    </#if>
                                    </span><i
                                            class="input-dropdown__chevron pull-right"></i>
                                        <div class="clear-fix"></div>
                                    </div>
                                    <div class="input-dropdown__options">
                                        <#if server == 3>
                                            <div data-value="2" class="input-dropdown__options__option" onclick="window.location='/highscores/${server}/2/${skill}'">Regular</div>
                                            <div data-value="4" class="input-dropdown__options__option" onclick="window.location='/highscores/${server}/10/${skill}'">Iron Man</div>
                                        <#else>
                                            <div data-value="1" class="input-dropdown__options__option" onclick="window.location='/highscores/${server}/1/${skill}'">Laid Back</div>
                                            <div data-value="2" class="input-dropdown__options__option" onclick="window.location='/highscores/${server}/2/${skill}'">Classic</div>
                                            <div data-value="3" class="input-dropdown__options__option" onclick="window.location='/highscores/${server}/4/${skill}'">Realism</div>
                                            <div data-value="4" class="input-dropdown__options__option" onclick="window.location='/highscores/${server}/10/${skill}'">Iron Man</div>
                                        </#if>
                                    </div>
                                </div>
                            </div>

                            <div class="hiscores__filters__field hiscores__filters__field--user">
                                <div class="hiscores__input">
                                    <form method="GET" action="/highscores/${server}/personal">
                                        <input type="text" name="user" placeholder="Find user" required>
                                        <button type="submit">
                                            <img src="/assets/img/search-icon.svg" width="22" height="26">
                                        </button>
                                    </form>
                                </div>
                            </div>
                            <div class="clear-fix"></div>
                        </div>
                        <div class="hiscores__container">
                            <aside role="sidebar" class="hiscores__skills">
                                <a href="/highscores/${server}/${gamemode}/kills"
                                   class="hiscores__skills__button hiscores__skills__button--attack <#if skill = "attack">hiscores__skills__button--active</#if>">
                                    <div class="hiscores__skills__button__container"><i class="hiscores__icon hiscores__icon--attack"></i><span class="hiscores__skills__button__name">Kills</span></div>
                                </a>
                                <a href="/highscores/${server}/${gamemode}/deaths" class="hiscores__skills__button hiscores__skills__button--defence <#if skill = "defence">hiscores__skills__button--active</#if>">
                                    <div class="hiscores__skills__button__container"><i class="hiscores__icon hiscores__icon--defence"></i><span class="hiscores__skills__button__name">Deaths</span></div>
                                </a>
                                <a href="/highscores/${server}/${gamemode}/killstreak" class="hiscores__skills__button hiscores__skills__button--strength <#if skill = "strength">hiscores__skills__button--active</#if>">
                                    <div class="hiscores__skills__button__container"><i class="hiscores__icon hiscores__icon--strength"></i><span class="hiscores__skills__button__name">Killstreak</span></div>
                                </a>
                                <a href="/highscores/${server}/${gamemode}/shutdown"
                                   class="hiscores__skills__button hiscores__skills__button--hitpoints <#if skill = "hitpoints">hiscores__skills__button--active</#if>">
                                    <div class="hiscores__skills__button__container"><i class="hiscores__icon hiscores__icon--hitpoints"></i><span class="hiscores__skills__button__name">Shutdown</span></div>
                                </a>
                            </aside>

                            <div class="hiscores__content">
                                <h1 class="hiscores__title">World ${server}
                                    <span class="hiscores__title__skill">
                                        <#if server == 1>
                                            <#if gamemode == 1>Laid Back</#if>
                                            <#if gamemode == 2>Classic</#if>
                                            <#if gamemode == 4>Realism</#if>
                                            <#if gamemode == 10>Iron Man</#if>
                                        <#elseif server == 3>
                                            <#if gamemode == 10>Iron Man</#if>
                                        </#if>
                                    ${skillname}
                                    </span> Highscores</h1>
                                <div class="hiscores__table">
                                    <div class="hiscores__table__header">
                                    <#if skill == "deaths" || skill == "kills">
                                        <div class="hiscores__table__cell hiscores__table__cell--rank">Rank</div>
                                        <div class="hiscores__table__cell hiscores__table__cell--username" style="width: 282px">Username</div>
                                        <div class="hiscores__table__cell hiscores__table__cell--level" style="width: 112px">Kills</div>
                                        <div class="hiscores__table__cell hiscores__table__cell--experience" style="width: 112px">Deaths</div>
                                        <div class="hiscores__table__cell hiscores__table__cell--experience" style="width: 112px">KDR</div>
                                    <#elseif skill == "killstreak">
                                        <div class="hiscores__table__cell hiscores__table__cell--rank">Rank</div>
                                        <div class="hiscores__table__cell hiscores__table__cell--username" style="width: 282px">Username</div>
                                        <div class="hiscores__table__cell hiscores__table__cell--level" style="width: 112px">Current</div>
                                        <div class="hiscores__table__cell hiscores__table__cell--experience" style="width: 112px">Highest</div>
                                        <div class="hiscores__table__cell hiscores__table__cell--experience" style="width: 112px">Bounty</div>
                                    <#elseif skill == "shutdown">
                                        <div class="hiscores__table__cell hiscores__table__cell--rank">Rank</div>
                                        <div class="hiscores__table__cell hiscores__table__cell--username" style="width: 262px">Username</div>
                                        <div class="hiscores__table__cell hiscores__table__cell--level" style="width: 160px">Highest Shutdown</div>
                                    </#if>
                                    </div>
                                    <div class="hiscores__table__container">
                                    <#list hs as entry>
                                        <div class="hiscores__table__row">
                                            <#if skill == "deaths" || skill == "kills">
                                                <div class="hiscores__table__cell hiscores__table__cell--rank"><strong>${entry.rank}</strong></div>
                                                <div class="hiscores__table__cell hiscores__table__cell--username"><a href="/highscores/${server}/personal/${entry.displayname}">${entry.displayname}</a></div>
                                                <div class="hiscores__table__cell hiscores__table__cell--level" style="width: 112px">${entry.kills}</div>
                                                <div class="hiscores__table__cell hiscores__table__cell--experience" style="width: 112px">${entry.deaths}</div>
                                                <div class="hiscores__table__cell hiscores__table__cell--experience" style="width: 112px">${entry.kdr}</div>
                                            <#elseif skill == "killstreak">
                                                <div class="hiscores__table__cell hiscores__table__cell--rank"><strong>${entry.rank}</strong></div>
                                                <div class="hiscores__table__cell hiscores__table__cell--username"><a href="/highscores/${server}/personal/${entry.displayname}">${entry.displayname}</a></div>
                                                <div class="hiscores__table__cell hiscores__table__cell--level" style="width: 112px">${entry.killstreak}</div>
                                                <div class="hiscores__table__cell hiscores__table__cell--experience" style="width: 112px">${entry.killstreak_top}</div>
                                                <div class="hiscores__table__cell hiscores__table__cell--experience" style="width: 112px">${entry.bounty}</div>
                                            <#elseif skill == "shutdown">
                                                <div class="hiscores__table__cell hiscores__table__cell--rank"><strong>${entry.rank}</strong></div>
                                                <div class="hiscores__table__cell hiscores__table__cell--username"><a href="/highscores/${server}/personal/${entry.displayname}">${entry.displayname}</a></div>
                                                <div class="hiscores__table__cell hiscores__table__cell--level" style="width: 160px">${entry.shutdown}</div>
                                            </#if>
                                        </div>
                                    </#list>
                                    </div>
                                </div>
                                <div class="hiscores__pagination">
                                    <#if paginator.leftArrowVisible>
                                        <a href="/highscores/${server}/${gamemode}/${skill}/#{paginator.current - 1}" class="hiscores__pagination__button hiscores__pagination__button--previous">
                                            <img src="/assets/img/chevron_left.svg" width="13" height="24">
                                        </a>
                                    </#if>
                                    <a href="#" class="hiscores__pagination__button hiscores__pagination__button--active">#{paginator.current}</a>
                                    <#--<a href="#" class="hiscores__pagination__button">2</a>
                                    <a href="#" class="hiscores__pagination__button">3</a>
                                    <div class="hiscores__pagination__dots">
                                        <img src="/assets/img/dots.svg" width="39" height="19">
                                    </div>
                                    <a href="#" class="hiscores__pagination__button">12</a>
                                    <a href="#" class="hiscores__pagination__button">13</a>
                                    <a href="#" class="hiscores__pagination__button">14</a>-->
                                    <#if paginator.rightArrowVisible>
                                        <a href="/highscores/${server}/${gamemode}/${skill}/#{paginator.current + 1}" class="hiscores__pagination__button hiscores__pagination__button--next">
                                            <img src="/assets/img/chevron_right.svg" width="13" height="24">
                                        </a>
                                    </#if>
                                </div>
                            </div>
                            <div class="clear-fix"></div>
                        </div>
                    </section>
                </div>
            </section>
        </div>
    </div>

<#include "includes/footer.ftlh">
</div>
<script src="//neo.apachenick.me/v/3.6/neo.min.js" rel="script" type="text/javascript"></script>
<script src="/assets/js/global.js" rel="script" type="text/javascript"></script>
</body>
</html>