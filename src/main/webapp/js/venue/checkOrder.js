/**
 * Created by liyipeng on 2018/3/11.
 */
var venueName;

$(document).ready(function () {

    venueName = sessionStorage.getItem("venueName");
    $('#venueNameSmallP').html(venueName);
    $('#venueNameBigP').html(venueName);

});


/*检票*/
$(document).on(
    {
        click:function () {
            var orderId = $('#orderId').val();

            $.ajax({

                type:"get",
                url:"/Venue/checkOrder",
                contentType:'application/json;charset=utf-8',
                data:{"orderId": orderId},
                success:function (result) {
                    if(result == "操作成功"){
                        alert("检票成功");
                        window.location.href = "./checkOrder.html";
                    }else {
                        alert(result);
                    }
                },
                error:function () {
                    alert("checkOrder failed!")
                }
            })



        }
    },'#checkOrderBtn'

);