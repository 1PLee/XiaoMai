/**
 * Created by liyipeng on 2018/3/19.
 */

$(document).ready(function () {

    loadNewVenueList();

});



function loadNewVenueList() {

    var newVenueList = new Array();

    $.ajax({
        type:"get",
        url:"/Manager/showVenueList",
        contentType:'application/json;charset=utf-8',
        data: {"venueType": 0},
        success:function (result) {
            newVenueList = result;
            for(var i = 0;i<newVenueList.length;i++){

                $('#newVenueSection').append("<div class='box box-info'>" +
                    "<div class='box-header'>"+newVenueList[i]["name"]+"</div>" +
                    "<div class='box-body'>" +
                    "<p>场馆位置:"+newVenueList[i]["location"]+"</p>" +
                    "<p>容纳人数:"+newVenueList[i]["capacity"]+"</p>" +
                    "<p>联系邮箱:"+newVenueList[i]["mail"]+"</p>" +
                    "<p>场馆描述:"+newVenueList[i]["description"]+"</p>" +
                    "<button type='button' class='btn btn-success btn-box-tool agreeCheck' data-widget='remove' data-toggle='tooltip' title='Remove' id='agree"+i+"'>通过审批</button>" +
                    "<button type='button' class='btn btn-danger btn-box-tool rejectCheck' data-widget='remove' data-toggle='tooltip' title='Remove' id='reject"+i+"'>拒绝申请</button>" +
                    "</div>" +
                    "</div>");

                document.getElementById("agree"+i).addEventListener("click", function (e) {

                    var boxBody = e.target.parentNode;
                    var boxHeader = boxBody.previousSibling;
                    var venueName = boxHeader.innerHTML;
                    agreeVenue(venueName);
                });

                document.getElementById("reject"+i).addEventListener("click", function (e) {
                    var boxBody = e.target.parentNode;
                    var boxHeader = boxBody.previousSibling;
                    var venueName = boxHeader.innerHTML;
                    rejectVenue(venueName);
                });

                 /*   document.getElementsByClassName("agreeCheck")[i].addEventListener("click",function () {
                        var boxBody = document.getElementsByClassName("agreeCheck")[i].parentNode;
                        var boxHeader = boxBody.previousSibling;
                        alert(boxHeader.innerHTML);
                    })*/

            }



        },
        error:function () {
            alert("showVenueList failed!");
        }


    })


}


/*同意场馆申请*/
function agreeVenue(venueName) {

    $.ajax({
        type:"get",
        url:"/Manager/checkVenueRegister",
        contentType:'application/json;charset=utf-8',
        data:{"venue": venueName, "action":1},
        success:function (result) {
            alert(result);
        },
        error:function () {
            alert("checkVenueRegister failed!");
        }




    })

}

/*拒绝场馆申请*/
function rejectVenue(venueName) {

    $.ajax({
        type:"get",
        url:"/Manager/checkVenueRegister",
        contentType:'application/json;charset=utf-8',
        data:{"venue": venueName, "action":0},
        success:function (result) {
            alert(result);
        },
        error:function () {
            alert("checkVenueRegister failed!");
        }




    })
}

