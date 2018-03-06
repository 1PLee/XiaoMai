/**
 * Created by liyipeng on 2018/3/2.
 */
var userId;
var orderId;
$(document).ready(function () {
    showUser();
    orderId = parseInt(getQueryString("orderId"));
    alert(orderId);
    userId = sessionStorage.getItem("userID");
    var orderVO = sessionStorage.getItem("orderVO");
    orderVO = JSON.parse(orderVO);
    $('#payMoneyP').html(orderVO.orderMoney);

    countTime();
});


function countTime() {
    var dataTime = document.getElementById("timeCountLable");

    var time = dataTime.getAttribute("data-time");
    var mins = parseInt(time / 60);
    var seconds = time % 60;

    if(mins>0) {
        dataTime.innerHTML = mins + "分" + seconds + "秒";
    }else {
        dataTime.innerHTML = "0分" + seconds + "秒";
    }

    if(time>0) {
        dataTime.setAttribute("data-time", time - 1);
        setTimeout("countTime()", 1000);
    }else {
        window.location.href = "../welcome.html";
    }


}

/*确认支付*/
$(document).on(
    {
        click:function (e) {
            var userName = $('#userNameInput').val();
            var password = parseInt($('#pwdInput').val());
            var orderMoney = parseFloat($('#payMoneyP').html());

            var payOrderVO = {
                "userName": userName,
                "password": password,
                "orderMoney": orderMoney,
                "userId": userId,
                "orderId": orderId
            };

            $.ajax({
               type:"post",
                url:"/UserOrder/payOrder",
                contentType:'application/json;charset=utf-8',
                data: JSON.stringify(payOrderVO),
                success: function (result) {
                   alert(result);
                   window.location.href = "./userInfo.html";


                },
                error: function () {
                    alert("payOrder failed!")
                }

            });


        }
    },'#conformPay'

);