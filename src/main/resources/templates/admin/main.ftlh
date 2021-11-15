<#setting locale="en_US">
<link rel="stylesheet" type="text/css" href="/assets/css/jquery.dataTables.css?v=1.1">
<#include "/includes/constants.ftlh">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/sceditor@3/minified/themes/default.min.css" />
<script src="https://cdn.jsdelivr.net/npm/sceditor@3/minified/sceditor.min.js"></script>
<body>
   <#include "/includes/navigation.ftlh">
   <#include "/includes/header.ftlh">
   <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.10.24/js/jquery.dataTables.min.js"></script>
   <style>
      .table {
      color: #aea1b4;
      font-weight: 400;
      text-shadow: 0 1px 3px rgba(0,0,0,0.5);
      }
      .card input {
      width: 100%;
      }
   </style>
   <div id="main">
      <div id="container" class="container-fluid">
         <div id="container-top"></div>
         <div id="content">
            <div id="breadcrumbs-outer">
               <div id="breadcrumbs-inner">
                  <div class="left">
                     <img src="/assets/img/breadcrumbs.png"  alt=""/>
                     <h3>Staff Panel</h3>
                  </div>
                  <div class="social">
                     <a href="#"><img src="/assets/img/fb.png"  alt=""/></a>
                     <a href="#"><img src="/assets/img/twitter.png"  alt=""/></a>
                     <a href="#"><img src="/assets/img/youtube.png"  alt=""/></a>
                  </div>
               </div>
            </div>
            <ul class="nav nav-pills mb-3">
               <li class="nav-item"><a class="nav-link active" href="#timesheet" data-toggle="tab"><span class="glyphicon glyphicon-pushpin"></span> TimeSheets</a></li>
               <li class="nav-item"><a class="nav-link" href="#donation" data-toggle="tab"><span class="glyphicon glyphicon-ok"></span> Donations</a></li>
               <#if account.getRights() == 3 || account.getRights() == 4>
                <li class="nav-item"><a class="nav-link" href="#blog" data-toggle="tab"><span class="glyphicon glyphicon-ok"></span> Blogs</a></li>
               </#if>
            </ul>
            <div class="tab-content">
               <div class="tab-pane fade show active" id="timesheet">
                  <div class="row justify-content-center p-1">
                     <div class="card">
                        <div class="card-body">
                           <h5 class="card-title">Timesheet</h5>
                           <#if clock??>
                           <p class="card-text">${clock}</p>
                           <form action="/admin/clock" method="post">
                              <button type="submit" class="enchanta-btn">Clock Out</button>
                              <input type="hidden" id="clockType" name="clockType" value=0>
                           </form>
                           <#else>
                           <form action="/admin/clock" method="post">
                              <button type="submit" class="enchanta-btn">Clock In</button>
                              <input type="hidden" id="clockType" name="clockType" value=1>
                           </form>
                           </#if>
                        </div>
                     </div>
                     <div class="card">
                        <div class="card-body">
                           <table id="table_id" class="table">
                              <thead>
                                 <tr>
                                    <th scope="col">#</th>
                                    <th scope="col">Clock In</th>
                                    <th scope="col">Clock Out</th>
                                    <th scope="col">Accumulated</th>
                                 </tr>
                              </thead>
                              <tbody>
                                 <#list allSheets as sheet>
                                 <tr>
                                    <th scope="row">${sheet?index}</th>
                                    <td>${sheet.timeIn()}</td>
                                    <#if sheet.timeOut()??>
                                    <td>${sheet.timeOut()}</td>
                                    <#else>
                                    <td>Not Clocked Out</td>
                                    </#if>
                                    <td>${sheet.difference()}</td>
                                 </tr>
                                 </#list>
                              </tbody>
                           </table>
                        </div>
                     </div>
                  </div>
               </div>
               <div class="tab-pane fade" id="donation">
                  <div class="row justify-content-center p-1">
                     <div class="card">
                        <div class="card-body">
                           <h5 class="card-title">Non-Stripe Donations</h5>
                           <form action="/admin/custom_donation" method="post">
                              <div class="form-group">
                                 <label for="username">Username</label>
                                 <input type="text" class="form-control" name="username" id="username" placeholder="Enter username">
                              </div>
                              <div class="form-group">
                                 <label for="selectingType">Select Type</label>
                                 <select class="form-control" name="type" id="type">
                                    <option>OSRS</option>
                                    <option>RS3</option>
                                    <option>Paypal</option>
                                    <option>Venmo</option>
                                    <option>CashApp</option>
                                 </select>
                              </div>
                              <div class="form-group">
                                 <label for="amount">Amount</label>
                                 <input type="number" class="form-control" name="amount" id="amount" placeholder="USD Amount">
                              </div>
                              <button type="submit" class="enchanta-btn">Submit</button>
                           </form>
                           <#if response??>
                           <span class="label">${response}</span>
                           </#if>
                        </div>
                     </div>
                  </div>
               </div>
               <#if account.getRights() == 3 || account.getRights() == 4>
                <div class="tab-pane fade" id="blog">
                  <div class="row justify-content-center p-1">
                     <div class="card">
                        <div class="card-body">
                           <h5 class="card-title">Blog</h5>
                           <form id="blogForm" action="/admin/submit_blog" method="post">
                              <div class="form-group">
                                 <textarea id="textarea" name="input" style="height: 300px" rows="2"></textarea>
                              </div>
                              <button id="blogSubmit" type="submit" class="enchanta-btn">Submit</button>
                           </form>
                           <#if response??>
                           ${response?no_esc}
                           </#if>
                        </div>
                     </div>
                  </div>
               </div>
               </#if>
            </div>
         </div>
         <div id="container-bottom"></div>
      </div>
   </div>
</body>
<script src="https://cdn.jsdelivr.net/npm/sceditor@3/minified/formats/bbcode.min.js"></script>
<script>
// Replace the textarea #example with SCEditor
var textarea = document.getElementById('textarea');
sceditor.create(textarea, {
	format: 'bbcode',
	style: 'https://cdn.jsdelivr.net/npm/sceditor@3/minified/themes/content/default.min.css'
});
</script>
<script>
   $(function () {
       $('.nav-tabs a:first').tab('show');
   });
   $(document).ready( function () {
       $('#table_id').DataTable();
   } );
</script>
<#include "/includes/footer.ftlh">