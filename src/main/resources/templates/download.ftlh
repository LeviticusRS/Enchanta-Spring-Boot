<#include "includes/constants.ftlh">
<body>
<#include "includes/navigation.ftlh">
<#include "includes/header.ftlh">
<div id="main">
    <div id="container" class="container-fluid">
        <div id="container-top"></div>
        <div id="content" class="blog">
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
            <!--breadcrumbs end -->
            <div class="download-content">

                <h1>Thanks for playing!</h1>
                <p>Please select the download option that is most appropriate for your system.<br>
                    If you have run a .jar-file before, you can download that and play Enchanta easily.<br>
                    If you're new to private servers in general, please try the Windows installer to play without hassle.</p>

                <div class="row">
                    <div class="card-group" style="width:100%;">

                        <div class="card grow">
                            <div class="card-img-top"><img src="assets/img/microsoft.png" class="img-fluid" width="134px" /></div>
                            <a href="/download/msi"><div class="enchanta-btn">Download Windows Bundle w/JRE</div></a>
                        </div>
                        <div class="card grow">
                            <div class="card-img-top"><img src="assets/img/jar.png" class="img-fluid" width="127px" /></div>
                            <a href="/download/launcher-jar"><div class="enchanta-btn">Download Launcher Jar</div></a>
                        </div>
                        <div class="card grow">
                            <div class="card-img-top"><img src="assets/img/jar.png" class="img-fluid" width="127px" /></div>
                            <a href="/download/jar"><div class="enchanta-btn">Download Jar Runnable</div></a>
                        </div>

                        <!--<div class="card grow">
                            <div class="card-img-top"><img src="assets/img/apple.png" class="img-fluid" width="120px" /></div>
                            <a href="/download/mac"><div class="enchanta-btn">Download Mac Client</div></a>
                        </div>-->

                    </div>
                </div>
            </div>
        </div>
        <div id="container-bottom"></div>
    </div>
</div>
</body>
<#include "includes/footer.ftlh">