/**
 * Created by liyipeng on 2018/3/9.
 */

/*提交注册申请按钮*/
$(document).on(
    {
        click:function () {
            var venueName = $('#venueName').val();
            var location = $('#venueLocation').val();
            var capacity = $('#capacity').val();
            var venueMail = $('#venueMail').val();
            var description = $('#description').val();
            alert(venueName);

            var registerVO = {
                "name": venueName,
                "location": location,
                "description": description,
                "mail": venueMail,
                "capacity": capacity
            }

            $.ajax({
               type:'post',
                url:'/Venue/registerVenue',
                contentType:'application/json;charset=utf-8',
                data:JSON.stringify(registerVO),
                success: function (result) {
                   alert(result);
                    window.location.href = "./venueLogin.html";

                },
                error:function () {
                    alert("registerVenue failed!")
                }

            });


        }
    },'#submitRegister'

);