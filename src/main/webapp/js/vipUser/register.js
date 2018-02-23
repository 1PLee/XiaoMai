/**
 * Created by liyipeng on 2018/2/18.
 */

var registerVO;

var userName;
var password;
var userMail;
var mailCode;//验证码


/*注册按钮*/
$(document).on(
    {
        click:function () {


            userName = document.getElementById('userName');
            password = document.getElementById('password');
            userMail = document.getElementById('userMail');
            mailCode = document.getElementById('checkCode');

            registerVO = {
                "id":userName.value,
                "password": password.value,
                "mail": userMail.value,
                "mailCode": mailCode.value


            };


            $.ajax({
                type:'post',
                url:'/User/register',
                contentType:'application/json;charset=utf-8',
                data:JSON.stringify(registerVO),
                success: function (result) {

                    switch (result.result){
                        case "SUCCESS":
                            window.location.href = "Login.html";
                            break;
                        case "FAILURE_WRONGMAILCODE":
                            alert("验证码有误");
                            break;
                        case "USER_REPEATED":
                            alert("用户已经存在");
                            break;
                        case "FAILURE_MAILREPEATED":
                            alert("邮箱已经被使用");
                            break;
                        default:
                            alert("注册失败！请重试");
                            break;
                    }
                },
                error:function () {
                    alert("register fail");
                }

            })

        }
    },'#submitUser'

);

/*点击邮箱输入框，验证码按钮变为可用状态*/
$(document).on(
    {
        click:function () {
            $('#sendMail').removeClass("disabled")

        }
    },'#userMail'

);

/*点击验证码输入框，注册按钮变为可用状态*/
$(document).on(
    {
        click:function () {
            $('#submitUser').removeClass("disabled")

        }
    },'#checkCode'

);

/*点击发送验证码按钮*/
$(document).on(
    {
        click:function () {

            $('#sendMail').hide();

            $('#sendMail_Reset').show();


            count_timer();

            userName = document.getElementById('userName');
            userMail = document.getElementById('userMail');

            $.ajax({
                type:'get',
                url:'/User/sendMailCode',
                data:{"userID":userName.value, "mail":userMail.value},
                success:function(result){
                    //alert("method success!")
                },
                error:function(){
                    alert("un success!")
                }

            })

        }
    },'#sendMail'

);

function count_timer() {
    var second = 20;
    var timer = null;



    timer = setInterval(function(){
        second -= 1;
        if(second > 0){
            $('#second').html(second);
        }else {
            clearInterval(timer);
            $('#sendMail').show();
            $('#sendMail_Reset').hide();
        }

    },1000);


}

