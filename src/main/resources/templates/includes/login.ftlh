<div id="login" class="modal fade bd-example-modal-lg" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">LOGIN TO ENCHANTA</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="row">
                <div class="left-panel col-md-6">
                    <form method="post">
                        <div class="form-group">
                            <label for="registerUsername">Username</label>
                            <label>
                                <input type="text" name="username" class="form-control" placeholder="Your Username" required="required">
                            </label>
                        </div>
                        <div class="form-group">
                            <label for="exampleInputPassword1">Password</label>
                            <label>
                                <input type="password" name="password" class="form-control" placeholder="Your Password" required="required">
                            </label>
                        </div>
                        <input type="hidden" name="server">
                        <button type="submit" name="login" class="enchanta-btn">LOGIN TO ENCHANTA</button>
                    </form>
                </div>
                <div class="col-md-6">
                    <div id="download-btns">
                        <a href="#"><div class="enchanta-btn">Download Enchanta</div></a>
                        <a href="#"><div class="enchanta-btn">Download Java</div></a>
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
    </div>
</div>