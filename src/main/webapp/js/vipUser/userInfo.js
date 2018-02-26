/**
 * Created by liyipeng on 2018/2/24.
 */

var userId;

$(document).ready(function () {
    $('#userInfoDiv').hide();
    $('#changePasswordDiv').hide();
    $('#vipGradeFormDiv').hide();
   showUser();
    userId = sessionStorage.getItem("userID");
});

/*查看个人信息*/
$(document).on(
    {
        click:function () {
            var urInfo;
            var userMail;
            var vipGrade;
            var vipScore;
            var vipIsStop;

            /*获取个人信息*/
            $.ajax({
               type:"get",
                url:"/User/getUserInfo",
                contentType:'application/json;charset=utf-8',
                data:{"userId":userId},
                success:function (result) {

                    urInfo = result.userInfo;
                    userMail = urInfo.mail;

                    vipGrade = urInfo.vipGrade;
                    vipScore = urInfo.vipScore;
                    vipIsStop = urInfo.vipIsStop;
                    $('#pUserID').html(userId);
                    $('#pUserMail').html(userMail);
                    if(vipIsStop != 0){
                        $('#pVipGrade').html("会员失效");
                        $('#cancelVIP').hide();
                    }else {

                        if(vipGrade == 1){
                            $('#pVipGrade').html("白银会员");
                        }else if(vipGrade == 2){
                            $('#pVipGrade').html("黄金会员");
                        }else {
                            $('#pVipGrade').html("钻石会员");
                        }

                    }


                    $('#pVipScore').html(vipScore);

                },
                error:function () {
                    alert('getUserInfo failed');
                }

            });

            /*获取个人优惠券信息*/
            $.ajax({

                type:"get",
                url:"/User/getCoupon",
                contentType:'application/json;charset=utf-8',
                data:{"userId":userId},
                success:function (result) {
                    $('#pCoupon').html(result.length + " 张");
                },
                error:function () {
                    alert("getCoupon failed!");
                }

            });

            $('#changePasswordDiv').hide();
            $('#vipGradeFormDiv').hide();
            $('#userInfoDiv').show();

        }
    },'#userInfoLi'

);

/*取消会员*/
$(document).on(
    {
        click:function () {

            $.ajax({
                type:"get",
                url:"/User/cancelVIP",
                contentType:'application/json;charset=utf-8',
                data:{"userId": userId},
                success:function (result) {
                    alert("您已经成功取消会员资格！");
                    window.location.href = "./userInfo.html"
                },
                error:function () {
                    alert("cancelVIP failed");
                }

            })

        }
    },'#confirmCancelVip'

);

/*点击会员等级展示*/
$(document).on(
    {
        click:function () {

            $('#userInfoDiv').hide();
            $('#changePasswordDiv').hide();
            $('#vipGradeFormDiv').show();

        }
    },'#vipGradeLi'

);

/*修改密码展示*/
$(document).on(
    {
        click:function () {
           $('#userInfoDiv').hide();
           $('#vipGradeFormDiv').hide();
           $('#changePasswordDiv').show();

        }
    },'#changePasswordLi'

);

/*修改密码 按钮*/
$(document).on(
    {
        click:function () {
            var changeVO;
            var oldPasswd;
            var newPasswd;
            var newPasswdAgain; //第二次确认密码

            oldPasswd = $('#oldPassword').val();

            newPasswd = $('#newPassword').val();

            newPasswdAgain = $('#againNew').val();


            if(newPasswd != newPasswdAgain){
                alert("新密码前后输入不一致！");
            }else {
                changeVO = {
                  "userId": userId,
                  "oldPasswd": parseInt(oldPasswd),
                  "newPasswd": parseInt(newPasswd)
                };

                $.ajax({

                    type:"post",
                    url:"/User/changePasswd",
                    contentType:'application/json;charset=utf-8',
                    data:JSON.stringify(changeVO),
                    success:function (result) {
                        if(result.result == "SUCCESS"){
                            alert("密码修改成功！");
                        }else if(result.result == "WRONG_PASSWORD") {
                            alert("旧密码错误！");
                        }else {
                            alert("密码修改失败");
                        }
                    },
                    error:function () {
                        alert("changePasswd failed!")
                    }


                });
            }


        }
    },'#changePasswordBtn'

);
