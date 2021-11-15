<header>
    <div class="logo">
        <img src="/assets/img/logo.png" alt="Enchanta Logo" class="img-fluid" />
    </div>
    <div id="slogan">Welcome to the #1 Custom RSPS Tailored to Our Players!</div>
    <div id="player-count">
        Join <span style="color:#b735de">#{playerCount}</span> other players online!
    </div>
    <br>
    <div id="cta-buttons">
        <a href="/download"><img src="/assets/img/play.png" /></a>
        <a href="/discord"><img src="/assets/img/joindiscord.png" /></a>
    </div>
    <!--<script>
        function makeTimer() {
            let endTime = new Date("May 14, 2021 15:00:00 EST");
            endTime = (Date.parse(endTime)) / 1000;
            let now = new Date();
            now = (Date.parse(now) / 1000);
            const timeLeft = endTime - now;
            let days = Math.floor(timeLeft / 86400);
            let hours = Math.floor((timeLeft - (days * 86400)) / 3600);
            let minutes = Math.floor((timeLeft - (days * 86400) - (hours * 3600)) / 60);
            let seconds = Math.floor((timeLeft - (days * 86400) - (hours * 3600) - (minutes * 60)));
            if (hours < "10") { hours = "0" + hours; }
            if (minutes < "10") { minutes = "0" + minutes; }
            if (seconds < "10") { seconds = "0" + seconds; }
            $("#slogan").html("<h1>" + days + " Days " + hours + " Hours " + minutes + " Minutes " + seconds + " Seconds " + "</h1>");
        }
        setInterval(function() { makeTimer(); }, 1000);
    </script>-->
</header>