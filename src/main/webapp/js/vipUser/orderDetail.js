/**
 * Created by liyipeng on 2018/3/7.
 */
var orderVO;
var userId;
$(document).ready(function () {
    showUser();
    userId = sessionStorage.getItem("userID");
    orderVO = sessionStorage.getItem("orderDetail");
    orderVO = JSON.parse(orderVO);
    loadOrderDetail();

});

function loadOrderDetail() {
    var orderTimestamp = orderVO.orderTime;
    var date = new Date(orderTimestamp);
    var Y = date.getFullYear() + '-';
    var M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
    var D = date.getDate() + ' ';
    var h = date.getHours() + ':';
    var m = date.getMinutes() + ':';
    var s = date.getSeconds();

    $('#orderIdP').html(orderVO.orderId);
    $('#userIdP').html(userId);
    $('#performIdP').html(orderVO.performId);
    $('#performNameP').html(orderVO.performName);
    $('#ticketMoneyP').html(orderVO.ticketMoney);
    $('#ticketNumP').html(orderVO.ticketNum);

    if(orderVO.couponId != 0){
        $('#isUseCouponP').html("是")
    }else {
        $('#isUseCouponP').html("否")
    }

    $('#orderMoneyP').html(orderVO.orderMoney);
    switch (orderVO.orderType){
        case 0:
            $('#orderTypeP').html("待支付");
            break;
        case 1:
            $('#orderTypeP').html("未完成");
            break;
        case 2:
            $('#orderTypeP').html("已完成");
            break;
        case 3:
            $('#orderTypeP').html("无效");

            break;
        default:
            $('#orderTypeP').html("已退款"); // type == 4

    }

    $('#orderTimeP').html(Y+M+D+h+m+s);

    if(orderVO.backMoney == 0){
        $('#backMoneyP').html("无")
    }else {
        $('#backMoneyP').html(orderVO.backMoney);
    }


}

/*确认按钮*/
$(document).on(
    {
        click:function () {
            window.location.href = "./userInfo.html";
        }
    },'#knowBtn'

);

/*请求退款按钮*/
$(document).on(
    {
        click:function () {
           var orderType = $('#orderTypeP').html();
           if(orderType != "未完成"){
               alert("无效/已退款/已完成/待支付订单不能申请退款！");
           }else {
               $('#cancelOrderModal').modal();

           }

        }
    },'#askCancelOrder'

);

/*确认退款按钮*/
$(document).on(
    {
        click:function () {
            var orderId = $('#orderIdP').html();
            var orderMoney = parseFloat($('#orderMoneyP').html());
            var ticketMoney = $('#ticketMoneyP').html();
            var ticketNum = $('#ticketNumP').html();
            var performId = $('#performIdP').html();

            var backOrderVO = {

                "userId": userId,
                "orderId": orderId,
                "orderMoney": orderMoney,
                "ticketMoney": ticketMoney,
                "ticketNum": ticketNum,
                "performId": performId

            };

            $.ajax({
               type:"post",
                url:"/UserOrder/cancelOrder",
                contentType:'application/json;charset=utf-8',
                data:JSON.stringify(backOrderVO),
                success:function () {
                    alert("退款成功！");
                    window.location.href = "./userInfo.html"
                },
                error: function () {
                    alert("cancelOrder failed!");
                }

            });


        }
    },'#confirmCancelOrder'

);
