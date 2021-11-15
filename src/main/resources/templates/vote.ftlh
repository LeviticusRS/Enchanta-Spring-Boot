<#include "includes/constants.ftlh">
<body>
   <#include "includes/navigation.ftlh">
   <#include "includes/header.ftlh">
   <#setting number_format="computer">
   <div id="main">
      <div id="container" class="container-fluid">
         <div id="container-top"></div>
         <div id="content" class="blog">
            <!--BREADCRUMBS-->
            <div id="breadcrumbs-outer">
               <div id="breadcrumbs-inner">
                  <div class="left">
                     <a href="#"><img src="assets/img/breadcrumbs.png"/>Back to Home</a>
                  </div>
                  <div class="social">
                     <a href="#"><img src="/assets/img/fb.png"/></a>
                     <a href="#"><img src="/assets/img/twitter.png"/></a>
                     <a href="#"><img src="/assets/img/youtube.png"/></a>
                  </div>
               </div>
            </div>
            <!--breadcrumbs end -->
            <div class="vote-content">
               <p>Vote for us on all of the toplists listed below and earn point to be used ingame to reap rewards!
                  <br>
                  <b>You can vote once every 12 hours!</b>
               </p>
               <b>::voted ingame after voting on a site.</b></p>
               <div class="row">
                  <div class="card-group" style="width:100%;">
                     <div class="card">
                        <div class="card-img-top"><img src="/assets/img/runelocus.png" class="img-fluid"
                           width="225px"/></div>
                        <b>Gives x2 rewards!!</b>
                        <#if runelocusTime??>
                        <div class="enchanta-btn vote-inactive">${runelocusTime}</div>
                        <#else>
                        <a href="/api/vote/?siteId=101&id=${account.id()}" target="_blank" rel="noopener noreferrer">
                           <div class="enchanta-btn">Vote Now</div>
                        </a>
                        </#if>
                     </div>
                     <div class="card">
                        <div class="card-img-top"><img src="/assets/img/top100arena.png" class="img-fluid"
                           width="225px"/></div>
                        <#if top100Time??>
                        <div class="enchanta-btn vote-inactive">${top100Time}</div>
                        <#else>
                        <a href="/api/vote/?siteId=102&id=${account.id()}" target="_blank" rel="noopener noreferrer">
                           <div class="enchanta-btn">Vote Now</div>
                        </a>
                        </#if>
                     </div>
                     <div class="card">
                        <div class="card-img-top"><img src="/assets/img/rsps-list.png" class="img-fluid"
                           width="220px"/></div>
                        <#if rspsListTime??>
                        <div class="enchanta-btn vote-inactive">${rspsListTime}</div>
                        <#else>
                        <a href="/api/vote/?siteId=103&id=${account.id()}" target="_blank" rel="noopener noreferrer">
                           <div class="enchanta-btn">Vote Now</div>
                        </a>
                        </#if>
                     </div>
                     <div class="card">
                        <div class="card-img-top"><img src="/assets/img/topg.png" class="img-fluid" width="207px"/>
                        </div>
                        <#if topGTime??>
                        <div class="enchanta-btn vote-inactive">${topGTime}</div>
                        <#else>
                        <a href="/api/vote/?siteId=105&id=${account.id()}" target="_blank" rel="noopener noreferrer">
                           <div class="enchanta-btn">Vote Now</div>
                        </a>
                        </#if>
                     </div>
                     <div class="card">
                        <div class="card-img-top"><img src="/assets/img/runelist.png" class="img-fluid" width="207px"/>
                        </div>
                        <b>Does not give reward, but we appreciate it if you did vote!</b>
                        <#if runelistTime??>
                        <div class="enchanta-btn vote-inactive">${runelistTime}</div>
                        <#else>
                        <a href="/api/vote/?siteId=106&id=${account.id()}" target="_blank" rel="noopener noreferrer">
                           <div class="enchanta-btn">Vote Now</div>
                        </a>
                        </#if>
                     </div>
               </div>
            </div>
                           <div class="card-group" style="width:100%;">
                                                 <div class="card">
                                                    <div class="card-body">
                                                       <div class="card-header">Top Monthly Voters</div>
                                                       <ul class="list-group list-group-flush">
                                                          <#list topVoters as voter>
                                                          <li class="list-group-item">
                                                             ${voter.displayname} : ${voter.monthlycount} votes
                                                          </li>
                                                          </#list>
                                                       </ul>
                                                    </div>
                                                 </div>
                                              </div>
                                             </div>
         </div>
         <div id="container-bottom"></div>
      </div>
   </div>
</body>
<#include "includes/footer.ftlh">