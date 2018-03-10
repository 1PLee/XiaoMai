/**
 * Created by liyipeng on 2018/3/9.
 */


$(document).on(
    {
        click:function () {

            var venueName = $('#venueName').val();
            var venueCode = $('#venueCode').val();
            var loginVO = {
                "name": venueName,
                "code": venueCode
            };

            $.ajax({
               type:"post",
                url:"/Venue/loginVenue",
                contentType:'application/json;charset=utf-8',
                data:JSON.stringify(loginVO),
                success: function (result) {
                    if(result == "操作成功"){
                        alert("登录成功");
                        sessionStorage.setItem("venueName", venueName);
                        window.location.href = "./venueWelcome.html";
                    } else {
                        alert("密码错误");
                    }

                },
                error:function () {
                    alert("loginVenue failed!")
                }

            });


        }
    },'#loginBtn'

);