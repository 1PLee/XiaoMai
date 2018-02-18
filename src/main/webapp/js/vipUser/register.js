/**
 * Created by liyipeng on 2018/2/18.
 */

var registerVO;
var userName;
var password;
var userMail;


/*注册按钮*/
$(document).on(
    {
        click:function () {

            userName = document.getElementById('userName')
            password = document.getElementById('password')
            userMail = document.getElementById('userMail')

            registerVO = {
              "userName":userName.value,
               "password": password.value,
                "userMail": userMail.value

            };


            $.ajax({
                type:'post',
                url:'/User/Register',
                contentType:'application/json;charset=utf-8',

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