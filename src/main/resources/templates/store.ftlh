<#include "includes/constants.ftlh">
<body>
<#include "includes/navigation.ftlh">
<#include "includes/header.ftlh">
<div id="main">
    <div id="container" class="container-fluid">
        <div id="container-top"></div>
        <div id="content">
            <!--BREADCRUMBS-->
            <div id="breadcrumbs-outer">
                <div id="breadcrumbs-inner">
                    <div class="left">
                        <a href="#"><img src="assets/img/breadcrumbs.png" />Back to Home</a>
                    </div>
                    <div class="social">
                        <a href="#"><img src="assets/img/fb.png" /></a>
                        <a href="#"><img src="assets/img/twitter.png" /></a>
                        <a href="#"><img src="assets/img/youtube.png" /></a>
                    </div>
                </div>
            </div>
            <div class="row">
                <!--LEFT CONTENT-->
                <div class="store-content col-lg-8 col-xl-9">

                    <!--STORE TABS-->
                    <ul class="nav nav-pills mb-3" id="pills-tab" role="tablist">
                        <li class="nav-item">
                            <a class="nav-link active" id="pills-limited-tab" data-toggle="pill" href="#pills-limited" role="tab" aria-controls="pills-limited" aria-selected="true">Limited Time</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" id="pills-armour-tab" data-toggle="pill" href="#pills-armour" role="tab" aria-controls="pills-armour" aria-selected="false">Armour</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" id="pills-weapons-tab" data-toggle="pill" href="#pills-weapons" role="tab" aria-controls="pills-weapons" aria-selected="false">Weapons</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" id="pills-ranks-tab" data-toggle="pill" href="#pills-ranks" role="tab" aria-controls="pills-ranks" aria-selected="false">Ranks</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" id="pills-specials-tab" data-toggle="pill" href="#pills-specials" role="tab" aria-controls="pills-specials" aria-selected="false">Specials</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" id="pills-boxes-tab" data-toggle="pill" href="#pills-boxes" role="tab" aria-controls="pills-boxes" aria-selected="false">Boxes</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" id="pills-bundles-tab" data-toggle="pill" href="#pills-bundles" role="tab" aria-controls="pills-bundles" aria-selected="false">Bundles</a>
                        </li>
                    </ul>
                    <!--STORE TABS END-->

                    <!--STORE TAB CONTENT-->
                    <div class="tab-content" id="pills-tabContent">
                        <div class="tab-pane fade show active" id="pills-limited" role="tabpanel" aria-labelledby="pills-limited-tab">
                              <#include 'store/limited-time.ftlh'>
                          </div>
                          <div class="tab-pane fade" id="pills-armour" role="tabpanel" aria-labelledby="pills-armour-tab">
                              <#include 'store/armour.ftlh'>
                           </div>
                           <div class="tab-pane fade" id="pills-weapons" role="tabpanel" aria-labelledby="pills-weapons-tab">
                               <#include 'store/weapons.ftlh'>
                           </div>
                           <div class="tab-pane fade" id="pills-ranks" role="tabpanel" aria-labelledby="pills-ranks-tab">
                               <#include 'store/ranks.ftlh'>
                           </div>
                           <div class="tab-pane fade" id="pills-specials" role="tabpanel" aria-labelledby="pills-specials-tab">
                               <#include 'store/specials.ftlh'>
                           </div>
                           <div class="tab-pane fade" id="pills-boxes" role="tabpanel" aria-labelledby="pills-boxes-tab">
                               <#include 'store/boxes.ftlh'>
                           </div>
                           <div class="tab-pane fade" id="pills-bundles" role="tabpanel" aria-labelledby="pills-bundles-tab">
                               <#include 'store/bundles.ftlh'>
                        </div>
                    </div>
                    <!--STORE TAB CONTENT END-->
                </div>
                <#include "includes/rightside.ftlh">
            </div>

        </div>
        <div id="container-bottom"></div>
    </div>
</div>
</body>
<#include "includes/footer.ftlh">