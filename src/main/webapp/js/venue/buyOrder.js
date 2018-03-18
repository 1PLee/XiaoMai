/**
 * Created by liyipeng on 2018/3/12.
 */

var venueName;
var perform;
var userInfoVO;

var orderMoney;//订单总价

$(document).ready(function () {

    venueName = sessionStorage.getItem("venueName");
    $('#venueNameSmallP').html(venueName);
    $('#venueNameBigP').html(venueName);
    loadOnSellPerform();

});

function loadOnSellPerform() {
    perform = new Array();


    $.ajax({
        type:"get",
        url:"/Venue/getOnSellPerform",
        contentType:'application/json;charset=utf-8',
        data:{"venue": venueName},
        success:function (result) {

            for(var i=0;i<result.length;i++){
                perform.push(result[i]);

                $('#nameSelect').append("<option>"+perform[i]["name"]+"</option>");
            }

        },
        error:function () {
            alert("getOnSellPerform failed!")
        }


    })


}


/*选择演出项目后加载票价信息*/
$('#nameSelect').change(function () {
    var selectPerformName = $('#nameSelect').val();
    alert(selectPerformName);
    var price = new Array();
    for(var i =0;i<perform.length;i++){
        if(perform[i]["name"] == selectPerformName){
            price = perform[i]["price"];
        }

    }

    $('#priceSelect').empty();
    for(var i =0;i<price.length;i++){
        $('#priceSelect').append("<option>" + price[i] + "</option>");
    }

});


/*查询用户是否为会员*/
$(document).on(
    {
        click:function () {
            var userId = $('#userId').val();


            $.ajax({

                type:"get",
                url:"/User/getUserInfo",
                contentType:'application/json;charset=utf-8',
                data:{"userId": userId},
                success:function (result) {
                    alert("用户存在");
                    userInfoVO = result['userInfo'];
                    if(userInfoVO.vipIsStop != 1){
                        $('#vipGrade').empty();
                        switch (userInfoVO.vipGrade){
                            case 1:
                                $('#vipGrade').append("<option>白银会员</option>");
                                break;
                            case 2:
                                $('#vipGrade').append("<option>黄金会员</option>");
                                break;
                            case 3:
                                $('#vipGrade').append("<option>钻石会员</option>");

                        }

                    }

                },
                error:function () {
                    alert("getUserInfo failed!")
                }
            })



        }
    },'#queryUser'

);

/*点击订单价格 出现价格*/
$(document).on(
    {
        click:function () {

            var ticketMoney = $('#priceSelect').val();
            var ticketNum = $('#ticketNumSelect').val();

            if($('#userId').val() == ""){
                orderMoney = ticketMoney * ticketNum;
            }else {
                var vipGrade = userInfoVO.vipGrade;
                switch (vipGrade){
                    case 1:
                        orderMoney = (0.95) * ticketMoney * ticketNum;
                        break;
                    case 2:
                        orderMoney = (0.88) * ticketMoney * ticketNum;
                        break;
                    case 3:
                        orderMoney = (0.8) * ticketMoney * ticketNum;
                }


            }

            $('#orderMoney').val(orderMoney);

        }
    },'#orderMoney'

);

/*创建订单*/
$(document).on(
    {
        click:function () {
            var ticketMoney = $('#priceSelect').val();
            var ticketNum = $('#ticketNumSelect').val();
            var performName = $('#nameSelect').val();
            var performId;
            var userId;
            for(var i=0;i<perform.length;i++){
                if(performName == perform[i]['name']){
                    performId = perform[i]['performID'];
                }
            }

            if($('#userId').val() == ""){
                userId = "Manager";
            }else {
                userId = $('#userId').val();
            }

            var orderVO = {
                "userId": userId,
                "performId":performId,
                "ticketNum":ticketNum,
                "ticketMoney":ticketMoney,
                "orderMoney":orderMoney
            };

            $.ajax({

                type:"post",
                url:"/Venue/buyTicketOnSite",
                contentType:'application/json;charset=utf-8',
                data:JSON.stringify(orderVO),
                success:function (result) {
                    if(result.result == "SUCCESS"){
                        alert("订单编号: "+result.orderId);
                        window.location.href = "./buyOrder.html";
                    }else {
                        alert("创建订单失败")
                    }

                },
                error:function () {
                    alert("buyTicketOnsite failed!")
                }
            })



        }
    },'#buyTickets'

);