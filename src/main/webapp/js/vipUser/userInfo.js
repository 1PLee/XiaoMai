/**
 * Created by liyipeng on 2018/2/24.
 */

var userId;
var couponNum;//个人优惠券数量
var urInfo;
var userMail;
var vipGrade;
var vipScore;
var vipIsStop;

var couponArray; //存放从后端得到的关于优惠券的信息
var convertCouponVO;//用于兑换优惠券时提交的VO

$(document).ready(function () {
    alert("执行");
    allHide();
    showUser();
    userId = sessionStorage.getItem("userID");
    loadUserInfo();
    loadCouponInfo();

});


function loadUserInfo() {

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

        },
        error:function () {
            alert('getUserInfo failed');
        }

    });
}

/*查看个人信息*/
$(document).on(
    {
        click:function () {


/*            /!*获取个人优惠券信息*!/
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

            });*/
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

            $('#pCoupon').html(couponNum + " 张");
            allHide();
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

/*加载个人优惠券信息*/
function loadCouponInfo() {
    couponArray = new Array();
    var nowDate = new Date();

    var nowYear = nowDate.getYear();
    var nowMonth = nowDate.getMonth()+1;
    var nowDay = nowDate.getDate();

    var activeUl = document.getElementById("myCoupon");//可以使用的优惠券

    var usedUl = document.getElementById("hasUsedCoupon");//已经使用过的优惠券

    var couponDes; //优惠描述
    var couponTime;//优惠时间

    $.ajax({

        type:"get",
        url:"/User/getCoupon",
        contentType:'application/json;charset=utf-8',
        data:{"userId":userId},
        success:function (result) {
            couponArray = result;
            couponNum = couponArray.length;
            for(var i =0;i<couponArray.length;i++){
                var beginYear = new Date(couponArray[i].beginDate).getFullYear();
                var beginMonth = new Date(couponArray[i].beginDate).getMonth()+1;
                var beginDay = new Date(couponArray[i].beginDate).getDate();

                var endYear = new Date(couponArray[i].endDate).getFullYear();
                var endMonth = new Date(couponArray[i].endDate).getMonth()+1;
                var endDay = new Date(couponArray[i].endDate).getDate();

                couponTime = beginYear+"."+beginMonth+"."+beginDay+"--"+endYear+"."+endMonth+"."+endDay;

                switch (couponArray[i].type)
                {
                    case 1:
                        couponDes = "满100减"+couponArray[i].money;
                        break;
                    case 2:
                        couponDes = "满200减"+couponArray[i].money;
                        break;
                }

                if(couponArray[i].isUse){
                    var couponLi = document.createElement("li");
                    couponLi.innerHTML = "<p>"+couponTime+"</p>"+"<p>"+couponDes+"</p>"
                    usedUl.append(couponLi);
                }else {
                    var couponLi = document.createElement("li");
                    couponLi.innerHTML = "<p>"+couponTime+"</p>"+"<p>"+couponDes+"</p>"
                    activeUl.appendChild(couponLi);
                }

            }
        },
        error:function () {
            alert("getCoupon failed!");
        }

    });

}


/*查看个人优惠券*/
$(document).on(
    {
        click:function () {

            allHide();
            $('#seeMyCoupon').show();

        }
    },'#seeCoupon'

);


/*点击会员等级展示*/
$(document).on(
    {
        click:function () {
            allHide();
            $('#vipGradeFormDiv').show();

        }
    },'#vipGradeLi'

);

/*修改密码展示*/
$(document).on(
    {
        click:function () {
            allHide();
            $('#changePasswordDiv').show();

        }
    },'#changePasswordLi'

);

/*兑换优惠券展示*/
$(document).on(
    {
        click:function () {

            allHide();
            $('#myScoreP').html(vipScore);
            $('#getCouponDiv').show();


        }
    },'#converCouponLi'

);

/*兑换优惠券按钮*/
$(document).on(
    {
        click:function (node) {

            var tr1 = node.target.parentNode.parentNode;
            var description = tr1.cells[0].innerHTML;
            var useTime = tr1.cells[1].innerHTML;
            var needScore = tr1.cells[2].innerHTML;

            if(needScore > vipScore){ //会员积分不足
                alert("抱歉！ 您的积分不足");
            }else {
                 $('#confirmCoupon').modal();
                 convertCouponVO = {
                    "description": description,
                    "useTime": useTime,
                    "needScore": needScore,
                    "userId": userId
                };
            }


        }
    },'td button'

);

/*确认兑换优惠券*/
$(document).on(
    {
        click:function () {

            $.ajax({

                type:"POST",
                url:"/User/convertCoupon",
                contentType:'application/json;charset=utf-8',
                data:JSON.stringify(convertCouponVO),
                success:function (result) {
                    if(result.result == "SUCCESS"){
                        alert("兑换成功！");
                        window.location.href = "./userInfo.html";
                    }
                },
                error:function () {
                    alert("convertCoupon failed!");
                }
            });


        }
    },'#confirmConvert'

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

function allHide() {
    $('#userInfoDiv').hide();
    $('#vipGradeFormDiv').hide();
    $('#changePasswordDiv').hide();
    $('#seeMyCoupon').hide();
    $('#getCouponDiv').hide()
}
