/**
 * Created by liyipeng on 2018/3/2.
 */


$(document).ready(function () {
    showUser();

    var orderVO = sessionStorage.getItem("orderVO");
    orderVO = JSON.parse(orderVO);

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