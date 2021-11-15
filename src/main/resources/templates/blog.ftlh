<#include "includes/constants.ftlh">
<body style="background-image:url('./assets/img/blog/cerberus.png');">
<#include "includes/navigation.ftlh">
<#include "includes/header.ftlh">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/sceditor@3/minified/themes/default.min.css" />
<script src="https://cdn.jsdelivr.net/npm/sceditor@3/minified/sceditor.min.js"></script>
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
        <div class="blog-content">
         <#if response??>
            <p>${response?no_esc}</p>
         </#if>
<!--<h1>This is a heading.</h1>
<p>I'm baby biodiesel banjo selvage distillery lyft banh mi. You probably haven't heard of them lo-fi umami kombucha marfa VHS actually live-edge messenger bag synth quinoa bitters mustache coloring book la croix. Roof party godard live-edge, williamsburg four dollar toast letterpress jean shorts. Flexitarian butcher blue bottle hashtag, PBR&B affogato irony drinking vinegar listicle celiac hot chicken cloud bread woke jianbing seitan.</p>
<center><img src="https://cdn.discordapp.com/attachments/701638647363141632/711618717720772618/extremembox.png" class="img-fluid" /></center>
<p>Yr pok pok snackwave cold-pressed master cleanse humblebrag squid woke pork belly. Biodiesel bitters microdosing semiotics four dollar toast, unicorn raclette fashion axe gentrify coloring book affogato edison bulb blog shoreditch squid. Unicorn 3 wolf moon mustache, skateboard PBR&B keffiyeh trust fund flannel crucifix ennui meh selvage shoreditch offal single-origin coffee. Fashion axe readymade pitchfork echo park migas selfies cliche hot chicken food truck meggings messenger bag.</p>
<h2>This is a heading.</h2>
<p>I'm baby biodiesel banjo selvage distillery lyft banh mi. You probably haven't heard of them lo-fi umami kombucha marfa VHS actually live-edge messenger bag synth quinoa bitters mustache coloring book la croix. Roof party godard live-edge, williamsburg four dollar toast letterpress jean shorts. Flexitarian butcher blue bottle hashtag, PBR&B affogato irony drinking vinegar listicle celiac hot chicken cloud bread woke jianbing seitan.</p>
<center><img src="https://cdn.discordapp.com/attachments/701638647363141632/711618717720772618/extremembox.png" class="img-fluid" /></center>
<p>Yr pok pok snackwave cold-pressed master cleanse humblebrag squid woke pork belly. Biodiesel bitters microdosing semiotics four dollar toast, unicorn raclette fashion axe gentrify coloring book affogato edison bulb blog shoreditch squid. Unicorn 3 wolf moon mustache, skateboard PBR&B keffiyeh trust fund flannel crucifix ennui meh selvage shoreditch offal single-origin coffee. Fashion axe readymade pitchfork echo park migas selfies cliche hot chicken food truck meggings messenger bag.</p>
<h3>This is a heading.</h3>
<p>I'm baby biodiesel banjo selvage distillery lyft banh mi. You probably haven't heard of them lo-fi umami kombucha marfa VHS actually live-edge messenger bag synth quinoa bitters mustache coloring book la croix. Roof party godard live-edge, williamsburg four dollar toast letterpress jean shorts. Flexitarian butcher blue bottle hashtag, PBR&B affogato irony drinking vinegar listicle celiac hot chicken cloud bread woke jianbing seitan.</p>
<center><img src="https://cdn.discordapp.com/attachments/701638647363141632/711618717720772618/extremembox.png" class="img-fluid" /></center>
<p>Yr pok pok snackwave cold-pressed master cleanse humblebrag squid woke pork belly. Biodiesel bitters microdosing semiotics four dollar toast, unicorn raclette fashion axe gentrify coloring book affogato edison bulb blog shoreditch squid. Unicorn 3 wolf moon mustache, skateboard PBR&B keffiyeh trust fund flannel crucifix ennui meh selvage shoreditch offal single-origin coffee. Fashion axe readymade pitchfork echo park migas selfies cliche hot chicken food truck meggings messenger bag.</p>
-->
</div>
<hr>

        <!--COMMENT THREAD
        <div class="comment-section py-2">
    <div class="row">
        <div class="comments" id="comments">
            <h3 class="mb-4 font-weight-light">Comments</h3>
            <div class="form-group">
                <label for="exampleFormControlTextarea3">Replying as: <span class="comment-user">Chance</span></label>
                <textarea id="example" style="height: 300px" class="form-control" rows="2"></textarea>
            </div>
            <div class="row pt-2">
                    <a href="" class="btn btn-sm btn-primary">Post Comment</a>
            </div>
            <hr>
        </div>
    </div>
</div>-->
<!--COMMENT THREAD END-->

</div>
    <div id="container-bottom"></div>
    </div>
</div>
</body>
<script src="https://cdn.jsdelivr.net/npm/sceditor@3/minified/formats/bbcode.min.js"></script>
<script>
// Replace the textarea #example with SCEditor
var textarea = document.getElementById('example');
sceditor.create(textarea, {
	format: 'bbcode',
	style: 'https://cdn.jsdelivr.net/npm/sceditor@3/minified/themes/content/default.min.css'
});
</script>
<#include "/includes/footer.ftlh">