/**
 * Created by liyipeng on 2018/3/1.
 */

/*在之前加载了 userInfo.js 得到user的基本信息和user所拥有的优惠券信息*/
/*performName和performTime从welcome.js中存储的sessionStorage中获取*/
var performId;
var performName;
var performTime;
var ticketMoney;
var ticketNum;
var ticketNumMax;
var buyTicketsVO;
var ticketSeat;// 座位等级
var vipDiscount;//享受的vip折扣
var vipMoney;

var totalMoney;//最终的付款金额
var useCouponId; //使用的优惠券id

var userId;


$(document).ready(function () {
    userId = sessionStorage.getItem("userID");
    ticketNum = 1;
    buyTicketsVO = sessionStorage.getItem("buyTicketsVO");
    buyTicketsVO = JSON.parse(buyTicketsVO);


    if(buyTicketsVO.ticketMoney == null){ //非选座购买
        ticketNumMax = 20;
        ticketMoney = buyTicketsVO.performMinMoney;
        ticketSeat = 1; //默认为最低级的座位进行配票
    }else {
        ticketNumMax = 6;
        ticketMoney = buyTicketsVO.ticketMoney;
        ticketSeat = buyTicketsVO.selectSeat;
    }
    showUser();
    loadOrderInfo();
    showOrderMoney();
    loadDiscount();  //初次加载折扣后的信息
    loadCouponUl(); //加载优惠券的信息


});

function loadOrderInfo() {

    performId = sessionStorage.getItem("performId");
    performName = sessionStorage.getItem("performName");
    performTime = sessionStorage.getItem("performTime");

    $('#performNameP').html(performName);
    $('#performTimeP').html(performTime);


}


/*加载折扣金额信息*/
function loadDiscount() {

    if(vipDiscount == null){ //初次加载
        var vipGrade;

        $.ajax({
            type:"get",
            url:"/User/getUserInfo",
            contentType:'application/json;charset=utf-8',
            data:{"userId":userId},
            success:function (result) {
                var urInfo = result.userInfo;
                vipGrade = urInfo.vipGrade;
                switch (vipGrade) {
                    case 1:
                        $('#personVip').html("白银会员(95%)");
                        vipDiscount = 0.95;
                        break;
                    case 2:
                        $('#personVip').html("黄金会员(88%)");
                        vipDiscount = 0.88;
                        break;
                    case 3:
                        $('#personVip').html("钻石会员(80%)");
                        vipDiscount = 0.8;
                        break;
                }
                vipMoney = ticketNum * ticketMoney * vipDiscount;
                totalMoney = vipMoney;
                loadCouponUl();

                $('#endPay').html(ticketMoney + " * " + ticketNum + " * " + vipDiscount + " = " + vipMoney);
            },
            error:function () {
                alert('getUserInfo failed');
            }

        });

    }else { //根据票数的不同动态加载金额
        vipMoney = ticketNum * ticketMoney * vipDiscount;
        totalMoney = vipMoney;
        useCouponId = null; //将优惠券信息清空
        loadCouponUl();
        $('#endPay').html(ticketMoney + " * " + ticketNum + " * " + vipDiscount + " = " + vipMoney);
    }


}

function loadCouponUl() {

    $('#useUl').empty(); //先清空已有的优惠券信息
    var couponUl = document.getElementById("useUl");

    var couponArray;

            $.ajax({

                type:"get",
                url:"/User/getCoupon",
                contentType:'application/json;charset=utf-8',
                data:{"userId":userId},
                success:function (result) {
                    couponArray = result;

                    for(var i = 0;i<couponArray.length;i++) {
                        if(!(couponArray[i].isUse)){ //未使用过
                            if((vipMoney/100)>=couponArray[i].type) { //符合满减规则
                                var useLi = document.createElement("li");
                                useLi.setAttribute("id", couponArray[i].couponID);
                                useLi.innerHTML = "满"+couponArray[i].type+"00减"+couponArray[i].money;
                                couponUl.append(useLi);
                            }
                        }
                    }

                },
                error:function () {
                    alert("getCoupon failed!");
                }

            });

}


/*根据票数的改变显示订单金额(非实付金额)*/
function showOrderMoney() {
    var orderMoney = 0;
    orderMoney = ticketNum * ticketMoney;

    $('#moneyTotal').html(ticketMoney + " * " + ticketNum + " = " + orderMoney);

}



/*点击选择优惠券 并相应改变应付金额*/
$(document).on(
    {
        click:function (e) {
            var couponInfo = e.target.innerHTML.split("减");
            totalMoney = vipMoney - parseInt(couponInfo[1]);
            useCouponId = e.target.id;

            $('#useCoupon').html(e.target.innerHTML+"<span class=\"caret\"></span>");

            $('#endPay').html(ticketMoney + " * " + ticketNum + " * " + vipDiscount + " - " + couponInfo[1] + " = " + totalMoney);
        }
    },'.dropdown-menu li'

);

/*取消订单*/
$(document).on(
    {
        click:function () {
            window.location.href = "../welcome.html";
        }
    },'#confirmCancelOrder'

);

/*增加票数*/
$(document).on(
    {
        click:function (e) {

            if(ticketNum == ticketNumMax){
                alert("已达到票数上限！");
            }else {
                ticketNum++;
                $('#selectNumP').html(ticketNum);
                $('#useCoupon').empty(); //清空应用的优惠券
                showOrderMoney();
                loadDiscount();
            }
        }
    },'#jiahao'

);

/*减少票数*/
$(document).on(
    {
        click:function (e) {
            if(ticketNum == 1){

            }else {
                ticketNum --;
                $('#selectNumP').html(ticketNum);
                $('#useCoupon').empty(); //清空应用的优惠券
                showOrderMoney();
                loadDiscount();
            }
        }
    },'#jianhao'

);


/*确认进入支付界面*/
$(document).on(
    {
        click:function () {
            var orderVO;
            if(useCouponId == null){
                useCouponId = 0;
            }

            orderVO = {
                "userId":userId,
                "couponId":useCouponId,
                "performId":performId,
                "ticketSeat": ticketSeat,
                "ticketNum":ticketNum,
                "ticketMoney":ticketMoney,
                "orderMoney":totalMoney
            };


            /*在后台创建订单*/
            $.ajax({
               type:"post",
                url:"/UserOrder/createOrder",
                contentType:'application/json;charset=utf-8',
                data:JSON.stringify(orderVO),
                success:function (result) {
                    alert(result.result);
                    sessionStorage.setItem("orderVO", JSON.stringify(orderVO));
                    window.location.href = "./payOrder.html?orderId="+result.orderId;
                },
                error:function () {
                    alert("createOrder failed!")
                }

            });




        }
    },'#confirmPay'

);
