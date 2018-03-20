/**
 * Created by liyipeng on 2018/2/17.
 */
var performId;
var name;
var time;
var venue;
var priceUl;//价格
var selectPrice;//选中的价格
var descriptionP;//演出描述
var allPrice; //演出所有的价格区间
var allSeat;  //演出的每个价格对应座位数量

var selectSeat;//选择的是哪个等级的座位

$(document).ready(function () {
    selectNum = 0;
    loadPerform();
    showUser();

    $('#selectNumDiv').hide();
});

/*加载表演的各种信息*/
function loadPerform() {
    priceUl = document.getElementById("price");
    descriptionP = document.getElementById("descriptionP");

    performId = sessionStorage.getItem("performId");

    allPrice = new Array();
    allSeat = new Array;

    /*得到表演的基本信息*/
    $.ajax({

        type:"get",
        url:"/Perform/getPerformInfo",
        contentType:'application/json;charset=utf-8',
        data:{"performId":performId},
        success:function (result) {

            allPrice = result.price;
            allSeat = result.seat;


            for(var i = 0;i<allPrice.length;i++){
                if(allPrice[i] == null){
                    break;
                }
                var priceLi = document.createElement("li");

                priceUl.append(priceLi);
                if(allSeat[i] == 0){

                    priceLi.setAttribute("id", "price" + i +"Out");
                }else {

                    priceLi.setAttribute("id", "price" + i);
                }
                priceLi.innerHTML = allPrice[i];


            }

        },
        error:function () {
            alert("getPerformInfo failed!");
        }


    });

    /*得到表演的描述*/
    $.ajax({

        type:"get",
        url:"/Perform/getDescription",
        contentType:'application/json;charset=utf-8',
        data:{"performID":performId},
        success:function (result) {
            $('#descriptionP').html(result.description);
        },
        error:function () {
            alert("getDescription failed");
        }

    });


    name = sessionStorage.getItem("performName");
    time = sessionStorage.getItem("performTime");
    venue = sessionStorage.getItem("performVenue");



    $('#performName').html(name);
    $('#timeP').html(time);
    $('#venueP').html(venue);


    switch (performId)      //更换不同的img
    {
        case ("139423"):
            loadImage(139423);
            break;
        case ("142049"):
            loadImage(142049);
            break;
        case ("142313"):
            loadImage(142313);
            break;
        case ("143099"):
            loadImage(143099);
            break;
        default:
            loadImage(142313);

    }





}

/*加载表演的图片信息*/
function loadImage(id) {

    $('#performImage').attr("src", "../images/" + id + "_n.jpg");
}


/*选择价格菜单栏*/
$(document).on(
    {
        click:function (e) {
            $('#price li').removeClass("slectedLi");
            var liID = e.target.id;
            if(liID.indexOf("Out") == -1){
                $('#' + liID).addClass("slectedLi");
                selectPrice = $('#'+liID).html();
                for(var i =0; i<allPrice.length; i++){
                    if(selectPrice == allPrice[i]){
                        selectSeat = i + 1;
                        break;
                    }
                }
            }


        }
    },'#price'

);


$(document).on(
    {
        click:function (e) {
            if(e.target.id.indexOf("Out") > -1) {
                alert("抱歉 票已经卖完了")
            }
        }
    },'#price li'

);



/*买票*/
$(document).on(
    {
        click:function () {
            var isLogin = showUser();
            var ticketNum;
            var buyTicketsVO;
            if(isLogin){ //有用户登录

                var userId = sessionStorage.getItem("userID");

                buyTicketsVO = {  //如果selectPrice为 null 代表没有选座
                    "userId": userId,
                    "ticketMoney": selectPrice,
                    "performMinMoney": allPrice[0],// 非选座购票默认最低价
                    "selectSeat": selectSeat
                };


                sessionStorage.setItem("buyTicketsVO", JSON.stringify(buyTicketsVO));

                window.location.href = "vipUser/buyTickets.html";

            }else {
                alert("请先登录账号！");
            }

        }
    },'#buyTickets'

);