/**
 * Created by liyipeng on 2018/2/23.
 */
var userID;
var password;
var loginVO;

$(document).ready(function () {

    userID = document.getElementById('userID');
    password = document.getElementById('password');
});

/*登录*/
$(document).on(
    {
        click:function () {
            loginVO = {
                "id": userID.value,
                "password": password.value
            }
            $.ajax({
                type:"post",
                url:"/User/login",
                contentType:'application/json;charset=utf-8',
                data: JSON.stringify(loginVO),
                success:function (result) {
                    switch (result.result){
                        case "SUCCESS":
                            sessionStorage.setItem("userID", userID.value);
                            window.location.href = "../welcome.html";
                            break;
                        case "NONE_USER":
                            alert("用户不存在！");
                            break;
                        case "WRONG_PASSWORD":
                            alert("密码错误！");
                            break;
                        default:
                            alert("网络错误 请等待");

                    }
                },
                error:function () {
                    alert("sorry is fail");
                }
            })

        }
    },'#login'

);