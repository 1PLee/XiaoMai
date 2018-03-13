/**
 * Created by liyipeng on 2018/3/12.
 */

var venueName;
var perform;


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