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