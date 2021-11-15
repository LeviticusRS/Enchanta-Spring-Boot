<div id="register" class="modal fade bd-example-modal-lg" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">REGISTER ACCOUNT</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="row">
                <div class="left-panel col-md-6">
                    <form method="post" >
                        <div class="form-group">
                            <label for="registerUsername">Username</label>
                            <input type="text" name="username" class="form-control" placeholder="Create Username" maxlength="12" id="username" autocomplete="off">
                            <small class="form-text text-muted">No special characters allowed. Only appropriate names tolerated.</small>
                        </div>
                        <div class="form-group">
                            <label for="registerEmail">Email address</label>
                            <input type="email" name="email" class="form-control" id="registerEmail" aria-describedby="emailHelp" placeholder="Enter email">
                            <small class="form-text text-muted">You need to have access to this email to confirm your account.</small>
                        </div>
                        <div class="form-group">
                            <label for="exampleInputPassword1">Password</label>
                            <input type="password" class="form-control" placeholder="Password" style="margin-bottom:5px;">
                            <input type="password" class="form-control" placeholder="Confirm Password">
                            <small class="form-text text-muted">Minimum length of 8 characters.</small>
                        </div>
                        <div class="form-check">
                            <input type="checkbox" class="form-check-input" id="exampleCheck1">
                            <label class="form-check-label" for="terms">I have read and accept the <a href="#">Terms & Rules</a> of Enchanta.</label>
                        </div>
                        <button type="submit" class="enchanta-btn">REGISTER ACCOUNT</button>
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